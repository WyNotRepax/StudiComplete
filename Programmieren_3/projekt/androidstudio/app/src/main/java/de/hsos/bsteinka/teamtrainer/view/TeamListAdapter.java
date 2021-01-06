package de.hsos.bsteinka.teamtrainer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Team;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    private TeamListAdapter.ViewHolderClickHandler onViewHolderClickHandler;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView teamDescriptionView;
        private final TextView teamSportView;
        private final TextView teamClubView;
        private final ConstraintLayout teamItemView;

        private int teamId;

        private ViewHolder(View itemView) {
            super(itemView);
            teamItemView = (ConstraintLayout) itemView;
            teamDescriptionView = itemView.findViewById(R.id.teamDescription);
            teamSportView = itemView.findViewById(R.id.teamSport);
            teamClubView = itemView.findViewById(R.id.teamClub);

        }

        private void onClick(View v) {
            if (onViewHolderClickHandler != null) {
                onViewHolderClickHandler.onClick(this);
            }
        }

        public int getTeamId() {
            return teamId;
        }
    }

    private final LayoutInflater layoutInflater;

    private List<Team> teamList;

    public TeamListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.team_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (teamList != null) {
            Team current = teamList.get(position);
            holder.teamId = current.id;
            holder.teamDescriptionView.setText(current.description);
            holder.teamSportView.setText(current.sport);
            holder.teamClubView.setText(current.club);
            holder.teamItemView.setOnClickListener(holder::onClick);
        }
    }


    @Override
    public int getItemCount() {
        if (teamList != null) {
            return teamList.size();
        }
        return 0;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    public void setOnViewHolderClickHandler(ViewHolderClickHandler viewHolderClickHandler) {
        this.onViewHolderClickHandler = viewHolderClickHandler;
    }

    public interface
    ViewHolderClickHandler {
        void onClick(TeamListAdapter.ViewHolder v);
    }

}