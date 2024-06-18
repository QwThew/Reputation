package dev.thew.reputation.service;

import dev.thew.reputation.Reputation;
import dev.thew.reputation.interfaces.RequestService;
import dev.thew.reputation.interfaces.UserService;
import dev.thew.reputation.model.Payment;
import dev.thew.reputation.model.enums.*;
import dev.thew.reputation.model.Request;
import dev.thew.reputation.model.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class IRequestService implements Listener, RequestService {

    private List<Request> requests = new ArrayList<>();
    private final UserService userService;

    public IRequestService(UserService userService) {
        this.userService = userService;
        Bukkit.getPluginManager().registerEvents(this, Reputation.getInstance());
    }

    @Override
    public void loadRequests(FileConfiguration config) {
        ConfigurationSection requestsSection = config.getConfigurationSection("requests");
        if (requestsSection == null) return;

        for (String key : requestsSection.getKeys(false)) {
            ConfigurationSection requestSection = requestsSection.getConfigurationSection(key);
            if (requestSection == null) break;

            ConfigurationSection eventSection = requestsSection.getConfigurationSection("event");
            if (eventSection == null) break;

            String type = eventSection.getString("type");
            String eventDataType = eventSection.getString("dataType");
            String eventData = eventSection.getString("data");

            DataType dataType = DataType.valueOf(eventDataType);
            EventType eventType = EventType.valueOf(type);
            Object data = dataType.generate(eventData);

            Payment payment = getPaymentFromSection(requestSection);
            double random = requestSection.getDouble("random");

            List<If> ifs = new ArrayList<>();

            ConfigurationSection ifsSection = requestSection.getConfigurationSection("ifs");
            if (ifsSection != null) ifs = getIfTypesFromSection(ifsSection);


            requests.add(Request.builder()
                    .data(data)
                    .type(eventType)
                    .dataType(dataType)
                    .ifs(ifs)
                    .payment(payment)
                    .random(random)
                    .build());
        }
    }

    private List<If> getIfTypesFromSection(ConfigurationSection section) {

        List<If> ifs = new ArrayList<>();

        String type;
        boolean userhavemore = section.get("userhavemore") != null;
        boolean userhaveless = section.get("userhaveless") != null;

        if (userhavemore) {
            type = "userhavemore";
            IfType ifType = IfType.valueOf(type.toUpperCase());
            int count = section.getInt(type.toLowerCase());

            ifs.add(new If(count, ifType));
        }

        if (userhaveless) {
            type = "userhaveless";
            IfType ifType = IfType.valueOf(type.toUpperCase());
            int count = section.getInt(type.toLowerCase());

            ifs.add(new If(count, ifType));
        }

        return ifs;
    }

    @Override
    public <T> void checkEvent(Player player, EventType eventType, T data) {

        User user = userService.getUser(player);

        List<Request> requestList = getCheckRequests(eventType, data);

        for (Request request : requestList) {

        }
    }


    private <T> List<Request> getCheckRequests(EventType eventType, T data) {
        List<Request> checkRequests = new ArrayList<>();

        requests.stream()
                .filter(req -> req.getType() == eventType)
                .filter(req -> data != null && req.isMatched(data))
                .forEach(checkRequests::add);

        return checkRequests;
    }

    private Payment getPaymentFromSection(ConfigurationSection section) {
        String type = "";

        boolean increase = section.get("increase") != null;
        boolean decrease = section.get("decrease") != null;

        if (increase) type = "increase";
        if (decrease) type = "decrease";

        PaymentType paymentType = PaymentType.valueOf(type.toUpperCase());
        int count = section.getInt(type.toLowerCase());

        return new Payment(count, paymentType);
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
