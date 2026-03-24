package com.example.turistguide.h2;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.jdbc.TouristRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class TouristGuideRepositoryTest {

    @Autowired
    private TouristRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void contextLoad() {
    }

    @Test
    void checkSchemaFile(){
        URL url = getClass().getClassLoader().getResource("db/data-test.sql");
        URL url2 = getClass().getClassLoader().getResource("db/schema-test.sql");
        System.out.println("URL= " + url);
        System.out.println("URL= " + url2);
    }

    @Test
    void getAllAttractions() {

        List<TouristAttraction> attractionList = repository.getAllAttractions();

        assertNotNull(attractionList);
        assertThat(attractionList.size()).isEqualTo(6);
        assertThat(attractionList.get(0).getName()).isEqualTo("Eiffel Tower");
        assertThat(attractionList.get(1).getName()).isEqualTo("Great Wall of China");
    }



}
