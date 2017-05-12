package serializerteam.serializer.dto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserShowDto {
    @SerializedName("externalShowId")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
