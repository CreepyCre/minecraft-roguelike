package greymerk.roguelike.dungeon.base;

import com.google.common.collect.Lists;

import com.github.srwaggon.roguelike.worldgen.SingleBlockBrush;

import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class SecretRoom extends DungeonBase {

  public SecretRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public boolean isValid(WorldEditor editor, Cardinal dir, Coord pos) {
    if (getRoomSetting().getCount() <= 0) {
      return false;
    }
    DungeonBase prototype = createPrototype();
    Coord cursor = new Coord(pos);
    cursor.translate(dir, prototype.getSize() + 5);
    return prototype.validLocation(editor, dir, cursor);
  }

  @Override
  public DungeonBase generate(Coord pos, List<Cardinal> entrances) {
    DungeonBase prototype = createPrototype();

    int size = prototype.getSize();

    Coord start = new Coord(pos);
    Coord end = new Coord(pos);
    Cardinal entrance = entrances.get(0);
    start.translate(entrance.orthogonals()[0]);
    start.translate(Cardinal.DOWN);
    start.translate(entrance, 2);
    end.translate(entrance.orthogonals()[1]);
    end.translate(entrance, size + 5);
    end.translate(Cardinal.UP, 2);
    RectSolid.newRect(start, end).fill(editor, settings.getTheme().getPrimary().getWall(), false, true);

    end = new Coord(pos);
    end.translate(entrance, size + 5);
    end.translate(Cardinal.UP);
    RectSolid.newRect(pos, end).fill(editor, SingleBlockBrush.AIR);

    end.translate(Cardinal.DOWN);
    prototype.generate(end, Lists.newArrayList(entrance));
    return prototype;
  }

  @Override
  public int getSize() {
    return createPrototype().getSize();
  }

  private DungeonBase createPrototype() {
    return getRoomSetting().instantiate(settings, editor);
  }

}