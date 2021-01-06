package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.roguelike.worldgen.block.normal.StairsBlock;

import java.util.List;

import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.worldgen.BlockBrush;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;

public class DungeonLinkerTop extends DungeonBase {

  public DungeonLinkerTop(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Cardinal> entrances) {

    ThemeBase theme = settings.getTheme();

    BlockBrush pillar = theme.getPrimary().getPillar();
    BlockBrush wall = theme.getPrimary().getWall();
    BlockBrush floor = theme.getPrimary().getFloor();
    StairsBlock stair = theme.getPrimary().getStair();

    Coord start;
    Coord end;
    Coord cursor;

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, 5, 4));
    RectHollow.newRect(start, end).fill(editor, wall, false, true);

    cursor = new Coord(origin);
    cursor.translate(Cardinal.UP, 5);
    settings.getTheme().getPrimary().getLightBlock().stroke(editor, cursor);

    start = new Coord(origin);
    end = new Coord(origin);
    start.translate(new Coord(-4, -1, -4));
    end.translate(new Coord(4, -1, 4));
    RectSolid.newRect(start, end).fill(editor, floor);

    for (Cardinal dir : Cardinal.DIRECTIONS) {

      start = new Coord(origin);
      end = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 3);
      end.translate(dir, 4);
      end.translate(dir.antiClockwise(), 4);
      end.translate(Cardinal.UP, 4);
      RectSolid.newRect(start, end).fill(editor, pillar);

      start = new Coord(origin);
      start.translate(dir, 3);
      start.translate(dir.antiClockwise(), 2);
      start.translate(Cardinal.UP, 4);
      end = new Coord(start);
      end.translate(dir.clockwise(), 4);
      RectSolid.newRect(start, end).fill(editor, wall);
      start.translate(dir.reverse());
      end.translate(dir.reverse());
      RectSolid.newRect(start, end).fill(editor, stair.setUpsideDown(true).setFacing(dir.reverse()));

      for (Cardinal o : dir.orthogonals()) {
        cursor = new Coord(origin);
        cursor.translate(dir, 3);
        cursor.translate(Cardinal.UP, 2);
        cursor.translate(o, 2);
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
        cursor.translate(Cardinal.UP);
        wall.stroke(editor, cursor);
        cursor.translate(o.reverse());
        stair.setUpsideDown(true).setFacing(o.reverse()).stroke(editor, cursor);
      }
    }


    return this;
  }

  @Override
  public int getSize() {
    return 6;
  }

}