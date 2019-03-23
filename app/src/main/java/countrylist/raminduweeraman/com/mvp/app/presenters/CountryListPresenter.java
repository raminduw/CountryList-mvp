package countrylist.raminduweeraman.com.mvp.app.presenters;

import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.contractors.CountryListActivityContract;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepository;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;



public class CountryListPresenter implements CountryListActivityContract.Presenter {

    private CountryListActivityContract.CountryListActivityView countryListActivityView;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private CountryRepository countryRepository;
    private boolean isOnline;
    private CompositeDisposable compositeDisposable;

    public CountryListPresenter(
            CountryRepository countryRepository, Scheduler mainScheduler, Scheduler backgroundScheduler, boolean isOnline)
    {
        this.countryRepository = countryRepository;
        this.mainScheduler = mainScheduler;
        this.backgroundScheduler = backgroundScheduler;
        this.isOnline = isOnline;
    }

    @Override
    public void getCountryList() {
        if (isOnline) {
            retrieveCountryListFromRemote();
        } else {
            retrieveCountryListFromLocal();
        }
    }

    @Override
    public void attach(CountryListActivityContract.CountryListActivityView countryListActivityView) {
        this.countryListActivityView = countryListActivityView;
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    private void retrieveCountryListFromRemote() {
        countryListActivityView.toggleLoadingIndicator(true);
        countryRepository.getCountryListFromRemote().subscribeOn(backgroundScheduler).doOnNext(new Consumer<List<CountryListItemResponse>>() {
            @Override
            public void accept(List<CountryListItemResponse> CountryListItemResponse) throws Exception {
                countryRepository.saveCountryList(CountryListItemResponse);

            }
        }).map(new Function<List<CountryListItemResponse>, List<CountryListItemViewModel>>() {
            @Override
            public List<CountryListItemViewModel> apply(List<CountryListItemResponse> list) throws Exception {
                return countryRepository.getCountryViewModelList(list);
            }
        }).observeOn(mainScheduler)
                .subscribe(new Observer<List<CountryListItemViewModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<CountryListItemViewModel> countryListItemViewModels) {
                        showSuccess(countryListItemViewModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void retrieveCountryListFromLocal() {
        countryListActivityView.toggleLoadingIndicator(true);
        compositeDisposable.add(countryRepository.getCountryListFromLocal().
                observeOn(mainScheduler).
                subscribeOn(backgroundScheduler).map(new Function<List<CountryListItemResponse>, List<CountryListItemViewModel>>() {
            @Override
            public List<CountryListItemViewModel> apply(List<CountryListItemResponse> list) throws Exception {
                return countryRepository.getCountryViewModelList(list);
            }
        }).
                subscribeWith(new DisposableSingleObserver<List<CountryListItemViewModel>>() {
                    @Override
                    public void onSuccess(List<CountryListItemViewModel> countryListItemViewModels) {
                        showSuccess(countryListItemViewModels);
                    }
                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }
                }));

    }


    @Override
    public void detach() {
        this.countryListActivityView = null;
        compositeDisposable.clear();
    }

    private void showSuccess(List<CountryListItemViewModel> countryListItemViewModels) {
        if (countryListActivityView != null) {
            countryListActivityView.toggleLoadingIndicator(false);
            countryListActivityView.showCountryList(countryListItemViewModels);
        }
    }

    private void showError() {
        if (countryListActivityView != null) {
            countryListActivityView.toggleLoadingIndicator(false);
            countryListActivityView.showErrorMessage();
        }
    }


}
