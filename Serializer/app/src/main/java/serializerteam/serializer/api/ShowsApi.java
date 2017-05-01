package serializerteam.serializer.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;

/**
 * Created by xxx on 2017-05-01.
 */

public interface ShowsApi {
    @GET("search/shows")
    Call<SearchedShow[]> searchShows(@Query("q") String name);
    @GET("shows/{id}")
    Call<ShowDto> getShow(@Path("id") int id);
    @GET("shows/{id}?embed=cast")
    Call<ShowDto> getShowWithCast(@Path("id") int id);
}



