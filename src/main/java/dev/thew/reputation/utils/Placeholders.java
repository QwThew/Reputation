package dev.thew.reputation.utils;

import dev.thew.reputation.interfaces.ReputationAPI;
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
    public @NonNull String onPlaceholderRequest(Player player, @NonNull String identifier) {
        ReputationAPI reputationAPI = ReputationAPI.get();
        User user = reputationAPI.getUser(player);
        if (user == null) return ChatColor.RED + "Ошибка!";

        return switch (identifier) {
            case "status" -> user.getStatus();
            case "rating" -> String.valueOf(user.getReputation());
            default -> "";
        };
    }
}
