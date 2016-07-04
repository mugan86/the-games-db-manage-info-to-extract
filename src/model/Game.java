/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author anartzmugika
 */
public class Game {
    private int id;
    private String GameTitle, ReleaseDate;
    private int PlatformId;
    private String Publisher, Developer;
    private Similar Similar;

    public Similar getSimilar() {
        return Similar;
    }

    public void setSimilar(Similar Similar) {
        this.Similar = Similar;
    }
    
     public int getPlatformId() {
        return PlatformId;
    }

    public void setPlatformId(int PlatformId) {
        this.PlatformId = PlatformId;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public String getDeveloper() {
        return Developer;
    }

    public void setDeveloper(String Developer) {
        this.Developer = Developer.replace("'", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameTitle() {
        return GameTitle;
    }

    public void setGameTitle(String GameTitle) {
        this.GameTitle = GameTitle.replace("'", "");
    }

    public String getReleaseDate() {
        if (this.ReleaseDate == null) return "";
        return ReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }
    
}
