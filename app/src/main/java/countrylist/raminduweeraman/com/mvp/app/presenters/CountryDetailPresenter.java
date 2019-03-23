package countrylist.raminduweeraman.com.mvp.app.presenters;

import countrylist.raminduweeraman.com.mvp.app.contractors.CountryDetailActivityContract;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepository;
import countrylist.raminduweeraman.com.mvp.app.repository.image.ImageViewModel;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryDetailViewModel;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;



public class CountryDetailPresenter implements CountryDetailActivityContract.Presenter {
    private CountryDetailActivityContract.CountryDetailActivityView countryDetailActivityView;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private CountryRepository countryRepository;
    private CompositeDisposable compositeDisposable;
    private ImageViewModel imageViewModel;

    public CountryDetailPresenter(
            CountryRepository countryRepository,
            ImageViewModel imageViewModel,
            Scheduler mainScheduler,
            Scheduler backgroundScheduler
    ) {
        this.imageViewModel = imageViewModel;
        this.countryRepository = countryRepository;
        this.mainScheduler = mainScheduler;
        this.backgroundScheduler = backgroundScheduler;
    }


    @Override
    public void getCountryDetails(String countryCode) {
        countryDetailActivityView.toggleLoadingIndicator(true);
        compositeDisposable.add(countryRepository.getCountryDetailFromLocal(countryCode).
                observeOn(mainScheduler).
                subscribeOn(backgroundScheduler).map(new Function<CountryListItemResponse, CountryDetailViewModel>() {
            @Override
            public CountryDetailViewModel apply(CountryListItemResponse list) throws Exception {
                return countryRepository.getCountryDetailViewModel(list);
            }
        }).
                subscribeWith(new DisposableSingleObserver<CountryDetailViewModel>() {
                    @Override
                    public void onSuccess(CountryDetailViewModel countryDetailViewModel) {
                        if (countryDetailActivityView != null && countryDetailViewModel!=null) {
                            countryDetailActivityView.toggleLoadingIndicator(false);
                            countryDetailActivityView.showCountryDetails(countryDetailViewModel);
                        }else{
                            showError();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }
                }));

    }

    @Override
    public void downloadCountryFlag() {
        if (imageViewModel!= null) {
            imageViewModel.downloadCountryFlag();
        }
    }

    @Override
    public void attach(CountryDetailActivityContract.CountryDetailActivityView countryDetailActivityView) {
        this.countryDetailActivityView = countryDetailActivityView;
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void detach() {
        this.countryDetailActivityView = null;
        compositeDisposable.clear();
    }

    private void showError() {
        if (countryDetailActivityView != null) {
            countryDetailActivityView.toggleLoadingIndicator(false);
            countryDetailActivityView.showErrorMessage();
        }
    }
}
