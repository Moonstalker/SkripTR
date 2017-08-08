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

package etkiler;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.bukkitutil.PlayerUtils;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class MesajYayınla extends Effect {

	private Expression<String> messages;
	@Nullable
	private Expression<World> worlds;

	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean init(final Expression<?>[] vars, final int matchedPattern, final Kleenean isDelayed,
			final ParseResult parser) {
		messages = (Expression<String>) vars[1];
		worlds = (Expression<World>) vars[0];
		return true;
	}

	@Override
	public void execute(final Event e) {
		for (final String m : messages.getArray(e)) {
			final Expression<World> worlds = this.worlds;
			if (worlds == null) {
				for (final Player p : PlayerUtils.getOnlinePlayers()) {
					p.sendMessage(m);
				}
				Bukkit.getConsoleSender().sendMessage(m);
			} else {
				for (final World w : worlds.getArray(e)) {
					for (final Player p : w.getPlayers()) {
						p.sendMessage(m);
					}
				}
			}
		}
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		final Expression<World> worlds = this.worlds;
		return "broadcast " + messages.toString(e, debug) + (worlds == null ? "" : " to " + worlds.toString(e, debug));
	}

}
