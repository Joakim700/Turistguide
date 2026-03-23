package com.example.turistguide.h2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

@SpringBootTest
public class TouristGuideTest {

    @Test
    void contextLoad(){

    }

    @Test
    void checkSchemaFile(){
        URL url = getClass().getClassLoader().getResource("db/schema-test.sql");
        System.out.println("URL= " + url);
    }
}
