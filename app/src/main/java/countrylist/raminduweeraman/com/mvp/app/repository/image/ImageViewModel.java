package countrylist.raminduweeraman.com.mvp.app.repository.image;

import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import countrylist.raminduweeraman.com.mvp.R;
import countrylist.raminduweeraman.com.mvp.app.CountryConstants;


public class ImageViewModel {

    private ImageView imageView;
    private String countryCode;


    public ImageViewModel(ImageView imageView, String countryCode) {
        this.imageView = imageView;
        this.countryCode = countryCode;
    }


    public void downloadCountryFlag() {
        //first try to download image from cache
        Picasso.get()
                .load(getImageUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .fit()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                         //if image is not available in cache,download it from remote
                        downloadFromRemote();
                    }
                });
    }

    private void downloadFromRemote(){
        Picasso.get()
                .load(getImageUrl())
                .placeholder(R.drawable.placeholder_img)
                .error(R.drawable.error_img)
                .fit()
                .into(imageView);
    }

    private String getImageUrl() {
        String imageUrl = "";
        imageUrl = CountryConstants.BASE_IMAGE_URL + countryCode.toLowerCase() + ".png";
        return imageUrl;
    }
}
