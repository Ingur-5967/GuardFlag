package ru.solomka.guard.core.scoreboard;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.GMetaData;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class GScoreboard {

    public static final String EMPTY_ARGUMENT = " ";

    private static final String[] EMPTY_SYMBOLS = {
            "&a", "&c", "&f",
            "&d", "&e", "&6",
            "&9", "&6", "&5",
            "&4", "&e&l", "&6&l",
            "&7&l", "&8&l", "&9&l",
            "&6&l", "&5&l", "&4&l",
            "&a&l", "&c&l", "&f&l",
            "&d&l", "&7", "&8",
            "&a&f", "&c&f", "&f&f",
    };

    public void setupToPlayer(Player player, Builder builder) {
        player.setScoreboard(builder.getScoreboard());
    }

    public List<Player> getActivePlayersInRegion(String region) {
        List<Object> meta = GMetaData.getAll(new GMetaData.GData<>("scoreboard-region", region));

        if(meta.isEmpty()) return Collections.emptyList();

        return meta.stream().map(s -> UUID.fromString(s.toString())).map(Bukkit::getPlayer).collect(Collectors.toList());
    }

    public static class Builder {

        @Getter private final ScoreboardManager scoreboardManager;
        @Getter private final Scoreboard scoreboard;

        @Setter @Getter private Objective scoreboardObjective;

        private Builder() {
            scoreboardManager = Bukkit.getScoreboardManager();
            scoreboard = scoreboardManager.getNewScoreboard();
        }

        public Builder initObjective(String title) {
            Objective objective = scoreboard.registerNewObjective(translateAlternateColorCodes('&', title), "Server");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            setScoreboardObjective(objective);
            return this;
        }

        public Builder initScores(String ...rows) {

            List<String> list = new ArrayList<>(Arrays.asList(rows));
            List<String> symbols = new ArrayList<>(Arrays.asList(EMPTY_SYMBOLS));

            for(int i = 0; i < list.size(); i++) {

                if(list.get(i).equals(EMPTY_ARGUMENT)) {

                    int index = 0;
                    StringBuilder stringBuilder = new StringBuilder();

                    while(list.contains(stringBuilder.toString())) {
                        index = new Random().nextInt(symbols.size());
                        stringBuilder.append(EMPTY_ARGUMENT).append(symbols.get(index));
                    }
                    symbols.remove(index);
                    list.set(i, stringBuilder.toString());
                }

                if(list.get(i).toCharArray().length > 40)
                    throw new IllegalArgumentException("The argument for the string size cannot be greater than 40. (Argument: '" + list.get(i) + "')");

                Score activeScore = getScoreboardObjective().getScore(translateAlternateColorCodes('&', list.get(i)));
                activeScore.setScore(i);
            }
            return this;
        }

        @Contract(" -> new")
        public static @NotNull Builder of() {
            return new Builder();
        }
    }
}