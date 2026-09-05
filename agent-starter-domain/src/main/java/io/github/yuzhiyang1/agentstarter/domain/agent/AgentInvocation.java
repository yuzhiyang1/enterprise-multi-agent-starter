package io.github.yuzhiyang1.agentstarter.domain.agent;

import java.util.Objects;

/**
 * 进入 Agent 运行时的一次领域调用。
 *
 * @param agentKey 目标 Agent 的稳定标识
 * @param sessionId 本轮调用所属的会话标识，不能为空
 * @param userMessage 用户原始消息，不能为空或纯空白
 */
public record AgentInvocation(
        AgentKey agentKey,
        String sessionId,
        String userMessage
) {

    public AgentInvocation {
        Objects.requireNonNull(agentKey, "Agent 标识不能为空");
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("会话标识不能为空");
        }
        if (userMessage == null || userMessage.isBlank()) {
            throw new IllegalArgumentException("用户消息不能为空");
        }
    }
}
