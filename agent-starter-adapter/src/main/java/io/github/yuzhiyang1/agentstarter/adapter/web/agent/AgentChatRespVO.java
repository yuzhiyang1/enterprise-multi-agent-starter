package io.github.yuzhiyang1.agentstarter.adapter.web.agent;

import io.github.yuzhiyang1.agentstarter.client.result.AgentChatResult;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatStatus;

/**
 * Agent 对话 HTTP 响应。
 */
public final class AgentChatRespVO {

    /**
     * 实际处理请求的 Agent 唯一标识。
     */
    private final String agentKey;

    /**
     * 本轮请求所属的会话标识；新会话时为服务端生成值，后续请求应继续使用。
     */
    private final String sessionId;

    /**
     * Agent 返回给调用方的文本内容。
     */
    private final String content;

    /**
     * 本轮执行状态；当前同步接口固定返回 SUCCEEDED，失败通过 HTTP 错误响应表达。
     */
    private final AgentChatStatus status;

    private AgentChatRespVO(String agentKey, String sessionId, String content, AgentChatStatus status) {
        this.agentKey = agentKey;
        this.sessionId = sessionId;
        this.content = content;
        this.status = status;
    }

    static AgentChatRespVO from(AgentChatResult result) {
        return new AgentChatRespVO(
                result.agentKey(),
                result.sessionId(),
                result.content(),
                result.status()
        );
    }

    public String getAgentKey() {
        return agentKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getContent() {
        return content;
    }

    public AgentChatStatus getStatus() {
        return status;
    }
}
