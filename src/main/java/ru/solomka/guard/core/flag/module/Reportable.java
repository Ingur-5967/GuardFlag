package ru.solomka.guard.core.flag.module;

import org.bukkit.entity.Player;
import ru.solomka.guard.core.GMetaData;

public interface Reportable {

    default void sendErrorMessage(Player player) {
        if(getErrorMessage().equals("")) return;

        long now = System.currentTimeMillis();

        GMetaData userMetaData = new GMetaData(player);

        if(userMetaData.getData() == null || now - (Long) userMetaData.getData().getInfo() >= 700L) {
            player.sendMessage(getErrorMessage());
            userMetaData.saveUser(new GMetaData.GData<>("cooldown-message", now));
        }

        else if(now - (Long) userMetaData.getData().getInfo() >= 500L)
            userMetaData.clearEntry();
    }
    default String getErrorMessage() {
        return "";
    }
}