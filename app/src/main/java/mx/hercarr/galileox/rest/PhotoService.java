package mx.hercarr.galileox.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hercarr on 7/13/16.
 */
public interface PhotoService {

    @GET("photos")
    Call<PhotoSearchResponse> findPhotos(@Query("feature") String feature);

    @GET("photos/search")
    Call<PhotoSearchResponse> searchPhotos(@Query("term") String keyword);

}
