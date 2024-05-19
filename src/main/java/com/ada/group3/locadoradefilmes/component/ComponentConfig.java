package com.ada.group3.locadoradefilmes.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfig {
    @Bean
    public MyComponent myComponent(){
        return new MyComponent();
    }
}
