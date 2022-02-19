package com.sigmundgranaas.forgero.core.material.material.simple;

import com.sigmundgranaas.forgero.core.material.material.SecondaryMaterial;

public interface SimpleSecondaryMaterial extends SecondaryMaterial {
    int getMiningLevel();

    float getMiningSpeedAddition();

    float getAttackDamageAddition();

    float getAttackSpeedAddition();
}