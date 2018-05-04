package de.smartsquare.kickchain.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Objects;

@Entity
public class BlockchainEntity {

    @Id
    String name;

    @Column
    @Lob
    String jsonBlockchain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonBlockchain() {
        return jsonBlockchain;
    }

    public void setJsonBlockchain(String jsonBlockchain) {
        this.jsonBlockchain = jsonBlockchain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockchainEntity that = (BlockchainEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(jsonBlockchain, that.jsonBlockchain);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, jsonBlockchain);
    }
}
