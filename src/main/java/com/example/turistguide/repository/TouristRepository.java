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
        attractions.add(new TouristAttraction("Nyhavn", "Gammel havn, tæt på centrum. Mange restauranter og mulighed for kanalrundfart.", "Copenhagen", List.of(TouristTags.GRATIS, TouristTags.BØRNEVENLIG)));
        attractions.add(new TouristAttraction("Tivoli", "Danmarks første forlystelsespark. Inviterer til en god dag ude for hele familien.", "Copenhagen", List.of(TouristTags.BØRNEVENLIG)));
        attractions.add(new TouristAttraction("Amalienborg", "Hvor Kongen og den royale familie bor, og man kan se Garderne gå runde.", "Copenhagen", List.of(TouristTags.BØRNEVENLIG)));
        attractions.add(new TouristAttraction("Marmorkirken", "En imponerende kirke af marmor, kendt for sin runde kuppel.", "Copenhagen", List.of(TouristTags.GRATIS)));
        attractions.add(new TouristAttraction("Christiansborg","Hvor politikerne styrer landet fra. Man kan komme ind og gå rundtur.", "Copenhagen", List.of(TouristTags.GRATIS)));
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