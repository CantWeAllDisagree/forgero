package com.sigmundgranaas.forgero.resource.data.v2.data;

import com.google.gson.annotations.SerializedName;
import com.sigmundgranaas.forgero.property.attribute.Category;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.sigmundgranaas.forgero.util.Identifiers.EMPTY_IDENTIFIER;

@Builder(toBuilder = true)
public class SlotData {
    @Builder.Default
    @Nullable
    private final List<Category> category = Collections.emptyList();

    @Builder.Default
    @Nullable
    private final String type = EMPTY_IDENTIFIER;

    @Builder.Default
    @Nullable
    private final String description = EMPTY_IDENTIFIER;

    @SerializedName(value = "upgrade_type", alternate = "upgradeType")
    @Builder.Default
    @Nullable
    private String upgradeType = EMPTY_IDENTIFIER;


    @Builder.Default
    private int tier = 1;

    @NotNull
    public String upgradeType() {
        return Objects.requireNonNullElse(upgradeType, EMPTY_IDENTIFIER);
    }

    @NotNull
    public List<Category> category() {
        return Objects.requireNonNullElse(category, Collections.emptyList());
    }

    @NotNull
    public String type() {
        return Objects.requireNonNullElse(type, EMPTY_IDENTIFIER);
    }

    @NotNull
    public String description() {
        return Objects.requireNonNullElse(description, EMPTY_IDENTIFIER);
    }


    public int tier() {
        return tier;
    }
}