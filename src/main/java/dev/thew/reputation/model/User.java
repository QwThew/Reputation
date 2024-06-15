package dev.thew.reputation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class User {

    @Getter
    private final Player player;
    @Getter
    private int reputation;
    @Getter
    private String status;

    public void increaseReputation(final int count) {
        reputation += count;
    }

    public void decreaseReputation(final int count) {
        reputation -= count;
    }


}
