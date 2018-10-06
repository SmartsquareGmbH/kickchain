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

### Docker

    $ docker build . -t kickchain
    $ docker run -p 8080:8080 kickchain
    