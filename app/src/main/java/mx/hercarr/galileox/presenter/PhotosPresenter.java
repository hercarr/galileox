package mx.hercarr.galileox.presenter;

import android.util.Log;

import java.util.List;

import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.rest.FiveHundredPXClient;
import mx.hercarr.galileox.rest.PhotoSearchResponse;
import mx.hercarr.galileox.view.IPhotosView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hercarr on 7/13/16.
 */
public class PhotosPresenter {

    private IPhotosView view;

    public PhotosPresenter(IPhotosView view) {
        this.view = view;
    }

    public void searchPhotos() {
        Call<PhotoSearchResponse> call = FiveHundredPXClient.getPhotoService().searchPhotos();
        call.enqueue(new Callback<PhotoSearchResponse>() {
            @Override
            public void onResponse(Call<PhotoSearchResponse> call, Response<PhotoSearchResponse> response) {
                List<Photo> photos = response.body().getPhotos();
                if (photos != null && photos.size() > 0)
                    view.showPhotos(photos);
                else
                    view.showNoResults();
            }

            @Override
            public void onFailure(Call<PhotoSearchResponse> call, Throwable t) {
                view.showError(t.getLocalizedMessage());
            }
        });
    }

}