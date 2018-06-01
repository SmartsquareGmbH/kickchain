package de.smartsquare.kickchain.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.MessageDigestUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

public class Block<T extends BlockContent> {

    private int index;
    private Instant timestamp;
    private String previousHash;

    private List<T> blockContent;

    private long proof;

    public Block(int index, Instant timestamp, List<T> blockContent, long proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.blockContent = blockContent;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    private Block() {
    }

    public int getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<T> getBlockContent() {
        return blockContent;
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
