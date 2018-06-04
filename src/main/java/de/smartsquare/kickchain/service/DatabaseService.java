package de.smartsquare.kickchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.neo4j.entities.BlockNodeEntity;
import de.smartsquare.kickchain.neo4j.entities.BlockchainNodeEntity;
import de.smartsquare.kickchain.neo4j.repository.BlockRepository;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.neo4j.repository.BlockchainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DatabaseService {

    private final BlockRepository blockRepository;
    private final BlockchainRepository blockchainRepository;

    private final ObjectMapper mapper;

    @Autowired
    public DatabaseService(BlockRepository blockRepository, BlockchainRepository blockchainRepository, ObjectMapper mapper) {
        this.blockRepository = blockRepository;
        this.blockchainRepository = blockchainRepository;
        this.mapper = mapper;
    }

    public void createBlockchain(String name, Block genesisBlock) {
        BlockNodeEntity blockNodeEntity = getBlockNodeEntity(genesisBlock);

        BlockchainNodeEntity blockchainNodeEntity = new BlockchainNodeEntity();
        blockchainNodeEntity.setName(name);
        blockchainNodeEntity.setGenesisNode(blockNodeEntity);
        BlockchainNodeEntity save = blockchainRepository.save(blockchainNodeEntity);
    }

    private BlockNodeEntity getBlockNodeEntity(Block genesisBlock) {
        BlockNodeEntity blockNodeEntity = new BlockNodeEntity();
        blockNodeEntity.setIndex(genesisBlock.getIndex());
        blockNodeEntity.setPreviousHash(genesisBlock.getPreviousHash());
        blockNodeEntity.setProof(genesisBlock.getProof());
        blockNodeEntity.setTimestamp(genesisBlock.getTimestamp());
        blockNodeEntity.setContent(genesisBlock.getBlockContent());
        blockNodeEntity.setNextBlock(null);
        return blockNodeEntity;
    }

    public void addBlock(String name, Block block) {

        BlockchainNodeEntity byName = blockchainRepository.findByName(name);
        BlockNodeEntity blockNode = byName.getGenesisNode();
        while (blockNode.getNextBlock() != null) {
            blockNode = blockNode.getNextBlock();
        }

        BlockNodeEntity blockNodeEntity = getBlockNodeEntity(block);
        blockNode.setNextBlock(blockNodeEntity);
        blockRepository.save(blockNode);
    }

    public Blockchain loadBlockchain(String name) throws IOException {
//        BlockchainEntity blockchainEntity = repository.fgetOne(name);
//        Blockchain blockchain = mapper.readValue(blockchainEntity.getJsonBlockchain(), Blockchain.class);
        BlockchainNodeEntity byName = blockchainRepository.findByName(name);
        Blockchain blockchain = new Blockchain(name);

        BlockNodeEntity bne = byName.getGenesisNode();
        while (bne != null) {
            Block block = new Block(bne.getIndex(), bne.getTimestamp(), bne.getContent(), bne.getProof(), bne.getPreviousHash());
            blockchain.addBlock(block);
            bne = bne.getNextBlock();
        }

        return blockchain;
    }

    public void deleteBlockchain(String name) {
        blockchainRepository.deleteById(name);
    }

    public void saveBlockchain(Blockchain blockchain) throws IOException {
//        StringWriter writer = new StringWriter();
//        mapper.writeValue(writer, blockchain);
//
//        BlockchainEntity blockchainEntity = new BlockchainEntity();
//        blockchainEntity.setName(blockchain.getName());
//        blockchainEntity.setJsonBlockchain(writer.toString());
//
//        repository.save(blockchainEntity);
    }

}
