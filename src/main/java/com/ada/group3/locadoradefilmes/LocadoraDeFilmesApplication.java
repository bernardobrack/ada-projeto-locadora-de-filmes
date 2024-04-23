package com.ada.group3.locadoradefilmes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LocadoraDeFilmesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocadoraDeFilmesApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
