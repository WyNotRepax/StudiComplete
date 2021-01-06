package de.hsos.bsteinka.teamtrainer.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class AppRepository {
    private TeamDao teamDao;
    private PlayerDao playerDao;
    private MatchDao matchDao;
    private LiveData<List<Team>> allTeams;
    private LiveData<List<Team>> hiddenTeams;
    private LiveData<List<Player>> allPlayers;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        teamDao = db.teamDao();
        playerDao = db.playerDao();
        matchDao = db.matchDao();
        this.allTeams = teamDao.getAllVisible(false);
        this.hiddenTeams = teamDao.getAllVisible(true);
        this.allPlayers = playerDao.getAll();
    }

    public LiveData<Team> getTeam(int teamId) {
        return teamDao.get(teamId);
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }

    public LiveData<List<Team>> getHiddenTeams() {
        return hiddenTeams;
    }

    public void insertTeam(Team team) {
        AppDatabase.databaseWriteExecutor.execute(() -> teamDao.insert(team));
    }

    public void updateTeam(Team team) {
        AppDatabase.databaseWriteExecutor.execute(() -> teamDao.update(team));
    }

    public void deleteTeam(int teamId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            playerDao.deleteTeam(teamId);
            matchDao.deleteTeam(teamId);
            teamDao.delete(teamId);
        });
    }

    public void hideTeam(int teamId) {
        AppDatabase.databaseWriteExecutor.execute(() -> teamDao.setHidden(teamId, true));
    }


    public void unHideTeam(int teamId) {
        AppDatabase.databaseWriteExecutor.execute(() -> teamDao.setHidden(teamId, false));
    }

    public void unHideAllTeams() {
        AppDatabase.databaseWriteExecutor.execute(() -> teamDao.setHiddenAll(false));
    }

    public LiveData<Player> getPlayer(int playerId) {
        return playerDao.get(playerId);
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }

    public LiveData<List<Player>> getPlayersForTeam(int teamId) {
        return playerDao.getForTeam(teamId);
    }

    public void insertPlayer(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> playerDao.insert(player));
    }

    public void updatePlayer(Player player) {
        AppDatabase.databaseWriteExecutor.execute(() -> playerDao.update(player));
    }

    public void updatePlayerTeam(int playerId, int teamId) {
        AppDatabase.databaseWriteExecutor.execute(() -> playerDao.updateTeam(playerId, teamId));
    }

    public void deletePlayer(int playerId) {
        AppDatabase.databaseWriteExecutor.execute(() -> playerDao.delete(playerId));
    }

    public LiveData<List<Match>> getMatchesForTeam(int teamId) {
        return matchDao.getForTeam(teamId);
    }

    public void insertMatch(Match match) {
        AppDatabase.databaseWriteExecutor.execute(() -> matchDao.insert(match));
    }

    public void deleteMatch(int matchId) {
        AppDatabase.databaseWriteExecutor.execute(() -> matchDao.delete(matchId));
    }

    public void updateMatchScore(int matchId, int score, int opponendScore) {
        AppDatabase.databaseWriteExecutor.execute(() -> matchDao.updateScore(matchId, score, opponendScore));
    }

    public LiveData<Match> getMatch(int matchId) {
        return matchDao.get(matchId);
    }
}
