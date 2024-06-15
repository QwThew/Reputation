package dev.thew.reputation.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Level {

    int rating;
    String title;

    public Level(String rating, String title) {
        this.rating = Integer.parseInt(rating);
        this.title = title;
    }

}
