/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.vs;

import java.util.ArrayList;


/**
 *
 * @author benno
 */
public class BillBoardJsonAdapter extends BillBoard implements BillBoardAdapterIf{
    
    
    
    

    public BillBoardJsonAdapter(String ctxt) {
        super(ctxt);
    }

    

    /**
     * Lesen eines Eintraeges. 
     *
     * @param idx Index des Parameters
     * @param caller_ip IP-Adresse des Aufrufers
     * @return Eintrag als Tabellen-Eintrag
     */
    @Override
    public String readEntry(int idx, String caller_ip) {
        BillBoardEntry bbe = getEntry(idx);
        if (bbe == null) {
            System.err.println("BillBoardServer - readEntry: Objekt null; ggf. ist Idx falsch");
            return null;
        }
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"id\":").append(bbe.id).append(",");
        result.append("\"disable_edits\":").append(Boolean.valueOf(!bbe.belongsToCaller(caller_ip))).append(",");
        result.append("\"text\":\"").append(bbe.text).append("\"");
        result.append("}");
        return result.toString();
    }
    
    /**
     * Lesen eines Eintrags. Der Eintrag wird als html-Tabelle
     * zurückgegeben und kann ohne weiteres in ein html-Dokument
     * eingebunden werden.
     * 
     * @param caller_ip IP-Adresse des Aufrufers
     * @return Eintrag als html-Tabelle
     */
    @Override
    public String readEntries(String caller_ip) {
        StringBuilder result = new StringBuilder();
        result.append("{\"billboard\":[\n");
        for (int i = 0; i < billboard.length; i++) {
            result.append(readEntry(billboard[i].id, caller_ip));
            if(i < billboard.length - 1){
                result.append(",");
            }
            result.append("\n");
        }
        result.append("],\"context\":\"").append(getCtxt()).append("\"}");
        return result.toString();
    };
    
}
