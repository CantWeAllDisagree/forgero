package com.sigmundgranaas.forgero.core.state;

import com.sigmundgranaas.forgero.core.property.Property;
import com.sigmundgranaas.forgero.core.type.Type;

import java.util.List;

public interface Ingredient extends State {
    static Ingredient of(Composite composite) {
        return new CompositeIngredient(composite);
    }

    static Ingredient of(String name, Type type, List<Property> properties) {
        return new SimpleState(name, type, properties);
    }

    static Ingredient of(String name, String nameSpace, Type type, List<Property> properties) {
        return new SimpleState(name, nameSpace, type, properties);
    }
}