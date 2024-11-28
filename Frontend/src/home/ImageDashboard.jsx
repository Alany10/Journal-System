import React, { useState, useEffect } from 'react';
import axios from 'axios'; // För att göra API-anrop

const ImageDashboard = () => {
    const [image, setImage] = useState(null); // För att hålla den valda bilden för uppladdning
    const [uploadedImages, setUploadedImages] = useState([]); // Håller uppladdade bilder (id, name)
    const [selectedImage, setSelectedImage] = useState(null); // Håller den valda bilden
    const [loading, setLoading] = useState(false); // För att visa laddningsstatus
    const [uploading, setUploading] = useState(false); // För att visa laddning under uppladdning

    // Funktion för att hämta alla uppladdade bilder
    const fetchUploadedImages = async () => {
        try {
            const response = await axios.get('http://localhost:3000/image/getAll'); // API-anrop för att hämta alla bilder
            setUploadedImages(response.data); // Sätt de uppladdade bilderna i state
        } catch (error) {
            console.error('Error fetching images', error);
        }
    };

    // Funktion för att hantera val av bild för uppladdning
    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);
        }
    };

    // Funktion för att ladda upp en bild
    const handleImageUpload = async () => {
        if (!image) {
            alert('Please select an image first!');
            return;
        }

        setUploading(true); // Starta uppladdning
        const formData = new FormData();
        formData.append('image', image); // Lägg till bilden i formData

        try {
            const response = await axios.post('http://localhost:3000/image/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            alert('Image uploaded successfully!');
            fetchUploadedImages(); // Hämta uppladdade bilder igen
        } catch (error) {
            console.error('Error uploading image', error);
            alert('Error uploading image');
        } finally {
            setUploading(false); // Sluta ladda
        }
    };

    // Funktion för att hämta den valda bildens data
    const fetchSelectedImage = async (id) => {
        setLoading(true); // Starta laddning
        try {
            const response = await axios.get(`http://localhost:3000/image/get/${id}`); // Hämta bilden från backend baserat på ID
            setSelectedImage(response.data); // Sätt den hämtade bilden i state
        } catch (error) {
            console.error('Error fetching image', error);
        } finally {
            setLoading(false); // Sluta ladda
        }
    };

    // Funktion för att stänga modalen
    const closeModal = () => {
        setSelectedImage(null);
    };

    // Hämta bilder när komponenten laddas
    useEffect(() => {
        fetchUploadedImages();
    }, []);

    return (
        <div>
            <h2>Image Dashboard</h2>

            {/* Formulär för att ladda upp bilder */}
            <div>
                <input type="file" onChange={handleImageChange} />
                <button onClick={handleImageUpload} disabled={uploading}>
                    {uploading ? 'Uploading...' : 'Upload Image'}
                </button>
            </div>

            {/* Lista alla uppladdade bilder */}
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

            {/* Modal för att visa den valda bilden */}
            {selectedImage && (
                <div style={modalStyle.overlay}>
                    <div style={modalStyle.modal}>
                        <h3>{selectedImage.name}</h3>
                        {loading ? (
                            <p>Loading...</p>
                        ) : (
                            <img
                                src={selectedImage.data} // Visa base64-kodad bild
                                alt={selectedImage.name}
                                style={modalStyle.image} // Justera bildens storlek
                            />
                        )}
                        <button onClick={closeModal} style={modalStyle.closeButton}>
                            Close
                        </button>
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
        flexDirection: 'column', // Använd column layout för att stapla bilden och knappen vertikalt
        alignItems: 'center', // Centrera innehållet
    },
    image: {
        maxWidth: '600px', // Begränsa maxbredden på bilden
        maxHeight: '400px', // Begränsa maxhöjden på bilden
        width: 'auto', // Behåll bildens proportioner
        height: 'auto', // Behåll bildens proportioner
    },
    closeButton: {
        marginTop: '20px', // Skapa mellanrum mellan bilden och knappen
        padding: '10px 20px',
        backgroundColor: 'red',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
    },
};

export default ImageDashboard;
