package com.lucas.cursomc.config;

import com.lucas.cursomc.services.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DbService dbService;

    @Bean
    public boolean instantiateDataBase() throws Exception {
        dbService.instantiateDastaBase();
        return true;
    }
}
