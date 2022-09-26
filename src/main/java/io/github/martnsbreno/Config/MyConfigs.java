package io.github.martnsbreno.Config;

import org.springframework.context.annotation.Bean;

public class MyConfigs {
    @Bean(name = "applicationName")
    public String applicationName() {
        return "Sistema de Vendas";
    }
}
