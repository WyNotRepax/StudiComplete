package de.hsos.bsteinka.teamtrainer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Team team);

    @Update
    void update(Team team);

    @Query("DELETE FROM team")
    void deleteAll();

    @Query("DELETE FROM team WHERE id=(:teamId)")
    void delete(int teamId);

    @Query("UPDATE team SET hidden = (:hidden) WHERE id=(:teamId)")
    void setHidden(int teamId, boolean hidden);

    @Query("UPDATE team SET hidden = (:hidden)")
    void setHiddenAll(boolean hidden);

    @Query("SELECT * FROM team WHERE hidden=(:hidden)")
    LiveData<List<Team>> getAllVisible(boolean hidden);

    @Query("SELECT * FROM team WHERE id=(:teamId)")
    LiveData<Team> get(int teamId);

}
