package mx.hercarr.galileox.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by hercarr on 7/13/16.
 */
public class ImageLoaderUtil {

    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
             .load(url)
             .centerCrop()
             .crossFade()
             .into(imageView);
    }

}