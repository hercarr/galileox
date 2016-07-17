package mx.hercarr.galileox.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.util.Constants;
import mx.hercarr.galileox.util.ImageLoaderUtils;

public class PhotoDetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        setupToolbar();
        renderDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.label_photo_detail_loading));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void renderDetails() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            String imageUrl = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_IMAGE_URL);
            String tags = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_TAGS);
            ImageLoaderUtils.loadImageDetail(PhotoDetailActivity.this, imgPhoto, imageUrl);
            collapsingToolbarLayout.setTitle(tags);
        }
    }

}