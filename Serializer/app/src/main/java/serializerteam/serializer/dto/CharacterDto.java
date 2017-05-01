package serializerteam.serializer.dto;

/**
 * Created by xxx on 2017-05-01.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CharacterDto {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
//    @SerializedName("image")
//    @Expose
//    private Object image;
//    @SerializedName("_links")
//    @Expose
//    private Links__ links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Object getImage() {
//        return image;
//    }
//
//    public void setImage(Object image) {
//        this.image = image;
//    }

//    public Links__ getLinks() {
//        return links;
//    }
//
//    public void setLinks(Links__ links) {
//        this.links = links;
//    }

}