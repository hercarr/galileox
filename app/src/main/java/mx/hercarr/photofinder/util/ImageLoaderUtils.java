package mx.hercarr.photofinder.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import mx.hercarr.photofinder.R;

public class ImageLoaderUtils {

    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
             .load(url)
             .centerCrop()
             .crossFade()
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .placeholder(R.mipmap.ic_background)
             .error(R.drawable.ic_no_results_found)
             .into(imageView);
    }

    public static void loadImageDetail(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_detail)
                .error(R.drawable.ic_no_results_found)
                .into(imageView);
    }

    public static void loadFullImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}