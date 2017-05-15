package serializerteam.serializer.api;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.PersonDto;

/**
 * Created by xxx on 2017-05-01.
 */

public interface EpisodeApi {
    @GET("episodes/{id}")
    Call<EpisodeDto> getEpisode(@Path("id") int id);
    @GET("shows/{id}/episodesbydate")//yyyy-mm-dd
    Call<EpisodeDto> getEpisodeByDate(@Path("id")int showId, @Query("date") String date);
}
