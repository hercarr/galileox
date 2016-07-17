package mx.hercarr.galileox.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mx.hercarr.galileox.R;

/**
 * Created by hercarr on 7/13/16.
 */
public class ImageLoaderUtils {

    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
             .load(url)
             .centerCrop()
             .crossFade()
             .placeholder(R.mipmap.ic_launcher)
             .error(R.mipmap.ic_launcher)
             .into(imageView);
    }

}