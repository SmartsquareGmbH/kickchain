package de.smartsquare.kickchain.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.MessageDigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class BlockHeader {

    private long index;
    private Instant timestamp;
    private String transactionHash;
    private String previousHash;
    private long proof;

    private final Logger logger = LoggerFactory.getLogger(BlockHeader.class);

    public BlockHeader(long index, Instant timestamp, String transactionHash, String previousHash, long proof) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactionHash = transactionHash;
        this.previousHash = previousHash;
        this.proof = proof;
    }

    private BlockHeader() {
    }

    public long getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getProof() {
        return proof;
    }

    public String toHash() throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, this);
        String blockHeaderAsString = writer.toString();
        logger.info(String.format("BlockHeader with index %d as string: %s", index, blockHeaderAsString));
        return MessageDigestUtils.sha256(blockHeaderAsString);
    }

}
