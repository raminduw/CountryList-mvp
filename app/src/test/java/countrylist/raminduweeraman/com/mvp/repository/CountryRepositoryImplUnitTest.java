package countrylist.raminduweeraman.com.mvp.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import rebtel.raminduweeraman.com.rebtel.app.repository.CountryRepositoryImpl;
import rebtel.raminduweeraman.com.rebtel.app.repository.local.CountryRoomDatabase;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.CountryApiImpl;
import rebtel.raminduweeraman.com.rebtel.app.repository.remote.network.CountryListItemResponse;
import rebtel.raminduweeraman.com.rebtel.app.viewModels.CountryDetailViewModel;
import rebtel.raminduweeraman.com.rebtel.app.viewModels.CountryListItemViewModel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CountryRepositoryImplUnitTest {

    CountryApiImpl countryApi;
    CountryRoomDatabase countryRoomDatabase;
    CountryRepositoryImpl countryRepository;

    @Before
    public void before() throws Exception {
        countryApi = Mockito.mock(CountryApiImpl.class);
        countryRoomDatabase = Mockito.mock(CountryRoomDatabase.class);
        countryRepository = new CountryRepositoryImpl(countryApi,countryRoomDatabase);
    }


    @Test
    public void testGetCountryViewModelListWithNull() {
        List<CountryListItemViewModel> resultList = countryRepository.getCountryViewModelList(null);
        assertEquals(0,resultList.size());
    }

    @Test
    public void testGetCountryViewModelListWithEmptyList() {
        List<CountryListItemViewModel> resultList = countryRepository.getCountryViewModelList(new ArrayList<CountryListItemResponse>());
        assertEquals(0,resultList.size());
    }

    @Test
    public void testGetCountryViewModelListWithNonEmptyList() {
        List list = new ArrayList<CountryListItemResponse>();
        list.add(Mockito.mock(CountryListItemResponse.class));
        list.add(Mockito.mock(CountryListItemResponse.class));
        list.add(Mockito.mock(CountryListItemResponse.class));
        List<CountryListItemViewModel> resultList = countryRepository.getCountryViewModelList(list);
        assertEquals(list.size(),resultList.size());
    }

    @Test
    public void testGetCountryDetailViewModelWithNull() {
        CountryDetailViewModel result = countryRepository.getCountryDetailViewModel(null);
        assertEquals(null,result);
    }


    @Test
    public void testGetCountryDetailViewModel() {
        CountryListItemResponse countryListItemResponse = Mockito.spy(CountryListItemResponse.class);
        when(countryListItemResponse.getName()).thenReturn("srilanka");
        when(countryListItemResponse.getAlpha2Code()).thenReturn("");
        when(countryListItemResponse.getRegion()).thenReturn("");
        when(countryListItemResponse.getCapital()).thenReturn("");
        when(countryListItemResponse.getPopulation()).thenReturn(0);
        when(countryListItemResponse.getArea()).thenReturn(0.0);
        CountryDetailViewModel result = countryRepository.getCountryDetailViewModel(countryListItemResponse);
        assertEquals("srilanka",result.getName());
    }

}
