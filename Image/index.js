const fs = require('fs');
const https = require('https');
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const imageRoutes = require('./routes/imageRoutes');

const app = express();

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// API-routes
app.use('/image', imageRoutes);

// Läsa in certifikatfiler
const privateKey = fs.readFileSync('./resources/key.pem', 'utf8');
const certificate = fs.readFileSync('./resources/cert.pem', 'utf8');
const credentials = { key: privateKey, cert: certificate };

// Ändra port till 8004
const PORT = 8004;

// Starta HTTPS-servern
https.createServer(credentials, app).listen(PORT, () => {
    console.log(`Image service running on https://localhost:${PORT}`);
});
