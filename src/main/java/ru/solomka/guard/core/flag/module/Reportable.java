package ru.solomka.guard.core.flag.module;

import org.bukkit.entity.Player;
import ru.solomka.guard.config.lang.entity.Message;
import ru.solomka.guard.core.GMetaData;

public interface Reportable {

    default void sendErrorMessage(Player player, Message message) {
        long now = System.currentTimeMillis();

        GMetaData userMetaData = new GMetaData(player);

        if(userMetaData.getData() == null || now - (Long) userMetaData.getData().getInfo() >= 700L) {
            //player.sendMessage(message.getMessageSection().keySet().toArray(Message[]::new)[0]);
            userMetaData.saveUser(new GMetaData.GData<>("cooldown-message", now));
        }
        else if(now - (Long) userMetaData.getData().getInfo() >= 500L)
            userMetaData.clearEntry();
    }
}