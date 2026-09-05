package io.github.yuzhiyang1.agentstarter.adapter.web.agent;

import io.github.yuzhiyang1.agentstarter.client.exception.AgentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 将 Agent 契约异常转换为稳定的 HTTP 错误语义。
 */
@RestControllerAdvice
public final class AgentExceptionHandler {

    @ExceptionHandler(AgentNotFoundException.class)
    ProblemDetail handleAgentNotFound(AgentNotFoundException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        detail.setTitle("Agent 不存在");
        detail.setProperty("agentKey", exception.getAgentKey());
        return detail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handleIllegalArgument(IllegalArgumentException exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        detail.setTitle("请求参数不合法");
        return detail;
    }
}
