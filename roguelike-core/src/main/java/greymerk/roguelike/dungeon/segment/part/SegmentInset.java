package greymerk.roguelike.dungeon.segment.part;

import com.github.srwaggon.minecraft.block.SingleBlockBrush;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import java.util.Random;

import greymerk.roguelike.dungeon.DungeonLevel;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class SegmentInset extends SegmentBase {


  @Override
  protected void genWall(WorldEditor editor, Random rand, DungeonLevel level, Direction dir, ThemeBase theme, Coord origin) {
    StairsBlock stair = theme.getSecondary().getStair();

    Coord cursor;
    Coord start;
    Coord end;

    Direction[] orthogonals = dir.orthogonals();

    start = origin.copy();
    start.translate(dir, 2);
    end = start.copy();
    start.translate(orthogonals[0], 1);
    end.translate(orthogonals[1], 1);
    end.up(2);
    RectSolid.newRect(start, end).fill(editor, SingleBlockBrush.AIR);
    start.translate(dir, 1);
    end.translate(dir, 1);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getWall());

    for (Direction d : orthogonals) {
      cursor = origin.copy();
      cursor.up(2);
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(true).setFacing(dir.reverse());
      stair.stroke(editor, cursor);

      cursor = origin.copy();
      cursor.translate(dir, 2);
      cursor.translate(d, 1);
      stair.setUpsideDown(false).setFacing(d.reverse());
      stair.stroke(editor, cursor);
    }

    cursor = origin.copy();
    cursor.up(1);
    cursor.translate(dir, 3);
    SingleBlockBrush.AIR.stroke(editor, cursor);
    cursor.up(1);
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(editor, cursor);
  }
}
