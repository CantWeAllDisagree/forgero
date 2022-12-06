package com.sigmundgranaas.forgero.resource.data.v2.data;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sigmundgranaas.forgero.util.Identifiers.EMPTY_IDENTIFIER;

public record DefaultResourcePair(DataResource resource, Optional<DataResource> OptionalDefault) {

    public static Optional<DataResource> linkDefaults(List<DataResource> defaults) {
        return defaults.stream()
                .sorted(Comparator.comparingInt(aDefault -> aDefault.context().get().path().split("\\" + File.separator).length))
                .reduce(DefaultResourcePair::applyDefaults);
    }

    public static DataResource applyDefaults(DataResource resource, DataResource defaultResource) {
        var builder = resource.toBuilder();

        if (Objects.equals(resource.name(), EMPTY_IDENTIFIER)) {
            builder.name(defaultResource.name());
        }
        if (Objects.equals(resource.parent(), EMPTY_IDENTIFIER)) {
            builder.parent(defaultResource.parent());
        }
        if (resource.resourceType() == ResourceType.UNDEFINED) {
            builder.resourceType(defaultResource.resourceType());
        }
        if (Objects.equals(resource.nameSpace(), EMPTY_IDENTIFIER)) {
            builder.namespace(defaultResource.nameSpace());
        }
        if (resource.dependencies().isEmpty()) {
            builder.dependencies(defaultResource.dependencies());
        }

        return builder.build();
    }
}