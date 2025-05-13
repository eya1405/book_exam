# Étape 1: image de base
FROM node:18-alpine

# Étape 2: répertoire de travail
WORKDIR /app

# Étape 3: copier les fichiers
COPY . .

# Étape 4: installer serve
RUN npm install -g serve

# Étape 5: exposer le port 3000
EXPOSE 3000

# Étape 6: lancer le serveur
CMD ["serve", ".", "-l", "3000"]
