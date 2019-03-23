package countrylist.raminduweeraman.com.mvp.app.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;
import io.reactivex.Single;


@Dao
public interface CountryListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CountryListItemResponse> countryListItemResponseList);

    @Query("SELECT * FROM country_table")
    Single<List<CountryListItemResponse>> getAllCountries();


    @Query("SELECT * FROM country_table WHERE alpha2Code = :code")
    Single<CountryListItemResponse> getCountryDetail(String code);
}
