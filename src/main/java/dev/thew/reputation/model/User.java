package dev.thew.reputation.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
@Data
@AllArgsConstructor
public class User {

    private final Player player;
    private int reputation;
    private String status;

    public void increaseReputation(@NonNull final int count) {
        reputation += count;
    }

    public void decreaseReputation(@NonNull final int count) {
        reputation -= count;
    }

    public boolean isOnline(){
        return player.isOnline();
    }

    public void increaseReputation(@NonNull final int count,@NonNull String message) {
        increaseReputation(count);
        if(isOnline()) player.sendMessage(message.replace("{reputation}", String.valueOf(reputation)));
    }

    public void decreaseReputation(@NonNull final int count,@NonNull String message) {
        decreaseReputation(count);
        if(isOnline()) player.sendMessage(message.replace("{reputation}", String.valueOf(reputation)));
    }
}
