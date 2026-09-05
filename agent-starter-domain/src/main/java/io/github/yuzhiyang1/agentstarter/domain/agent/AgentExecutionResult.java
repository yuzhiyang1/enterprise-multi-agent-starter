package io.github.yuzhiyang1.agentstarter.domain.agent;

/**
 * Agent 执行端口返回的领域结果。
 *
 * @param content Agent 生成的文本内容，不能为空或纯空白
 */
public record AgentExecutionResult(String content) {

    public AgentExecutionResult {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Agent 返回内容不能为空");
        }
    }
}
