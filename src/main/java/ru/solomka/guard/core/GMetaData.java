package ru.solomka.guard.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GMetaData {

    private static Map<Object, GData<?>> CONTAINER = new HashMap<>();

    @Getter
    private Object info;

    public GMetaData(@NotNull Player player) {
        this.info = player.getUniqueId();
    }

    public GMetaData(@NotNull Block block) {
        this.info = block.getLocation();
    }

    public GMetaData(@NotNull Entity entity) {
        this.info = entity.getEntityId();
    }

    public GMetaData() {
    }

    public void saveUser(GData<?> data) {
        CONTAINER.put(info, data);
    }


    public GData<?> getData() {
        return CONTAINER.get(info);
    }

    public void clearEntry() {
        CONTAINER.remove(info);
    }

    public static List<Object> getAll(GData<?> data) {
        if (CONTAINER.isEmpty()) return Collections.emptyList();

        List<Object> entries = new ArrayList<>(CONTAINER.size());

        for (Map.Entry<Object, GData<?>> aMap : CONTAINER.entrySet()) {

            if (!data.getKey().equals(aMap.getKey()) && data.getInfo().equals(aMap.getValue())) continue;

            entries.add(aMap.getKey());
        }
        return entries;
    }

    public void removeContainer() {
        CONTAINER = null;
    }

    public static Map<Object, GData<?>> getContainer() {
        return CONTAINER;
    }

    @Data
    @AllArgsConstructor
    public static class GData<T> {
        private String key;
        private final T info;
    }
}