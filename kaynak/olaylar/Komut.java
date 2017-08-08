/**
 *  
 *  This file is part of SkripTR. SkripTR is free software: you can redistribute it and/or modify
 *  SkripTR is using Skript software.
 * 
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter GÃ¼ttinger and contributors
 */

package olaylar;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.StringUtils;

public class Komut extends SkriptEvent {
	@Nullable
	private String komut = null;

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
		if (args[0] != null) {
			komut = ((Literal<String>) args[0]).getSingle();
			if (komut.startsWith("/"))
				komut = komut.substring(1);
		}
		return true;
	}

	@Override
	public boolean check(final Event e) {
		if (komut == null)
			return true;
		final String mesaj;
		if (e instanceof PlayerCommandPreprocessEvent) {
			assert ((PlayerCommandPreprocessEvent) e).getMessage().startsWith("/");
			mesaj = ((PlayerCommandPreprocessEvent) e).getMessage().substring(1);
		} else {
			mesaj = ((ServerCommandEvent) e).getCommand();
		}
		return StringUtils.startsWithIgnoreCase(mesaj, komut) && (komut.contains(" ")
				|| mesaj.length() == komut.length() || Character.isWhitespace(mesaj.charAt(komut.length())));
	}

	@Override
	public String toString(@Nullable Event e, boolean b) {return "";}

}
