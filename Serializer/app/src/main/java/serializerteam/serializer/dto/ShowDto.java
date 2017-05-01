package serializerteam.serializer.dto;

/**
 * Created by xxx on 2017-05-01.
 */

import java.util.HashMap;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowDto {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("runtime")
    @Expose
    private int runtime;
    @SerializedName("premiered")
    @Expose
    private String premiered;
//    @SerializedName("schedule")
//    @Expose
//    private Schedule schedule;
//    @SerializedName("rating")
//    @Expose
//    private Rating rating;
    @SerializedName("weight")
    @Expose
    private int weight;
//    @SerializedName("network")
//    @Expose
//    private Network network;
    @SerializedName("webChannel")
    @Expose
    private Object webChannel;
//    @SerializedName("externals")
//    @Expose
//    private Externals externals;
    @SerializedName("image")
    @Expose
    private HashMap<String,String> image;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("updated")
    @Expose
    private int updated;
    @SerializedName("_links")
    @Expose
    private HashMap<String,LinkDto> links;
  @SerializedName("_embedded")
  @Expose
  private EmbeddedDto embedded;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

//    public Schedule getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule schedule) {
//        this.schedule = schedule;
//    }

//    public Rating getRating() {
//        return rating;
//    }
//
//    public void setRating(Rating rating) {
//        this.rating = rating;
//    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

//    public Network getNetwork() {
//        return network;
//    }
//
//    public void setNetwork(Network network) {
//        this.network = network;
//    }

    public Object getWebChannel() {
        return webChannel;
    }

    public void setWebChannel(Object webChannel) {
        this.webChannel = webChannel;
    }
//
//    public Externals getExternals() {
//        return externals;
//    }
//
//    public void setExternals(Externals externals) {
//        this.externals = externals;
//    }

    public  HashMap<String,String> getImage() {
        return image;
    }

    public void setImage( HashMap<String,String> image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public HashMap<String,LinkDto> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String,LinkDto> links) {
        this.links = links;
    }

    public EmbeddedDto getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddedDto embedded) {
        this.embedded = embedded;
    }
}