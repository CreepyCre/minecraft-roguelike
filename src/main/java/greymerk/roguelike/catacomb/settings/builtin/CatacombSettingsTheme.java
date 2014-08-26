package greymerk.roguelike.catacomb.settings.builtin;

import greymerk.roguelike.catacomb.settings.CatacombLevelSettings;
import greymerk.roguelike.catacomb.settings.CatacombSettings;
import greymerk.roguelike.catacomb.theme.Theme;
import greymerk.roguelike.catacomb.tower.Tower;

public class CatacombSettingsTheme extends CatacombSettings{
	
	public CatacombSettingsTheme(){
		
		this.tower = Tower.ROGUE;
		
		Theme[] themes = {Theme.OAK, Theme.OAK, Theme.CRYPT, Theme.MOSSY, Theme.NETHER};
		
		for(int i = 0; i < 5; ++i){
			CatacombLevelSettings level = new CatacombLevelSettings();
			level.setTheme(Theme.getTheme(themes[i]));
			levels.put(i, level);
		}
	}

	
}