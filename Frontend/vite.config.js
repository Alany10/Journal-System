import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import fs from 'fs';

// HTTPS-konfiguration
export default defineConfig({
  plugins: [react()],
  server: {
    https: {
      key: fs.readFileSync('./src/resources/key.pem'),
      cert: fs.readFileSync('./src/resources/cert.pem'),
    },
    host: 'localhost', // Använd 'localhost' eller specificera en IP om nödvändigt
    port: 8000,        // Ändra till önskad port om det behövs
  },
});
