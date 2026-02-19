package com.example.turistguide.repository;
import com.example.turistguide.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

@Repository
public class TouristRepository {

    private List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {

        populateAttractions();
    }

    private void populateAttractions() { // DATABASE {Name, Description}

        attractions.add(new TouristAttraction("Nyhavn", "Gammel havn, tæt på centrum. Mange restauranter og mulighed for kanalrundfart.", "Copenhagen", TouristTags.GRATIS));
        attractions.add(new TouristAttraction("Tivoli", "Danmarks første forlystelsespark. Inviterer til en god dag ude for hele familien.", "Copenhagen", TouristTags.BØRNEVENLIG));
        attractions.add(new TouristAttraction("Amalienborg", "Hvor Kongen og den royale familie bor, og man kan se Garderne gå runde.", "Copenhagen", TouristTags.BØRNEVENLIG));
        attractions.add(new TouristAttraction("Marmorkirken", "En imponerende kirke af marmor, kendt for sin runde kuppel.", "Copenhagen", TouristTags.GRATIS));
        attractions.add(new TouristAttraction("Christiansborg","Hvor politikerne styrer landet fra. Man kan komme ind og gå rundtur.", "Copenhagen", TouristTags.GRATIS));
    }

    public List<TouristAttraction> getAllAttractions() { // Vis alle attractions

        return new ArrayList<>(attractions);
    }


    public TouristAttraction getAttractionByName(String name) { // Hent attraction ud fra getAttractionsByName()

        for (TouristAttraction attraction : attractions) {

            if (attraction.getName().equals(name)) {

                return attraction;
            }
        }
        return null;
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
            allCities.add(t.getCity());
        }
        return allCities;
    }



}