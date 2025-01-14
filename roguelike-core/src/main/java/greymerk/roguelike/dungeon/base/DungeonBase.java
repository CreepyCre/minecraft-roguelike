package greymerk.roguelike.dungeon.base;

import java.util.List;

import greymerk.roguelike.dungeon.rooms.RoomSetting;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.worldgen.Direction;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.WorldEditor;
import greymerk.roguelike.worldgen.shapes.RectHollow;
import greymerk.roguelike.worldgen.spawners.MobType;
import greymerk.roguelike.worldgen.spawners.SpawnerSettings;
import lombok.EqualsAndHashCode;

import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;

@EqualsAndHashCode
public abstract class DungeonBase implements Comparable<DungeonBase> {

  private final RoomSetting roomSetting;
  protected final LevelSettings levelSettings;
  protected final WorldEditor worldEditor;

  public DungeonBase(RoomSetting roomSetting, LevelSettings levelSettings, WorldEditor worldEditor) {
    this.roomSetting = roomSetting;
    this.levelSettings = levelSettings;
    this.worldEditor = worldEditor;
  }

  public List<Coord> chooseRandomLocations(int limit, List<Coord> spaces) {
    shuffle(spaces, worldEditor.getRandom());

    return spaces.stream()
        .limit(limit)
        .collect(toList());
  }

  public abstract DungeonBase generate(Coord origin, List<Direction> entrances);

  protected void generateSpawner(Coord spawnerLocation, MobType... defaultMobs) {
    int difficulty = levelSettings.getDifficulty(spawnerLocation);
    getSpawnerSettings(difficulty, defaultMobs, levelSettings.getSpawners()).generateSpawner(worldEditor, spawnerLocation, difficulty);
  }

  private SpawnerSettings getSpawnerSettings(int difficulty, MobType[] defaultMobs, SpawnerSettings levelSettingsSpawners) {
    String spawnerId = roomSetting.getSpawnerId();
    if (spawnerId != null) {

      // todo: lift
      DungeonSettings dungeonSettings = null;
      try {
        dungeonSettings = SettingsResolver.initSettingsResolver().getByName(spawnerId);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (dungeonSettings != null) {
        SpawnerSettings dungeonSettingsSpawners = dungeonSettings.getLevelSettings(difficulty).getSpawners();
        if (!dungeonSettingsSpawners.isEmpty()) {
          return dungeonSettingsSpawners;
        }
      }
    }
    return !levelSettingsSpawners.isEmpty()
        ? levelSettingsSpawners
        : MobType.newSpawnerSetting(defaultMobs.length > 0 ? defaultMobs : MobType.COMMON_MOBS);
  }

  public abstract int getSize();

  public boolean validLocation(WorldEditor editor, Direction dir, Coord pos) {

    int size = getSize();
    Coord start = new Coord(pos.getX() - size, pos.getY() - 2, pos.getZ() - size);
    Coord end = new Coord(pos.getX() + size, pos.getY() + 5, pos.getZ() + size);

    for (Coord cursor : new RectHollow(start, end)) {
      if (!editor.isSolidBlock(cursor)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(DungeonBase other) {
    return getSize() - other.getSize();
  }

  protected RoomSetting getRoomSetting() {
    return roomSetting;
  }
}
