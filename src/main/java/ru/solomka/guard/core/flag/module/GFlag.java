package ru.solomka.guard.core.flag.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.flag.utils.FlagRoute;

import javax.print.DocFlavor;
import java.util.function.Predicate;

@AllArgsConstructor
public abstract class GFlag<E extends Event> implements Reportable {

    @Getter private final String idFlag;
    @Getter private final Object[] allowParams;

    public abstract void onEnable(E event);

    public boolean existsFlag(String regionId) {
        return FlagRoute.isExistsFlag(regionId, idFlag) && FlagRoute.getParamsFlag(regionId, idFlag) != null;
    }

    public static GMaterialFilter getFilter() {
        return new GMaterialFilter();
    }

    public static class GMaterialFilter {

        public boolean canBuild(Material onClick, Material inHand) {
            return !canUse(onClick) && inHand.isSolid();
        }

        public boolean canUse(Material material) {
            switch (material) {
                case ACACIA_DOOR:
                case ACACIA_FENCE:
                case ACACIA_FENCE_GATE:
                case ACACIA_STAIRS:
                case ANVIL:
                case BEACON:
                case BIRCH_DOOR:
                case BIRCH_FENCE:
                case BIRCH_FENCE_GATE:
                case BLACK_SHULKER_BOX:
                case BLUE_SHULKER_BOX:
                case BREWING_STAND:
                case BRICK_STAIRS:
                case BROWN_SHULKER_BOX:
                case CAKE:
                case CAULDRON:
                case CHEST:
                case COBBLESTONE_STAIRS:
                case CYAN_SHULKER_BOX:
                case DARK_OAK_DOOR:
                case DARK_OAK_FENCE:
                case DARK_OAK_FENCE_GATE:
                case DARK_OAK_STAIRS:
                case DAYLIGHT_DETECTOR:
                case DISPENSER:
                case DRAGON_EGG:
                case DROPPER:
                case ENDER_CHEST:
                case FLOWER_POT:
                case FURNACE:
                case GRAY_SHULKER_BOX:
                case GREEN_SHULKER_BOX:
                case HOPPER:
                case IRON_DOOR:
                case IRON_TRAPDOOR:
                case JUKEBOX:
                case JUNGLE_DOOR:
                case JUNGLE_FENCE:
                case JUNGLE_FENCE_GATE:
                case LEVER:
                case LIGHT_BLUE_SHULKER_BOX:
                case LIME_SHULKER_BOX:
                case MAGENTA_SHULKER_BOX:
                case NETHER_BRICK_STAIRS:
                case NOTE_BLOCK:
                case ORANGE_SHULKER_BOX:
                case PINK_SHULKER_BOX:
                case PUMPKIN:
                case PURPLE_SHULKER_BOX:
                case PURPUR_STAIRS:
                case QUARTZ_STAIRS:
                case REDSTONE_ORE:
                case RED_SANDSTONE_STAIRS:
                case RED_SHULKER_BOX:
                case SANDSTONE_STAIRS:
                case SPRUCE_DOOR:
                case SPRUCE_FENCE:
                case SPRUCE_FENCE_GATE:
                case STONE_BUTTON:
                case STRUCTURE_BLOCK:
                case TNT:
                case TRAPPED_CHEST:
                case WHITE_SHULKER_BOX:
                case YELLOW_SHULKER_BOX:
                    return true;
                default:
                    return false;
            }
        }
    }
}