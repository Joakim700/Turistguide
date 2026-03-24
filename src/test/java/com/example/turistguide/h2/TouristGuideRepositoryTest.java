package com.example.turistguide.h2;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.jdbc.TouristRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void contextLoad() {
    }

    @Test
    void checkSchemaFile(){
        URL url = getClass().getClassLoader().getResource("h2init.sql");
        System.out.println("URL= " + url);
    }

    @Test
    void getAllAttractions() {

        List<TouristAttraction> attractionList = repository.getAllAttractions();

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attractions", Integer.class);
        System.out.println("ANTAL ATTRAKTIONER I DB: " + count);

        assertNotNull(attractionList);
        assertThat(attractionList.size()).isEqualTo(6);
        assertThat(attractionList.get(0).getName()).isEqualTo("Eiffel Tower");
        assertThat(attractionList.get(1).getName()).isEqualTo("Great Wall of China");
    }



}
