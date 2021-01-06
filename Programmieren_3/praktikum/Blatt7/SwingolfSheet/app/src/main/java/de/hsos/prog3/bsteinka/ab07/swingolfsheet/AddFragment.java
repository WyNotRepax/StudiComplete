package de.hsos.prog3.bsteinka.ab07.swingolfsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import de.hsos.prog3.bsteinka.ab07.swingolfsheet.data.SheetCreator;

public class AddFragment extends Fragment {

    private LinearLayout playerList;
    private LinearLayout holeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_sheet, container, false);
        v.findViewById(R.id.addPlayerButton).setOnClickListener(this::onAddPlayerButtonClick);
        v.findViewById(R.id.addHoleButton).setOnClickListener(this::onAddHoleButtonClick);
        v.findViewById(R.id.cancelButton).setOnClickListener(this::onCancelButtonClick);
        v.findViewById(R.id.confirmButton).setOnClickListener(this::onConfirmButtonClick);
        playerList = v.findViewById(R.id.playerList);
        holeList = v.findViewById(R.id.holeList);
        return v;
    }

    public void onAddPlayerButtonClick(View v) {
        ConstraintLayout cv = (ConstraintLayout) getLayoutInflater().inflate(R.layout.added_player, null);
        cv.findViewById(R.id.minusPlayerButton).setOnClickListener(this::onMinusButtonClick);
        playerList.addView(cv);
    }

    public void onAddHoleButtonClick(View v) {
        ConstraintLayout cv = (ConstraintLayout) getLayoutInflater().inflate(R.layout.added_hole, null);
        cv.findViewById(R.id.minusHoleButton).setOnClickListener(this::onMinusButtonClick);
        holeList.addView(cv);
    }

    public void onMinusButtonClick(View v) {
        ((ViewGroup) v.getParent().getParent()).removeView((View) v.getParent());
    }

    public void onCancelButtonClick(View v) {
        getFragmentManager().popBackStack();
    }

    public void onConfirmButtonClick(View v) {
        SheetCreator.save(SheetCreator.from(this), getContext());
        getFragmentManager().popBackStack();
    }

}
