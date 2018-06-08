package de.smartsquare.kickchain.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.MessageDigestUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

public class Block {

    private long index;
    private Instant timestamp;
    private String previousHash;

    private List<Game> content;

    private long proof;

    public Block(long index, Instant timestamp, List<Game> blockContent, long proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.content = blockContent;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    private Block() {
    }

    public long getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<Game> getContent() {
        return content;
    }

    public long getProof() {
        return proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String toHash() throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, this);
        return MessageDigestUtils.sha256(writer.toString());
    }

}
