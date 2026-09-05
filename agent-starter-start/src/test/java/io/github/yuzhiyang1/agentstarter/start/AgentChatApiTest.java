package io.github.yuzhiyang1.agentstarter.start;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentChatApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCompleteRequestThroughAllLayers() {
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "/api/v1/agents/echo/chat",
                Map.of("message", "hello COLA"),
                JsonNode.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().path("agentKey").asText()).isEqualTo("echo");
        assertThat(response.getBody().path("sessionId").asText()).isNotBlank();
        assertThat(response.getBody().path("content").asText()).isEqualTo("ECHO: hello COLA");
        assertThat(response.getBody().path("status").asText()).isEqualTo("SUCCEEDED");
    }

    @Test
    void shouldReturnNotFoundForUnknownAgent() {
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "/api/v1/agents/missing/chat",
                Map.of("message", "hello"),
                JsonNode.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().path("agentKey").asText()).isEqualTo("missing");
    }

    @Test
    void shouldRejectBlankMessage() {
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "/api/v1/agents/echo/chat",
                Map.of("message", " "),
                JsonNode.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
