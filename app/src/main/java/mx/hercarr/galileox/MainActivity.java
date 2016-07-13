package mx.hercarr.galileox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mx.hercarr.galileox.rest.FiveHundredPXClient;
import mx.hercarr.galileox.rest.PhotoSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GALILEOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<PhotoSearchResponse> call = FiveHundredPXClient.getPhotoService().search();
        call.enqueue(new Callback<PhotoSearchResponse>() {
            @Override
            public void onResponse(Call<PhotoSearchResponse> call, Response<PhotoSearchResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().getPhotos().size());
            }

            @Override
            public void onFailure(Call<PhotoSearchResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}
