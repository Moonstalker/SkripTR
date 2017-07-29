package effects;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class KonsolaMesajGönder extends Effect {
	Expression<String> yazı;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] a, int arg1, Kleenean arg2, ParseResult arg3) {
		yazı=(Expression<String>) a[0];
		return true;
	}

	@Override public String toString(@Nullable Event arg0, boolean arg1) {return null;}

	@Override
	protected void execute(Event a0) {
		Bukkit.getConsoleSender().sendMessage(yazı.getSingle(a0).toString());
	}

}
