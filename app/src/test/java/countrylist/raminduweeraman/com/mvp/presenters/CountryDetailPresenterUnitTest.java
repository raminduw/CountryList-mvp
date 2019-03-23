package countrylist.raminduweeraman.com.mvp.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import rebtel.raminduweeraman.com.rebtel.app.contractors.CountryDetailActivityContract;
import rebtel.raminduweeraman.com.rebtel.app.presenters.CountryDetailPresenter;
import rebtel.raminduweeraman.com.rebtel.app.repository.CountryRepository;
import rebtel.raminduweeraman.com.rebtel.app.repository.image.ImageViewModel;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.CountryApiImpl;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.network.CountryListItemResponse;
import rebtel.raminduweeraman.com.rebtel.app.viewModels.CountryDetailViewModel;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CountryDetailPresenterUnitTest {

    CountryApiImpl countryApi;
    CountryDetailActivityContract.CountryDetailActivityView view;
    CountryRepository countryRepository;
    ImageViewModel imageViewModel;
    CountryDetailPresenter presenter;


    @Before
    public void before() throws Exception {
        countryApi = Mockito.mock(CountryApiImpl.class);
        view = Mockito.mock(CountryDetailActivityContract.CountryDetailActivityView.class);
        countryRepository = Mockito.mock(CountryRepository.class);
        imageViewModel = Mockito.mock(ImageViewModel.class);

    }

    @Test
    public void testGetCountryDetailsError() {
        presenter = new CountryDetailPresenter
                (countryRepository, imageViewModel, Schedulers.trampoline(), Schedulers.trampoline());
        presenter.attach(view);

        Exception exception = new Exception();
        when(countryRepository.getCountryDetailFromLocal("AR"))
                .thenReturn(Single.<CountryListItemResponse>error(exception));
        presenter.getCountryDetails("AR");
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showErrorMessage();
    }

    @Test
    public void testGetCountryDetailsInValidResponse() {
        presenter = new CountryDetailPresenter
                (countryRepository, imageViewModel, Schedulers.trampoline(), Schedulers.trampoline());
        presenter.attach(view);

        CountryListItemResponse countryListItemResponse = Mockito.mock(CountryListItemResponse.class);
        CountryDetailViewModel countryDetailViewModel = Mockito.mock(CountryDetailViewModel.class);
        when(countryRepository.getCountryDetailFromLocal("AR"))
                .thenReturn(Single.just(countryListItemResponse));

        when(countryRepository.getCountryDetailViewModel(null))
                .thenReturn(countryDetailViewModel);
        presenter.getCountryDetails("AR");
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showErrorMessage();
    }

    @Test
    public void testGetCountryDetailsSuccess() {
        presenter = new CountryDetailPresenter
                (countryRepository, imageViewModel, Schedulers.trampoline(), Schedulers.trampoline());
        presenter.attach(view);

        CountryListItemResponse countryListItemResponse = Mockito.mock(CountryListItemResponse.class);
        CountryDetailViewModel countryDetailViewModel = Mockito.mock(CountryDetailViewModel.class);
        when(countryRepository.getCountryDetailFromLocal("AR"))
                .thenReturn(Single.just(countryListItemResponse));

        when(countryRepository.getCountryDetailViewModel(countryListItemResponse))
                .thenReturn(countryDetailViewModel);
        presenter.getCountryDetails("AR");
        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).showCountryDetails(countryDetailViewModel);
    }



}
