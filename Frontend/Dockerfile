# 1. Använd en Node.js-bild som bas
FROM node:18

# 2. Sätt arbetskatalogen i containern
WORKDIR /app

# 3. Kopiera projektfiler till containern
COPY . .

# 4. Installera beroenden
RUN npm install

# 5. Exponera port 5173 (detta är standardporten för Vite)
EXPOSE 5173

# 6. Ange miljövariabeln för att tillåta extern åtkomst
ENV HOST=0.0.0.0

# 7. Starta Vite-servern i utvecklingsläge
CMD ["npm", "run", "dev"]

