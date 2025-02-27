package com.sigmundgranaas.forgero.minecraft.common.handler.blockbreak.filter;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sigmundgranaas.forgero.core.Forgero;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

/**
 * This filter is used to determine whether blocks are similar based on custom criteria defined in block tags.
 * Blocks can be grouped using tags under a "similar_block" path.
 * The filter will then identify blocks as similar if they share at least one tag in this category.
 */
public class SimilarBlockFilter implements BlockFilter {
	public static final String Key = "forgero:similar_block";

	public static final SimilarBlockFilter DEFAULT = new SimilarBlockFilter();


	private final Cache<Block, Set<Identifier>> blockTagCache = CacheBuilder.newBuilder().softValues().build();
	private final Cache<BlockState, Boolean> similarityCache = CacheBuilder.newBuilder().softValues().build();
	private Set<TagKey<Block>> similarBlockTags;
	private boolean tagsLoaded = false;

	@Override
	public boolean filter(Entity entity, BlockPos currentPos, BlockPos root) {
		BlockState currentBlock = entity.getWorld().getBlockState(currentPos);
		BlockState rootBlock = entity.getWorld().getBlockState(root);

		try {
			return similarityCache.get(currentBlock, () -> evaluateSimilarity(currentBlock, rootBlock));
		} catch (ExecutionException e) {
			Forgero.LOGGER.error(e);
			return false;
		}
	}

	private boolean evaluateSimilarity(BlockState currentBlock, BlockState rootBlock) throws ExecutionException {
		if (currentBlock.getBlock().equals(rootBlock.getBlock())) {
			return true;
		}

		Set<Identifier> tagsCurrentBlock = blockTagCache.get(currentBlock.getBlock(), () -> getTagsInPath(currentBlock));
		Set<Identifier> tagsRootBlock = blockTagCache.get(rootBlock.getBlock(), () -> getTagsInPath(rootBlock));

		for (Identifier tag : tagsCurrentBlock) {
			if (tagsRootBlock.contains(tag)) {
				return true;
			}
		}
		return false;
	}

	private Set<Identifier> getTagsInPath(BlockState block) {
		loadSimilarBlockTags();
		return similarBlockTags.stream()
				.filter(block::isIn)
				.map(TagKey::id)
				.collect(Collectors.toSet());
	}

	private void loadSimilarBlockTags() {
		if (!tagsLoaded) {
			similarBlockTags = Registry.BLOCK.streamTags()
					.filter(key -> key.id().getPath().startsWith("similar_block"))
					.collect(Collectors.toSet());
			tagsLoaded = true;
		}
	}

	public void onReload() {
		blockTagCache.invalidateAll();
		similarityCache.invalidateAll();
		tagsLoaded = false;
	}
}

