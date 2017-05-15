package serializerteam.serializer.model.episode;

/**
 * Created by android on 2017-03-10.
 */

public class EpisodeListItem {
    private long id;
    private String title;
    private String description;
    private String showTime;
    private String imageUrl;

    public EpisodeListItem(long id, String title, String description, String imageUrl, String showTime) {
        this.id = id;
        this.title = title;
        this.showTime = showTime;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }
    public String getShowTime () {return  showTime;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }
}
