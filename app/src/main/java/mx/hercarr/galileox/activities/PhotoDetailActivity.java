package mx.hercarr.galileox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.rest.FiveHundredPXClient;
import mx.hercarr.galileox.rest.PhotoResponse;
import mx.hercarr.galileox.util.Constants;
import mx.hercarr.galileox.util.ImageLoaderUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;

    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);
        findPhoto();
        setupToolbar();
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

    private void findPhoto() {
        Long photoID = getIntent().getLongExtra(Constants.IntentExtras.PHOTO_ID, 0);
        // TODO - use a presenter or send all data from intent
        Call<PhotoResponse> call = FiveHundredPXClient.getPhotoService().findPhoto(photoID);
        call.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                photo = response.body().getPhoto();
                renderDetails();
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {

            }
        });
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.label_photo_detail_loading));

        toolbar.getBackground().setAlpha(0);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void renderDetails() {
        if (imgPhoto != null) {
            ImageLoaderUtil.loadImage(PhotoDetailActivity.this, imgPhoto, photo.getImageUrl());
            toolbar.setTitle(photo.getName());
        }
    }

}
