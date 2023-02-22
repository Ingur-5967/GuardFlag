package ru.solomka.guard.core.gui;

import org.jetbrains.annotations.NotNull;
import ru.solomka.guard.core.gui.module.GController;

import java.util.Arrays;

public class ControllerManager {

    public void initControllers(GController<?> ...controllerHelper) {
        Arrays.stream(controllerHelper).forEach(this::register);
    }

    public void register(@NotNull GController<?> controllerHelper) {
        controllerHelper.register();
    }

    public void unregister(@NotNull GController<?> controllerHelper) {
        controllerHelper.unregister();
    }
}