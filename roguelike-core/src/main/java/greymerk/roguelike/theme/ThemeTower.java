package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

public class ThemeTower extends ThemeBase {

  public ThemeTower() {

    BlockJumble stone = new BlockJumble();
    stone.addBlock(BlockType.STONE_BRICK.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush());
    stone.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush());

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(SingleBlockBrush.AIR, 5);
    walls.addBlock(stone, 30);
    walls.addBlock(BlockType.COBBLESTONE.getBrush(), 10);
    walls.addBlock(BlockType.GRAVEL.getBrush(), 5);

    StairsBlock stair = StairsBlock.stoneBrick();

    BlockBrush pillar = BlockType.ANDESITE_POLISHED.getBrush();
    this.primary = new BlockSet(walls, stair, pillar);
    this.secondary = this.primary;

  }
}
