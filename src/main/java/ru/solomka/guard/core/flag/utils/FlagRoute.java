package ru.solomka.guard.core.flag.utils;

import ru.solomka.guard.config.Yaml;
import ru.solomka.guard.core.GRegionManager;
import ru.solomka.guard.core.flag.entity.enums.Flag;

import java.util.*;
import java.util.stream.Collectors;

public class FlagRoute {

    @SuppressWarnings("unchecked")
    public static <T> T getParamsFlag(String idRegion, String idFlag) {
        Yaml file = new GRegionManager(idRegion).getFileRegion();

        Flag flag = Arrays.stream(Flag.values()).filter(f -> f.getIdFlag().equals(idFlag)).findAny().orElse(null);

        if(flag == null) return null;

        Object multipleArgumentsFlag = Arrays.stream(flag.getArgumentsToCommand()).filter(f -> f.equals(":")).findAny().orElse(null);

        T element;

        if(multipleArgumentsFlag == null)
            element = (T) file.getObject("flags." + idFlag + ".params");
        else
            element = (T) flag.getValidArguments().stream().filter(f -> file.getString("flags." + idFlag + ".params." + f) != null).collect(Collectors.toList());

        return isExistsFlag(idRegion, idFlag) ? element : null;
    }

    public static boolean isExistsFlag(String idRegion, String idFlag) {
        return new GRegionManager(idRegion).getFileRegion().getString("flags." + idFlag) != null;
    }
}
