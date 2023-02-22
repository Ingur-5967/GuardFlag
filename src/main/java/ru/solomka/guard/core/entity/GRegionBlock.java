package ru.solomka.guard.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;

@Data @AllArgsConstructor
public class GRegionBlock {
    private final Material material;
    private final String state;
}
