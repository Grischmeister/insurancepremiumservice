package grischa.premiumservice.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class PostcodeRegion {

    @CsvBindByName(column = "REGION1")
    private String bundesland;

    @CsvBindByName(column = "POSTLEITZAHL")
    private String plz;

    public PostcodeRegion(String bundesland, String plz) {
        this.bundesland = bundesland;
        this.plz = plz;
}
}