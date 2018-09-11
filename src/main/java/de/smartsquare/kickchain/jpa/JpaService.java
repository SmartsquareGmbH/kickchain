package de.smartsquare.kickchain.jpa;

import de.smartsquare.kickchain.domain.*;
import de.smartsquare.kickchain.jpa.entities.BlockEntity;
import de.smartsquare.kickchain.jpa.entities.GameEntity;
import de.smartsquare.kickchain.jpa.entities.PlayerEntity;
import de.smartsquare.kickchain.jpa.repository.JpaBlockRepository;
import de.smartsquare.kickchain.jpa.repository.JpaGameRepository;
import de.smartsquare.kickchain.jpa.repository.JpaPlayerRepository;
import de.smartsquare.kickchain.service.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JpaService implements DatabaseService {

    private final JpaBlockRepository blockRepository;
    private final JpaPlayerRepository playerRepository;
    private final JpaGameRepository gameRepository;

    public JpaService(JpaBlockRepository blockRepository, JpaPlayerRepository playerRepository, JpaGameRepository gameRepository) {
        this.blockRepository = blockRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void createPlayer(String name, String publicKey) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setName(name);
        playerEntity.setPublicKey(publicKey);
        playerRepository.save(playerEntity);
    }

    @Override
    public String getPublicKeyByPlayerName(String name) {
        return playerRepository.findById(name)
                .map(PlayerEntity::getPublicKey)
                .orElse(null);
    }

    @Override
    public List<String> playerNames() {
        return StreamSupport.stream(playerRepository.findAll().spliterator(), false)
                .map(PlayerEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void createBlockchain(String name, Block genesisBlock) {
        BlockEntity blockEntity = getBlockEntity(name, genesisBlock, null);
        blockRepository.save(blockEntity);
    }

    private BlockEntity getBlockEntity(String name, Block block, BlockEntity predecessor) {
        BlockEntity blockEntity = new BlockEntity();
        blockEntity.setBlockchain(name);
        blockEntity.setFollows(predecessor);
        List<Game> content = block.getContent();
        List<GameEntity> gameEntities = getGameEntities(content);
        blockEntity.setGames(gameEntities);
        blockEntity.setIndex(block.getIndex());
        blockEntity.setPreviousHash(block.getPreviousHash());
        blockEntity.setProof(block.getProof());
        blockEntity.setTimestamp(block.getTimestamp());
        return blockEntity;
    }

    private List<GameEntity> getGameEntities(List<Game> content) {
        if (content == null) return null;
        return content.stream().map(g -> {
                GameEntity ge = new GameEntity();
                ge.setScore1(g.getScore().getGoals1());
                ge.setScore2(g.getScore().getGoals2());
                ge.setTeam1(g.getTeam1().getPlayers());
                ge.setTeam2(g.getTeam2().getPlayers());
                ge = gameRepository.save(ge);
                return ge;
            }).collect(Collectors.toList());
    }

    @Override
    public void addBlock(String name, Block block) {
        BlockEntity lastBlock = blockRepository.findAllByBlockchain(name).stream()
                .max(compareByIndex())
                .orElseThrow(() -> new IllegalStateException("Blockchain must have at least the genesis block."));

        BlockEntity blockEntity = getBlockEntity(name, block, lastBlock);
        blockRepository.save(blockEntity);
    }

    private Comparator<BlockEntity> compareByIndex() {
        return (b1, b2) -> (int) (b2.getIndex() - b1.getIndex());
    }

    @Override
    public Blockchain loadBlockchain(String name) {
        List<Block> blocks = blockRepository.findAllByBlockchain(name).stream()
                .sorted(compareByIndex())
                .map(be -> new Block(be.getIndex(), be.getTimestamp(), getGames(be), be.getProof(), be.getPreviousHash()))
                .collect(Collectors.toList());
        if (blocks.isEmpty()) {
            return null;
        }
        Blockchain blockchain = new Blockchain();
        blockchain.setChain(blocks);
        blockchain.setName(name);
        return blockchain;
    }

    private List<Game> getGames(BlockEntity be) {
        return be.getGames().stream().map(ge -> {
            Team team1 = new Team(ge.getTeam1());
            Team team2 = new Team(ge.getTeam2());
            Score score = new Score(ge.getScore1(), ge.getScore2());
            return new Game(team1, team2, score);
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteBlockchain(String name) {
        blockRepository.deleteAllByBlockchain(name);
    }

    @Override
    public void deletePlayer(String name) {
        playerRepository.deleteByName(name);
    }

}