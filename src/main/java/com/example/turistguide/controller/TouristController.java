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
        return "attractions";
    }

    @GetMapping("{name}")
    public ResponseEntity<TouristAttraction> getAttractionsByName(@PathVariable String name) {

        TouristAttraction attraction = service.getAttractionByName(name);
        return attraction == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(attraction);
    }

//    @GetMapping("/add")
//
//
//    @PostMapping("/save")


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