const express = require('express');
const multer = require('multer');
const db = require('../config/db');
const router = express.Router();

// Multer för filuppladdning
const storage = multer.memoryStorage(); // Lagras temporärt i minnet
const upload = multer({ storage });

// POST /upload - Ladda upp en bild
router.post('/upload', upload.single('image'), (req, res) => {
    const { originalname } = req.file;
    const base64Data = req.file.buffer.toString('base64');

    const query = 'INSERT INTO image (name, data) VALUES (?, ?)';
    db.query(query, [originalname, base64Data], (err, result) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Failed to upload image' });
        }
        res.status(201).json({ id: result.insertId, message: 'Image uploaded successfully' });
    });
});

// GET /image/:id - Hämta en bild baserat på ID
router.get('/get/:id', (req, res) => {
    const { id } = req.params;

    const query = 'SELECT * FROM image WHERE id = ?';
    db.query(query, [id], (err, results) => {
        if (err || results.length === 0) {
            console.error(err);
            return res.status(404).json({ error: 'Image not found' });
        }

        const image = results[0];
        res.json({
            name: image.name,
            data: `data:image/jpeg;base64,${image.data}`, // Bilden som base64-sträng
        });
    });
});


// GET /images - Hämta alla bilder (namn och ID)
router.get('/getAll', (req, res) => {
    const query = 'SELECT id, name FROM image'; // Välj bara ID och namn
    db.query(query, (err, results) => {
        if (err) {
            console.error(err);
            return res.status(500).json({ error: 'Failed to fetch images' });
        }
        res.json(results); // Skicka tillbaka en lista med bilder (ID och namn)
    });
});


module.exports = router;

