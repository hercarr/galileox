package mx.hercarr.galileox.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.ByteArrayOutputStream;

import mx.hercarr.galileox.R;

public class ImageViewUtil {

    public static void shareImage(Context context, ImageView imageView, String title) {
        Bitmap bitmap;
        if (imageView.getDrawable() instanceof GlideBitmapDrawable) {
            bitmap = ((GlideBitmapDrawable) imageView.getDrawable()).getBitmap();
        } else if (imageView.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            imageView.buildDrawingCache();
            bitmap = imageView.getDrawingCache();
        }
        if (bitmap != null) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType(Constants.Files.IMAGE_TYPE);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, title, null);
            Uri imageUri = Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            context.startActivity(Intent.createChooser(share, context.getString(R.string.title_photo_share)));
        }
        imageView.destroyDrawingCache();
    }

}