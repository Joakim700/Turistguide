package com.example.turistguide.repository;
import com.example.turistguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
        populateAttractions();
    }

    private void populateAttractions() { // DATABASE {Name, Description}

        attractions.add(new TouristAttraction("Eiffel Tower", "An impressive tower in the heart of Paris, with a great view. Close to many other attractions.", "Paris", List.of(TouristTags.VERDENSKENDT, TouristTags.BØRNEVENLIG, TouristTags.SIGHTSEEING)));
        attractions.add(new TouristAttraction("Great Wall of China", "An impressive wall spanning the northern parts of China. Spend hours walking the long paths, and taking in the impressive sights.", "Beijing", List.of(TouristTags.BØRNEVENLIG, TouristTags.GRATIS, TouristTags.MINDESMÆRKE, TouristTags.SIGHTSEEING, TouristTags.VERDENSKENDT)));
        attractions.add(new TouristAttraction("The Little Mermaid", "A small statue of a mermaid, calling back to the great danish writer H.C.Andersen. Its since, almost, become a symbol of the city of Copenhagen itself.", "Copenhagen", List.of(TouristTags.BØRNEVENLIG, TouristTags.GRATIS, TouristTags.SIGHTSEEING)));
        attractions.add(new TouristAttraction("Grand Canyon", "A historical site, of great value to both archeologists, geologists and tourists who flock to it for the great view.", "Arizona", List.of(TouristTags.GRATIS, TouristTags.SIGHTSEEING, TouristTags.BØRNEVENLIG, TouristTags.VERDENSKENDT, TouristTags.NATUR, TouristTags.OPLEVELSE)));
        attractions.add(new TouristAttraction("Tower Of London","An old fort, having had funtioned as a tollhouse, a prison and a seat of governance. An important building to the city of London.", "London", List.of(TouristTags.DYR, TouristTags.MUSEUM, TouristTags.MINDESMÆRKE)));
        attractions.add(new TouristAttraction("The Louvre", "A world famous museum and art gallery housing some of the most famous artworks in the world.", "Paris", (List.of(TouristTags.DYR, TouristTags.MUSEUM, TouristTags.KUNST, TouristTags.VERDENSKENDT))));
    }

    public List<TouristAttraction> getAllAttractions() { // Vis alle attractions
        return new ArrayList<>(attractions);
    }


    public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().replaceAll("\\s+","").equalsIgnoreCase(name)) {//Ignores spaces and letter case for easier search
                return attraction;
            }
        }return null;
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        TouristAttraction updateAttraction = getAttractionByName(attraction.getName());
        attractions.remove(updateAttraction);
        attractions.add(attraction);
        return attraction;
    }

    public TouristAttraction saveAttractionToDatabase(TouristAttraction attraction) { // Add new Attraction
        attractions.add(attraction);
        return attraction;
    }

    public void deleteAttraction(TouristAttraction attraction) {
        attractions.remove(attraction);
    }

    public List<String> getCities() {
        List<String> allCities = new ArrayList<>();
        for (TouristAttraction t : attractions) {
            if(!allCities.contains(t.getCity())){
                allCities.add(t.getCity());
            }
        }
        return allCities;
    }

    public List<TouristTags> getTags() {
        List<TouristTags> allTags = new ArrayList<>();
        for(TouristAttraction tA : attractions){
            for(TouristTags tT : tA.getTags()){
                if(!allTags.contains(tT)){
                    allTags.add(tT);
                }
            }
        }
        return allTags;
    }

}