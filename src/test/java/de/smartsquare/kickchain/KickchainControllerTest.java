package de.smartsquare.kickchain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.domain.Score;
import de.smartsquare.kickchain.domain.Team;
import de.smartsquare.kickchain.service.ConsensusService;
import de.smartsquare.kickchain.service.DatabaseService;
import de.smartsquare.kickchain.service.KickchainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KickchainController.class)
public class KickchainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    KickchainService kickchainService;

    @MockBean
    ConsensusService consensusService;

    @MockBean
    DatabaseService databaseService;

    @Test
    public void testNewGame() throws Exception {
        Team team1 = new Team("A");
        Team team2 = new Team("B", "C");
        Score score = new Score(10, 3);
        Game game = new Game(team1, team2, score);
        mvc.perform(post("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(game)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister() throws Exception {
        String node = "162.168.2.1:8080";

        Set<String> nodes = new HashSet<>();
        nodes.add("localhost:8080");
        Mockito.when(consensusService.getNodes()).thenReturn(nodes);

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        MvcResult result = mvc.perform(post("/nodes/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(node))
                .andExpect(status().isCreated()).andReturn();
        Set<String> registeredNodes = mapper.readValue(result.getResponse().getContentAsString(), Set.class);

        assertThat(registeredNodes, hasItem("localhost:8080"));
    }

    @Test
    public void testRegister_fail() throws Exception {
        mvc.perform(post("/nodes/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testResolve() throws Exception {
        mvc.perform(get("/nodes/resolve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}