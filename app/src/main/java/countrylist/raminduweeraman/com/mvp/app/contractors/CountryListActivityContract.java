package countrylist.raminduweeraman.com.mvp.app.contractors;


import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.presenters.BasePresenter;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;

public interface CountryListActivityContract  {

    interface CountryListActivityView {
        void showErrorMessage();
        void showCountryList(List<CountryListItemViewModel> countryListItemViewModels);
        void toggleLoadingIndicator(boolean isShow);
    }

    interface Presenter extends BasePresenter<CountryListActivityView> {
        void getCountryList();
    }


}
