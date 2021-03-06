package mx.hercarr.photofinder.view;

import java.util.List;

import mx.hercarr.photofinder.model.Photo;

public interface IPhotosView {

    void showPhotos(List<Photo> photos);
    void showMorePhotos(List<Photo> photos);
    void showLoadMorePhotos();
    void showNoResults();
    void showError(String error);
    void showNetworkError();

}