package countrylist.raminduweeraman.com.mvp.app.repository;

import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryDetailViewModel;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface CountryRepository {

    Observable<List<CountryListItemResponse>> getCountryListFromRemote();

    Single<List<CountryListItemResponse>> getCountryListFromLocal();

    void saveCountryList(List<CountryListItemResponse> countryListItemResponseList);

    Single<CountryListItemResponse> getCountryDetailFromLocal(String countryCode);

    List<CountryListItemViewModel> getCountryViewModelList(List<CountryListItemResponse> countryListItemResponseList);

    CountryDetailViewModel getCountryDetailViewModel(CountryListItemResponse countryListItemResponse);

}
