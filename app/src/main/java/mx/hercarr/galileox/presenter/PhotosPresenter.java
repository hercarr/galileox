package mx.hercarr.galileox.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.rest.PixabayClient;
import mx.hercarr.galileox.rest.PhotoSearchResponse;
import mx.hercarr.galileox.util.ConnectionUtils;
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

    public void searchPhotos(Context context, String category, String keyword) {
        if (ConnectionUtils.hasInternetAccess(context)) {
            Call<PhotoSearchResponse> call = PixabayClient.getPhotoService().searchPhotos(category, keyword);
            call.enqueue(new Callback<PhotoSearchResponse>() {
                @Override
                public void onResponse(Call<PhotoSearchResponse> call, Response<PhotoSearchResponse> response) {
                    notifyResults(response.body().getHits());
                }

                @Override
                public void onFailure(Call<PhotoSearchResponse> call, Throwable t) {
                    view.showError(t.getLocalizedMessage());
                }
            });
        } else {
            view.showNetworkError();
        }
    }

    private void notifyResults(List<Photo> photos) {
        if (photos != null && photos.size() > 0)
            view.showPhotos(photos);
        else
            view.showNoResults();
    }

}