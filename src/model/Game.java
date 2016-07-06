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
    private int similar1, similar2;
    private String genre1, genre2;
    private int players;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getSimilar1() {
        if (similar1 == 0) return -1;
        return similar1;
    }

    public void setSimilar1(int similar1) {
        this.similar1 = similar1;
    }

    public int getSimilar2() {
        if (similar2 == 0) return -1;
        return similar2;
    }

    public void setSimilar2(int similar2) {
        this.similar2 = similar2;
    }

    public String getGenre1() {
        if (genre1 == null) return "";
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        if (genre2 == null) return "";
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
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
        this.ReleaseDate =  ReleaseDate.replace("/", "-");
    }
    
    public String toString()
    {
        return this.getId() + " / " + this.getGameTitle() + " / " + this.getReleaseDate();
    }
    
}
