package mx.hercarr.photofinder.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService {

    @GET("api/")
    Call<PhotoSearchResponse> searchPhotos(@Query("page") int page,  @Query("category") String category, @Query("q") String keyword);

}
