# Steg 1: Använd en officiell Node.js-bild som bas
FROM node:18-alpine

# Steg 2: Sätt arbetskatalogen i containern
WORKDIR /usr/src/app

# Steg 3: Kopiera package.json och package-lock.json (om de finns)
COPY package*.json ./

# Steg 4: Installera Node.js-bibliotek (beroenden)
RUN npm install

# Steg 5: Kopiera resten av applikationen till containern
COPY . .

# Steg 6: Exponera porten som applikationen kommer att lyssna på
EXPOSE 3000

# Steg 7: Starta applikationen
CMD ["node", "index.js"]
