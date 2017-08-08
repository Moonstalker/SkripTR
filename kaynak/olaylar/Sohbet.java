package olaylar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.EventExecutor;

import ch.njol.skript.SkriptConfig;
import ch.njol.skript.SkriptEventHandler;
import ch.njol.skript.events.util.PlayerChatEventHandler;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.util.Task;

@SuppressWarnings("deprecation")
public class Sohbet extends SelfRegisteringSkriptEvent {

	final static Collection<Trigger> triggers = new ArrayList<>();

	private static boolean registeredExecutor = false;
	private final static EventExecutor executor = new EventExecutor() {

		final void execute(final Event e) {
			SkriptEventHandler.logEventStart(e);
			for (final Trigger t : triggers) {
				assert t != null : triggers;
				SkriptEventHandler.logTriggerStart(t);
				t.execute(e);
				SkriptEventHandler.logTriggerEnd(t);
			}
			SkriptEventHandler.logEventEnd();
		}

		@Override
		public void execute(final @Nullable Listener l, final @Nullable Event e) throws EventException {
			if (e == null)
				return;
			if (!triggers.isEmpty()) {
				if (e instanceof PlayerChatEvent || !e.isAsynchronous()) {
					execute(e);
					return;
				}
				Task.callSync(new Callable<Void>() {
					@Override
					@Nullable
					public Void call() throws Exception {
						execute(e);
						return null;
					}
				});
			}
		}
	};

	@Override
	public boolean init(final Literal<?>[] args, final int matchedPattern, final ParseResult parser) {
		return true;
	}

	@Override public String toString(@Nullable Event e, boolean b) {return "";}

	@Override
	public void register(final Trigger t) {
		triggers.add(t);
		if (!registeredExecutor) {
			PlayerChatEventHandler.registerChatEvent(SkriptConfig.defaultEventPriority.value(), executor, true);
			registeredExecutor = true;
		}
	}

	@Override
	public void unregister(final Trigger t) {
		triggers.remove(t);
	}

	@Override
	public void unregisterAll() {
		triggers.clear();
	}
}
