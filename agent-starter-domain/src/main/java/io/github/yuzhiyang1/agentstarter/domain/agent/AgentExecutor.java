package io.github.yuzhiyang1.agentstarter.domain.agent;

/**
 * 业务 Agent 的扩展端口。
 *
 * <p>每个 Agent 插件实现该端口即可参与应用层路由，领域层不感知 AgentScope 等具体框架。</p>
 */
public interface AgentExecutor {

    /**
     * 返回当前执行器负责的唯一 Agent 标识。
     *
     * @return Agent 标识
     */
    AgentKey key();

    /**
     * 执行一轮 Agent 调用。
     *
     * @param invocation 已完成基础校验的领域调用
     * @return Agent 执行结果
     */
    AgentExecutionResult execute(AgentInvocation invocation);
}
