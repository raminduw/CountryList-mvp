package countrylist.raminduweeraman.com.mvp.app.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import countrylist.raminduweeraman.com.mvp.app.repository.remote.network.CountryListItemResponse;


@Database(entities = {CountryListItemResponse.class}, version = 1, exportSchema = false)
public abstract class CountryRoomDatabase extends RoomDatabase {

    public abstract CountryListDao countryListDao();

    private static CountryRoomDatabase instance;

    public static CountryRoomDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (CountryRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CountryRoomDatabase.class, "country_db")
                            .build();
                }
            }
        }
        return instance;
    }
}

