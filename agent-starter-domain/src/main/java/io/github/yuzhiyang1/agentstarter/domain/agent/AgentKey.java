package io.github.yuzhiyang1.agentstarter.domain.agent;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Agent 的稳定业务标识。
 *
 * <p>统一使用小写短横线格式，避免路由、配置和持久化中的标识不一致。</p>
 *
 * @param value Agent 标识，长度为 2 到 64，只能包含小写字母、数字和短横线
 */
public record AgentKey(String value) {

    private static final Pattern VALID_KEY = Pattern.compile("[a-z][a-z0-9-]{1,63}");

    public AgentKey {
        Objects.requireNonNull(value, "Agent 标识不能为空");
        if (!VALID_KEY.matcher(value).matches()) {
            throw new IllegalArgumentException("Agent 标识必须以小写字母开头，且只能包含小写字母、数字和短横线");
        }
    }

    /**
     * 将外部输入规范化为领域标识。
     *
     * @param rawKey 外部传入的 Agent 标识
     * @return 规范化后的 Agent 标识
     */
    public static AgentKey from(String rawKey) {
        if (rawKey == null || rawKey.isBlank()) {
            throw new IllegalArgumentException("Agent 标识不能为空");
        }
        return new AgentKey(rawKey.trim().toLowerCase(Locale.ROOT));
    }
}
