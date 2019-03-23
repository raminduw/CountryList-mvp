package countrylist.raminduweeraman.com.mvp.app.repository.remote.network;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "country_table")
public class CountryListItemResponse {

    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    private String name;

    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;

    @SerializedName("region")
    @Expose
    private String region;

    @SerializedName("capital")
    @Expose
    private String capital;

    @SerializedName("subregion")
    @Expose
    private String subregion;

    @SerializedName("population")
    @Expose
    private Integer population;

    @SerializedName("area")
    @Expose
    private Double area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

}


