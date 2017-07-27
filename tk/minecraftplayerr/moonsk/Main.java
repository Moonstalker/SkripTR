package tk.minecraftplayerr.moonsk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import tk.minecraftplayerr.moonsk.SunucuKapat;
import tk.minecraftplayerr.moonsk.oplist;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            Skript.registerAddon(this);
            Skript.registerExpression(oplist.class, String.class, ExpressionType.PROPERTY,
            		"(oplist|opliste|oplistesi|op listesi|operatör listesi)");
            Skript.registerEffect(SunucuKapat.class,
            		"sunucuyu kapat");
            Skript.registerEffect(Yasakla.class,
            		"%string% -> (ban|banla|yasakla) [-> [(sebep|neden|süre|zaman):] %-string%] [-> [(sebep|neden|süre|zaman):] %-string%]",
            		"(ban|banla|yasakla) -> %string% [-> [(sebep|neden|süre|zaman):] %-string%] [-> [(sebep|neden|süre|zaman):] %-string%]");
        }else{setEnabled(false);}
	}

}
