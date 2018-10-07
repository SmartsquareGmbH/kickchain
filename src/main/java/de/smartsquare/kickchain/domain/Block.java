package de.smartsquare.kickchain.domain;

import java.time.Instant;
import java.util.List;

public class Block {

    private BlockHeader header;
    private List<Game> content;

    public Block(long index, Instant timestamp, long proof, String previousHash, String transactionHash, List<Game> blockContent) {
        header = new BlockHeader(index, timestamp, transactionHash, previousHash, proof);
        content = blockContent;
    }

    private Block() {
    }

    public BlockHeader getHeader() {
        return header;
    }

    public List<Game> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Block{" +
                "header=" + header +
                ", content=" + content +
                '}';
    }
}
