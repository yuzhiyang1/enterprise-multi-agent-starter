# Enterprise Multi-Agent Starter

面向 Java 团队的企业级多 Agent 应用脚手架。项目当前完成的是一套可编译、可运行、可验证的 COLA 分层骨架，后续可以在不破坏核心层的前提下接入 AgentScope Java、数据查询 Agent、RAG、MCP、审批和观测能力。

> 当前状态：**架构基线（v0）**。仓库已经具备模块边界和完整示例链路，但还不是开箱即用的生产级多 Agent 平台。

## 技术基线

- Java 17
- Spring Boot 3.5.6
- Maven 多模块工程
- COLA 六层结构
- ArchUnit 架构约束测试
- GitHub Actions 持续集成

## 模块结构

| 模块 | 职责 | 允许依赖 |
| --- | --- | --- |
| `agent-starter-client` | 对外用例接口、Command、Result 和契约异常 | 无内部模块依赖 |
| `agent-starter-domain` | Agent 领域模型和扩展端口 | 无内部模块依赖 |
| `agent-starter-app` | 用例编排、Agent 路由 | `client`、`domain` |
| `agent-starter-infrastructure` | AgentScope、数据库、消息、外部服务等出站适配器 | `domain` |
| `agent-starter-adapter` | REST、SSE、MQ、MCP 等入站适配器 | `client` |
| `agent-starter-start` | Spring Boot 启动和依赖装配 | 其余模块 |

详细设计见 [架构说明](docs/architecture.md)。

## 本地启动

```bash
mvn clean verify
mvn -pl agent-starter-start -am spring-boot:run
```

启动后可以用 PowerShell 验证示例链路：

```powershell
$body = @{ message = 'hello COLA' } | ConvertTo-Json
Invoke-RestMethod `
  -Method Post `
  -Uri 'http://localhost:8080/api/v1/agents/echo/chat' `
  -ContentType 'application/json' `
  -Body $body
```

响应示例：

```json
{
  "agentKey": "echo",
  "sessionId": "自动生成的会话 ID",
  "content": "ECHO: hello COLA",
  "status": "SUCCEEDED"
}
```

`echo` 只是用来证明六层依赖和运行链路正确，不代表真实 Agent 能力。

## 接入第一个业务 Agent

推荐把 `dodo-agentx` 重构为新的独立 Maven 模块，例如 `agent-plugin-data-query`：

1. 依赖 `agent-starter-domain`。
2. 实现 `AgentExecutor` 扩展端口，并声明唯一的 `AgentKey`。
3. 把 AgentScope、Text-to-SQL、元数据、权限、审计等实现留在插件或基础设施层。
4. 由 Spring 自动发现插件，现有路由和 REST Controller 不需要跟着业务改动。

在数据查询场景真正开放给运营和产品前，仍需补齐只读数据源、表/字段白名单、SQL 解析与限流、行列级权限、脱敏、审计、超时和结果集上限。

## 路线图

- [x] COLA 多模块骨架
- [x] 模块依赖测试和 CI
- [x] 可运行的 Agent 扩展示例
- [ ] AgentScope Java 运行时适配器
- [ ] 会话、流式输出和中断协议
- [ ] Data Query Agent 插件
- [ ] 权限、审计、观测和生产部署示例

## 许可证

许可证尚未确定。在加入或迁移第三方代码前，请先确认原项目许可证和代码归属。
