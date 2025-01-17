package greymerk.roguelike.dungeon.settings.builtin;

import net.minecraft.init.Items;

import greymerk.roguelike.dungeon.segment.Segment;
import greymerk.roguelike.dungeon.segment.SegmentGenerator;
import greymerk.roguelike.dungeon.settings.DungeonSettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.settings.SettingIdentifier;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.TowerSettings;
import greymerk.roguelike.dungeon.settings.base.SettingsBase;
import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.treasure.loot.WeightedRandomLoot;
import greymerk.roguelike.treasure.loot.rule.SingleUseLootRule;
import greymerk.roguelike.worldgen.filter.Filter;

import static com.google.common.collect.Lists.newArrayList;
import static net.minecraftforge.common.BiomeDictionary.Type.JUNGLE;

public class SettingsJungleTheme extends DungeonSettings {

  public static final SettingIdentifier ID = new SettingIdentifier(SettingsContainer.BUILTIN_NAMESPACE, "jungle");

  public SettingsJungleTheme() {
    super(ID);
    setExclusive(true);
    getInherit().add(SettingsBase.ID);
    getCriteria().setBiomeTypes(newArrayList(JUNGLE));
    setTowerSettings(new TowerSettings(Tower.JUNGLE, Theme.JUNGLE));

    for (int i = 0; i < 5; ++i) {
      getLootRules().add(new SingleUseLootRule(new WeightedRandomLoot(Items.EMERALD, 0, 1, 1 + i, 1), i, 6));
      getLootRules().add(new SingleUseLootRule(new WeightedRandomLoot(Items.DIAMOND, 1), i, 3 + i * 3));
    }


    Theme[] themes = {Theme.JUNGLE, Theme.JUNGLE, Theme.MOSSY, Theme.MOSSY, Theme.NETHER};

    SegmentGenerator segments;
    for (int i = 0; i < 5; ++i) {
      LevelSettings level = new LevelSettings();
      if (i < 4) {
        level.setDifficulty(3);
        segments = new SegmentGenerator(Segment.MOSSYARCH);
        segments.add(Segment.SHELF, 2);
        segments.add(Segment.INSET, 2);
        segments.add(Segment.JUNGLE, 5);
        segments.add(Segment.SKULL, 1);
        segments.add(Segment.ARROW, 1);
        segments.add(Segment.CELL, 1);
        segments.add(Segment.SILVERFISH, 1);
        segments.add(Segment.CHEST, 1);
        segments.add(Segment.SPAWNER, 2);
        level.setSegments(segments);
        level.addFilter(Filter.VINE);
      }

      level.setTheme(themes[i].getThemeBase());
      getLevelSettings().put(i, level);
    }
  }
}