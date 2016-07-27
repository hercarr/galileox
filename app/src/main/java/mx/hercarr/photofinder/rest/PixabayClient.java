package mx.hercarr.photofinder.rest;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PixabayClient {

    private static final String BASE_URL = "https://pixabay.com/";
    private static final String KEY = "2931831-84f06e086293fb6ddc689b629";
    private static Retrofit retrofit;
    private static OkHttpClient  okHttpClient;
    private static PhotoService photoService;

    private PixabayClient() {

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
                                         .addQueryParameter("key", KEY)
                                         .addQueryParameter("per_page", String.valueOf(Parameters.DEFAULT_RECORDS))
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
        public static final String ANIMALS = "animals";
        public static final String BACKGROUNDS = "backgrounds";
        public static final String COMPUTER = "computer";
        public static final String EDUCATION = "education";
        public static final String FASHION = "fashion";
        public static final String FOOD = "food";
        public static final String MUSIC = "music";
        public static final String NATURE = "nature";
        public static final String PEOPLE = "people";
        public static final String PLACES = "places";
        public static final String SPORTS = "sports";
        public static final String TRAVELS = "travel";
        public static final Integer DEFAULT_RECORDS = 50;
    }

}