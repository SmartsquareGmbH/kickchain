package de.smartsquare.kickchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.neo4j.repository.BlockRepository;
import de.smartsquare.kickchain.domain.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DatabaseService {

    private final BlockRepository repository;

    private final ObjectMapper mapper;

    @Autowired
    public DatabaseService(BlockRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Blockchain loadBlockchain(String name) throws IOException {
//        BlockchainEntity blockchainEntity = repository.fgetOne(name);
//        Blockchain blockchain = mapper.readValue(blockchainEntity.getJsonBlockchain(), Blockchain.class);
        Blockchain blockchain = new Blockchain(name);
        return blockchain;
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
