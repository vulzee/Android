package serializerteam.serializer.model;

public class SettingsEntity {

    private int id;
    private boolean areNotificationsOn;
    private int time;

    public SettingsEntity(int id, boolean areNotificationsOn, int time) {
        this.id = id;
        this.areNotificationsOn = areNotificationsOn;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAreNotificationsOn() {
        return areNotificationsOn;
    }

    public void setAreNotificationsOn(boolean areNotificationsOn) {
        this.areNotificationsOn = areNotificationsOn;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
