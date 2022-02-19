package com.sigmundgranaas.forgero.core.toolpart.strategy.realistic;

import com.sigmundgranaas.forgero.core.material.material.realistic.RealisticPrimaryMaterial;
import com.sigmundgranaas.forgero.core.toolpart.ToolPartPropertyStrategy;

public abstract class RealisticToolPartStrategy implements ToolPartPropertyStrategy {
    protected RealisticPrimaryMaterial material;

    public RealisticToolPartStrategy(RealisticPrimaryMaterial material) {
        this.material = material;
    }

    @Override
    public int getDurability() {
        return calculateDurability(material.getStiffness());
    }

    private int calculateDurability(int stiffness) {
        return (int) (Math.pow((float) stiffness / 2, 2) - (5 * stiffness) + 70);
    }

}