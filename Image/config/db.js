const mysql = require('mysql2');

const db = mysql.createConnection({
    host: 'image-db-service',        // Din databasvärd, vanligtvis localhost om den körs på samma maskin
    user: 'journal_user',     // Din MySQL-användare
    password: 'journal321',   // Ditt lösenord
    database: 'image',        // Namnet på databasen
    port: 3310                // Ange porten som din MySQL-databas kör på
});

db.connect((err) => {
    if (err) {
        console.error('Database connection failed:', err);
        process.exit(1);
    }
    console.log('Database connected!');
});

module.exports = db;
