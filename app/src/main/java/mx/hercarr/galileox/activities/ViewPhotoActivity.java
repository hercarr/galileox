package mx.hercarr.galileox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.util.Constants;
import mx.hercarr.galileox.util.ImageLoaderUtils;
import mx.hercarr.galileox.util.TouchImageView;

/**
 * Created by hercarr on 7/17/16.
 */
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