package tk.minecraftplayerr.moonsk;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class SunucuKapat extends Effect {

	@Override
	  public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {return true;}
	@Override
	  public String toString(@Nullable Event arg0, boolean arg1) {return "";}
	@Override
	  protected void execute(Event arg0) {
	    Bukkit.getServer().shutdown();
	  }
}
