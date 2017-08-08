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

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.bukkitutil.PlayerUtils;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;

public class Olayİptal extends Effect {
	private boolean cancel;

	@Override
	public boolean init(final Expression<?>[] a0, final int arg1, final Kleenean arg2, final ParseResult arg3) {
		if (arg2 == Kleenean.TRUE) {
			Skript.error("Olaylar zamanı geçtikten sonra iptal edilemezler. Hata:", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		cancel = arg1 == 0;
		final Class<? extends Event>[] es = ScriptLoader.getCurrentEvents();
		if (es == null)
			return false;
		for (final Class<? extends Event> e : es) {
			if (Cancellable.class.isAssignableFrom(e) || BlockCanBuildEvent.class.isAssignableFrom(e))
				return true;
		}
		if (ScriptLoader.isCurrentEvent(PlayerLoginEvent.class))
			Skript.error(
					"Sunucuya bağlanma olayı iptal edilemez ancak oyuncu atılabilir ('oyuncuyu \"...\" nedeniyle at')",
					ErrorQuality.SEMANTIC_ERROR);
		else
			Skript.error("Bu olay (" + Utils.A(ScriptLoader.getCurrentEventName()) + ") iptal edilemez.",
					ErrorQuality.SEMANTIC_ERROR);
		return false;
	}

	@Override
	public void execute(final Event e) {
		if (e instanceof Cancellable)
			((Cancellable) e).setCancelled(cancel);
		if (e instanceof PlayerInteractEvent) {
			((PlayerInteractEvent) e).setUseItemInHand(cancel ? Result.DENY : Result.DEFAULT);
			((PlayerInteractEvent) e).setUseInteractedBlock(cancel ? Result.DENY : Result.DEFAULT);
		} else if (e instanceof BlockCanBuildEvent) {
			((BlockCanBuildEvent) e).setBuildable(!cancel);
		} else if (e instanceof PlayerDropItemEvent) {
			PlayerUtils.updateInventory(((PlayerDropItemEvent) e).getPlayer());
		}
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return (cancel ? "" : "un") + "cancel event";
	}
}
