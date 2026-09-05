package io.github.yuzhiyang1.agentstarter.client.result;

/**
 * 一轮 Agent 对话的应用结果。
 *
 * @param agentKey 实际处理请求的 Agent 唯一标识
 * @param sessionId 本轮请求所属的会话标识；新会话时由服务端生成
 * @param content Agent 返回给调用方的文本内容
 * @param status 本轮执行状态；当前同步契约只有 {@link AgentChatStatus#SUCCEEDED}
 */
public record AgentChatResult(
        String agentKey,
        String sessionId,
        String content,
        AgentChatStatus status
) {
}
