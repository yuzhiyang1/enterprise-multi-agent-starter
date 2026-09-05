package io.github.yuzhiyang1.agentstarter.app.agent;

import io.github.yuzhiyang1.agentstarter.client.api.AgentChatFacade;
import io.github.yuzhiyang1.agentstarter.client.command.AgentChatCommand;
import io.github.yuzhiyang1.agentstarter.client.exception.AgentNotFoundException;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatResult;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatStatus;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutionResult;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentExecutor;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentInvocation;
import io.github.yuzhiyang1.agentstarter.domain.agent.AgentKey;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Agent 对话门面的默认实现。
 *
 * <p>应用层只负责用例编排和执行器路由，不包含具体 Agent 或外部框架实现。</p>
 */
@Service
public final class DefaultAgentChatFacade implements AgentChatFacade {

    private final Map<String, AgentExecutor> executors;

    public DefaultAgentChatFacade(List<AgentExecutor> executors) {
        Objects.requireNonNull(executors, "Agent 执行器列表不能为空");
        this.executors = indexExecutors(executors);
    }

    @Override
    public AgentChatResult chat(String agentKey, AgentChatCommand command) {
        Objects.requireNonNull(command, "对话命令不能为空");

        AgentKey normalizedKey = AgentKey.from(agentKey);
        AgentExecutor executor = executors.get(normalizedKey.value());
        if (executor == null) {
            throw new AgentNotFoundException(normalizedKey.value());
        }

        String sessionId = resolveSessionId(command.sessionId());
        AgentInvocation invocation = new AgentInvocation(normalizedKey, sessionId, command.message());
        AgentExecutionResult result = executor.execute(invocation);

        return new AgentChatResult(
                normalizedKey.value(),
                sessionId,
                result.content(),
                AgentChatStatus.SUCCEEDED
        );
    }

    private static Map<String, AgentExecutor> indexExecutors(List<AgentExecutor> executors) {
        Map<String, AgentExecutor> indexedExecutors = new LinkedHashMap<>();
        for (AgentExecutor executor : executors) {
            Objects.requireNonNull(executor, "Agent 执行器不能为空");
            String key = executor.key().value();
            AgentExecutor duplicated = indexedExecutors.putIfAbsent(key, executor);
            if (duplicated != null) {
                // 启动阶段立即失败，避免同一 Agent 标识在运行时被不确定地路由。
                throw new IllegalStateException("Agent 标识重复注册：" + key);
            }
        }
        return Collections.unmodifiableMap(indexedExecutors);
    }

    private static String resolveSessionId(String requestedSessionId) {
        if (requestedSessionId == null || requestedSessionId.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return requestedSessionId.trim();
    }
}
