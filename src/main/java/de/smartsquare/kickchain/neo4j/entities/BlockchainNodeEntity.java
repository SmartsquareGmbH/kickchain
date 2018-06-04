package de.smartsquare.kickchain.neo4j.entities;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class BlockchainNodeEntity {

    @Id
    private String name;

    private BlockNodeEntity genesisNode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockNodeEntity getGenesisNode() {
        return genesisNode;
    }

    public void setGenesisNode(BlockNodeEntity genesisNode) {
        this.genesisNode = genesisNode;
    }

    @Override
    public String toString() {
        return "BlockchainNodeEntity{" +
                "name='" + name + '\'' +
                ", genesisNode=" + genesisNode +
                '}';
    }
}
