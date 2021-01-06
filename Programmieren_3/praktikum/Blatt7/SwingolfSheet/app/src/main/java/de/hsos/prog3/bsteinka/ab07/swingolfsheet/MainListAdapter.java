package de.hsos.prog3.bsteinka.ab07.swingolfsheet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.prog3.bsteinka.ab07.swingolfsheet.data.Sheet;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder> {

    private Sheet[] sheets;
    private final FragmentManager manager;


    public static class MainListViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout itemView;

        public MainListViewHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    private static class OnItemClickListener implements View.OnClickListener {

        public FragmentManager manager;
        public Sheet sheet;

        public OnItemClickListener(FragmentManager manager, Sheet sheet) {
            this.manager = manager;
            this.sheet = sheet;
        }

        @Override
        public void onClick(View v) {
            Fragment f = new GameFragment(sheet);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.mainFrameLayout, f);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public MainListAdapter(Sheet[] sheets, FragmentActivity a) {
        this.sheets = sheets;
        manager = a.getSupportFragmentManager();
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        MainListViewHolder vh = new MainListViewHolder(constraintLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
        TextView t = holder.itemView.findViewById(R.id.listItemName);
        holder.itemView.setOnClickListener(new OnItemClickListener(manager, sheets[position]));
        t.setText(sheets[position].getName());
    }

    @Override
    public int getItemCount() {
        return sheets.length;
    }
}
