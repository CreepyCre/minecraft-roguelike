package greymerk.roguelike.dungeon.settings.base;

import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;

public class SettingsTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "theme");

  public SettingsTheme() {
    super(ID);

    setTowerSettings(new TowerSettings(Tower.ROGUE, Theme.TOWER));

    Theme[] themes = {Theme.OAK, Theme.SPRUCE, Theme.CRYPT, Theme.MOSSY, Theme.HELL};

    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      level.setTheme(themes[i].getThemeBase());
      getLevelSettings().put(i, level);
    }
  }
}
