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

import org.bukkit.event.Event;

import ch.njol.skript.events.bukkit.ScriptEvent;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;

public class ScriptYüklendiğinde extends SelfRegisteringSkriptEvent {

	private boolean yükle;

	@Override
	public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
		yükle = matchedPattern == 0;
		return true;
	}

	private Trigger t;

	@Override
	public void register(final Trigger t) {
		this.t = t;
		if (yükle)
			t.execute(new ScriptEvent());
	}

	@Override
	public void unregister(final Trigger t) {
		assert t == this.t;
		if (!yükle)
			t.execute(new ScriptEvent());
		this.t = null;
	}

	@Override
	public void unregisterAll() {
		if (!yükle && t != null)
			t.execute(new ScriptEvent());
		t = null;
	}

	@Override
	public String toString(Event e, boolean debug) {return "";}

}
