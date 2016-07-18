package mx.hercarr.photofinder.presenter;

import android.content.Context;

import java.util.List;

import mx.hercarr.photofinder.model.Photo;
import mx.hercarr.photofinder.rest.PhotoSearchResponse;
import mx.hercarr.photofinder.rest.PixabayClient;
import mx.hercarr.photofinder.util.ConnectionUtils;
import mx.hercarr.photofinder.view.IPhotosView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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