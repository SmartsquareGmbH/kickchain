package de.smartsquare.kickchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.domain.Score;
import de.smartsquare.kickchain.domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class KickchainApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testNewGame() throws Exception {
        Team team1 = new Team("A");
        Team team2 = new Team("B", "C");
        Score score = new Score(10, 3);
        Game game = new Game(team1, team2, score);
        mvc.perform(post("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(game)))
                .andExpect(status().isCreated());
    }

}