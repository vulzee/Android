package serializerteam.serializer.dto;

/**
 * Created by xxx on 2017-05-01.
 */

public class SearchedShow {
    double score;
    ShowDto show;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ShowDto getShow() {
        return show;
    }

    public void setShow(ShowDto show) {
        this.show = show;
    }
}
