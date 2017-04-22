package serializerteam.serializer.model;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * Created by android on 2017-03-10.
 */

public class ShowListItem {
    private long id;
    private String title;
    private String description;
    private String imageUrl;

    public ShowListItem(long id, String title, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

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
