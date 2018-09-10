package de.smartsquare.kickchain.filedb;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.service.DatabaseService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("fileObjectService")
public class FileObjectService implements DatabaseService {

    private final FileObjectStore playerStore = new FileObjectStore<>("players", String.class);

    @Override
    public void createPlayer(String name, String publicKey) {
        playerStore.store(name, name);
    }

    @Override
    public String getPublicKeyByPlayerName(String name) {
        return null;
    }

    @Override
    public List<String> playerNames() {
        return playerStore.readAll();
    }

    @Override
    public void createBlockchain(String name, Block genesisBlock) {

    }

    @Override
    public void addBlock(String name, Block block) {

    }

    @Override
    public Blockchain loadBlockchain(String name) {
        return null;
    }

    @Override
    public void deleteBlockchain(String name) {

    }

    @Override
    public void deletePlayer(String name) {

    }
}
