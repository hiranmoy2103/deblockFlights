#Description
DeblockFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.

Build jar:
```$xslt
Go to project home directory and run below command in commmandline.
Once build is successful then go to /build/libs folder to check if exercise-1.0.0.jar is avialable or not.

build command ::

$ gradle clean build


```

To run as standalone:
```$xslt
Copy the jar from build/libs folder to any folder and run below command.
You can also run this jar directly from build/libs directory.

$ java -jar exercise-1.0.0.jar
```

Once running, the rest service can be accessed using any rest client like postman, RestAssured etc. or any other Advanced Rest Client.
It can also be accessed by curling requests at the relevant endpoints documented below.

##Note
Flight data are stored in /flightsdata/CrazyAirs.json and ./flightsdata/ToughJets.json, application will take those files as 
masterdata and return matched flights.


## Endpoints

###### Search Flights

GET 
Sample Request::
http://localhost:8080/deblockflights/search?origin=CDG&destination=LHR&departureDate=01-08-2022&returnDate=10-08-2022&numberOfPassengers=1


**Requests and responses are as per the requirements provided in the GitHub repo**