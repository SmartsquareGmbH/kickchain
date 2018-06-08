package de.smartsquare.kickchain.neo4j.entities;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class PlayerNodeEntity {

    @Id
    private String name;

    @Property
    private String publicKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "PlayerNodeEntity{" +
                ", name='" + name + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
