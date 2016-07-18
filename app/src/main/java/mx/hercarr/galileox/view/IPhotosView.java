package mx.hercarr.galileox.view;

import java.util.List;

import mx.hercarr.galileox.model.Photo;

public interface IPhotosView {

    void showPhotos(List<Photo> photos);
    void showNoResults();
    void showError(String error);
    void showNetworkError();

}