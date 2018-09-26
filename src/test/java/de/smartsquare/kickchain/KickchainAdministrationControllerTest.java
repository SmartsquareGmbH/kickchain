package de.smartsquare.kickchain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.service.ConsensusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
@AutoConfigureMockMvc

public class KickchainAdministrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();


    @MockBean
    private ConsensusService consensusService;


    @Test
    public void testNewChain() throws Exception {
        mvc.perform(get("/admin/chain/testchain/new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testChainNotThere() throws Exception {
        mvc.perform(get("/admin/chain/testchain")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testChainAfterCreated() throws Exception {
        mvc.perform(get("/admin/chain/testchain/new")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(get("/admin/chain/testchain")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister() throws Exception {
        String node = "162.168.2.1:8080";

        Set<String> nodes = new HashSet<>();
        nodes.add("localhost:8080");
        Mockito.when(consensusService.getNodes()).thenReturn(nodes);

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        MvcResult result = mvc.perform(post("/admin/nodes/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(node))
                .andExpect(status().isCreated()).andReturn();
        Set<String> registeredNodes = mapper.readValue(result.getResponse().getContentAsString(), Set.class);

        assertThat(registeredNodes, hasItem("localhost:8080"));
    }

    @Test
    public void testRegister_fail() throws Exception {
        mvc.perform(post("/admin/nodes/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

}