# Country List- MVP Sample


### Libraries Used:<br/>
1) Retrofit2<br/>
2) rxjava2 <br/>
3) Mockito 2.7.22<br/>
4) Design Support Library<br/>
5) junit:junit:4.12<br/>
6) android.arch.persistence.room:runtime:1.1.1
7) android.arch.persistence.room:compiler:1.1.1
8) android.arch.persistence.room:rxjava2:1.1.1
9) com.squareup.picasso:picasso:2.71828

### App Support:
 compileSdkVersion 27<br/>
 minSdkVersion 15<br/>
 targetSdkVersion 27<br/>
 
### Application Id:
countrylist.raminduweeraman.com.mvp

### MVP Architecture:
Followed MVP(Model-View-Presenter) architecture.<br/>This helps in proper deep Unit testing and code modularity.

### Networking:
Retrofit library use to perform network request.

### Database:
Android Room database with rxjava2.

### Image downloading:
Picasso - Square library use to download images.


### Offline Support:
Applications supports to cache the results recived from the inital country list request(https://restcountries.eu/rest/v2/all). 
When the user navigate to each country details ,application will download the flag and cache it for offilne usage.
Country which the user has not navigated during online will not be able to display its flag.
Image cache is implemented using square-picaso library.
Note : Application perfom only one API call to get country details since above API returns enough data to be display in detail screen.

### Unit Test:
Unit tests added using Mockito and junit.<br/>
Presenter layer covered with unit tests.<br/>
https://github.com/raminduw/Rebtell/tree/master/app/src/test/java/rebtel/raminduweeraman/com/rebtel

### Screens:
Application has three screens.</br>
 01.Splash Screen<br/>
 02.Country list screen<br/>
 03.Country detail screen<br/>
![splash_screen](https://user-images.githubusercontent.com/5441853/50518637-a8fc1680-0af1-11e9-8e27-fb324363c655.png)
![country_detail_screen](https://user-images.githubusercontent.com/5441853/50518648-b74a3280-0af1-11e9-805b-f4694ce5d3f0.png)
