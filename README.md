# kickchain
Blockchained Kicker-Scores



Inspired by https://hackernoon.com/learn-blockchains-by-building-one-117428612f46


## Prerequisites

You can either use a JPA database backend or a Neo4j server. By default the JPA backend is enabled with an in-memory h2 database.
To change that you need to modify the property 'database.type' to 'jpa' or 'neo4j'.

### Neo4J

Install and setup a Neo4j server. 

    $ docker run \
          --publish=7474:7474 --publish=7687:7687 \
          --volume=$HOME/neo4j/data:/data \
          --volume=$HOME/neo4j/logs:/logs \
          neo4j:3.4

## Build

### Maven

    $ mvn clean install
    
### Start stand-alone

    $ java -Djava.security.egd=file:/dev/./urandom -jar target/kickchain-0.0.1-SNAPSHOT.jar

### List of configuration options

    # default peers for the internal consensus service; comma separated list of ip-address:ports
    consensus.peers=

    # can be neo4j or jpa (default)
    database.type=jpa


### Docker

    $ docker build . -t kickchain
    $ docker run -p 8080:8080 kickchain

## Web UI

Navigate to

    http://localhost:8080/

## Administration REST calls

### Register a connected node

    $ curl -X POST --header "Content-Type: text/plain" http://localhost:8080/admin/nodes/register -d 'localhost:8080'

### Unregister a connected node

    $ curl -X --header "Content-Type: text/plain" POST http://localhost:8080/admin/nodes/unregister -d 'localhost:8080'

### List all connected nodes

    $ curl http://localhost:8080/admin/nodes/list

### Resolve a chain

    $ curl http://localhost:8090/admin/chain/Kickchain/resolve