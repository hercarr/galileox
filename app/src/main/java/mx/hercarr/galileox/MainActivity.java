package mx.hercarr.galileox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.presenter.PhotosPresenter;
import mx.hercarr.galileox.rest.FiveHundredPXClient;
import mx.hercarr.galileox.rest.PhotoSearchResponse;
import mx.hercarr.galileox.rest.PhotoService;
import mx.hercarr.galileox.view.IPhotosView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity
        extends AppCompatActivity
        implements IPhotosView {

    private PhotosPresenter presenter;

    private static final String TAG = "GALILEOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new PhotosPresenter(this);
        presenter.searchPhotos();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        Log.d(TAG, String.valueOf(photos.size()));
    }

    @Override
    public void showNoResults() {
        Log.d(TAG, String.valueOf("showNoResults"));
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, String.valueOf(error));
    }

    @Override
    public void showNetworkError() {
        Log.d(TAG, String.valueOf("showNetworkError"));
    }

}
