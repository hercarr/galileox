package mx.hercarr.galileox.rest;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hercarr on 7/13/16.
 */
public class FiveHundredPXClient {

    private static final String BASE_URL = "https://api.500px.com/v1/";
    private static final String CONSUMER_KEY = "ofVPy9cubsigZCiCcL7fFLsjAIqYHXXWzh1zj70K";
    private static Retrofit retrofit;
    private static OkHttpClient  okHttpClient;
    private static PhotoService photoService;

    private FiveHundredPXClient() {

    }

    static {
        setupOkHttp();
        setupRetrofit();
        setupServices();
    }

    private static void setupOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();

                HttpUrl url = originalUrl.newBuilder()
                                         .addQueryParameter("consumer_key", CONSUMER_KEY)
                                         .addQueryParameter("rpp", Parameters.DEFAULT_RECORDS)
                                         .addQueryParameter("image_size", Parameters.DEFAULT_IMAGE_SIZE)
                                         .build();

                Request.Builder builder = originalRequest.newBuilder()
                                                         .url(url);

                Request request = builder.build();
                return  chain.proceed(request);
            }
        });
        okHttpClient = builder.build();
    }

    private static void setupRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

    private static void  setupServices() {
        photoService = retrofit.create(PhotoService.class);
    }

    public static PhotoService getPhotoService() {
        return photoService;
    }

    public static class Parameters {
        public static final String EDITORS = "editors";
        public static final String POPULAR = "popular";
        public static final String UPCOMING = "upcoming";
        public static final String DEFAULT_RECORDS = "100";
        public static final String DEFAULT_IMAGE_SIZE = "4";
    }

}