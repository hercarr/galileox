package mx.hercarr.galileox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.activities.listeners.PhotoListListener;
import mx.hercarr.galileox.adapters.PhotoListAdapter;
import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.presenter.PhotosPresenter;
import mx.hercarr.galileox.rest.FiveHundredPXClient;
import mx.hercarr.galileox.util.Constants;
import mx.hercarr.galileox.util.ItemOffsetDecoration;
import mx.hercarr.galileox.view.IPhotosView;

public class MainActivity
        extends AppCompatActivity
        implements IPhotosView, PhotoListListener {

    private static final String TAG = "GALILEOX";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchView)
    FloatingSearchView searchView;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;

    private PhotosPresenter presenter;
    private PhotoListAdapter adapter;

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

        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        setupToolbar();
        setupSearchView();
        setupAdapter();
        setupSwipeToRefresh();
        setupRecyclerView();
        presenter = new PhotosPresenter(this);
        presenter.findPhotos(null);
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void setupSearchView() {
        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {}

            @Override
            public void onFocusCleared() {
                presenter.searchPhotos(searchView.getQuery());
            }
        });
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                String feature = null;
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_search_editors:
                        feature = FiveHundredPXClient.Parameters.EDITORS;
                        break;
                    case R.id.action_search_popular:
                        feature = FiveHundredPXClient.Parameters.POPULAR;
                        break;
                    case R.id.action_search_upcoming:
                        feature = FiveHundredPXClient.Parameters.UPCOMING;
                        break;
                }
                presenter.findPhotos(feature);
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
                presenter.searchPhotos(searchView.getQuery());
            }
        });
        swipeToRefresh.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
    }

    private void setupRecyclerView() {
        rvPhotos.setLayoutManager(new GridLayoutManager(this, 2));
        rvPhotos.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.card_view_item_offset));
        rvPhotos.setAdapter(adapter);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        adapter.reload(photos);
        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }
        Log.d(TAG, String.valueOf(photos.size()));
    }

    @Override
    public void showNoResults() {
        Log.d(TAG, String.valueOf("showNoResults"));
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, String.valueOf(error));
    }

    @Override
    public void showNetworkError() {
        Log.d(TAG, String.valueOf("showNetworkError"));
    }


    @Override
    public void show(Photo photo) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(Constants.IntentExtras.PHOTO_ID, photo.getId());
        startActivity(intent);
    }

    @Override
    public void share(Photo photo) {

    }

}
