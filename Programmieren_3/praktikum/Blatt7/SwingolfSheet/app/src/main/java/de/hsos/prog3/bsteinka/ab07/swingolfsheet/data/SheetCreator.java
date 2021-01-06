package de.hsos.prog3.bsteinka.ab07.swingolfsheet.data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import de.hsos.prog3.bsteinka.ab07.swingolfsheet.R;

public class SheetCreator {
    public static Sheet from(Fragment f) {
        LinearLayout playerList = f.getView().findViewById(R.id.playerList);
        LinearLayout holeList = f.getView().findViewById(R.id.holeList);
        TextInputLayout nameView = f.getView().findViewById(R.id.sheetName);
        String name = nameView.getEditText().getText().toString();
        if (playerList == null || holeList == null) {
            return null;
        }
        String[] players = new String[playerList.getChildCount() + 1];
        players[0] = f.getString(R.string.par);
        for (int i = 1; i < players.length; i++) {
            View player = playerList.getChildAt(i - 1);
            TextInputLayout textInput = player.findViewById(R.id.addedPlayerInput);
            String playerName = textInput.getEditText().getText().toString();
            players[i] = playerName;
        }
        int[] holePars = new int[holeList.getChildCount()];
        for (int i = 0; i < holePars.length; i++) {
            View hole = holeList.getChildAt(i);
            TextInputLayout textInput = hole.findViewById(R.id.addedHoleInput);
            String holeString = textInput.getEditText().getText().toString();
            int holePar;
            try {
                holePar = Integer.parseInt(holeString);
            } catch (NumberFormatException e) {
                holePar = 0;
            }
            holePars[i] = holePar;
        }
        Log.i("players", Arrays.toString(players));
        Log.i("holePars", Arrays.toString(holePars));
        Sheet sheet = new Sheet(name, players, holePars.length);
        for (int i = 0; i < holePars.length; i++) {
            sheet.setScore(0, i, holePars[i]);
        }
        return sheet;
    }


    public static void save(Sheet sheet, Context ctx) {
        try (FileOutputStream fos = ctx.openFileOutput(String.format("%s.sav", sheet.getName()), Context.MODE_PRIVATE); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(sheet);
        } catch (IOException e) {
            Log.e("save", e.getClass().getName(), e);
        }
    }

    public static Sheet[] loadAll(Context ctx) {
        String[] sheetFiles = ctx.getFilesDir().list((dir, name) -> name.toLowerCase().endsWith(".sav"));
        ArrayList<Sheet> sheets = new ArrayList<>();
        for (String sheetFile : sheetFiles) {
            try (FileInputStream fis = ctx.openFileInput(sheetFile); ObjectInputStream ois = new ObjectInputStream(fis)) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Sheet) {
                        sheets.add((Sheet) obj);
                    } else {
                        Log.e("load", "Non Sheet object save");
                    }
                } catch (ClassNotFoundException e) {
                    Log.e("load", "Malformed save", e);
                }
            } catch (IOException e) {
                Log.e("load", e.getClass().getName(), e);
            }
        }
        Collections.reverse(sheets);
        return sheets.toArray(new Sheet[0]);
    }
}

