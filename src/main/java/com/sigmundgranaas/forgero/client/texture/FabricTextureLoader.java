package com.sigmundgranaas.forgero.client.texture;

import com.sigmundgranaas.forgero.Forgero;
import com.sigmundgranaas.forgero.core.identifier.texture.TextureIdentifier;
import com.sigmundgranaas.forgero.core.identifier.texture.toolpart.PaletteIdentifier;
import com.sigmundgranaas.forgero.core.identifier.texture.toolpart.PaletteTemplateIdentifier;
import com.sigmundgranaas.forgero.core.io.FileLoader;
import com.sigmundgranaas.forgero.core.io.FileService;
import com.sigmundgranaas.forgero.core.texture.RawTexture;
import com.sigmundgranaas.forgero.core.texture.Texture;
import com.sigmundgranaas.forgero.core.texture.TextureLoader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

public class FabricTextureLoader implements TextureLoader {

    private final Function<Identifier, Resource> getResource;
    private final FileLoader fileLoader;
    private final FileService fileService;

    public FabricTextureLoader(Function<Identifier, Resource> getResource) {
        this.fileLoader = new FileLoader();
        this.getResource = getResource;
        this.fileService = new FileService();
    }

    @Override
    public Texture getResource(TextureIdentifier id) {
        try {
            if (id instanceof PaletteTemplateIdentifier) {
                return RawTexture.createRawTexture(id, getResource.apply(new Identifier(id.getFileNameWithExtension())).getInputStream());
            } else {
                return RawTexture.createRawTexture(id, fileLoader.loadStreamFromFile(fileService.getFile(id)));
            }
        } catch (IOException | URISyntaxException e) {
            Forgero.LOGGER.error("Unable to load {} due to {}, Falling back to default image", id.getIdentifier(), e);
            return new RawTexture(id, new BufferedImage(32, 32, BufferedImage.TYPE_INT_BGR));
        }
    }

    @Override
    public Texture getResource(PaletteIdentifier id) {
        try {
            return RawTexture.createRawTexture(id, (fileLoader.loadStreamFromFile(fileService.getFile(id))));
        } catch (IOException | URISyntaxException e) {
            Forgero.LOGGER.error("Unable to load {} due to {}, Falling back to default image", id.getIdentifier(), e);
            return new RawTexture(id, new BufferedImage(32, 32, BufferedImage.TYPE_INT_BGR));
        }
    }

    @FunctionalInterface
    public interface Function<T, K> {
        K apply(T id) throws IOException;
    }
}