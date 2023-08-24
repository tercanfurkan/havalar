A template full stack web application with cookie-based jwt authentication and backing postgresql

# Prerequisites

* Java 17
* NodeJS
* NPM
* Docker
* docker-compose

# Gradle

## Build the app
Tasks run from the root module cascade into each submodule

For example, to build the entire project

    ./gradlew build-all

To build a specific module only, then use one of [`havalar-client`, `havalar-backend`]

    ./gradlew havalar-client:build

## Run the app
In the root of the repository, run

    $ ./gradlew build-all

This will produce a server artifact, located in the [havalar-backend](web/build/libs/fat.jar) module, and
a client bundle.  The backend runs from gradle directly and will include the current build of the
client.  The docker-compose runs the app's database and backend service.

Then, run

    $ docker-compose up
    $ ./gradlew run

To start the app:

    havalar-client: localhost:8080


# Gradle Modules
The project consists of 2 modules which contains the client and backend layers.

## havalar-backend
The web module is the serverside entrypoint to the app.  It starts a web server
and holds all the api routes.  This module is where the application configuration
resources are.

## havalar-client
This module contains the ReactJS frontend which is to be intended to be served as 
static resource.

    npm run build # bundle the client code with assets in client/build
    npm run start # starts the client on localhost:3000

# TODOs

* Backend: Make a cron job to trigger daoRefreshAll -- insert latest weather data for all cities in Finland -- 
every 10 minutes. 
* Backend: Switch to a document database where it would be more efficient to replace a single document
instead of deleting and inserting rows
* Client: Make data fetch work together with search functionality. Fetch cities with search prefix
* Client: Add infinite-scroll functionality to the list
* There are other stuff too..
