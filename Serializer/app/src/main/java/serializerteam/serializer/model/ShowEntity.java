package serializerteam.serializer.model;

public class ShowEntity {
    private int id;
    private int showId;
    private String name;

    public ShowEntity(int showId, String name, int id) {
        this.showId = showId;
        this.name = name;
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public String getName() {
        return name;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
