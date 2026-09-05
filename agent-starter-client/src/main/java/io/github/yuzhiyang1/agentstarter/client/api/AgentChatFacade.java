package io.github.yuzhiyang1.agentstarter.client.api;

import io.github.yuzhiyang1.agentstarter.client.command.AgentChatCommand;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatResult;

/**
 * Agent 对话用例的公开门面。
 *
 * <p>入站适配器只依赖这个契约，不感知应用层的具体实现。</p>
 */
public interface AgentChatFacade {

    /**
     * 调用指定 Agent 完成一轮同步对话。
     *
     * @param agentKey Agent 的唯一标识
     * @param command 本轮对话命令
     * @return Agent 执行结果
     */
    AgentChatResult chat(String agentKey, AgentChatCommand command);
}
