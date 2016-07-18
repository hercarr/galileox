package mx.hercarr.photofinder.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.photofinder.R;
import mx.hercarr.photofinder.adapters.PhotoListAdapter;
import mx.hercarr.photofinder.listeners.PhotoListListener;
import mx.hercarr.photofinder.model.Photo;
import mx.hercarr.photofinder.presenter.PhotosPresenter;
import mx.hercarr.photofinder.rest.PixabayClient;
import mx.hercarr.photofinder.util.Constants;
import mx.hercarr.photofinder.util.ImageViewUtil;
import mx.hercarr.photofinder.util.ItemOffsetDecoration;
import mx.hercarr.photofinder.view.IPhotosView;

public class MainActivity
        extends AppCompatActivity
        implements IPhotosView, PhotoListListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchView)
    FloatingSearchView searchView;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;
    @BindView(R.id.lLayoutMessage)
    LinearLayout lLayoutMessage;
    @BindView(R.id.lblMessage)
    TextView lblMessage;

    private PhotosPresenter presenter;
    private PhotoListAdapter adapter;
    private String category = null;

    private ImageView imageView;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (imageView != null && title != null) {
                        share(imageView, title);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void init() {
        setupToolbar();
        setupSearchView();
        setupAdapter();
        setupSwipeToRefresh();
        setupRecyclerView();
        presenter = new PhotosPresenter(this);
        presenter.searchPhotos(this, null, null);
        swipeToRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeToRefresh.setRefreshing(true);
            }
        });
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }

    private void setupSearchView() {
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
            }

            @Override
            public void onFocusCleared() {
                presenter.searchPhotos(MainActivity.this, null, searchView.getQuery());
            }
        });
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_search_animals:
                        category = PixabayClient.Parameters.ANIMALS;
                        break;
                    case R.id.action_search_fashion:
                        category = PixabayClient.Parameters.FASHION;
                        break;
                    case R.id.action_search_food:
                        category = PixabayClient.Parameters.FOOD;
                        break;
                    case R.id.action_search_music:
                        category = PixabayClient.Parameters.MUSIC;
                        break;
                    case R.id.action_search_nature:
                        category = PixabayClient.Parameters.NATURE;
                        break;
                    case R.id.action_search_people:
                        category = PixabayClient.Parameters.PEOPLE;
                        break;
                    case R.id.action_search_places:
                        category = PixabayClient.Parameters.PLACES;
                        break;
                    case R.id.action_search_sports:
                        category = PixabayClient.Parameters.SPORTS;
                        break;
                    case R.id.action_search_travel:
                        category = PixabayClient.Parameters.TRAVELS;
                        break;
                }
                presenter.searchPhotos(MainActivity.this, category, searchView.getQuery());
            }
        });
    }

    private void setupAdapter() {
        adapter = new PhotoListAdapter(this, new ArrayList<Photo>(), this);
    }

    private void setupSwipeToRefresh() {
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.searchPhotos(MainActivity.this, category, searchView.getQuery());
            }
        });
        swipeToRefresh.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
    }

    private void setupRecyclerView() {
        hideMessage();
        rvPhotos.setLayoutManager(new GridLayoutManager(this, 2));
        rvPhotos.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.card_view_item_offset));
        rvPhotos.setAdapter(adapter);
    }

    private void hideRefreshing() {
        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }
    }

    private void showMessage(String message) {
        lblMessage.setText(message);
        lLayoutMessage.setVisibility(View.VISIBLE);
        rvPhotos.setVisibility(View.GONE);
    }

    private void hideMessage() {
        lLayoutMessage.setVisibility(View.GONE);
        rvPhotos.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        hideMessage();
        hideRefreshing();
        adapter.reload(photos);
        rvPhotos.smoothScrollToPosition(0);
    }

    @Override
    public void showNoResults() {
        showMessage(getString(R.string.photo_list_label_no_results));
        hideRefreshing();
    }

    @Override
    public void showError(String error) {
        showMessage(String.format(getString(R.string.photo_list_label_error), error));
        hideRefreshing();
    }

    @Override
    public void showNetworkError() {
        showMessage(getString(R.string.msg_no_internet_connection));
        hideRefreshing();
    }


    @Override
    public void show(Photo photo) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(Constants.IntentExtras.PHOTO_TAGS, photo.getTags());
        intent.putExtra(Constants.IntentExtras.PHOTO_DOWNLOADS, photo.getDownloads());
        intent.putExtra(Constants.IntentExtras.PHOTO_LIKES, photo.getLikes());
        intent.putExtra(Constants.IntentExtras.PHOTO_IMAGE_URL, photo.getImageUrl());
        intent.putExtra(Constants.IntentExtras.PHOTO_PAGE_URL, photo.getUrl());
        startActivity(intent);
    }

    @Override
    public void share(ImageView imageView, String title) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            this.imageView = imageView;
            this.title = title;
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.Permissions.WRITE_EXTERNAL_STORAGE);
        } else {
            ImageViewUtil.shareImage(this, imageView, title);
        }
    }

}