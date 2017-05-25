package serializerteam.serializer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Patryk on 2017-05-25.
 */

public class SettingsDto {
    @SerializedName("Time")
    @Expose
    private int time;
    @SerializedName("AreNotificationsOn")
    @Expose
    private boolean areNotificationsOn;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isAreNotificationsOn() {
        return areNotificationsOn;
    }

    public void setAreNotificationsOn(boolean areNotificationsOn) {
        this.areNotificationsOn = areNotificationsOn;
    }
}
