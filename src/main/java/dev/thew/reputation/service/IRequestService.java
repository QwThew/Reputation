package dev.thew.reputation.service;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.interfaces.RequestService;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.model.enums.EventType;
import dev.thew.reputation.model.Request;
import dev.thew.reputation.model.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

@Getter
public class IRequestService implements Listener, RequestService {

    private Request[] requests;
    private final UserService userService;


    public IRequestService(UserService userService) {
        this.userService = userService;
        Bukkit.getPluginManager().registerEvents(this, Reputation.getInstance());
    }

    @Override
    public void loadRequests(FileConfiguration config) {

    }

    @Override
    public <T> void checkEvent(Player player, EventType eventType, T data) {

        User user = userService.getUser(player);


    }

    /* Quest events */

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        checkEvent(event.getPlayer(), EventType.BLOCK_BREAK, block.getType());
    }

    @EventHandler(ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent event) {
        checkEvent(event.getPlayer(), EventType.CONSUME, event.getItem().getType());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityKill(EntityDeathEvent event) {

        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();

        checkEvent(killer, EventType.ENTITY_KILL, event.getEntity());
    }

    @EventHandler(ignoreCancelled = true)
    public void onRegen(EntityRegainHealthEvent event) {

        if (event.getEntity() instanceof Player player)
            checkEvent(player, EventType.REGEN, null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onLevelUp(PlayerLevelChangeEvent event) {
        checkEvent(event.getPlayer(), EventType.LEVEL_UP, null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent event) {
        checkEvent(event.getEnchanter(), EventType.ENCHANT_ITEM, event.getItem());
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        checkEvent((Player) event.getWhoClicked(), EventType.CRAFT, event.getRecipe().getResult());
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemBreak(PlayerItemBreakEvent event) {
        checkEvent(event.getPlayer(), EventType.ITEM_BREAK, event.getBrokenItem());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEggThrow(PlayerEggThrowEvent event) {
        checkEvent(event.getPlayer(), EventType.EGG_THROW, null);
    }

}
