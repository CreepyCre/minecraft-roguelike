package greymerk.roguelike.dungeon.settings.builtin;

import java.util.stream.IntStream;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;

import static com.google.common.collect.Lists.newArrayList;
import static greymerk.roguelike.dungeon.towers.Tower.PYRAMID;
import static greymerk.roguelike.theme.Theme.ICE;
import static net.minecraftforge.common.BiomeDictionary.Type.SNOWY;

public class SettingsIceTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "ice");

  public SettingsIceTheme() {
    super(ID);
    setExclusive(true);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(SNOWY));
    setTowerSettings(new TowerSettings(PYRAMID, ICE));
    IntStream.range(0, 5)
        .forEach(i -> getLevelSettings().put(i, generateLevelSettings(i)));
  }

  private LevelSettings generateLevelSettings(int i) {
    LevelSettings levelSettings = new LevelSettings();

    levelSettings.setTheme(ICE);

    return levelSettings;
  }
}
