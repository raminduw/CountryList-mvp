package countrylist.raminduweeraman.com.mvp.app.repository.remote;


import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CountryApi {

    @GET("all")
    Observable<List<CountryListItemResponse>> getCountryList();

}
