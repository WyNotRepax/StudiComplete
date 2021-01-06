package de.hsos.prog3.bsteinka.ab07.swingolfsheet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import de.hsos.prog3.bsteinka.ab07.swingolfsheet.data.Sheet;
import de.hsos.prog3.bsteinka.ab07.swingolfsheet.data.SheetCreator;
import de.hsos.prog3.bsteinka.ab07.swingolfsheet.view.SynchronizedHorizontalScrollView;
import de.hsos.prog3.bsteinka.ab07.swingolfsheet.view.SynchronizedScrollView;

public class GameFragment extends Fragment {

    private Sheet sheet;

    public GameFragment(Sheet s) {
        this.sheet = s;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout displaySheet = (ConstraintLayout) inflater.inflate(R.layout.game_view, container, false);
        ((SynchronizedScrollView) displaySheet.findViewById(R.id.valueScrollViewVert)).setSync(displaySheet.findViewById(R.id.playerScrollView));
        ((SynchronizedHorizontalScrollView) displaySheet.findViewById(R.id.valueScrollViewHor)).setSync(displaySheet.findViewById(R.id.holeScrollView));

        TableLayout playerTable = displaySheet.findViewById(R.id.playerTable);
        for (int holeIndex = 0; holeIndex < sheet.getPlayerNum(); holeIndex++) {
            TableRow tr = new TableRow(getContext());
            TextView tv = (TextView) inflater.inflate(R.layout.game_view_name, tr, false);
            tr.addView(tv);
            playerTable.addView(tr);
            tv.setText(sheet.getPlayer(holeIndex));
        }

        TableLayout holeTable = displaySheet.findViewById(R.id.holeTable);
        TableRow tr = new TableRow(getContext());
        for (int holeIndex = 0; holeIndex < sheet.getHoleNum(); holeIndex++) {
            TextView tv = (TextView) inflater.inflate(R.layout.game_view_other, tr, false);
            tr.addView(tv);
            tv.setText(String.format(Locale.getDefault(), "%s %d", getString(R.string.holeNr), holeIndex + 1));
        }
        holeTable.addView(tr);

        TableLayout scoreTable = displaySheet.findViewById(R.id.scoreTable);
        for (int playerIndex = 0; playerIndex < sheet.getPlayerNum(); playerIndex++) {
            tr = new TableRow(getContext());
            for (int holeIndex = 0; holeIndex < sheet.getHoleNum(); holeIndex++) {
                EditText tv = (EditText) inflater.inflate(R.layout.game_view_score, tr, false);
                tv.addTextChangedListener(new scoreTextWatcher(playerIndex, holeIndex));
                tv.setText(String.valueOf(sheet.getScore(playerIndex, holeIndex)));
                tr.addView(tv);
            }
            scoreTable.addView(tr);
        }
        return displaySheet;
    }

    @Override
    public void onDestroyView() {
        SheetCreator.save(sheet, getContext());
        super.onDestroyView();
    }

    private class scoreTextWatcher implements TextWatcher {

        private final int playerIndex, holeIndex;

        public scoreTextWatcher(int playerIndex, int holeIndex) {
            this.playerIndex = playerIndex;
            this.holeIndex = holeIndex;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                int i = Integer.parseInt(s.toString());
                sheet.setScore(playerIndex, holeIndex, i);

            } catch (NumberFormatException e) {
                s.clear();
                s.append(String.valueOf(0));
            }
        }
    }
}
