package serializerteam.serializer.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xxx on 2017-05-01.
 */
///class generator http://www.jsonschema2pojo.org
public final class ApiSettings {
    static String API_URL="https://api.tvmaze.com";
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static public ShowsApi showsApiService = retrofit.create(ShowsApi.class);
    static public PeopleApi peopleApi = retrofit.create(PeopleApi.class);
    static public UrlApi urlApi = retrofit.create(UrlApi.class);
    static public EpisodeApi episodeApi = retrofit.create(EpisodeApi.class);
}
