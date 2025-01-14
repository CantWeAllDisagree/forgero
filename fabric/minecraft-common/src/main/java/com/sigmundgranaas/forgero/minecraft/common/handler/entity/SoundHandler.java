package com.sigmundgranaas.forgero.minecraft.common.handler.entity;

import com.google.gson.JsonObject;
import com.sigmundgranaas.forgero.core.property.v2.feature.HandlerBuilder;
import com.sigmundgranaas.forgero.core.property.v2.feature.JsonBuilder;
import com.sigmundgranaas.forgero.minecraft.common.handler.targeted.onHitBlock.OnHitBlockHandler;
import com.sigmundgranaas.forgero.minecraft.common.handler.targeted.onHitEntity.OnHitHandler;
import lombok.Getter;
import lombok.experimental.Accessors;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * Represents a handler that plays a specified sound when triggered.
 * The handler can be configured using a JSON format.
 *
 * <p>
 * Example JSON configurations:
 * </p>
 *
 * <pre>
 * Playing a simple sound:
 * {
 *   "type": "minecraft:play_sound",
 *   "sound": "minecraft:entity.zombie.ambient"
 * }
 *
 * Playing a sound with volume and pitch configurations:
 * {
 *   "type": "minecraft:play_sound",
 *   "sound": "minecraft:block.anvil.place",
 *   "volume": 0.5,
 *   "pitch": 1.2
 * }
 * </pre>
 *
 * <p>
 * Note: If the "volume" or "pitch" fields are absent, default values are 1.0.
 * </p>
 */
@Getter
@Accessors(fluent = true)
public class SoundHandler implements EntityHandler, OnHitBlockHandler, OnHitHandler {
	public static final String TYPE = "minecraft:play_sound";
	public static final JsonBuilder<SoundHandler> BUILDER = HandlerBuilder.fromObject(SoundHandler.class, SoundHandler::fromJson);

	private final Identifier soundId;
	private final float volume;
	private final float pitch;

	/**
	 * Constructs a new {@link SoundHandler} with the specified properties.
	 *
	 * @param soundId Identifier for the sound to be played.
	 * @param volume  Volume of the sound. Defaults to 1.0.
	 * @param pitch   Pitch of the sound. Defaults to 1.0.
	 */
	public SoundHandler(Identifier soundId, float volume, float pitch) {
		this.soundId = soundId;
		this.volume = volume;
		this.pitch = pitch;
	}

	/**
	 * Constructs a {@link SoundHandler} from a JSON object.
	 *
	 * @param json The JSON object.
	 * @return A new instance of {@link SoundHandler}.
	 */
	public static SoundHandler fromJson(JsonObject json) {
		Identifier soundId = new Identifier(json.get("sound").getAsString());
		float volume = json.has("volume") ? json.get("volume").getAsFloat() : 1.0f;
		float pitch = json.has("pitch") ? json.get("pitch").getAsFloat() : 1.0f;

		return new SoundHandler(soundId, volume, pitch);
	}

	@Override
	public void handle(Entity rootEntity) {
		playSound(rootEntity);
	}

	private void playSound(Entity entity) {
		SoundEvent sound = Registry.SOUND_EVENT.get(soundId);
		if (sound != null) {
			entity.world.playSound(null, entity.getBlockPos(), sound, SoundCategory.NEUTRAL, volume, pitch);
		}
	}

	@Override
	public void onHit(Entity root, World world, BlockPos pos) {
		playSound(root);
	}

	@Override
	public void onHit(Entity root, World world, Entity target) {
		playSound(root);
	}
}
