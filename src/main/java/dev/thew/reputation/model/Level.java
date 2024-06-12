package dev.thew.reputation.model;

import lombok.Data;

@Data
public class Level {

    int reputation;
    String name;

    public Level(String level, String name) {
        this.reputation = Integer.parseInt(level);
        this.name = name;
    }

    public Level(int reputation, String name) {
        this.reputation = reputation;
        this.name = name;
    }
}
