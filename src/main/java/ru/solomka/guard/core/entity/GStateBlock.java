package ru.solomka.guard.core.entity;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class GStateBlock {
    private final String idRegion;
    private final ProtectedRegion region;
}
