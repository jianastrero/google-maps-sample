package dev.jianastrero.googlemapstest.api;

import java.util.List;

import dev.jianastrero.googlemapstest.model.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApi {
    @GET("pokemon")
    Call<List<Pokemon>> getPokemons();

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);
}
