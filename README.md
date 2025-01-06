# Multiplatform Crypto Wallet (SmartPesa)

## Overview
**SmartPesa** is an innovative, Kotlin Multiplatform (KMP)-based open-source project designed to revolutionize cryptocurrency wallet management across multiple platforms. By leveraging advanced KMP technology, SmartPesa ensures a seamless and secure experience for users, enabling effortless transactions by simply scanning a QR code.

### Key Features
- **Error-Free Transactions**: Avoid wallet address errors with QR code-based transactions.
- **Enhanced Security**: Mitigate wallet poisoning attacks in crypto markets.
- **User-Friendly**: Designed for both crypto newcomers and seasoned users.
- **Financial Inclusion**: Focused on empowering unbanked and underbanked populations, particularly in Sub-Saharan Africa.

### Components
SmartPesa comprises:
1. A **Ktor-based server** integrating with **Circle.io** for:
    - Programmable wallets
    - Transaction processing
    - Wallet management
2. A **Kotlin Multiplatform Android application** for seamless mobile wallet management.
3. A **desktop application** offering extended functionality and accessibility.

---

## Ktor Server Setup

### Local Environment Setup
To configure the Ktor server locally:

1. Create a `.env` file in the root directory of the server module and add the following configurations:

```env
# Circle Programmable Wallets Configuration
API_KEY=TEST_API_KEY             # Replace with Circle's test API key
ENTITY_SECRETE=circle_entity_secret

# JWT Configuration
JWT_SECRETE=your_own_jwt_secret

# MongoDB Configuration
MONGODBUSERNAME=your_mongodb_username
MONGODBPASSWORD=your_mongodb_password
DATABASENAME=your_mongodb_database_name
```
2. Add the .env file variables to your development environment (e.g., IntelliJ IDEA or Android Studio).
3. Run the server application. The server will start on port 8081.
4. Create mongodb database from mongodb atlas for free and quick setup or modify the database url build to work with your local mongodb.

## Docker Setup
You can also deploy the server using Docker for faster and more consistent setup.

Docker Compose File
Create a docker-compose.yml file with the following content:
```yaml
version: '3.8'

services:
  smartpesa:
    image: pascarl/smartpesa:latest
    container_name: smartpesa-app
    ports:
      - "8080:8081"
    env_file:
      - .env
    restart: unless-stopped
    networks:
      - smartpesa-network
    volumes:
      - ./app_data:/app_data

networks:
  smartpesa-network:
    driver: bridge

```
## Live Test
To test the server, a live deployment has been made on an AWS EC2 instance for free testing.
You can access the server using the following base URL

Base URL: http://52.57.41.193

You can also get the api documentation [here](https://documenter.getpostman.com/view/27366427/2sAYJ7hKUB)

## CI/CD 
To save time managing deployments i wanted to automate that part so with docker, 
docker hub and github actions i was able to automate that part. This saved me lots 
of time a nd helped me reduce deployments errors and bugs.
How ever i faced challenge when i tried to use ktor in build docker plugin. for some 
reason i was anable to use the docker plugin to build and genrate the docker images. but the funny thing is 
i only experience this problem withing kmp project but as ktor stand alone everything was working well 
so i revolted to building my own dockerfile which you can find it at the root of the project.

I tweeted the issue on x maybet @jetbrains team might follow it up. here is the [tweet](https://x.com/pasaka254/status/1867994507658379280)

Then the github workflows are in the .github directory in the root folder. you will find build-image.yml
for building the docker image and publishing it to dockerhub, and deploy-to-ec2.yml for deploying the image
to aws ec2 instance. make sure you server is configured with docker and docker-compose.

