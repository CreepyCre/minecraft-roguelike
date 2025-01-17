package greymerk.roguelike.dungeon.rooms.prototype;

import com.github.srwaggon.minecraft.block.BlockType;
import com.github.srwaggon.minecraft.block.decorative.BedBlock;
import com.github.srwaggon.minecraft.block.decorative.FlowerPotBlock;
import com.github.srwaggon.minecraft.block.decorative.TorchBlock;
import com.github.srwaggon.minecraft.block.normal.StairsBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.base.DungeonBase;
import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.theme.ThemeBase;
import greymerk.roguelike.treasure.loot.ChestType;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class DungeonBedRoom extends DungeonBase {

  public DungeonBedRoom(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    super(roomSetting, levelSettings, worldEditor);
  }

  public void pillar(WorldEditor editor, Direction dir, ThemeBase theme, final Coord base) {
    Coord start = base.copy();
    Coord end = base.copy();

    end.up(2);
    RectSolid.newRect(start, end).fill(editor, theme.getSecondary().getPillar());
    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    end.translate(dir.reverse());
    stair.stroke(editor, end);
  }

  @Override
  public DungeonBase generate(Coord origin, List<Direction> entrances) {
    Random rand = worldEditor.getRandom();
    ThemeBase theme = levelSettings.getTheme();

    Coord cursor;
    Coord start;
    Coord end;

    Direction dir = entrances.get(0);

    start = origin.copy();
    end = origin.copy();

    start.translate(dir.antiClockwise(), 4);
    end.translate(dir.clockwise(), 4);
    start.translate(dir.reverse(), 4);
    end.translate(dir, 4);
    start.down();
    end.up(4);

    RectHollow.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall(), false, true);

    start = origin.copy();
    start.down();
    end = start.copy();
    start.translate(dir.antiClockwise(), 1);
    end.translate(dir.clockwise(), 1);
    start.translate(dir.reverse(), 2);
    end.translate(dir, 2);

    RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getWall());

    for (Direction o : dir.orthogonals()) {
      StairsBlock stair = theme.getSecondary().getStair();
      stair.setUpsideDown(true).setFacing(o.reverse());

      start = origin.copy();
      start.translate(o, 3);
      end = start.copy();
      start.translate(o.antiClockwise(), 2);
      end.translate(o.clockwise(), 2);

      RectSolid.newRect(start, end).fill(worldEditor, stair);
      start.up(2);
      end.up(2);
      RectSolid.newRect(start, end).fill(worldEditor, stair);
      start.up();
      end.up();
      RectSolid.newRect(start, end).fill(worldEditor, theme.getPrimary().getWall());
      start.translate(o.reverse());
      end.translate(o.reverse());
      RectSolid.newRect(start, end).fill(worldEditor, stair);
    }

    for (Direction o : dir.orthogonals()) {
      cursor = origin.copy();
      cursor.translate(o, 3);
      pillar(worldEditor, o, theme, cursor);
      for (Direction p : o.orthogonals()) {
        Coord c = cursor.copy();
        c.translate(p, 3);
        pillar(worldEditor, o, theme, c);
      }
    }

    cursor = origin.copy();
    cursor.up(3);
    cursor.translate(dir.reverse(), 3);

    for (int i = 0; i < 3; ++i) {
      start = cursor.copy();
      end = cursor.copy();
      start.translate(dir.antiClockwise(), 2);
      end.translate(dir.clockwise(), 2);
      RectSolid.newRect(start, end).fill(worldEditor, theme.getSecondary().getWall());
      cursor.translate(dir, 3);
    }

    Direction side = rand.nextBoolean() ? dir.antiClockwise() : dir.clockwise();

    cursor = origin.copy();
    cursor.translate(dir, 3);
    BedBlock.bed().setColor(DyeColor.chooseRandom(worldEditor.getRandom(cursor))).setFacing(dir.reverse()).stroke(worldEditor, cursor);
    cursor.translate(side, 2);
    BlockType.BOOKSHELF.getBrush().stroke(worldEditor, cursor);
    cursor.up();
    FlowerPotBlock.flowerPot().withRandomContent(worldEditor.getRandom(cursor)).stroke(worldEditor, cursor);
    cursor.translate(side.reverse(), 3);
    cursor.down();
    StairsBlock stair = theme.getSecondary().getStair();
    stair.setUpsideDown(true).setFacing(dir.reverse());
    stair.stroke(worldEditor, cursor);
    cursor.up();
    TorchBlock.torch().setFacing(Direction.UP).stroke(worldEditor, cursor);

    side = dir.orthogonals()[rand.nextBoolean() ? 1 : 0];
    cursor = origin.copy();
    cursor.translate(dir);
    cursor.translate(side, 3);

    worldEditor.getTreasureChestEditor().createChest(cursor.add(Direction.UP), false, Dungeon.getLevel(cursor.getY()), getRoomSetting().getChestType().orElse(ChestType.STARTER));

    cursor.translate(side.reverse(), 6);
    if (rand.nextBoolean()) {
      cursor.up();
      TorchBlock.torch().setFacing(Direction.UP).stroke(worldEditor, cursor);
      cursor.down();
      cursor.translate(dir);
      BlockType.CRAFTING_TABLE.getBrush().stroke(worldEditor, cursor);
    } else {
      BlockType.CRAFTING_TABLE.getBrush().stroke(worldEditor, cursor);
      cursor.translate(dir);
      cursor.up();
      TorchBlock.torch().setFacing(Direction.UP).stroke(worldEditor, cursor);
      cursor.down();
    }

    side = rand.nextBoolean() ? dir.antiClockwise() : dir.clockwise();
    cursor = origin.copy();
    cursor.translate(dir.reverse());
    cursor.translate(side, 3);
    if (rand.nextBoolean()) {
      cursor.translate(dir.reverse());
    }
    BlockType.FURNACE.getBrush().setFacing(side.reverse()).stroke(worldEditor, cursor);
    worldEditor.setItem(cursor, WorldEditor.FURNACE_FUEL_SLOT, new ItemStack(Items.COAL, 2 + rand.nextInt(3)));
    return this;
  }

  @Override
  public int getSize() {
    return 5;
  }

  @Override
  public boolean validLocation(WorldEditor editor, Direction dir, Coord pos) {
    Coord start;
    Coord end;

    start = pos.copy();
    end = start.copy();
    start.translate(dir.reverse(), 5);
    end.translate(dir, 5);
    start.translate(dir.antiClockwise(), 5);
    end.translate(dir.clockwise(), 5);
    start.down();
    end.up(3);

    for (Coord c : new RectHollow(start, end)) {
      if (editor.isAirBlock(c)) {
        return false;
      }
    }

    return true;
  }
}
