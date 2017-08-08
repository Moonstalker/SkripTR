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

package ifadeler;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

public class oplist extends SimpleExpression<String> {
	public static final ExpressionType PROPERTY = null;

	@Override
	protected String[] get(Event event) {

		StringBuilder ops = new StringBuilder();
		for (OfflinePlayer all : Bukkit.getOperators()) {
			if (ops.length() > 0) {
				ops.append(", ");
			}
			ops.append(all.getName());
		}

		return new String[] { ops.toString() };
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event event, boolean b) {return "";}

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {return true;}
}
