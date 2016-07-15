package mx.hercarr.galileox.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.adapters.PhotosAdapter;
import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.presenter.PhotosPresenter;
import mx.hercarr.galileox.view.IPhotosView;

public class MainActivity
        extends AppCompatActivity
        implements IPhotosView {

    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;

    private PhotosPresenter presenter;
    private PhotosAdapter adapter;

    private static final String TAG = "GALILEOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setupAdapter();
        setupRecyclerView();
        presenter = new PhotosPresenter(this);
        presenter.searchPhotos();
    }

    private void setupAdapter() {
        adapter = new PhotosAdapter(this, new ArrayList<Photo>());
    }

    private void setupRecyclerView() {
        rvPhotos.setLayoutManager(new GridLayoutManager(this, 2));

        rvPhotos.setAdapter(adapter);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        adapter.reload(photos);
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

}
