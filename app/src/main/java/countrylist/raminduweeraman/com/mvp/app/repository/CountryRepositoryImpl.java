package countrylist.raminduweeraman.com.mvp.app.repository;

import java.util.ArrayList;
import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.repository.local.CountryRoomDatabase;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.CountryApiImpl;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryDetailViewModel;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;



public class CountryRepositoryImpl implements CountryRepository {

    private CountryApiImpl countryApi;
    private CountryRoomDatabase countryRoomDatabase;

    public CountryRepositoryImpl(CountryApiImpl countryApi, CountryRoomDatabase countryRoomDatabase) {
        this.countryApi = countryApi;
        this.countryRoomDatabase = countryRoomDatabase;
    }

    @Override
    public Observable<List<CountryListItemResponse>> getCountryListFromRemote() {
        return countryApi.getCountryList();
    }

    @Override
    public Single<List<CountryListItemResponse>> getCountryListFromLocal() {
        return countryRoomDatabase.countryListDao().getAllCountries();
    }

    @Override
    public List<CountryListItemViewModel> getCountryViewModelList(List<CountryListItemResponse> countryListItemResponses) {
        List<CountryListItemViewModel> list = new ArrayList<>();
        if (countryListItemResponses!=null) {
            for(CountryListItemResponse s : countryListItemResponses){
                list.add(new CountryListItemViewModel(s.getName(),s.getAlpha2Code(),s.getRegion()));
            }
        }
        return list;
    }

    @Override
    public CountryDetailViewModel getCountryDetailViewModel(CountryListItemResponse countryListItemResponse) {
        CountryDetailViewModel countryDetailViewModel = null;
        if (countryListItemResponse!=null) {
            countryDetailViewModel = new CountryDetailViewModel();
            countryDetailViewModel.setName(countryListItemResponse.getName());
            countryDetailViewModel.setAlpha2Code(countryListItemResponse.getAlpha2Code());
            countryDetailViewModel.setRegion(countryListItemResponse.getRegion());
            countryDetailViewModel.setCapital(countryListItemResponse.getCapital());
            countryDetailViewModel.setPopulation(countryListItemResponse.getPopulation());
            countryDetailViewModel.setArea(countryListItemResponse.getArea());
        }
        return countryDetailViewModel;
    }

    @Override
    public Single<CountryListItemResponse> getCountryDetailFromLocal(String countryCode) {
        return countryRoomDatabase.countryListDao().getCountryDetail(countryCode);
    }

    @Override
    public void saveCountryList(List<CountryListItemResponse> countryListItemResponseList) {
        countryRoomDatabase.countryListDao().insertAll(countryListItemResponseList);
    }
}
