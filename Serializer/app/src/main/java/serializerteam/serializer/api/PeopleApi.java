package serializerteam.serializer.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import serializerteam.serializer.dto.PersonDto;
import serializerteam.serializer.dto.SearchedShow;
import serializerteam.serializer.dto.ShowDto;

/**
 * Created by xxx on 2017-05-01.
 */


public interface PeopleApi {

    @GET("people/{id}")
    Call<PersonDto> getPerson(@Path("id") int id);
}
