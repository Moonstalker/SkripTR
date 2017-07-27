package expressions;

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
    	for(OfflinePlayer all : Bukkit.getOperators()){
    		if(ops.length() > 0){ops.append(", ");}
    	ops.append(all.getName());
    	}
    	
        return new String[] {ops.toString()};
    }
    @Override
    public boolean isSingle() {return true;}
    @Override
    public Class getReturnType() {return String.class;}
    @Override
    public String toString(Event event, boolean b) {return "";}

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
