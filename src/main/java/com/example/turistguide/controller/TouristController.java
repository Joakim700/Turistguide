package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristTags;
import com.example.turistguide.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/touristguide")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService touristService) {
        this.service = touristService;
    }

    @GetMapping("/homepage")
    public String showHomePage() {
        return "homepage";
    }

    @GetMapping("/about-us")
    public String getAboutUs(){
        return "about-us";
    }

    @GetMapping("/attractions")
    public String attractionList(Model model) {

        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "allattractions";
    }

    @GetMapping("/attractions/{name}")
    public String getAttractionsByName(@PathVariable String name, Model model) {

        TouristAttraction attraction = service.getAttractionByName(name);

        if (attraction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraction not found");
        }

        model.addAttribute("attraction", attraction);
        return "attraction";
    }


    @GetMapping("/attractions/add")
    public String addAttraction(Model model) {
        TouristAttraction attraction = new TouristAttraction();
        model.addAttribute("attraction", attraction);
        model.addAttribute("tags", TouristTags.values());
        return "addnewattraction";
    }

    @PostMapping("/attractions/save")
    public String addAttraction(@ModelAttribute TouristAttraction touristAttraction) {

        for (TouristAttraction a : service.getAllAttractions()) {

            if (touristAttraction.getName().equalsIgnoreCase(a.getName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Attraction already exists");
            }
        }
        service.createAttraction(touristAttraction);
        return "redirect:/touristguide/attractions";
    }


    @PostMapping("/attractions/update")
    public String updateAttraction(@ModelAttribute TouristAttraction updateAttraction) {

        service.updateAttraction(updateAttraction);

        if (updateAttraction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Try again");
        }
        return "redirect:/update";
    }

    @PostMapping("/attractions/delete/{name}")
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