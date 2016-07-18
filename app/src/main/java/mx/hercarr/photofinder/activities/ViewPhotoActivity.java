package mx.hercarr.photofinder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.photofinder.R;
import mx.hercarr.photofinder.util.Constants;
import mx.hercarr.photofinder.util.ImageLoaderUtils;
import mx.hercarr.photofinder.util.TouchImageView;

public class ViewPhotoActivity extends AppCompatActivity {

    @BindView(R.id.imgPhoto)
    TouchImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_photo);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String url = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_IMAGE_URL);
        ImageLoaderUtils.loadFullImage(this, imgPhoto, url);
        Toast.makeText(this, getString(R.string.photo_view_msg_tip), Toast.LENGTH_LONG).show();
    }

}