package com.ada.group3.locadoradefilmes.component;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
    @Value("${api.mapping}")
    private String apiMapping;

    public String getApiMapping(){
        return apiMapping;
    }
}
