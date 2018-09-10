package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;

import java.util.List;

public interface DatabaseService {

    void createPlayer(String name, String publicKey);

    String getPublicKeyByPlayerName(String name);

    List<String> playerNames();

    void createBlockchain(String name, Block genesisBlock);

    void addBlock(String name, Block block);

    Blockchain loadBlockchain(String name);

    void deleteBlockchain(String name);

    void deletePlayer(String name);

}
