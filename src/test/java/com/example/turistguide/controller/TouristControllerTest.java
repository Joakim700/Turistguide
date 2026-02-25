package com.example.turistguide.controller;


import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristTags;
import com.example.turistguide.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        TouristAttraction mockAttraction = new TouristAttraction("Eiffel Tower", "Tower", "Paris", List.of(TouristTags.VERDENSKENDT));

        Mockito.when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));
    }

    @Test
    void shouldShowAttractionTags() throws Exception {

        List<TouristTags> mockTags = List.of(TouristTags.VERDENSKENDT);
        TouristAttraction mockAttraction = new TouristAttraction("Eiffel Tower", "Tower", "Paris", mockTags);

        Mockito.when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}/tags", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attribute("tags", mockTags));
    }

    @Test
    void shouldShowAddAttractionPage() throws Exception {
        mockMvc.perform(get("/touristguide/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewattraction"));
    }

//    @Test
//    void shouldSaveAttraction() throws Exception {
//        mockMvc.perform(post("/touristguide/attractions/save")
//                        .param("Eiffel Tower")
//                        .param(""))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/touristguide/attractions"));
//
//        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
//        verify(touristService).createAttraction(captor.capture());
//
//        TouristAttraction captured = captor.capture();
//        captured.getName();
//        assertEquals(captured)
//    }


}
