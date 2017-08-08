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

import java.util.Arrays;
import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.log.CountingLogHandler;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.log.ParseLogHandler;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Patterns;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;

public class Değiştir extends Effect {
	private static Patterns<ChangeMode> patterns = new Patterns<>(new Object[][] {
			// ~ işareti olanlar 2. değerdir.
			{"(artır|arttır|ekle) %objects% -> %~objects%", ChangeMode.ADD},
			{"ayarla %~objects% -> %objects%", ChangeMode.SET},
			{"~objects%'den tüm %objects%'leri sil", ChangeMode.REMOVE_ALL},
			{"%~objects%['(i|dan)] [->] %objects% [kadar] (azalt|düşür)", ChangeMode.REMOVE},
			{"%~objects% -> (sil|temizle)", ChangeMode.DELETE},
			{"%~objects% -> sıfırla", ChangeMode.RESET}
	});
	
	private Expression<?> changed;
	@Nullable
	private Expression<?> changer = null;
	
	private ChangeMode mode;
	
	private boolean single;
	
//	private Changer<?, ?> c = null;
	
	@SuppressWarnings({"unchecked"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parser) {
		mode = patterns.getInfo(matchedPattern);
		
		switch (mode) {
			case ADD:
				if (matchedPattern == 0) {
					changer = exprs[0];
					changed = exprs[1];
				} else {
					changer = exprs[1];
					changed = exprs[0];
				}
				break;
			case SET:
				changer = exprs[1];
				changed = exprs[0];
				break;
			case REMOVE_ALL:
				changer = exprs[0];
				changed = exprs[1];
				break;
			case REMOVE:
				if (matchedPattern == 5) {
					changer = exprs[0];
					changed = exprs[1];
				} else {
					changer = exprs[1];
					changed = exprs[0];
				}
				break;
			case DELETE:
				changed = exprs[0];
				break;
			case RESET:
				changed = exprs[0];
		}
		
		final CountingLogHandler h = SkriptLogger.startLogHandler(new CountingLogHandler(Level.SEVERE));
		final Class<?>[] rs;
		final String what;
		try {
			rs = changed.acceptChange(mode);
			final ClassInfo<?> c = Classes.getSuperClassInfo(changed.getReturnType());
			final Changer<?> changer = c.getChanger();
			what = changer == null || !Arrays.equals(changer.acceptChange(mode), rs) ? changed.toString(null, false) : c.getName().withIndefiniteArticle();
		} finally {
			h.stop();
		}
		if (rs == null) {
			if (h.getCount() > 0)
				return false;
			switch (mode) {
				case SET:
					Skript.error(what + " hiçbir şeye ayarlanmaaz.", ErrorQuality.SEMANTIC_ERROR);
					break;
				case DELETE:
					if (changed.acceptChange(ChangeMode.RESET) != null)
						Skript.error(what + " silinemez/sıfırlanamaz. It can however be reset which might result in the desired effect.", ErrorQuality.SEMANTIC_ERROR);
					else
						Skript.error(what + " silinemez/sıfırlanamaz", ErrorQuality.SEMANTIC_ERROR);
					break;
				case REMOVE_ALL:
					if (changed.acceptChange(ChangeMode.REMOVE) != null) {
						Skript.error(what + " can't have 'all of something' removed from it. Use 'remove' instead of 'remove all' to fix this.", ErrorQuality.SEMANTIC_ERROR);
						break;
					}
					//$FALL-THROUGH$
				case ADD:
				case REMOVE:
					Skript.error(what + " can't have anything " + (mode == ChangeMode.ADD ? "added to" : "removed from") + " it", ErrorQuality.SEMANTIC_ERROR);
					break;
				case RESET:
					if (changed.acceptChange(ChangeMode.DELETE) != null)
						Skript.error(what + " sıfırlanamaz ancak silinebilir.", ErrorQuality.SEMANTIC_ERROR);
					else
						Skript.error(what + " sıfırlanamaz", ErrorQuality.SEMANTIC_ERROR);
			}
			return false;
		}
		
		final Class<?>[] rs2 = new Class<?>[rs.length];
		for (int i = 0; i < rs.length; i++)
			rs2[i] = rs[i].isArray() ? rs[i].getComponentType() : rs[i];
		final boolean allSingle = Arrays.equals(rs, rs2);
		
		Expression<?> ch = changer;
		if (ch != null) {
			Expression<?> v = null;
			final ParseLogHandler log = SkriptLogger.startParseLogHandler();
			try {
				for (final Class<?> r : rs) {
					log.clear();
					if ((r.isArray() ? r.getComponentType() : r).isAssignableFrom(ch.getReturnType())) {
						v = ch.getConvertedExpression(Object.class);
						break; // break even if v == null as it won't convert to Object apparently
					}
				}
				if (v == null)
					v = ch.getConvertedExpression((Class<Object>[]) rs2);
				if (v == null) {
					if (log.hasError()) {
						log.printError();
						return false;
					}
					log.clear();
					log.printLog();
					final Class<?>[] r = new Class[rs.length];
					for (int i = 0; i < rs.length; i++)
						r[i] = rs[i].isArray() ? rs[i].getComponentType() : rs[i];
					if (rs.length == 1 && rs[0] == Object.class)
						Skript.error("Can't understand this expression: " + changer, ErrorQuality.NOT_AN_EXPRESSION);
					else if (mode == ChangeMode.SET)
						Skript.error(what + " şuna ayarlanamaz: " + changer + " çünkü the latter is " + SkriptParser.notOfType(r), ErrorQuality.SEMANTIC_ERROR);
					else
						Skript.error(changer + " can't be " + (mode == ChangeMode.ADD ? "added to" : "removed from") + " " + what + " çünkü the former is " + SkriptParser.notOfType(r), ErrorQuality.SEMANTIC_ERROR);
					return false;
				}
				log.printLog();
			} finally {
				log.stop();
			}
			
			Class<?> x = Utils.getSuperType(rs2);
			single = allSingle;
			for (int i = 0; i < rs.length; i++) {
				if (rs2[i].isAssignableFrom(v.getReturnType())) {
					single = !rs[i].isArray();
					x = rs2[i];
					break;
				}
			}
			assert x != null;
			changer = ch = v;
			
			if (!ch.isSingle() && single) {
				if (mode == ChangeMode.SET)
					Skript.error(changed + " can only be set to one " + Classes.getSuperClassInfo(x).getName() + ", not more", ErrorQuality.SEMANTIC_ERROR);
				else
					Skript.error("only one " + Classes.getSuperClassInfo(x).getName() + " can be " + (mode == ChangeMode.ADD ? "added to" : "removed from") + " " + changed + ", not more", ErrorQuality.SEMANTIC_ERROR);
				return false;
			}
			
			if (changed instanceof Variable && !((Variable<?>) changed).isLocal() && (mode == ChangeMode.SET || ((Variable<?>) changed).isList() && mode == ChangeMode.ADD)) {
				final ClassInfo<?> ci = Classes.getSuperClassInfo(ch.getReturnType());
				if (ci.getC() != Object.class && ci.getSerializer() == null && ci.getSerializeAs() == null && !SkriptConfig.disableObjectCannotBeSavedWarnings.value())
					Skript.warning(ci.getName().withIndefiniteArticle() + " cannot be saved, i.e. the contents of the variable " + changed + " will be lost when the server stops.");
			}
		}
		return true;
	}
	
	@Override
	protected void execute(final Event e) {
		final Expression<?> changer = this.changer;
		final Object[] delta = changer == null ? null : changer.getArray(e);
		if (delta != null && delta.length == 0)
			return;
		changed.change(e, delta, mode);
	}
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "";
	}
	
}
