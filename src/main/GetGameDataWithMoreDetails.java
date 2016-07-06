/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.ConnectionManager;

/**
 *
 * @author anartzmugika
 */
public class GetGameDataWithMoreDetails {
    public static void main (String [] args)
    {
        ConnectionManager conn = new ConnectionManager();
        conn.getGamesList("SELECT * FROM games WHERE revised = 0 LIMIT 0,500");
    }
}
