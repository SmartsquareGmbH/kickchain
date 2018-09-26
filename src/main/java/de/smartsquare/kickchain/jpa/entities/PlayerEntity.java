package de.smartsquare.kickchain.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Players")
public class PlayerEntity {

    @Id
    String name;

    String publicKey;

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
}
