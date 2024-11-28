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

// Starta servern
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Image service running on http://localhost:${PORT}`);
});
