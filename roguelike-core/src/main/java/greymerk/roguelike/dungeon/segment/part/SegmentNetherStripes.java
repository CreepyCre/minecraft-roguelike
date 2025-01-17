package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.SlabBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentNetherStripes extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {

    StairsBlock stair = theme.getSecondary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    cursor = origin.copy();
    cursor.translate(dir, 2);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor = origin.copy();
    cursor.translate(dir, 5);
    boolean isAir = editor.isAirBlock(cursor);
    boolean isLava = rand.nextInt(5) == 0;


    BlockBrush slab = SlabBlock.netherBrick();
    cursor = origin.copy();
    cursor.translate(dir, 2);
    slab.stroke(editor, cursor);
    cursor.up(1);
    slab.stroke(editor, cursor);
    cursor.up(1);
    slab.stroke(editor, cursor);

    for (Direction orthogonal : dir.orthogonals()) {
      start = origin.copy();
      start.translate(dir, 3);
      end = start.copy();
      start.translate(orthogonal, 1);
      start.up(3);
      end.down(2);
      if (isLava && !isAir) {
        RectSolid.newRect(start, end).fill(editor, BlockType.LAVA_FLOWING.getBrush(), false, true);
      }

      stair.setUpsideDown(true).setFacing(orthogonal.reverse());
      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(orthogonal, 1);
      stair.stroke(editor, cursor);
      cursor.up(1);
      stair.stroke(editor, cursor);
      cursor.up(1);
      stair.stroke(editor, cursor);
      cursor.translate(dir.reverse(), 1);
      stair.stroke(editor, cursor);
    }
  }

}