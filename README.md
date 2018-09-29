# kickchain
Blockchained Kicker-Scores



Inspired by https://hackernoon.com/learn-blockchains-by-building-one-117428612f46


## Prerequisites

### Neo4J

Install and setup a Neo4j server. 

    $ docker run \
          --publish=7474:7474 --publish=7687:7687 \
          --volume=$HOME/neo4j/data:/data \
          --volume=$HOME/neo4j/logs:/logs \
          --env NEO4J_AUTH=neo4j/secret \
          neo4j:3.4

## Build

### Maven

    $ mvn clean install

### Docker

    $ docker build . -t kickchain
    $ docker run -p 8080:8080 kickchain
    