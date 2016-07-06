/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.ConnectionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anartzmugika
 */
public class InsertDataInSelectTable {
    public static void main(String [] args)
    {
        File folder = new File("sql");
        File [] listOfFiles = folder.listFiles();
        System.out.println("listOfFiles: " + listOfFiles.length);
        ConnectionManager con = new ConnectionManager();
        int count = 0;
        for (File listOfFile : listOfFiles) {
            
            if (listOfFile.isFile() && !listOfFile.getName().equals(".DS_Store")) {

                FileReader fr = null;
                Scanner scan = null;
                try {
                    //if (type == 1 && listOfFile.getName().contains(String.valueOf(year)))
                    /*fr = new FileReader("sql/" + listOfFile.getName());
                    //Leemos el fichero y lo mostramos por pantalla
                    int valor=fr.read();
                    String text = "";
                    while(valor!=-1){
                        //System.out.print((char)valor);
                        valor=fr.read();
                        text = text + (char)valor;
                        
                    }
                    System.out.println(text.substring(0,text.length()-1));
                    
                    System.out.println(text);*/
                    
                    scan = new Scanner(new File("sql/" + listOfFile.getName()));
                    while(scan.hasNextLine()){
                        String line = scan.nextLine();
                        if (line != null)
                        {
                            System.out.println(line);
                            
                            
                            con.insert(line);
                        }
                        //Here you can manipulate the string the way you want
                    }
                    //files_to_read.add(listOfFile.getName());
                    count++;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InsertDataInSelectTable.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InsertDataInSelectTable.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    scan.close();
                }
            } 
        }
    }
}
