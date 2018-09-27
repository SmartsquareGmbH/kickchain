package de.smartsquare.kickchain.neo4j;

import de.smartsquare.kickchain.domain.*;
import de.smartsquare.kickchain.neo4j.Neo4jService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class Neo4jServiceTest {

    private static final String TEST_CHAIN_NAME = "__Test__";



    @Autowired
    private Neo4jService neo4jService;

    @Test
    public void testResolve() {

        neo4jService.createPlayer("Alpha", "Alpha-pubKey");
        neo4jService.createPlayer("Bravo", "Bravo-pubKey");
        neo4jService.createPlayer("Charly", "Charly-pubKey");
        neo4jService.createPlayer("Delta", "Delta-pubKey");

        Blockchain blockchain = new Blockchain(TEST_CHAIN_NAME);
        Block genesis = new Block(1, Instant.now(), new ArrayList<>(), 1, null);

        neo4jService.createBlockchain(TEST_CHAIN_NAME, genesis);

        Team t1 = new Team("Alpha", "Bravo");
        Team t2 = new Team("Charly", "Delta");

        Game game1 = new Game(t1, t2, new Score(10, 3), "test signature");
        List<Game> games1 = Collections.singletonList(game1);
        Block block = new Block(2, Instant.now(), games1, 2, "preHash 1");
        neo4jService.addBlock(TEST_CHAIN_NAME, block);

        Game game2 = new Game(t1, t2, new Score(10, 7), "test signature");
        List<Game> games2 = Collections.singletonList(game2);
        block = new Block(3, Instant.now(), games2, 3, "preHash 2");
        neo4jService.addBlock(TEST_CHAIN_NAME, block);

        Blockchain blockchain1 = neo4jService.loadBlockchain(TEST_CHAIN_NAME);
        System.out.println(blockchain1);

        neo4jService.deleteBlockchain(TEST_CHAIN_NAME);
        neo4jService.deletePlayer("Alpha");
        neo4jService.deletePlayer("Bravo");
        neo4jService.deletePlayer("Charly");
        neo4jService.deletePlayer("Delta");
    }

    @Test
    @Ignore
    public void createPlayer() {
        neo4jService.createPlayer("P1", "P1-pubKey");
        neo4jService.createPlayer("P2", "P2-pubKey");
        neo4jService.createPlayer("P3", "P3-pubKey");
        neo4jService.createPlayer("P4", "P4-pubKey");
    }

}