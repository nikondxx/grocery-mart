# Use Node.js 20.19.3 as the base image
FROM node:20.19.3-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json (if available)
COPY package*.json ./

# Install all dependencies (not just production)
RUN npm ci

# Copy the rest of the application code
COPY . .

# Expose the port the app runs on (e.g., 3000 or 5173 for Vite)
EXPOSE 3000

# Start the development server (e.g., for Vite or React)
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]
