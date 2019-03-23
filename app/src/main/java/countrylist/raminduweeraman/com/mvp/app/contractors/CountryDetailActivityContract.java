package countrylist.raminduweeraman.com.mvp.app.contractors;


import countrylist.raminduweeraman.com.mvp.app.presenters.BasePresenter;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryDetailViewModel;

public interface CountryDetailActivityContract {

    interface CountryDetailActivityView {
        void showErrorMessage();
        void showCountryDetails(CountryDetailViewModel countryDetailViewModel);
        void toggleLoadingIndicator(boolean isShow);
    }

    interface Presenter extends BasePresenter<CountryDetailActivityView> {
        void getCountryDetails(String countryCode);
        void downloadCountryFlag();
    }
}
