package de.smartsquare.kickchain.neo4j;

import de.smartsquare.kickchain.domain.*;
import de.smartsquare.kickchain.neo4j.entities.*;
import de.smartsquare.kickchain.neo4j.repository.*;
import de.smartsquare.kickchain.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("neo4jService")
public class Neo4jService implements DatabaseService {

    private final BlockRepository blockRepository;
    private final FollowsRepository followsRepository;
    private final PlayerRepository playerRepository;
    private final HasGameRepository hasGameRepository;
    private final GameRepository gameRepository;

    @Autowired
    public Neo4jService(BlockRepository blockRepository,
                        FollowsRepository followsRepository,
                        PlayerRepository playerRepository,
                        GameRepository gameRepository,
                        HasGameRepository hasGameRepository) {
        this.blockRepository = blockRepository;
        this.followsRepository = followsRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.hasGameRepository = hasGameRepository;
    }

    @Override
    public void createPlayer(String name, String publicKey) {
        PlayerNodeEntity playerNodeEntity = new PlayerNodeEntity();
        playerNodeEntity.setName(name);
        playerNodeEntity.setPublicKey(publicKey);
        playerRepository.save(playerNodeEntity);
    }

    @Override
    public String getPublicKeyByPlayerName(String name) {
        return playerRepository.findByName(name).getPublicKey();
    }

    @Override
    public List<String> playerNames() {
        List<String> names = new ArrayList<>();
        playerRepository.findAll().forEach(p -> names.add(p.getName()));
        return names;
    }

    @Override
    public void createBlockchain(String name, Block genesisBlock) {
        // TODO implement check whether chain already exists
        BlockNodeEntity blockNodeEntity = getBlockNodeEntity(name, genesisBlock);
        blockRepository.save(blockNodeEntity);
    }

    private BlockNodeEntity getBlockNodeEntity(String blockchain, Block block) {
        BlockNodeEntity blockNodeEntity = new BlockNodeEntity();
        blockNodeEntity.setProof(block.getHeader().getProof());
        blockNodeEntity.setTimestamp(block.getHeader().getTimestamp());
        blockNodeEntity.setBlockchain(blockchain);
        blockNodeEntity.setIndex(block.getHeader().getIndex());
        blockNodeEntity.setPreviousHash(block.getHeader().getPreviousHash());

        return blockNodeEntity;
    }

    private List<GameNodeEntity> getGameNodeEntity(List<Game> blockContent) {
        if (blockContent == null) {
            return null;
        } else {
            return blockContent.stream()
                    .map(this::getGameNodeEntity)
                    .collect(Collectors.toList());
        }
    }

    private GameNodeEntity getGameNodeEntity(BlockContent blockContent) {
        if (blockContent instanceof Game) {
            Game game = (Game) blockContent;

            List<PlayerNodeEntity> team1 = game.getTeam1().getPlayers().stream()
                    .map(playerRepository::findByName)
                    .collect(Collectors.toList());
            List<PlayerNodeEntity> team2 = game.getTeam2().getPlayers().stream()
                    .map(playerRepository::findByName)
                    .collect(Collectors.toList());

            GameNodeEntity gameNodeEntity = new GameNodeEntity();
            gameNodeEntity.setCreated(Instant.now());
            gameNodeEntity.setScore1(game.getScore().getGoals1());
            gameNodeEntity.setScore2(game.getScore().getGoals2());

            gameNodeEntity.setTeam1(game.getTeam1().getPlayers());
            gameNodeEntity.setTeam2(game.getTeam2().getPlayers());

            return gameNodeEntity;
        }
        throw new RuntimeException("Only BlockContent of type Game allowed yet.");
    }

