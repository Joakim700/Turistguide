package com.example.turistguide.controller;

import com.example.turistguide.model.City;
import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.TouristTags;
import com.example.turistguide.service.TouristService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    TouristService touristService;
    City city;

    @BeforeEach
    void setUp() {
        city = new City(1L, "Paris");
    }

    @Test
    void shouldShowAttractions() throws Exception {
        mockMvc.perform(get("/touristguide/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-attractions"));
    }

    @Test
    void shouldShowAttractionByName() throws Exception {

        TouristAttraction mockAttraction = new TouristAttraction(1L, "Eiffel Tower", "Tower", city, new HashSet<>(Arrays.asList(TouristTags.VERDENSKENDT, TouristTags.OPLEVELSE)) );

        when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));
    }

    @Test
    void shouldShowAttractionTags() throws Exception {

        Set falseTags = new HashSet<>(Arrays.asList(TouristTags.BØRNEVENLIG, TouristTags.GRATIS, TouristTags.OPLEVELSE));
        TouristAttraction mockAttraction = new TouristAttraction(1L,"Eiffel Tower", "Tower", city, falseTags);

        when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}/tags", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attribute("tags", falseTags));
    }

    @Test
    void shouldShowAddAttractionPage() throws Exception {
        mockMvc.perform(get("/touristguide/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addnewattraction"));
    }

    @Test
    void shouldSaveAttraction() throws Exception {

        mockMvc.perform(post("/touristguide/attractions/save")
                        .param("name", "Eiffel Tower")
                        .param("description", "Attraction in Paris")
                        .param(String.valueOf(city), city.getName())
                        .param("tags", "GRATIS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/touristguide/attractions"));

        verify(touristService).createAttraction(any(TouristAttraction.class));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).createAttraction(captor.capture());
        TouristAttraction captured = captor.getValue();

        assertNotNull(captured);
        assertEquals("Eiffel Tower", captured.getName());
        assertEquals("Attraction in Paris", captured.getDescription());
        assertEquals("Paris", city.getName());
        assertTrue(captured.getTags().toString().contains("GRATIS"));
    }

    @Test
    void shouldUpdateAttraction() throws Exception {

        mockMvc.perform(post("/touristguide/attractions/update")
                        .param("name", "Eiffel Tower")
                        .param("description", "Tower")
                        .param(String.valueOf(city), city.getName())
                        .param("tags", "GRATIS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/touristguide/attractions"));

        verify(touristService, times(1)).updateAttraction(any(TouristAttraction.class));
    }

    @Test
    void shouldShowEditByNamePage() throws Exception {

        Set falseTags = new HashSet<>(Arrays.asList(TouristTags.BØRNEVENLIG, TouristTags.GRATIS, TouristTags.OPLEVELSE));
        TouristAttraction mockAttraction = new TouristAttraction(1L,"Eiffel Tower", "Tower", city, falseTags);

        when(touristService.getAttractionByName("Eiffel Tower")).thenReturn(mockAttraction);

        mockMvc.perform(get("/touristguide/attractions/{name}/edit", "Eiffel Tower"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-attraction"))
                .andExpect(model().attribute("attraction", mockAttraction));
    }

    @Test
    void shouldRedirectDelete() throws Exception {
        mockMvc.perform(post("/touristguide/attractions/delete/{name}", "Eiffel Tower"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/touristguide/attractions"));
        verify(touristService).deleteAttraction("Eiffel Tower");
    }

    @Test
    void shouldDelete() {
        Set falseTags = new HashSet<>(Arrays.asList(TouristTags.BØRNEVENLIG, TouristTags.GRATIS, TouristTags.OPLEVELSE));
        TouristAttraction mockAttraction = new TouristAttraction(1L,"Eiffel Tower", "Tower", city, falseTags);

        touristService.createAttraction(mockAttraction);
        touristService.deleteAttraction("Eiffel Tower");

        assertNull(touristService.getAttractionByName("Eiffel Tower"));
    }
}
