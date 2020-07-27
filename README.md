# WHAT
Service which consumes public rest API, stores the results in the database and expose its data by API.  

### API

#### GET /api/availability
    embedId: int, required
    productId:  String, required
    minTime: long (epoch seconds), required
    maxTime: long (epoch seconds), required
    interval: internal in minutes, the value should be between 30 minutes and 24 hours. int, required.

### Run
    mvn clean install
    java -jar target/livereach-*.jar