    @Override
    public void addBlock(String name, Block block) {
        BlockNodeEntity lastBlock = blockRepository.findEndByBlockchain(name);
        BlockNodeEntity newBlock = getBlockNodeEntity(name, block);
        newBlock.setIndex(lastBlock.getIndex() + 1);

        List<GameNodeEntity> gameNodeEntity = getGameNodeEntity(block.getContent());
        if (gameNodeEntity != null) {
            GameNodeEntity endGame = gameNodeEntity.get(0);
            endGame = gameRepository.save(endGame);

            HasGamesRelationshipEntity hasGamesRelationshipEntity = createHasGamesRelationshipEntity(newBlock, endGame);
            newBlock.setGame(hasGamesRelationshipEntity);
            hasGameRepository.save(hasGamesRelationshipEntity);
        }

        FollowsRelationshipEntity follows = createFollowsRelationshipEntity(lastBlock, newBlock);
        newBlock.setFollows(follows);
        followsRepository.save(follows);

        BlockNodeEntity save = blockRepository.save(newBlock);
        System.out.println("Save: " + save);
    }

    private HasGamesRelationshipEntity createHasGamesRelationshipEntity(BlockNodeEntity newBlock, GameNodeEntity endGame) {
        HasGamesRelationshipEntity hasGamesRelationshipEntity = new HasGamesRelationshipEntity();
        hasGamesRelationshipEntity.setEndGame(endGame);
        hasGamesRelationshipEntity.setStartBlock(newBlock);
        return hasGamesRelationshipEntity;
    }

    private FollowsRelationshipEntity createFollowsRelationshipEntity(BlockNodeEntity lastBlock, BlockNodeEntity newBlock) {
        FollowsRelationshipEntity follows = new FollowsRelationshipEntity();
        follows.setCreated(Instant.now());
        follows.setEndBlock(lastBlock);
        follows.setStartBlock(newBlock);
        return follows;
    }

    private List<Game> getGamesRE(List<HasGamesRelationshipEntity> gameNodeEntities) {
        return gameNodeEntities == null ? null : gameNodeEntities.stream()
                .map(HasGamesRelationshipEntity::getEndGame)
                .map(this::getGame)
                .collect(Collectors.toList());
    }

    private List<Game> getGames(List<GameNodeEntity> gameNodeEntities) {
        return gameNodeEntities == null ? null : gameNodeEntities.stream()
                .map(this::getGame)
                .collect(Collectors.toList());
    }

    private Game getGame(HasGamesRelationshipEntity hasGamesRelationshipEntity) {

        return (hasGamesRelationshipEntity == null || hasGamesRelationshipEntity.getEndGame() == null)
                ? null : getGame(hasGamesRelationshipEntity.getEndGame());
    }

    private Game getGame(GameNodeEntity g) {
        System.out.println("g is " + g);
        return g == null || g.getTeam1() == null || g.getTeam2() == null ? null :
                new Game(
                        new Team(g.getTeam1()),
                        new Team(g.getTeam2()),
//                new Team(g.getTeam1().stream().map(PlayerNodeEntity::getName).collect(Collectors.toList())),
//                new Team(g.getTeam2().stream().map(PlayerNodeEntity::getName).collect(Collectors.toList())),
                        new Score(g.getScore1(), g.getScore2()),
                        "signature"
                );
    }

    @Override
    public Blockchain loadBlockchain(String name) {
        List<BlockNodeEntity> byBlockchain = blockRepository.findByBlockchain(name);
        if (byBlockchain.size() > 1) {
            byBlockchain = byBlockchain.stream()
                    .sorted((c1, c2) -> (int) (c2.getIndex() - c1.getIndex()))
                    .collect(Collectors.toList());
        }

        if (byBlockchain.isEmpty()) return null;

        Blockchain blockchain = new Blockchain(name);
        for (BlockNodeEntity bne : byBlockchain) {
            List<Game> games = bne.getGame() == null ? null : Collections.singletonList(getGame(bne.getGame()));
            Block block = new Block(bne.getIndex(), bne.getTimestamp(), games, bne.getProof(), bne.getPreviousHash());
            blockchain.addBlock(block);
        }

        return blockchain;
    }


    @Override
    public void deleteBlockchain(String name) {
        followsRepository.deleteAll();
        blockRepository.deleteAll();
    }

    @Override
    public void deletePlayer(String name) {
        playerRepository.deleteById(name);
    }

}
