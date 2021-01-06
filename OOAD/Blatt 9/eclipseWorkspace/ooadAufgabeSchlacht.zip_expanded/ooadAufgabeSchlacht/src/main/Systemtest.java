package main;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class Systemtest {
    private Main dialog;
    
    //Aufbau: {  {Anzahl Kaempfer}, {waffe, fuer,Waffe, fuer, ...}
    //          ,{ruestung, fuer, ruestung, fuer, ...}, {DuplosenRest} }
    private int[][][] szenarien = {
                  {{1,0,0},{},{},{480}}
                , {{0,1,0},{},{},{490}}
                , {{0,0,1},{},{},{500}}
                , {{1,0,0},{0,0},{},{450}}
                , {{1,0,0},{1,0},{},{410}}
                , {{1,0,0},{2,0},{},{390}}
                , {{1,0,0},{3,0},{},{360}}
                , {{1,0,0},{},{0,0},{460}}
                , {{1,0,0},{},{1,0},{430}}
                , {{1,0,0},{},{2,0},{410}}
                , {{1,0,0},{},{3,0},{380}}
                , {{1,0,0},{3,0,3,0},{3,0},{140}}
                , {{1,0,0},{3,0,3,0,0,0},{3,0,2,0},{40}}
                , {{0,1,0},{3,0},{},{370}}
                , {{0,1,0},{3,0},{3,0},{270}}
                , {{0,0,1},{3,0,3,0},{3,0},{160}}
                , {{0,0,1},{},{3,0},{400}}
                , {{1,1,1},{3,0,2,1,0,2},{1,0,1,1,0,2},{-90}}
                , {{0,3,0},{3,0,2,1,0,2},{1,0,1,1,0,2},{-90}}
                , {{1,1,0},{2,0,3,1},{3,0,0,1,0,1,0,1},{0}}
    };
    
    @Rule
    public final SystemOutRule systemOutMock = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInMock
            = TextFromStandardInputStream.emptyStandardInputStream();
    
    @Before
    public void setUp(){
        this.dialog = new Main();
    }

    private String auswahl(int[][] szene){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< 3; i++){
            sb.append("Es werden " + szene[0][i]+ " Kaempfer vom Typ " + i + " ausgewaehlt\n");
        }
        for(int i=0; i< szene[1].length; i=i+2){
            sb.append("Kaempfer Nr." + szene[1][i+1] + " erhaelt Waffe " + szene[1][i] + "\n");
        }
        for(int i=0; i< szene[2].length; i=i+2){
            sb.append("Kaempfer Nr." + szene[2][i+1] + " erhaelt Waffe " + szene[2][i] + "\n");
        }
        String erg = sb.append("Es bleiben " + szene[3][0] + " Duplosen").toString();
        return erg;
}
    
    private void ruestung(int wahl, int kaempfer){
        String[] inputs = {""+wahl, ""+kaempfer};
        this.systemInMock.provideLines(inputs);
        this.dialog.neueRuestung();
    }
 
    private void waffe(int wahl, int kaempfer){
        String[] inputs = {""+wahl, ""+kaempfer};
        this.systemInMock.provideLines(inputs);
        this.dialog.neueWaffe();
    } 
    
    private void soeldner(int wahl){
        String[] inputs = {""+wahl};
        this.systemInMock.provideLines(inputs);
        this.dialog.neuerSoeldner();
    }   
    
    private Object szenarien(){
        return this.szenarien;
    }
       
    @Test
    @Parameters(method = "szenarien")
    public void testSzenarienstarten(int[][] szene){ 
        this.dialog = new Main(); 
        StringBuilder sb = new StringBuilder();
        for(int j=0; j< 3; j++){
            for(int i=0; i< szene[0][j]; i++){
                this.soeldner(j);
            }
        }
        for(int i=0; i< szene[1].length; i=i+2){
            this.waffe(szene[1][i], szene[1][i+1]);
        }
        for(int i=0; i< szene[2].length; i=i+2){
            this.ruestung(szene[2][i], szene[2][i+1]);
        }
        Assert.assertEquals(this.auswahl(szene)
                , szene[3][0], this.dialog.getDuplosen());               
    }
    
    @Test
    public void testDurchlaufendesSpiel(){
        String[] inputs = {"1","0","1","1","2","2","0","2","3","1"
                ,"3","3","0","3","0","1","3","0","1","3","0","1","0"};
        this.systemInMock.provideLines(inputs);
        this.dialog.starten();
        Assert.assertTrue("Einfaches Spiel lauft nicht durch.",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Main.25")));
    }
    
    @Test
    public void testNichtDurchlaufendesSpiel(){
        String[] inputs = {"1","0","1","1","2","2","0","2","3","1"
                ,"3","3","0","3","0","1","3","0","1","3","0","1","3","0","1"};
        this.systemInMock.provideLines(inputs);
        this.dialog.starten();
        Assert.assertTrue("ZUviel Geld ausgegeben, Spiel laeuft trotzdem.",
                this.systemOutMock.getLog()
                        .contains(Messages.getString("Main.16")));
    }       
}
