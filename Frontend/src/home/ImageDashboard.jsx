import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

const ImageDashboard = () => {
    const [userRole, setUserRole] = useState(null); // State för att lagra användarens roll
    const [image, setImage] = useState(null);
    const [uploadedImages, setUploadedImages] = useState([]);
    const [selectedImage, setSelectedImage] = useState(null);
    const [loading, setLoading] = useState(false);
    const [uploading, setUploading] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const canvasRef = useRef(null);
    const ctxRef = useRef(null);
    const [draw, setDraw] = useState(false);
    const [lastPos, setLastPos] = useState({ x: 0, y: 0 });
    const [text, setText] = useState("");
    const [textPos, setTextPos] = useState({ x: 0, y: 0 });
    const [isAddingText, setIsAddingText] = useState(false);

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user) {
            setUserRole(user.role);
        }
    }, []); // Lägg till ett tomt beroende för att köra detta bara vid mount

    const fetchUploadedImages = async () => {
        try {
            const response = await axios.get('http://localhost:3000/image/getAll');
            setUploadedImages(response.data);
        } catch (error) {
            console.error('Error fetching images', error);
        }
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);
        }
    };

    const handleImageUpload = async () => {
        if (!image) {
            alert('Please select an image first!');
            return;
        }

        setUploading(true);
        const formData = new FormData();
        formData.append('image', image);

        try {
            const response = await axios.post('http://localhost:3000/image/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            alert('Image uploaded successfully!');
            fetchUploadedImages();
        } catch (error) {
            console.error('Error uploading image', error);
            alert('Error uploading image');
        } finally {
            setUploading(false);
        }
    };

    const fetchSelectedImage = async (id) => {
        setLoading(true);
        try {
            const response = await axios.get(`http://localhost:3000/image/get/${id}`);
            setSelectedImage(response.data);
        } catch (error) {
            console.error('Error fetching image', error);
        } finally {
            setLoading(false);
        }
    };

    const closeModal = () => {
        setSelectedImage(null);
        setIsEditing(false);
        setIsAddingText(false);
        setText("");
    };

    const handleEdit = () => {
        setIsEditing((prev) => !prev);
    };

    const handleAddText = (e) => {
        if (!isEditing) return;
        setIsAddingText(true);
        const rect = canvasRef.current.getBoundingClientRect();
        setTextPos({ x: e.clientX - rect.left, y: e.clientY - rect.top });
    };

    const handleMouseDown = (e) => {
        if (!isEditing) return;

        setDraw(true);
        const rect = canvasRef.current.getBoundingClientRect();
        setLastPos({ x: e.clientX - rect.left, y: e.clientY - rect.top });
    };

    const handleMouseMove = (e) => {
        if (!draw || !isEditing) return;

        const rect = canvasRef.current.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;

        ctxRef.current.beginPath();
        ctxRef.current.moveTo(lastPos.x, lastPos.y);
        ctxRef.current.lineTo(x, y);
        ctxRef.current.stroke();

        setLastPos({ x, y });
    };

    const handleMouseUp = () => {
        if (!isEditing) return;
        setDraw(false);
    };

    const handlePlaceText = () => {
        if (text.trim() === "") return;
        const ctx = ctxRef.current;
        ctx.font = "20px Arial";
        ctx.fillStyle = "black";
        ctx.fillText(text, textPos.x, textPos.y);
        setText("");
        setIsAddingText(false);
    };

    const handleSave = async () => {
        const canvas = canvasRef.current;
        const editedImage = canvas.toDataURL('image/png');
        const formData = new FormData();
        formData.append('image', dataURItoBlob(editedImage), 'editedImage.png');

        try {
            setUploading(true);
            await axios.post('http://localhost:30004/image/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            alert('Edited image saved successfully!');
            fetchUploadedImages();
        } catch (error) {
            console.error('Error saving edited image', error);
            alert('Error saving edited image');
        } finally {
            setUploading(false);
        }
    };

    const dataURItoBlob = (dataURI) => {
        const byteString = atob(dataURI.split(',')[1]);
        const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
        const ab = new ArrayBuffer(byteString.length);
        const ia = new Uint8Array(ab);
        for (let i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], { type: mimeString });
    };

    useEffect(() => {
        fetchUploadedImages();
    }, []);

    useEffect(() => {
        if (selectedImage) {
            const canvas = canvasRef.current;
            const ctx = canvas.getContext('2d');
            ctxRef.current = ctx;

            const img = new Image();
            img.src = selectedImage.data;
            img.onload = () => {
                const maxWidth = 600;
                const scaleFactor = Math.min(maxWidth / img.width, 1);
                const scaledWidth = img.width * scaleFactor;
                const scaledHeight = img.height * scaleFactor;

                canvas.width = scaledWidth;
                canvas.height = scaledHeight;
                ctx.drawImage(img, 0, 0, scaledWidth, scaledHeight);
            };
        }
    }, [selectedImage]);

    return (
        <div>
            <h2>Image Dashboard</h2>
            <div>
                <input type="file" onChange={handleImageChange} />
                <button onClick={handleImageUpload} disabled={uploading}>
                    {uploading ? 'Uploading...' : 'Upload Image'}
                </button>
            </div>
            <div>
                <h3>Uploaded Images</h3>
                {uploadedImages.length > 0 ? (
                    <ul>
                        {uploadedImages.map((image) => (
                            <li key={image.id}>
                                <button onClick={() => fetchSelectedImage(image.id)}>
                                    {image.name}
                                </button>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No images uploaded yet.</p>
                )}
            </div>
            {selectedImage && (
                <div style={modalStyle.overlay}>
                    <div style={modalStyle.modal}>
                        <h3>{selectedImage.name}</h3>
                        {loading ? (
                            <p>Loading...</p>
                        ) : (
                            <div>
                                <canvas
                                    ref={canvasRef}
                                    style={modalStyle.canvas}
                                    onMouseDown={handleMouseDown}
                                    onMouseMove={handleMouseMove}
                                    onMouseUp={handleMouseUp}
                                    onClick={handleAddText}
                                />
                                {isAddingText && (
                                    <div>
                                        <input
                                            type="text"
                                            value={text}
                                            onChange={(e) => setText(e.target.value)}
                                            placeholder="Enter text"
                                        />
                                        <button onClick={handlePlaceText}>Place Text</button>
                                    </div>
                                )}
                                <div>
                                    {userRole === 'doctor' && (
                                        <button
                                            onClick={handleEdit}
                                            style={modalStyle.editButton}
                                        >
                                            {isEditing ? 'Stop Edit' : 'Edit'}
                                        </button>
                                    )}
                                    {isEditing && (
                                        <button
                                            onClick={handleSave}
                                            style={modalStyle.saveButton}
                                        >
                                            Save
                                        </button>
                                    )}
                                    <button onClick={closeModal} style={modalStyle.closeButton}>
                                        Close
                                    </button>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

const modalStyle = {
    overlay: {
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        zIndex: 1000,
    },
    modal: {
        backgroundColor: 'white',
        padding: '10px',
        borderRadius: '10px',
        maxWidth: '80%',
        maxHeight: '80%',
        overflow: 'auto',
        textAlign: 'center',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    canvas: {
        border: '1px solid black',
        marginBottom: '10px',
    },
    editButton: {
        padding: '10px 20px',
        marginRight: '10px',
        backgroundColor: 'blue',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
    saveButton: {
        padding: '10px 20px',
        marginRight: '10px',
        backgroundColor: 'green',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
    closeButton: {
        padding: '10px 20px',
        backgroundColor: 'red',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
};

export default ImageDashboard;
