package countrylist.raminduweeraman.com.mvp.app.repository.remote;


import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.CountryConstants;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryApiImpl implements CountryApi {

    private CountryApi api;

    public CountryApiImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(CountryConstants.BASE_URL)
                .build();
        this.api = retrofit.create(CountryApi.class);
    }

    @Override
    public Observable<List<CountryListItemResponse>> getCountryList() {
        return api.getCountryList();
    }
}
