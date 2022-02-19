package com.sigmundgranaas.forgero.core.toolpart;

import com.sigmundgranaas.forgero.core.gem.EmptyGem;
import com.sigmundgranaas.forgero.core.gem.Gem;
import com.sigmundgranaas.forgero.core.material.material.EmptySecondaryMaterial;
import com.sigmundgranaas.forgero.core.material.material.PrimaryMaterial;
import com.sigmundgranaas.forgero.core.material.material.SecondaryMaterial;
import com.sigmundgranaas.forgero.core.toolpart.head.ToolPartHead;
import com.sigmundgranaas.forgero.recipe.customrecipe.RecipeTypes;

public interface ForgeroToolPart {
    PrimaryMaterial getPrimaryMaterial();

    SecondaryMaterial getSecondaryMaterial();

    Gem getGem();

    int getDurability();

    String getToolPartName();

    String getToolPartIdentifier();

    ForgeroToolPartTypes getToolPartType();

    default void createToolPartDescription(ToolPartDescriptionWriter writer) {
        writer.addPrimaryMaterial(getPrimaryMaterial());
        if (!(getSecondaryMaterial() instanceof EmptySecondaryMaterial)) {
            writer.addSecondaryMaterial(getSecondaryMaterial());
        }
        if (!(getGem() instanceof EmptyGem)) {
            writer.addGem(getGem());
        }
    }

    default RecipeTypes getRecipeType() {
        return switch (getToolPartType()) {
            case HEAD -> switch (((ToolPartHead) this).getToolType()) {
                case PICKAXE -> RecipeTypes.PICKAXEHEAD_RECIPE;
                case SHOVEL -> RecipeTypes.SHOVELHEAD_RECIPE;
                case SWORD -> null;
            };
            case BINDING -> RecipeTypes.BINDING_RECIPE;
            case HANDLE -> RecipeTypes.HANDLE_RECIPE;
        };
    }
}