package ru.solomka.guard.core.flag.utils;

import org.jetbrains.annotations.Nullable;
import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;

public class FlagRoute {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<?>> @Nullable T getParamsFlag(String idRegion, String idFlag) {
        Yaml file = new GRegionManager().getFileRegion(idRegion);
        return isExistsFlag(idRegion, idFlag) ? (T) file.getObject("flags." + idFlag + ".params") : null;
    }

    public static boolean isExistsFlag(String idRegion, String idFlag) {
        return new GRegionManager().getFileRegion(idRegion).getString("flags." + idFlag) != null;
    }
}
