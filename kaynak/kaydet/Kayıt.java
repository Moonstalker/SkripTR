package kaydet;

import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import effects.KonsolaMesajGönder;
import effects.OyuncuyaMesajGönder;
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
            Skript.registerEffect(OyuncuyaMesajGönder.class,
            		"mesaj gönder -> %player% -> %string%");
            Skript.registerEffect(KonsolaMesajGönder.class,
            		"konsola mesaj gönder -> %string%");
            
            registerEvent(PlayerJoinEvent.class, "[sunucuya] (girildiğinde|giriş yapıldığında)");
            registerEvent(PlayerQuitEvent.class, "[sunucudan] (çıkış yapıldığında|çıkıldığında)");
            registerEvent(BlockDamageEvent.class, "blok kırılmaya başlandığında");
            registerEvent(PlayerMoveEvent.class, "herhangi bir yürümede");
		
		}else{setEnabled(false);}
	}
	@SuppressWarnings("unchecked")
	public static void registerEvent(@SuppressWarnings("rawtypes") Class clazz, String syntax) {
		Skript.registerEvent(syntax, SimpleEvent.class, clazz, syntax);
	}
}
