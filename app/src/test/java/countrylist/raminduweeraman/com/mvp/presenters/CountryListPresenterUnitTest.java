package countrylist.raminduweeraman.com.mvp.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import rebtel.raminduweeraman.com.rebtel.app.contractors.CountryListActivityContract;
import rebtel.raminduweeraman.com.rebtel.app.presenters.CountryListPresenter;
import rebtel.raminduweeraman.com.rebtel.app.repository.CountryRepository;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.CountryApiImpl;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.network.CountryListItemResponse;
import rebtel.raminduweeraman.com.rebtel.app.viewModels.CountryListItemViewModel;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CountryListPresenterUnitTest {

    CountryApiImpl countryApi;
    CountryListActivityContract.CountryListActivityView view;
    CountryRepository countryRepository;
    CountryListPresenter presenter;
    List<CountryListItemViewModel> countryListItemViewModels;

    @Before
    public void before() throws Exception {
        countryApi = Mockito.mock(CountryApiImpl.class);
        view = Mockito.mock(CountryListActivityContract.CountryListActivityView.class);
        countryRepository = Mockito.mock(CountryRepository.class);
        countryListItemViewModels = new ArrayList<>();
        countryListItemViewModels.add(Mockito.mock(CountryListItemViewModel.class));
        countryListItemViewModels.add(Mockito.mock(CountryListItemViewModel.class));
    }

    @Test
    public void testRetrieveCountryListFromRemoteError() {
        presenter = new CountryListPresenter
                (countryRepository, Schedulers.trampoline(), Schedulers.trampoline(), true);
        presenter.attach(view);
        Exception exception = new Exception();
        when(countryRepository.getCountryListFromRemote())
                .thenReturn(Observable.<List<CountryListItemResponse>>error(exception));
        presenter.getCountryList();
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showErrorMessage();

    }

    @Test
    public void testRetrieveCountryListFromLocalError() {
        presenter = new CountryListPresenter
                (countryRepository, Schedulers.trampoline(), Schedulers.trampoline(), false);
        presenter.attach(view);
        Exception exception = new Exception();
        when(countryRepository.getCountryListFromLocal())
                .thenReturn(Single.<List<CountryListItemResponse>>error(exception));
        presenter.getCountryList();
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showErrorMessage();

    }


    @Test
    public void testRetrieveCountryListFromRemoteSuccess() {
        presenter = new CountryListPresenter
                (countryRepository, Schedulers.trampoline(), Schedulers.trampoline(), true);
        presenter.attach(view);

        List<CountryListItemResponse> countryListItemResponse = new ArrayList<>();
        when(countryRepository.getCountryListFromRemote())
                .thenReturn(Observable.just(countryListItemResponse));
        when(countryRepository.getCountryViewModelList(countryListItemResponse)).thenReturn(countryListItemViewModels);
        presenter.getCountryList();
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showCountryList(countryListItemViewModels);

    }

    @Test
    public void testSaveCountryList() {
        presenter = new CountryListPresenter
                (countryRepository, Schedulers.trampoline(), Schedulers.trampoline(), true);
        presenter.attach(view);
        List<CountryListItemResponse> countryListItemResponse = new ArrayList<>();
        when(countryRepository.getCountryListFromRemote())
                .thenReturn(Observable.just(countryListItemResponse));
        when(countryRepository.getCountryViewModelList(countryListItemResponse)).thenReturn(countryListItemViewModels);
        presenter.getCountryList();
        InOrder inOrder = Mockito.inOrder(countryRepository);
        inOrder.verify(countryRepository, times(1)).saveCountryList(countryListItemResponse);

    }


    @Test
    public void testRetrieveCountryListFromLocalSuccess() {
        presenter = new CountryListPresenter
                (countryRepository, Schedulers.trampoline(), Schedulers.trampoline(), false);
        List<CountryListItemResponse> countryListItemResponse = new ArrayList<>();
        presenter.attach(view);
        when(countryRepository.getCountryListFromLocal())
                .thenReturn(Single.just(countryListItemResponse));
        when(countryRepository.getCountryViewModelList(countryListItemResponse)).thenReturn(countryListItemViewModels);
        presenter.getCountryList();
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showCountryList(countryListItemViewModels);
    }

}
