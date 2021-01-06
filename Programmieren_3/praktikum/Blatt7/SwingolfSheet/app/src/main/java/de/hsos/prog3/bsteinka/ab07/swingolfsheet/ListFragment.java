package de.hsos.prog3.bsteinka.ab07.swingolfsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.prog3.bsteinka.ab07.swingolfsheet.data.SheetCreator;


public class ListFragment extends Fragment {

    private ConstraintLayout mainList;
    private RecyclerView mainListRecyclerView;
    private ViewGroup container;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.container = container;
        this.mainList = (ConstraintLayout) inflater.inflate(R.layout.main_list, container, false);
        mainList.findViewById(R.id.addButton).setOnClickListener(this::addOnClick);
        mainListRecyclerView = mainList.findViewById(R.id.mainListRecyclerView);
        mainListRecyclerView.setHasFixedSize(true); // Size doesn't change
        mainListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Linear List Layout
        mainListRecyclerView.setAdapter(new MainListAdapter(SheetCreator.loadAll(getContext()), getActivity())); // Custom Display for the List
        return mainList;
    }

    public void addOnClick(View v) {
        Fragment f = new AddFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, f);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
