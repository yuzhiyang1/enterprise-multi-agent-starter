package io.github.yuzhiyang1.agentstarter.adapter.web.agent;

import io.github.yuzhiyang1.agentstarter.client.api.AgentChatFacade;
import io.github.yuzhiyang1.agentstarter.client.result.AgentChatResult;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Agent 同步对话的 HTTP 入站适配器。
 */
@RestController
@RequestMapping(path = "/api/v1/agents", produces = MediaType.APPLICATION_JSON_VALUE)
public final class AgentChatController {

    private final AgentChatFacade agentChatFacade;

    public AgentChatController(AgentChatFacade agentChatFacade) {
        this.agentChatFacade = agentChatFacade;
    }

    /**
     * 调用指定 Agent 完成一轮同步对话。
     *
     * @param agentKey URL 中的 Agent 唯一标识
     * @param request 对话请求
     * @return 对话结果
     */
    @PostMapping(path = "/{agentKey}/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AgentChatRespVO chat(
            @PathVariable String agentKey,
            @Valid @RequestBody AgentChatReqVO request
    ) {
        AgentChatResult result = agentChatFacade.chat(agentKey, request.toCommand());
        return AgentChatRespVO.from(result);
    }
}
