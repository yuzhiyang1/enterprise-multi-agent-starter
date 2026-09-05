package io.github.yuzhiyang1.agentstarter.client.exception;

/**
 * 请求的 Agent 未注册时抛出的契约异常。
 */
public final class AgentNotFoundException extends RuntimeException {

    /** 请求中使用但未注册的 Agent 唯一标识。 */
    private final String agentKey;

    public AgentNotFoundException(String agentKey) {
        super("未找到 Agent：" + agentKey);
        this.agentKey = agentKey;
    }

    public String getAgentKey() {
        return agentKey;
    }
}
