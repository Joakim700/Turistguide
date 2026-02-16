package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.model.UpdateRequest;
import com.example.turistguide.service.TouristService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/touristguide")
public class
TouristController {

    private final TouristService service;

    public TouristController(TouristService touristService) {
        this.service = touristService;
    }

    @GetMapping("/attractions")
    public String attractionList(Model model) {

        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "show-attractions";
    }

    @GetMapping("{name}")
    public ResponseEntity<TouristAttraction> getAttractionsByName(@PathVariable String name) {

        TouristAttraction attraction = service.getAttractionByName(name);
        return attraction == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(attraction);
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction) {

        TouristAttraction touristAttraction = service.createAttraction(attraction);
        return ResponseEntity.ok(touristAttraction);
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody UpdateRequest request) {

        TouristAttraction foundAttraction = null;

        for (TouristAttraction attraction : service.getAllAttractions()) {

            if (request.getOldName().equals(attraction.getName())) {
                foundAttraction = attraction;
                service.updateAttraction(foundAttraction, request.getNewName(), request.getNewDescription());
                return ResponseEntity.ok(foundAttraction);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity<TouristAttraction> deleteAttraction(@RequestBody TouristAttraction attractionToBeDeleted) {


        for (TouristAttraction attraction : service.getAllAttractions()) {

            if (attraction.getName().equals(attractionToBeDeleted.getName())) {
                service.deleteAttraction(attraction);
                return ResponseEntity.ok(attractionToBeDeleted);
            }
        }
        return ResponseEntity.notFound().build();
    }



}