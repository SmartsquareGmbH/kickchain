# kickchain
Blockchained Kicker-Scores



Inspired by https://hackernoon.com/learn-blockchains-by-building-one-117428612f46


## Prerequisites

### Neo4J

Install and setup a Neo4j server.

## Build

### Maven

    $ mvn -Dneo4j.uri=bolt://neo4j:secret@localhost clean install

### Docker

    $ docker build . -t kickchain
    $ docker run kickchain
    