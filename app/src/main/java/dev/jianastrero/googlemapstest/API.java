package dev.jianastrero.googlemapstest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

import dev.jianastrero.googlemapstest.api.PokemonApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static final String BASE_URL = "https://ffd5-136-158-39-123.ngrok-free.app/";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.LENIENT)
            .create();

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OK_HTTP_CLIENT)
            .build();

    public static PokemonApi pokemonApi() {
        return RETROFIT.create(PokemonApi.class);
    }
}
