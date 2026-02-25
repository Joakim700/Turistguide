package com.example.turistguide.controller;


import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristTags;
import com.example.turistguide.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    TouristService touristService;

    @Test
    void shouldShowAttractions() throws  Exception {
        mockMvc.perform(get("/touristguide/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("allattractions"));
    }

    @Test
    void shouldShowAttractionByName() throws Exception {
        TouristAttraction mockAttraction = new TouristAttraction("Eiffel Tower", "An impressive tower in the heart of Paris, with a great view. " +
                "Close to many other attractions.", "Paris", List.of(TouristTags.VERDENSKENDT, TouristTags.BØRNEVENLIG, TouristTags.SIGHTSEEING));

        Mockito.when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));
    }
}
