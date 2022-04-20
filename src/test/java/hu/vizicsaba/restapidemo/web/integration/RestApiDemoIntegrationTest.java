package hu.vizicsaba.restapidemo.web.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.vizicsaba.restapidemo.web.component.JwtTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RestApiDemoIntegrationTest {

    public static final String USERNAME = "johnsmith";

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenEndpointsForUsers_whenCallingEndpointsWithoutToken_then403StatusCodeShouldReturned() throws Exception {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/user")
            )
            .andExpect(status().isForbidden());

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/user/1")
            )
            .andExpect(status().isForbidden());

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .post("/user")
            )
            .andExpect(status().isForbidden());

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .delete("/user/1")
            )
            .andExpect(status().isForbidden());
    }

    @Test
    public void givenEndpointForGetAllUsers_whenCallingEndpointWithValidToken_thenAllUsersShouldReturned() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/user")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(getJsonFromFile("responses/getAllUsers.json")));
    }

    @Test
    public void givenEndpointForGetUserById_whenCallingEndpointWithValidTokenAndExistingId_thenMatchingUserShouldReturned() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/user/1")
                .header("Authorization", token)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(getJsonFromFile("responses/getUser_1.json")));
    }

    @Test
    public void givenEndpointForGetUserById_whenCallingEndpointWithValidTokenAndNonExistingId_then404StatusCodeShouldReturned() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .get("/user/100")
                .header("Authorization", token)
            )
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenEndpointForCreateUser_whenCallingEndpointWithValidToken_thenCreatedUserShouldReturned() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJsonFromFile("responses/getCreatedUserRequest.json"))
                .header("Authorization", token)
            )
            .andExpect(status().isCreated())
            .andExpect(content().json(getJsonFromFile("responses/getCreatedUserResponse.json")));
    }

    @Test
    public void givenEndpointForDeleteUser_whenCallingEndpointWithValidTokenAndExistingId_thenMatchingUserShouldDeleted() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(getJsonFromFile("responses/deleteUser_1.json")));
    }

    @Test
    public void givenEndpointForDeleteUser_whenCallingEndpointWithValidTokenAndNonExistingId_then500StatusCodeShouldReturned() throws Exception {
        String token = "Bearer " + jwtTokenService.getGeneratedToken(USERNAME);

        assertNotNull(token);

        mockMvc
            .perform(
                MockMvcRequestBuilders
                .delete("/user/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
            )
            .andExpect(status().isInternalServerError());
    }

    private String getJsonFromFile(String filePath) throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(stream, JsonNode.class);

        return mapper.writeValueAsString(jsonNode);
    }
}
