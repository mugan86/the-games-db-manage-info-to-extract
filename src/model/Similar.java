/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author anartzmugika
 */
public class Similar {
    private int SimilarCount;
    private ArrayList<Platform> Game;

    public int getSimilarCount() {
        return SimilarCount;
    }

    public void setSimilarCount(int SimilarCount) {
        this.SimilarCount = SimilarCount;
    }

    public ArrayList<Platform> getGame() {
        return Game;
    }

    public void setGame(ArrayList<Platform> Game) {
        this.Game = Game;
    }
}
