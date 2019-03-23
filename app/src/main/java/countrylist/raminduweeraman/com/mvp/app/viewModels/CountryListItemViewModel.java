package countrylist.raminduweeraman.com.mvp.app.viewModels;

public class CountryListItemViewModel {

    public CountryListItemViewModel(String name, String alpha2Code, String region) {
        this.name = name;
        this.alpha2Code = alpha2Code;
        this.region = region;
    }

    private String name;
    private String alpha2Code;
    private String region;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }




}
