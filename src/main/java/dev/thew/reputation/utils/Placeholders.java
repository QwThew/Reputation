package dev.thew.reputation.utils;

import com.sun.istack.internal.NotNull;
import dev.thew.reputation.Reputation;
import dev.thew.reputation.model.User;
import lombok.NonNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NonNull String getIdentifier() {
        return "reputation";
    }

    @Override
    public @NonNull String getAuthor() {
        return "Thew";
    }

    @Override
    public @NonNull String getVersion() {
        return "1.0";
    }

    @Override
    public @NonNull String onPlaceholderRequest(Player player, @NotNull String identifier) {
        User user = Reputation.getReputationAPI().getUserOfPlayer(player);
        if (user == null) return ChatColor.RED + "Ошибка!";

        switch (identifier) {
            case "status": return user.getStatus();
            case "rating": return String.valueOf(user.getReputation());
            default: break;
        }
        return "";
    }
}
