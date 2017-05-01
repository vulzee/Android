package serializerteam.serializer.api;

/**
 * Created by xxx on 2017-05-01.
 */

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UrlApi {
    @GET
    public Call<ResponseBody> getResponse(@Url String url);
}
