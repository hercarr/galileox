package mx.hercarr.galileox.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hercarr on 7/13/16.
 */
public interface PhotoService {

    @GET("api/")
    Call<PhotoSearchResponse> searchPhotos(@Query("category") String category, @Query("q") String keyword);

}
