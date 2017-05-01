package serializerteam.serializer.dto;

/**
 * Created by xxx on 2017-05-01.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmbeddedDto {

    @SerializedName("cast")
    @Expose
    private CastDto[] cast = null;

    public CastDto[] getCast() {
        return cast;
    }

    public void setCast(CastDto[] cast) {
        this.cast = cast;
    }

}