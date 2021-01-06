package de.hsos.bsteinka.teamtrainer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MatchDao {

    @Query("SELECT * FROM `match` WHERE id=(:matchId)")
    LiveData<Match> get(int matchId);

    @Query("SELECT * FROM `match` WHERE team_id=(:teamId)")
    LiveData<List<Match>> getForTeam(int teamId);

    @Insert
    void insert(Match match);

    @Update
    void update(Match match);

    @Query("UPDATE `match` SET score=(:score),opponent_score=(:opponentScore) WHERE id =(:matchId)")
    void updateScore(int matchId, int score, int opponentScore);

    @Query("DELETE FROM `match` WHERE id=(:matchId)")
    void delete(int matchId);

    @Query("DELETE FROM `match`")
    void deleteAll();

    @Query("DELETE FROM `match` WHERE team_id=(:teamId)")
    void deleteTeam(int teamId);
}
