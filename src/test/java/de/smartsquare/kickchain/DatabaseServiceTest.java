package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.*;
import de.smartsquare.kickchain.service.DatabaseService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class DatabaseServiceTest {

    private static final String TEST_CHAIN_NAME = "__Test__";



    @Autowired
    private DatabaseService databaseService;

    @Test
    public void testResolve() throws Exception {

        databaseService.createPlayer("Alpha", "Alpha-pubKey");
        databaseService.createPlayer("Bravo", "Bravo-pubKey");
        databaseService.createPlayer("Charly", "Charly-pubKey");
        databaseService.createPlayer("Delta", "Delta-pubKey");

        Blockchain blockchain = new Blockchain(TEST_CHAIN_NAME);
        Block genesis = new Block(1, Instant.now(), new ArrayList(), 1, null);

        databaseService.createBlockchain(TEST_CHAIN_NAME, genesis);

        Team t1 = new Team("Alpha", "Bravo");
        Team t2 = new Team("Charly", "Delta");

        BlockContent game1 = new Game(t1, t2, new Score(10, 3));
        List<BlockContent> games1 = Arrays.asList(game1);
        Block block = new Block(2, Instant.now(), games1, 2, "preHash 1");
        databaseService.addBlock(TEST_CHAIN_NAME, block);

        BlockContent game2 = new Game(t1, t2, new Score(10, 7));
        List<BlockContent> games2 = Arrays.asList(game2);
        block = new Block(3, Instant.now(), games2, 3, "preHash 2");
        databaseService.addBlock(TEST_CHAIN_NAME, block);

        Blockchain blockchain1 = databaseService.loadBlockchain(TEST_CHAIN_NAME);
        System.out.println(blockchain1);

        databaseService.deleteBlockchain(TEST_CHAIN_NAME);
        databaseService.deletePlayer("Alpha");
        databaseService.deletePlayer("Bravo");
        databaseService.deletePlayer("Charly");
        databaseService.deletePlayer("Delta");
    }

    @Test
    @Ignore
    public void createPlayer() {
        databaseService.createPlayer("P1", "P1-pubKey");
        databaseService.createPlayer("P2", "P2-pubKey");
        databaseService.createPlayer("P3", "P3-pubKey");
        databaseService.createPlayer("P4", "P4-pubKey");
    }

}