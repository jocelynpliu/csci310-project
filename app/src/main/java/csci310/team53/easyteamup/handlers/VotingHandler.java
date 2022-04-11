package csci310.team53.easyteamup.handlers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public void startVote(ObjectId eventID, LocalTime voteEnd) {
        LocalTime now = LocalTime.now();

        Duration duration = Duration.between(now, voteEnd);
        long diff = Math.abs(duration.toMinutes());

        scheduler.schedule(new VoteTimer(app, eventID.toString()), diff, TimeUnit.MINUTES);
    }
}
