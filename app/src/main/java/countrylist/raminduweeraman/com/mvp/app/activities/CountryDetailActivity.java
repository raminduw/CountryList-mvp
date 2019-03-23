package countrylist.raminduweeraman.com.mvp.app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import countrylist.raminduweeraman.com.mvp.R;
import countrylist.raminduweeraman.com.mvp.app.contractors.CountryDetailActivityContract;
import countrylist.raminduweeraman.com.mvp.app.presenters.CountryDetailPresenter;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepository;
import countrylist.raminduweeraman.com.mvp.app.repository.CountryRepositoryImpl;
import countrylist.raminduweeraman.com.mvp.app.repository.image.ImageViewModel;
import countrylist.raminduweeraman.com.mvp.app.repository.local.CountryRoomDatabase;
import countrylist.raminduweeraman.com.mvp.app.repository.remote.CountryApiImpl;
import countrylist.raminduweeraman.com.mvp.app.viewModels.CountryDetailViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CountryDetailActivity extends BaseActivity implements CountryDetailActivityContract.CountryDetailActivityView {
    private final static String TAG = CountryDetailActivity.class.getSimpleName();
    private ImageView imgViewFlag;
    private CountryDetailPresenter countryDetailPresenter;
    private String countyCode;
    private String countryName;
    private ProgressDialog dialog;
    private TextView txtArea;
    private TextView txtPopulation;
    private TextView txtCapital;
    private TextView txtRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countyCode = getIntent().getStringExtra("countryCode");
        countryName = getIntent().getStringExtra("countryName");
        getSupportActionBar().setTitle(countryName);
        imgViewFlag = (ImageView) findViewById(R.id.countryFlag);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtPopulation = (TextView) findViewById(R.id.txtPopulation);
        txtCapital = (TextView) findViewById(R.id.txtCapital);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        getCountryDetails();
    }

    private void getCountryDetails() {
        CountryRepository countryRepository = new CountryRepositoryImpl(new CountryApiImpl(), CountryRoomDatabase.getDatabase(this));
        ImageViewModel imageViewModel = new ImageViewModel(imgViewFlag, countyCode);
        countryDetailPresenter = new CountryDetailPresenter(countryRepository, imageViewModel, AndroidSchedulers.mainThread(),
                Schedulers.io());
        countryDetailPresenter.attach(this);
        countryDetailPresenter.getCountryDetails(countyCode);
        countryDetailPresenter.downloadCountryFlag();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, getString(R.string.country_detail_loading_failed),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCountryDetails(CountryDetailViewModel countryDetailViewModel) {
        txtArea.setText(getString(R.string.country_area,countryDetailViewModel.getArea()));
        txtPopulation.setText(String.valueOf(countryDetailViewModel.getPopulation()));
        txtCapital.setText(countryDetailViewModel.getCapital());
        txtRegion.setText(countryDetailViewModel.getRegion());
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
        countryDetailPresenter.detach();
    }
}
