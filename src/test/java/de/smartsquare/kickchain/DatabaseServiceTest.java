package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.service.DatabaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DatabaseServiceTest {

    private static final String TEST_CHAIN_NAME = "__Test__";

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void testResolve() throws Exception {

        Blockchain blockchain = new Blockchain(TEST_CHAIN_NAME);
        Block genesis = new Block(1, Instant.now(), new ArrayList(), 1, "1");

        databaseService.createBlockchain(TEST_CHAIN_NAME, genesis);

        Block block = new Block(2, Instant.now(), new ArrayList(), 1, "1");
        databaseService.addBlock(TEST_CHAIN_NAME, block);

        Blockchain blockchain1 = databaseService.loadBlockchain(TEST_CHAIN_NAME);
        System.out.println(blockchain1);

        databaseService.deleteBlockchain(TEST_CHAIN_NAME);
    }
}