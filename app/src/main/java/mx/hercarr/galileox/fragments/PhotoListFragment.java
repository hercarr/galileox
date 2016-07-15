package mx.hercarr.galileox.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.adapters.PhotosAdapter;
import mx.hercarr.galileox.model.Photo;
import mx.hercarr.galileox.presenter.PhotosPresenter;
import mx.hercarr.galileox.view.IPhotosView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements IPhotosView {

    private static final String TAG = "GALILEOX";

    @BindView(R.id.rvPhotos)
    RecyclerView rvPhotos;

    private PhotosPresenter presenter;
    private PhotosAdapter adapter;

    public PhotoListFragment() {}

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setupAdapter();
        setupRecyclerView();
        presenter = new PhotosPresenter(this);
        presenter.searchPhotos();
    }

    private void setupAdapter() {
        adapter = new PhotosAdapter(getActivity(), new ArrayList<Photo>());
    }

    private void setupRecyclerView() {
        rvPhotos.setLayoutManager(new GridLayoutManager(getActivity(), 2));

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
