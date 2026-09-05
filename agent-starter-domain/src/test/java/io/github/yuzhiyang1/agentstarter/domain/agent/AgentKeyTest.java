package io.github.yuzhiyang1.agentstarter.domain.agent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AgentKeyTest {

    @Test
    void shouldNormalizeExternalKey() {
        AgentKey key = AgentKey.from(" Data-Query ");

        assertThat(key.value()).isEqualTo("data-query");
    }

    @Test
    void shouldRejectInvalidKey() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> AgentKey.from("1_invalid"));
    }
}
