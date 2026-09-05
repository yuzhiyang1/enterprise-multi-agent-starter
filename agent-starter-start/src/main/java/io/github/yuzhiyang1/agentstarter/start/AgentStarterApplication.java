package io.github.yuzhiyang1.agentstarter.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 企业级多 Agent 脚手架的启动入口。
 */
@SpringBootApplication(scanBasePackages = "io.github.yuzhiyang1.agentstarter")
public class AgentStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentStarterApplication.class, args);
    }
}
