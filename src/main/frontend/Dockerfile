# Pull the latest node alpine image
FROM node:alpine

# Set the working directory in the container
WORKDIR /app

# Install dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install

# Move source code to the current working directory
COPY ./ ./

# Run the application
CMD ["npm", "start"]