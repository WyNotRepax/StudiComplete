package de.hsos.bsteinka.teamtrainer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Match;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.ViewHolder> {

    private ViewHolderClickHandler viewHolderClickHandler;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final TextView opponentTextView;
        private final TextView dateTextView;

        private int matchId;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            opponentTextView = itemView.findViewById(R.id.matchOpponent);
            dateTextView = itemView.findViewById(R.id.matchDate);

        }

        private void onClick(View v) {
            if (viewHolderClickHandler != null) {
                viewHolderClickHandler.onClick(this);
            }
        }

        public int getMatchId() {
            return matchId;
        }

    }


    private final LayoutInflater layoutInflater;

    private List<Match> matchList;

    public MatchListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.match_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (matchList != null) {
            Match current = matchList.get(position);
            holder.matchId = current.id;
            holder.opponentTextView.setText(current.opponent);
            holder.dateTextView.setText(String.format(Locale.getDefault(), Match.DATEFORMAT, current.dateDay, current.dateMonth + 1, current.dateYear));
            holder.itemView.setOnClickListener(holder::onClick);
        }
    }

    @Override
    public int getItemCount() {
        if (matchList != null) {
            return matchList.size();
        }
        return 0;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
        notifyDataSetChanged();
    }

    public void setOnViewHolderClickHandler(ViewHolderClickHandler viewHolderClickHandler) {
        this.viewHolderClickHandler = viewHolderClickHandler;
    }

    public interface
    ViewHolderClickHandler {
        void onClick(MatchListAdapter.ViewHolder v);
    }
}
