package mx.hercarr.galileox.activities.listeners;

import android.widget.ImageView;

import mx.hercarr.galileox.model.Photo;

/**
 * Created by hercarr on 7/15/16.
 */
public interface PhotoListListener {

    void show(Photo photo);
    void share(String title, ImageView imageView);

}