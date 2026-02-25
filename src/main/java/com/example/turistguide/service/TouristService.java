package com.example.turistguide.service;

import com.example.turistguide.model.TouristAttraction;
import com.example.turistguide.repository.TouristRepository;
import com.example.turistguide.repository.TouristTags;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    public TouristAttraction getAttractionByName(String name) {

        TouristAttraction attraction = repository.getAttractionByName(name);

        if (attraction != null && attraction.getName().equals(name)) {
            return attraction;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attraction not found");
    }

    public void createAttraction(TouristAttraction attraction) {

        for (TouristAttraction a : repository.getAllAttractions()) {

            if (attraction.getName().equalsIgnoreCase(a.getName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Attraction already exists");
            }
        }
        repository.saveAttraction(attraction);
    }


    public void updateAttraction(TouristAttraction attraction) {

        if (attraction == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Try again");
        }
        repository.updateAttraction(attraction);
    }

    public void deleteAttraction(String name) {

        TouristAttraction attraction = repository.getAttractionByName(name);

        if (attraction != null) {
            repository.deleteAttraction(attraction);
        }
    }

    public List<TouristTags> getAllTags(){
        return repository.getTags();
    }
}