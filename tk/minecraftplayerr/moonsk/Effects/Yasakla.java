package tk.minecraftplayerr.moonsk;

import org.apache.commons.io.IOExceptionWithCause;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class Yasakla extends Effect {
	private Expression<String> oyuncu;
	private Expression<String> yazı1;
	private Expression<String> yazı2; 
	
	@SuppressWarnings("unchecked")
	@Override
	  public boolean init(Expression<?>[] exp, int arg1, Kleenean arg2, ParseResult arg3) {
		oyuncu = (Expression<String>) exp[0];
		yazı1 = (Expression<String>) exp[1];
		yazı2 = (Expression<String>) exp[2];
		return true;
	  }
	@Override
	  public String toString(@Nullable Event evt, boolean arg1) {return null;}
	@Override
	  protected void execute(Event evt) {
		String sebep=null;
		Date sonsüre=null;
		int eklenecekgün=0;
		int ekleneceksaat=0;
		int eklenecekdakika=0;
		int ekleneceksaniye=0;
		int sebepyazısı=0;
		
		if(yazı1!=null){
			String yyazı1=yazı1.getSingle(evt).toString();
			yyazı1.toLowerCase().replaceAll(Pattern.quote("\""),"");
			if(yyazı1.contains("gün")){yyazı1=yyazı1.replaceAll("gün","").replaceAll(" ","");
				try{eklenecekgün=Integer.parseInt(yyazı1);sebepyazısı=2;}catch(NumberFormatException e){}
			}
			if(yyazı1.contains("saat")){yyazı1=yyazı1.replaceAll("saat","").replaceAll(" ","");
				try{ekleneceksaat=Integer.parseInt(yyazı1);sebepyazısı=2;}catch(NumberFormatException e){}
			}
			if(yyazı1.contains("dakika")){yyazı1=yyazı1.replaceAll("dakika","").replaceAll(" ","");
				try{eklenecekdakika=Integer.parseInt(yyazı1);sebepyazısı=2;}catch(NumberFormatException e){}
			}
			if(yyazı1.contains("saniye")){yyazı1=yyazı1.replaceAll("saniye","").replaceAll(" ","");
				try{ekleneceksaniye=Integer.parseInt(yyazı1);sebepyazısı=2;}catch(NumberFormatException e){}
			}
		}
			
		if(yazı2!= null){
			String yyazı2=yazı2.getSingle(evt).toString();
			yyazı2.toLowerCase().replaceAll(Pattern.quote("\""),"");
			if(yyazı2.contains("gün")){yyazı2=yyazı2.replaceAll("gün","").replaceAll(" ","");
				try{eklenecekgün=Integer.parseInt(yyazı2);sebepyazısı=1;}catch(NumberFormatException e){}
			}
			if(yyazı2.contains("saat")){yyazı2=yyazı2.replaceAll("saat","").replaceAll(" ","");
				try{ekleneceksaat=Integer.parseInt(yyazı2);sebepyazısı=1;}catch(NumberFormatException e){}
			}
			if(yyazı2.contains("dakika")){yyazı2=yyazı2.replaceAll("dakika","").replaceAll(" ","");
				try{eklenecekdakika=Integer.parseInt(yyazı2);sebepyazısı=1;}catch(NumberFormatException e){}
			}
			if(yyazı2.contains("saniye")){yyazı2=yyazı2.replaceAll("saniye","").replaceAll(" ","");
				try{ekleneceksaniye=Integer.parseInt(yyazı2);sebepyazısı=1;}catch(NumberFormatException e){}
			}
		}
		
		if(sebepyazısı==1){
			sebep=yazı1.getSingle(evt).toString();
		}
		if(sebepyazısı==2){
			sebep=yazı2.getSingle(evt).toString();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		
		if(eklenecekgün>0){calendar.add(Calendar.DAY_OF_MONTH, eklenecekgün);}
		if(ekleneceksaat>0){calendar.add(Calendar.HOUR, ekleneceksaat);}
		if(ekleneceksaat>0){calendar.add(Calendar.HOUR, ekleneceksaat);}
		sdf.format(calendar.getTime());

        sonsüre=calendar.getTime();

		Bukkit.getBanList(Type.NAME).addBan(oyuncu.getSingle(evt).toString().replaceAll(Pattern.quote("\""),""), sebep, sonsüre, null);
		
	  }
}
