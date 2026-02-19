package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristRepository;
import com.example.turistguide.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService service;
    private TouristRepository repository;

    public TouristController(TouristService touristService) {
        this.service = touristService;
    }

    @GetMapping()
    public String attractionList(Model model) {

        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    @GetMapping("{name}")
    public String getAttractionsByName(@PathVariable String name, Model model) {

        TouristAttraction attraction = service.getAttractionByName(name);

        if (attraction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraction not found");
        }

        model.addAttribute("attraction", attraction);
        return "attraction";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        TouristAttraction attraction = new TouristAttraction();
        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", repository.getCities());
        model.addAttribute("tags", repository.getTags());
        return "addnewattraction";
    }


//    @PostMapping("/update")
//    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody UpdateRequest request) {
//
//        TouristAttraction foundAttraction = null;
//
//        for (TouristAttraction attraction : service.getAllAttractions()) {
//
//            if (request.getOldName().equals(attraction.getName())) {
//                foundAttraction = attraction;
//                service.updateAttraction(foundAttraction, request.getNewName(), request.getNewDescription());
//                return ResponseEntity.ok(foundAttraction);
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }


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