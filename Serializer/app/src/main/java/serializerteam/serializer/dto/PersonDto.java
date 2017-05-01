package serializerteam.serializer.dto;

/**
 * Created by xxx on 2017-05-01.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class PersonDto {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private HashMap<String,String> image;
//    @SerializedName("_links")
//    @Expose
//    private Links_ links;

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

    public HashMap<String, String> getImage() {
        return image;
    }

    public void setImage(HashMap<String, String> image) {
        this.image = image;
    }
//
//    public Links_ getLinks() {
//        return links;
//    }
//
//    public void setLinks(Links_ links) {
//        this.links = links;
//    }

}
