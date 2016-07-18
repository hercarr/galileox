package mx.hercarr.photofinder.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.hercarr.photofinder.R;
import mx.hercarr.photofinder.util.Constants;
import mx.hercarr.photofinder.util.ImageLoaderUtils;
import mx.hercarr.photofinder.util.ImageViewUtil;

public class PhotoDetailActivity extends AppCompatActivity {

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.lblDownloads)
    TextView lblDownloads;
    @BindView(R.id.lblLikes)
    TextView lblLikes;

    private String imageUrl;
    private String pageUrl;
    private String tags;

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
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    share();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.photo_detail_label_loading));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void renderDetails() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            imageUrl = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_IMAGE_URL);
            pageUrl = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_PAGE_URL);
            tags = getIntent().getStringExtra(Constants.IntentExtras.PHOTO_TAGS);
            Integer downloads = getIntent().getIntExtra(Constants.IntentExtras.PHOTO_DOWNLOADS, 0);
            Integer likes = getIntent().getIntExtra(Constants.IntentExtras.PHOTO_LIKES, 0);
            ImageLoaderUtils.loadImageDetail(PhotoDetailActivity.this, imgPhoto, imageUrl);
            collapsingToolbarLayout.setTitle(tags);
            lblDownloads.setText(String.format(getString(R.string.photo_detail_label_downloads), downloads));
            lblLikes.setText(String.format(getString(R.string.photo_detail_label_likes), likes));
        }
    }

    @OnClick(R.id.imgPhoto)
    public void show() {
        Intent intent = new Intent(this, ViewPhotoActivity.class);
        intent.putExtra(Constants.IntentExtras.PHOTO_IMAGE_URL, imageUrl);
        intent.putExtra(Constants.IntentExtras.PHOTO_TAGS, tags);
        startActivity(intent);
    }

    @OnClick(R.id.fabShare)
    public void share() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.Permissions.WRITE_EXTERNAL_STORAGE);
        } else {
            ImageViewUtil.shareImage(this, imgPhoto, tags);
        }
    }

    @OnClick(R.id.btnWebSite)
    public void openBrowser() {
        Uri uri = Uri.parse(pageUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}