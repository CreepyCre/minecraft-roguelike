package greymerk.roguelike.theme;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.BlockStripes;
import greymerk.roguelike.worldgen.BlockWeightedRandom;

import static com.github.srwaggon.minecraft.block.normal.ColoredBlock.stainedHardenedClay;

public class ThemeEniko extends ThemeBase {

  public ThemeEniko() {

    BlockStripes floor = new BlockStripes();
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.LIGHT_BLUE));
    floor.addBlock(stainedHardenedClay().setColor(DyeColor.WHITE));

    BlockWeightedRandom walls = new BlockWeightedRandom();
    walls.addBlock(BlockType.STONE_BRICK.getBrush(), 100);
    walls.addBlock(BlockType.STONE_BRICK_CRACKED.getBrush(), 1);
    walls.addBlock(BlockType.STONE_BRICK_MOSSY.getBrush(), 5);

    StairsBlock stair = StairsBlock.stoneBrick();
    BlockBrush pillar = SlabBlock.stone().setTop(false).setFullBlock(true).setSeamless(true);

    this.primary = new BlockSet(floor, walls, stair, pillar);
    this.secondary = primary;
  }
}
