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

    private Context context;
    private IPhotosView view;
    private int currentPage;

    public PhotosPresenter(Context context,  IPhotosView view) {
        this.context = context;
        this.view = view;
        this.currentPage = 1;
    }

    public void searchPhotos(String category, String keyword) {
        currentPage = 1;
        downloadPhotos(category, keyword, false);
    }

    public void loadMorePhotos(String category, String keyword) {
        currentPage++;
        view.showLoadMorePhotos();
        downloadPhotos(category, keyword, true);
    }

    private void downloadPhotos(String category, String keyword, final boolean isLoadingMorePhotos) {
        if (ConnectionUtils.hasInternetAccess(context)) {
            Call<PhotoSearchResponse> call = PixabayClient.getPhotoService().searchPhotos(currentPage, category, keyword);
            call.enqueue(new Callback<PhotoSearchResponse>() {
                @Override
                public void onResponse(Call<PhotoSearchResponse> call, Response<PhotoSearchResponse> response) {
                    if (response.body() != null) {
                        notifyResults(response.body().getHits(), isLoadingMorePhotos);
                    } else {
                        view.showNoResults();
                    }
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

    private void notifyResults(List<Photo> photos, boolean isLoadingMorePhotos) {
        if (photos != null && photos.size() > 0) {
            if (isLoadingMorePhotos) {
                view.showMorePhotos(photos);
            } else {
                view.showPhotos(photos);
            }
        } else {
            view.showNoResults();
        }
    }

}