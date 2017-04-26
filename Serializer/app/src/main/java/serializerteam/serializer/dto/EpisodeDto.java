package com.example;

        import java.util.HashMap;
        import java.util.Map;

public class EpisodeDto {

    
    private int id;
    
    private String url;
   
    private String name;
   
    private int season;
   
    private int number;
   
    private String airdate;
   
    private String airtime;
   
    private String airstamp;
   
    private int runtime;
   
    private Object image;
   
    private String summary;
   

    public EpisodeDto() {
    }

    /**
     *
     * @param summary
     * @param id
     * @param airtime
     * @param airstamp
     * @param season
     * @param name
     * @param image
     * @param runtime
     * @param number
     * @param url
     * @param airdate
     */
    public EpisodeDto(int id, String url, String name, int season, int number, String airdate, String airtime, String airstamp, int runtime, Object image, String summary) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.season = season;
        this.number = number;
        this.airdate = airdate;
        this.airtime = airtime;
        this.airstamp = airstamp;
        this.runtime = runtime;
        this.image = image;
        this.summary = summary;
    }

   
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

   
    public Object getImage() {
        return image;
    }

   
    public void setImage(Object image) {
        this.image = image;
    }

   
    public String getSummary() {
        return summary;
    }

   
    public void setSummary(String summary) {
        this.summary = summary;
    }

}