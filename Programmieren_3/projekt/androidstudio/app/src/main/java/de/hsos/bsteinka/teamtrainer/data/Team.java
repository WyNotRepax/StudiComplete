package de.hsos.bsteinka.teamtrainer.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Team {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "description")
    @NonNull
    public String description;

    @ColumnInfo(name = "club")
    @Nullable
    public String club;

    @ColumnInfo(name = "sport")
    @Nullable
    public String sport;

    @ColumnInfo(name = "hidden")
    public boolean hidden;

    public Team(String description, String club, String sport) {
        this.description = description;
        this.club = club;
        this.sport = sport;
        this.hidden = false;
    }
}
