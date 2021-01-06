package de.hsos.bsteinka.teamtrainer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Player;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {
    private ViewHolderClickHandler viewHolderClickHandler;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final TextView playerFirstNameView;
        private final TextView playerLastNameView;


        private int playerId;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            playerFirstNameView = itemView.findViewById(R.id.playerFirstName);
            playerLastNameView = itemView.findViewById(R.id.playerLastName);
        }

        private void onClick(View v) {
            if (viewHolderClickHandler != null) {
                viewHolderClickHandler.onClick(this);
            }
        }

        public int getPlayerId() {
            return playerId;
        }

    }


    private final LayoutInflater layoutInflater;

    private List<Player> playerList;

    public PlayerListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.player_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (playerList != null) {
            Player current = playerList.get(position);
            holder.playerId = current.id;
            holder.playerFirstNameView.setText(current.firstName);
            holder.playerLastNameView.setText(current.lastName);
            holder.itemView.setOnClickListener(holder::onClick);
        }
    }

    @Override
    public int getItemCount() {
        if (playerList != null) {
            return playerList.size();
        }
        return 0;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
        notifyDataSetChanged();
    }

    public void setOnViewHolderClickHandler(ViewHolderClickHandler viewHolderClickHandler) {
        this.viewHolderClickHandler = viewHolderClickHandler;
    }

    public interface
    ViewHolderClickHandler {
        void onClick(PlayerListAdapter.ViewHolder v);
    }
}
