package mx.hercarr.galileox.rest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hercarr on 7/13/16.
 */
public interface PhotoService {

    @GET("photos")
    Call<PhotoSearchResponse> searchPhotos();

}
