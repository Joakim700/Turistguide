package com.example.turistguide.h2;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.repository.jdbc.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.InstanceOfAssertFactories.LONG;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TouristGuideRepositoryTest {

    @Autowired
    private TouristRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

        // DEBUG CHECK
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attractions", Integer.class);
        System.out.println("ANTAL ATTRAKTIONER I DB: " + count);

        assertNotNull(attractionList);
        assertThat(attractionList.size()).isEqualTo(6);
        assertThat(attractionList.get(0).getName()).isEqualTo("Eiffel Tower");
        assertThat(attractionList.get(1).getName()).isEqualTo("Grand Canyon");
    }

   @Test
   void getAttractionByName() {

        City city = new City(1L, "Paris");
        Set<TouristTags> tags = new HashSet<>();
        tags.add(TouristTags.OPLEVELSE);
        tags.add(TouristTags.SIGHTSEEING);

        TouristAttraction attraction = new TouristAttraction(1L, "Eiffel Tower", "Et tårn", city, tags);

        repository.getAttractionByName("Eiffel Tower");

        assertNotNull(attraction);
        assertEquals("Eiffel Tower", attraction.getName());
        assertEquals("Et tårn", attraction.getDescription());
        assertEquals("Paris", attraction.getCity().getName());
        assertTrue(attraction.getTags().contains(TouristTags.OPLEVELSE));
       assertTrue(attraction.getTags().contains(TouristTags.SIGHTSEEING));
   }

   @Test
    void updateAttractionCheck() {

       City city = new City(1L, "London");
       Set<TouristTags> tags = new HashSet<>();
       tags.add(TouristTags.OPLEVELSE);
       tags.add(TouristTags.SIGHTSEEING);

       TouristAttraction updatedAttraction = new TouristAttraction(1L, "Big Ben", "En klokke i en storby", city, tags);

       repository.updateAttraction(updatedAttraction);

       TouristAttraction result = repository.getAttractionByName("Big Ben");

       assertNotNull(result);
       assertEquals("Big Ben", result.getName());
       assertEquals("En klokke i en storby", result.getDescription());

       assertEquals(2, result.getTags().size());
       assertTrue(result.getTags().contains(TouristTags.OPLEVELSE));
       assertTrue(result.getTags().contains(TouristTags.SIGHTSEEING));
   }

    @Test
    void checkForDeleteAttraction() {

        City city = new City(1L, "London");

        TouristAttraction attraction = new TouristAttraction(1L, "Big Ben", "En stor klokke", city, new HashSet<>());

        repository.addAttraction(attraction);

        repository.deleteAttraction("Big Ben");

        assertThat(repository.getAttractionByName("Big Ben")).isNull();
    }



}
