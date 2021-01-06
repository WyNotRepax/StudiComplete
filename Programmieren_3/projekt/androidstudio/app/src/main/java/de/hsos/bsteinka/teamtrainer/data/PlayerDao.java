package de.hsos.bsteinka.teamtrainer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {


    @Query("SELECT * FROM player WHERE id=(:playerId)")
    LiveData<Player> get(int playerId);

    @Query(("SELECT * FROM player ORDER BY last_name COLLATE NOCASE ASC"))
    LiveData<List<Player>> getAll();

    @Query("SELECT * FROM player WHERE team_id=(:teamId)")
    LiveData<List<Player>> getForTeam(int teamId);

    @Insert
    void insert(Player player);

    @Update
    void update(Player player);

    @Query("UPDATE player SET team_id=(:teamId) WHERE id=(:playerId)")
    void updateTeam(int playerId, int teamId);

    @Query("DELETE FROM player WHERE id=(:playerId)")
    void delete(int playerId);

    @Query("DELETE FROM player")
    void deleteAll();

    @Query("DELETE FROM player WHERE team_id=(:teamId)")
    void deleteTeam(int teamId);


}
