package serializerteam.serializer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class EpisodeDto {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("season")
    @Expose
    private int season;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("airdate")
    @Expose
    private String airdate;
    @SerializedName("airtime")
    @Expose
    private String airtime;
    @SerializedName("airstamp")
    @Expose
    private String airstamp;
    @SerializedName("runtime")
    @Expose
    private int runtime;
    @SerializedName("image")
    @Expose
    private HashMap<String,String> image;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("_links")
    @Expose
    private HashMap<String,LinkDto> links;

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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirstamp() {
        return airstamp;
    }

    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public HashMap<String,String> getImage() {
        return image;
    }

    public void setImage(HashMap<String,String> image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public HashMap<String,LinkDto> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String,LinkDto> links) {
        this.links = links;
    }

}