package com.example.turistguide.controller;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristTags;
import com.example.turistguide.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("attraction", attraction);
        return "attraction";
    }

    @GetMapping("/attractions/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.getAttractionByName(name);
        model.addAttribute("tags", attraction.getTags());
        return "tags";
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
        service.createAttraction(touristAttraction);
        return "redirect:/touristguide/attractions";
    }

    @GetMapping("/attractions/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model){
        TouristAttraction attraction = service.getAttractionByName(name);
        model.addAttribute("attraction", attraction);
        return "update-attraction";
    }

    @PostMapping("/attractions/update")
    public String updateAttraction(@ModelAttribute TouristAttraction updateAttraction) {
        service.updateAttraction(updateAttraction);
        return "redirect:/touristguide/attractions";
    }

    @PostMapping("/attractions/delete/{name}")
    public String deleteAttraction(@PathVariable String name){
        service.deleteAttraction(name);
        return "redirect:/touristguide/attractions";
    }
}