package grischa.premiumservice.service;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostcodeRegionService {

    final Map<String, String> plzToRegionMap = new HashMap<>();

    private InputStream inputStream;

    public PostcodeRegionService() {
        this.inputStream = getClass().getClassLoader().getResourceAsStream("postcodes.csv");
    }

    public PostcodeRegionService(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @PostConstruct
    public void loadCSV() {
        if (inputStream == null) {
            throw new RuntimeException("File not found");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader csvReader = new CSVReader(reader);

            List<String[]> allRows = csvReader.readAll();
            if (allRows.isEmpty()) {
                throw new RuntimeException("File is empty");
            }
            String[] header = allRows.get(0);
            int postcodeIndex = -1;
            int regionIndex = -1;
            for (int i = 0; i < header.length; i++) {
                if (" POSTLEITZAHL".equals(header[i])) {
                    postcodeIndex = i;
                } else if (" REGION1".equals(header[i])) {
                    regionIndex = i;
                }
            }
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length > Math.max(postcodeIndex, regionIndex)) {
                    String postcode = row[postcodeIndex].trim();
                    String region = row[regionIndex].trim();
                    if (!postcode.isEmpty() && !region.isEmpty()) {
                        plzToRegionMap.put(postcode, region);
                    }
                }
            }
            }
        catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der postcodes.csv", e);
        }
    }


    public String getBundeslandByPlz(String plz) {
        return plzToRegionMap.getOrDefault(plz, "UNKNOWN");
    }
}