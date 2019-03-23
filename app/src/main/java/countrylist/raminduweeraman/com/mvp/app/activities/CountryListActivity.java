package countrylist.raminduweeraman.com.mvp.app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import countrylist.raminduweeraman.com.mvp.R;
import countrylist.raminduweeraman.com.mvp.app.adapters.CountryListAdapter;
import countrylist.raminduweeraman.com.mvp.app.contractors.CountryListActivityContract;
import countrylist.raminduweeraman.com.mvp.app.presenters.CountryListPresenter;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepository;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepositoryImpl;
import countrylist.raminduweeraman.com.mvp.app.repository.local.CountryRoomDatabase;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.CountryApiImpl;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryListItemViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class CountryListActivity extends BaseActivity implements CountryListActivityContract.CountryListActivityView,CountryListAdapter.ItemClickListener {

    private final static String TAG = CountryListActivity.class.getSimpleName();
    private CountryListPresenter countryListPresenter;
    private ProgressDialog dialog;
    private CountryListAdapter countryListAdapter;
    private RecyclerView countryRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        countryRecycleView = (RecyclerView) findViewById(R.id.countryList);
        getCountryList();
    }

    private void getCountryList(){
        CountryRepository countryRepository = new CountryRepositoryImpl(new CountryApiImpl(), CountryRoomDatabase.getDatabase(this));
        countryListPresenter = new CountryListPresenter(countryRepository, AndroidSchedulers.mainThread(), Schedulers.io(), isNetworkAvailable());
        countryListPresenter.attach(this);
        countryListPresenter.getCountryList();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, getString(R.string.country_list_loading_failed), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCountryList(List<CountryListItemViewModel> countryListItemViewModels) {
        countryListAdapter = new CountryListAdapter(countryListItemViewModels, this);
        countryRecycleView.setAdapter(countryListAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        countryRecycleView.setLayoutManager(mLayoutManager);
        countryRecycleView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(countryRecycleView.getContext(),
                DividerItemDecoration.VERTICAL);
        countryRecycleView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void toggleLoadingIndicator(boolean isShow) {
        if (isShow) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(getString(R.string.loading));
            dialog.show();
        } else {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countryListPresenter.detach();
    }

    @Override
    public void onClick(CountryListItemViewModel countryListItemViewModel) {
        Intent countryDetailIntent = new Intent(this, CountryDetailActivity.class);
        countryDetailIntent.putExtra("countryCode",countryListItemViewModel.getAlpha2Code());
        countryDetailIntent.putExtra("countryName",countryListItemViewModel.getName());
        startActivity(countryDetailIntent);

    }
}
