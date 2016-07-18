package mx.hercarr.photofinder.listeners;

import android.widget.ImageView;

import mx.hercarr.photofinder.model.Photo;

public interface PhotoListListener {

    void show(Photo photo);
    void share(ImageView imageView, String title);

}