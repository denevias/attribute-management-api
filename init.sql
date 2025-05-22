-- Create the application database
CREATE DATABASE axiomatics_db;

-- Create the application user with password
CREATE USER axiomatics_user WITH PASSWORD 'pass123';

-- Grant all privileges on the database to the user
GRANT ALL PRIVILEGES ON DATABASE axiomatics_db TO axiomatics_user;

-- Connect to the new database and grant schema privileges
\c axiomatics_db;
GRANT ALL ON SCHEMA public TO axiomatics_user;