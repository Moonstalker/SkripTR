package com.denemeproje.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            Skript.registerAddon(this);
            Skript.registerExpression(oplist.class, String.class, ExpressionType.PROPERTY,
            "oplist");
            Skript.registerEffect(SunucuKapat.class, "sunucuyu kapat");
        }else{setEnabled(false);}
	}

}
