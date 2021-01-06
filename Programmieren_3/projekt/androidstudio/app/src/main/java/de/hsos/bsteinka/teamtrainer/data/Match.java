package de.hsos.bsteinka.teamtrainer.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "team_id"),
        indices = {@Index("team_id")})
public class Match {
    @Ignore
    public static final String DATEFORMAT = "%02d.%02d.%04d";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "team_id")
    public int teamId;

    @ColumnInfo(name = "date_year")
    public int dateYear;

    @ColumnInfo(name = "date_month")
    public int dateMonth;

    @ColumnInfo(name = "date_day")
    public int dateDay;

    @ColumnInfo(name = "opponent")
    @NonNull
    public String opponent;

    @ColumnInfo(name = "home_game")
    public boolean isHomeGame;

    @ColumnInfo(name = "referee")
    @Nullable
    public String referee;

    @ColumnInfo(name = "location")
    @Nullable
    public String location;

    @ColumnInfo(name = "score")
    public int score;

    @ColumnInfo(name = "opponent_score")
    public int opponentScore;

    public Match(@NonNull String opponent) {
        this.opponent = opponent;
    }

}
