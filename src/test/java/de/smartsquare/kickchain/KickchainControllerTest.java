package de.smartsquare.kickchain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.KcGame;
import de.smartsquare.kickchain.domain.KcScore;
import de.smartsquare.kickchain.domain.KcTeam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KickchainController.class)
public class KickchainControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testNewGame() throws Exception {
        KcTeam team1 = new KcTeam("A");
        KcTeam team2 = new KcTeam("B", "C");
        KcScore score = new KcScore(10, 3);
        KcGame game = new KcGame(team1, team2, score);
        mvc.perform(post("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(game)))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void testRegister() throws Exception {
        String node = "162.168.2.1:8080";
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
      /*  mvc.perform(get("/nodes/resolve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());*/
    }
}