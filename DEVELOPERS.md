# User API Dev Guide

## Database configuration

1. Create user table

   CREATE TABLE public.zip_user (
   id serial PRIMARY KEY,
   email VARCHAR ( 220 ) UNIQUE NOT NULL,
   name VARCHAR ( 220 ) NOT NULL,
   monthly_salary DECIMAL NOT NULL,
   monthly_expense DECIMAL NOT NULL,
   created_at TIMESTAMP NOT NULL
   );

2. Create account table

   CREATE TABLE public.account (
   id serial PRIMARY KEY,
   user_id INTEGER,
   amount_spent INTEGER DEFAULT 0 NOT NULL,
   status INTEGER DEFAULT 1 NOT NULL,
   monthly_plan_start_date TIMESTAMP ,
   created_at TIMESTAMP NOT NULL,
   CONSTRAINT fk_zip_user
   FOREIGN KEY(user_id)
   REFERENCES zip_user(id)
   );

## Building
./gradlew bootRun

## Testing
REST endpoints have been created postman
You will have to export the json file in postman to test the application
File location : src/main/resources/ZIP.postman_collection.json

## Deploying
Build the application locally and run UserApiApplication main method to start the spring boot application lcoally

## Additional Information
