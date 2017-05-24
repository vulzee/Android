package serializerteam.serializer.api;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import serializerteam.serializer.dto.SettingsDto;
import serializerteam.serializer.dto.UserShowDto;

public interface UsersApi {

    @FormUrlEncoded
    @POST("/api/AddShowForUser")
    Call<ResponseBody> addShowForUser(@Field("ExternalUserId") String externalUserId, @Field("ExternalShowId") String externalShowId);

    @FormUrlEncoded
    @POST("/api/IsShowInUsersFavourites")
    Call<ResponseBody> isShowInUsersFavourites(@Field("ExternalUserId") String externalUserId, @Field("ExternalShowId") String externalShowId);

    @FormUrlEncoded
    @POST("/api/DeleteShowForUser")
    Call<ResponseBody> deleteShowForUser(@Field("ExternalUserId") String externalUserId, @Field("ExternalShowId") String externalShowId);

    @GET("/api/GetUserShows/{externalUserId}")
    Call<int[]> getUserShows(@Path("externalUserId") String externalUserId);

    @GET("/api/GetSettings/{externalUserId}")
    Call<SettingsDto> getSettings(@Path("externalUserId") String externalUserId);

    @FormUrlEncoded
    @POST("/api/SaveSettings")
    Call<ResponseBody> saveSettings(@Field("Time") int time, @Field("AreNotificationsOn") boolean areNotificationsOn, @Field("ExternalUserId") String externalUserId);
}
