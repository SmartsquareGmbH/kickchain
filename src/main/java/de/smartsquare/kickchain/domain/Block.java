package de.smartsquare.kickchain.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.MessageDigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

public class Block {

    private BlockHeader header;
    private List<Game> content;

    public Block(long index, Instant timestamp, List<Game> blockContent, long proof, String previousHash) {
        header = new BlockHeader(index, timestamp, MessageDigestUtils.transactionHash(blockContent), previousHash, proof);
        this.content = blockContent;
    }

    private Block() {
    }


    public BlockHeader getHeader() {
        return header;
    }

    public List<Game> getContent() {
        return content;
    }



}
