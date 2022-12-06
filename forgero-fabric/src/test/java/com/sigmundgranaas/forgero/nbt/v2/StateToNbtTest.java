package com.sigmundgranaas.forgero.nbt.v2;

import com.sigmundgranaas.forgero.item.nbt.v2.CompositeEncoder;
import com.sigmundgranaas.forgero.item.nbt.v2.CompoundEncoder;
import com.sigmundgranaas.forgero.state.State;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.sigmundgranaas.forgero.item.nbt.v2.NbtConstants.*;
import static com.sigmundgranaas.forgero.testutil.Tools.IRON_PICKAXE;
import static com.sigmundgranaas.forgero.testutil.Upgrades.BINDING;


public class StateToNbtTest {
    private static final CompoundEncoder<State> encoder = new CompositeEncoder();

    @Test
    void encodeCompound() {
        NbtCompound compound = encoder.encode(IRON_PICKAXE);
        Assertions.assertEquals("iron-pickaxe", compound.getString(NAME_IDENTIFIER));
    }

    @Test
    void encodeCompoundWithIngredients() {
        NbtCompound compound = encoder.encode(IRON_PICKAXE);
        NbtList list = compound.getList(INGREDIENTS_IDENTIFIER, NbtElement.COMPOUND_TYPE);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals(((NbtCompound) list.get(0)).getString(NAME_IDENTIFIER), "oak-handle");
        Assertions.assertEquals(((NbtCompound) list.get(1)).getString(NAME_IDENTIFIER), "iron-pickaxe_head");
    }

    @Test
    void encodeCompoundWithUpgrades() {
        NbtCompound compound = encoder.encode(IRON_PICKAXE.upgrade(BINDING));
        NbtList list = compound.getList(UPGRADES_IDENTIFIER, NbtElement.COMPOUND_TYPE);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(((NbtCompound) list.get(0)).getString(NAME_IDENTIFIER), "oak-binding");
    }
}