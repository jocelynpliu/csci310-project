package csci310.team53.easyteamup.handlers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.util.VoteTimer;

/**
 * Handles the scheduling and execution of vote timer tasks.
 *
 * @author Thomas Peters
 */
public class VotingHandler {

    private final EasyTeamUp app;
    private final ScheduledExecutorService scheduler;

    public VotingHandler(EasyTeamUp app) {
        this.app = app;
        this.scheduler = Executors.newScheduledThreadPool(100);
    }

    // TODO: Add additional parameters to pass in list of vote slots maybe?
    public void startVote(String eventID, LocalDateTime voteEnd) {
        LocalDateTime date = LocalDateTime.of(2018, 6, 17, 18, 30, 0);
        scheduler.schedule(new VoteTimer(app, eventID), LocalDateTime.now().until(date, ChronoUnit.MINUTES), TimeUnit.MINUTES);
    }
}
