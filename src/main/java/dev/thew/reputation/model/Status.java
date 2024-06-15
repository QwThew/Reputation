package dev.thew.reputation.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Status {

    private final List<Level> levels = new ArrayList<>();

    public void addLevel(Level level) {
        levels.add(level);
    }

    public String getStatus(final User user) {
        int userRating = user.getReputation();
        String stats = "";

        if (userRating > 0) for (Level level : levels) if (level.getRating() <= userRating) stats = level.getTitle();
        if (userRating < 0) for (Level level : levels) if (level.getRating() >= userRating) stats = level.getTitle();
        if (userRating == 0) for (Level level : levels) if (level.getRating() == 0) stats = level.getTitle();

        return stats;
    }

}
