package de.hsos.bsteinka.teamtrainer.data;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "team_id"),
        indices = {@Index("team_id")})
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nr")
    @Nullable
    public Integer number;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "team_id")
    @Nullable
    public Integer teamId;

    @ColumnInfo(name = "address")
    @Nullable
    public String address;

    @ColumnInfo(name = "phone_number")
    @Nullable
    public String phoneNumber;

    @ColumnInfo(name = "email")
    @Nullable
    public String email;

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
