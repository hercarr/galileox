package mx.hercarr.galileox.listeners;

import android.widget.ImageView;

import mx.hercarr.galileox.model.Photo;

public interface PhotoListListener {

    void show(Photo photo);
    void share(ImageView imageView, String title);

}