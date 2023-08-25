# EcoBike System

This README provides instructions on setting up and running the EcoBike System, a comprehensive application that manages bike rentals with user-friendly interface. Follow these steps to get the system up and running on your local environment.

## Prerequisites

Before proceeding, ensure you have the following prerequisites installed on your system:

- [PostgreSQL](https://www.postgresql.org/download/) (version 15 or higher)
- [Node.js](https://nodejs.org/)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or higher)

## Setup Instructions

### 1. Set Environment Variables for PostgreSQL

Add the PostgreSQL `bin` and `lib` directories to your system's `PATH` environment variable. This step allows you to run PostgreSQL commands and connect to the database from the command line.

Example paths (adjust according to your PostgreSQL installation):

- `C:\Program Files\PostgreSQL\15\bin`
- `C:\Program Files\PostgreSQL\15\lib`

### 2. Import Data

- **Create Databases:** Start by creating two databases, `EcoBikeSystem` and `Interbank`, in your PostgreSQL installation.

- **Import Data:** Open a command prompt as an administrator and execute the following commands to import data into the databases. Replace `"path/to/EcoBikeSystem.sql"` and `"path/to/Interbank.sql"` with the actual file paths to the SQL data dump files.

   ```shell
   psql -U postgres -d EcoBikeSystem -h localhost -f "path/to/EcoBikeSystem.sql"
   psql -U postgres -d Interbank -h localhost -f "path/to/Interbank.sql"

- Configure Database Password
Open the src/main/java/utils/Config.java file and locate the DB_PASSWORD variable. Change its value to the correct password for your PostgreSQL database.

### 3. Run the EcoBike System and Interbank
To start the EcoBike System and the Interbank subsystem, follow these steps:

- Go to src/main/java and run App.java for the main application.
- Go to  src/main/java/subsystem/interbank and run InterbankApp.java for the Interbank subsystem.


### 4. Set Up the Client Application
The EcoBike System client application is a React application that runs on Node.js. Follow these steps to set up the client application:
- Navigate to the client directory in your project and run the following command to install the dependencies:
```shell
npm install
```

- After installing the dependencies, run the following command to start the client application:
```shell
npm start
```
- The client application should now be running on http://localhost:5173. If you encounter any issues, refer to the [React documentation](https://reactjs.org/docs/getting-started.html) for troubleshooting.

## Usage
You are now ready to use the EcoBike System. Log in, explore its features, and manage bike rentals seamlessly.

(Because our project does not consider features such as user authentication, you can change the customer ID in the  `client/src/config.js` file to any of the following values to log in as a different customer)

Happy biking!