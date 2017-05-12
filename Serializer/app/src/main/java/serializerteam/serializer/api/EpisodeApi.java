package serializerteam.serializer.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.PersonDto;

/**
 * Created by xxx on 2017-05-01.
 */

public interface EpisodeApi {
    @GET("episodes/{id}")
    Call<EpisodeDto> getEpisode(@Path("id") int id);
}
