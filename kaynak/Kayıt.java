package kayıtişlemleri;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import effects.SunucuKapat;
import effects.Yasakla;
import expressions.oplist;

public class Kayıt extends JavaPlugin {
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            Skript.registerAddon(this);
            Skript.registerExpression(oplist.class, String.class, ExpressionType.PROPERTY,
            		"(oplist|opliste|oplistesi|op listesi|operatör listesi)");
            Skript.registerEffect(SunucuKapat.class,"sunucuyu kapat");
            Skript.registerEffect(Yasakla.class,
            		"(ban|banla|yasakla) -> %string% [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]]",
            		"%string% -> (ban|banla|yasakla) [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca]]",
            		"%string% [-> [(sebep|neden|süre|zaman):] %-string% [boyunca] [(banla|yasakla)]] [-> [(sebep|neden|süre|zaman):] %-string% [boyunca] [(banla|yasakla)]]");
        }else{setEnabled(false);}
	}

}
