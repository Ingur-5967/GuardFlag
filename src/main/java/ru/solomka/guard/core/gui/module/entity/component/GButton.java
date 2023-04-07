package ru.solomka.guard.core.gui.module.entity.component;

import ru.solomka.guard.core.gui.module.entity.BaseElement;
import ru.solomka.guard.core.gui.module.entity.GMenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class GButton extends BaseElement<GButton> {

    public GButton(BaseElement.ElementOption option) {
        super("BUTTON", option);
    }

    @Override
    public void init(GMenuAdapter adapter) {
        List<BaseElement<?>> elements = new ArrayList<>(adapter.getComponents());
        elements.add(this);

        elements.forEach(c -> {

            //todo

        });

        adapter.setComponents(elements);
    }

    @Override
    public GButton getInstance() {
        return this;
    }
}
