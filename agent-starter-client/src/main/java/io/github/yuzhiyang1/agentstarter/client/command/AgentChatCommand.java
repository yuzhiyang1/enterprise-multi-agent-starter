package io.github.yuzhiyang1.agentstarter.client.command;

/**
 * 发起一轮 Agent 对话的应用命令。
 *
 * @param sessionId 会话标识；可空，为空时由应用层生成新的会话标识
 * @param message 用户本轮输入的原始消息；不能为空或纯空白
 */
public record AgentChatCommand(
        String sessionId,
        String message
) {

    public AgentChatCommand {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message 不能为空");
        }
    }
}
