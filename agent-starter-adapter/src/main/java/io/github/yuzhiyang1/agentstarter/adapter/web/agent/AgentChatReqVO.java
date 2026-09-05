package io.github.yuzhiyang1.agentstarter.adapter.web.agent;

import io.github.yuzhiyang1.agentstarter.client.command.AgentChatCommand;
import jakarta.validation.constraints.NotBlank;

/**
 * Agent 对话 HTTP 请求参数。
 */
public final class AgentChatReqVO {

    /**
     * 会话标识；首次对话可空，为空时由服务端生成，后续轮次应原样传回。
     */
    private String sessionId;

    /**
     * 用户本轮输入的原始消息；不能为空或纯空白。
     */
    @NotBlank(message = "message 不能为空")
    private String message;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    AgentChatCommand toCommand() {
        return new AgentChatCommand(sessionId, message);
    }
}
