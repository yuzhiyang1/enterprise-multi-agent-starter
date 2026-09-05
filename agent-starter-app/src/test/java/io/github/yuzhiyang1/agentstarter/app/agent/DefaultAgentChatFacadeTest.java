package io.github.yuzhiyang1.agentstarter.app.agent;

import io.github.yuzhiyang1.agentstarter.client.command.AgentChatCommand;
import io.github.yuzhiyang1.agentstarter.client.exception.AgentNotFoundException;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatResult;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutionResult;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutor;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentInvocation;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentKey;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class DefaultAgentChatFacadeTest {

    @Test
    void shouldRouteRequestAndGenerateSessionId() {
        DefaultAgentChatFacade facade = new DefaultAgentChatFacade(List.of(new StubAgentExecutor("demo")));

        AgentChatResult result = facade.chat("DEMO", new AgentChatCommand(null, "hello"));

        assertThat(result.agentKey()).isEqualTo("demo");
        assertThat(result.sessionId()).isNotBlank();
        assertThat(result.content()).isEqualTo("handled: hello");
    }

    @Test
    void shouldRejectUnknownAgent() {
        DefaultAgentChatFacade facade = new DefaultAgentChatFacade(List.of(new StubAgentExecutor("demo")));

        assertThatExceptionOfType(AgentNotFoundException.class)
                .isThrownBy(() -> facade.chat("missing", new AgentChatCommand("session-1", "hello")));
    }

    @Test
    void shouldFailFastWhenAgentKeyIsDuplicated() {
        assertThatIllegalStateException()
                .isThrownBy(() -> new DefaultAgentChatFacade(List.of(
                        new StubAgentExecutor("demo"),
                        new StubAgentExecutor("demo")
                )))
                .withMessageContaining("重复注册");
    }

    private record StubAgentExecutor(AgentKey key) implements AgentExecutor {

        private StubAgentExecutor(String key) {
            this(AgentKey.from(key));
        }

        @Override
        public AgentExecutionResult execute(AgentInvocation invocation) {
            return new AgentExecutionResult("handled: " + invocation.userMessage());
        }
    }
}
