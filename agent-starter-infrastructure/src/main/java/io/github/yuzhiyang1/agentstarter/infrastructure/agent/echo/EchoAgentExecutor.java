package io.github.yuzhiyang1.agentstarter.infrastructure.agent.echo;

import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutionResult;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutor;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentInvocation;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentKey;
import org.springframework.stereotype.Component;

/**
 * 用于验证脚手架完整调用链路的回显示例 Agent。
 *
 * <p>它不调用大模型，后续接入真实业务 Agent 时可以直接删除。</p>
 */
@Component
public final class EchoAgentExecutor implements AgentExecutor {

    private static final AgentKey KEY = AgentKey.from("echo");

    @Override
    public AgentKey key() {
        return KEY;
    }

    @Override
    public AgentExecutionResult execute(AgentInvocation invocation) {
        return new AgentExecutionResult("ECHO: " + invocation.userMessage());
    }
}
