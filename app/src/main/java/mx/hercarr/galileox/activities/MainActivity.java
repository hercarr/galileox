package mx.hercarr.galileox.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mx.hercarr.galileox.R;
import mx.hercarr.galileox.fragments.PhotoListFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setupToolbar();
        loadDefaultFragment();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void loadDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, PhotoListFragment.newInstance())
            .commit();
    }

}
