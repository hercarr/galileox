package mx.hercarr.galileox.view;

import java.util.List;

import mx.hercarr.galileox.model.Photo;

/**
 * Created by hercarr on 7/13/16.
 */
public interface IPhotosView {

    void showPhotos(List<Photo> photos);
    void showNoResults();
    void showError(String error);
    void showNetworkError();

}