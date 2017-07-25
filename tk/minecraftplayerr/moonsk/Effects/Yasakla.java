package tk.minecraftplayerr.moonsk;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

public class Yasakla extends Effect {
	private Expression<String> oyuncu;
	private Expression<String> neden;
	//private Expression<Timespan> süre; 
	
	@SuppressWarnings("unchecked")
	@Override
	  public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
		oyuncu = (Expression<String>) exp[0];
		neden = (Expression<String>) exp[1];
		//süre = (Expression<Timespan>) exp[2];
		return true;
	  }
	@Override
	  public String toString(@Nullable Event evt, boolean arg1) {return null;}
	@Override
	  protected void execute(Event evt) {
		String sebep = null;
		Date süre = null;
		/*Date dNow = new Date();
	     SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss zzz");
	     if(süre!=null){((Expression<Timespan>) süre).getSingle(evt).getTicks();}*/
		if(neden!=null){sebep=neden.toString().replaceAll(Pattern.quote("\""),"");}
		Bukkit.getBanList(Type.NAME).addBan(oyuncu.getSingle(evt).toString().replaceAll(Pattern.quote("\""),""), sebep, süre, null);
		//Bukkit.getConsoleSender().sendMessage("Current Date: " + ft.format(dNow));
	  }
}
