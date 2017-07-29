package effects;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class Yasakla extends Effect {
	Expression<String> oyuncu;
	Expression<String> yazı1;
	Expression<String> yazı2; 
	
	@SuppressWarnings("unchecked")
	@Override public boolean init(Expression<?>[] a, int arg1, Kleenean arg2, ParseResult arg3) {
		oyuncu=(Expression<String>) a[0];
		yazı1=(Expression<String>) a[1];
		yazı2=(Expression<String>) a[2];
		return true;
	}
	@Override public String toString(@Nullable Event evt, boolean arg1) {return null;}
	@Override protected void execute(Event a0) {
		String sebep=null;
		Date sonsüre=null;
		int eklenecekyüzyıl=0;
		int eklenecekyıl=0;
		int eklenecekay=0;
		int eklenecekhafta=0;
		int eklenecekgün=0;
		int ekleneceksaat=0;
		int eklenecekdakika=0;
		int ekleneceksaniye=0;
		int sebepyazısı=0;
		
		if(yazı1!=null){
			String yyazı1=yazı1.getSingle(a0).toString().toLowerCase().replaceAll(Pattern.quote("\""),"");
			if(yyazı1.contains("yüzyıl")){yyazı1=yyazı1.replaceAll("yüzyıl","").replaceAll(" ","");
				try{eklenecekyüzyıl=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("yıl")){yyazı1=yyazı1.replaceAll("yıl","").replaceAll(" ","");
				try{eklenecekyıl=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("ay")){yyazı1=yyazı1.replaceAll("ay","").replaceAll(" ","");
				try{eklenecekay=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("hafta")){yyazı1=yyazı1.replaceAll("hafta","").replaceAll(" ","");
				try{eklenecekhafta=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("gün")){yyazı1=yyazı1.replaceAll("gün","").replaceAll(" ","");
				try{eklenecekgün=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("saat")){yyazı1=yyazı1.replaceAll("saat","").replaceAll(" ","");
				try{ekleneceksaat=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("dakika")){yyazı1=yyazı1.replaceAll("dakika","").replaceAll(" ","");
				try{eklenecekdakika=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
			if(yyazı1.contains("saniye")){yyazı1=yyazı1.replaceAll("saniye","").replaceAll(" ","");
				try{ekleneceksaniye=Integer.parseInt(yyazı1);sebepyazısı=1;}catch(Error e){}
			}
		}
			
		if(yazı2!= null){
			String yyazı2=yazı2.getSingle(a0).toString().toLowerCase().replaceAll(Pattern.quote("\""),"");
			if(yyazı2.contains("yüzyıl")){yyazı2=yyazı2.replaceAll("yüzyıl","").replaceAll(" ","");
				try{eklenecekyüzyıl=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("yıl")){yyazı2=yyazı2.replaceAll("yıl","").replaceAll(" ","");
				try{eklenecekyıl=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("ay")){yyazı2=yyazı2.replaceAll("ay","").replaceAll(" ","");
				try{eklenecekay=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("hafta")){yyazı2=yyazı2.replaceAll("hafta","").replaceAll(" ","");
				try{eklenecekhafta=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("gün")){yyazı2=yyazı2.replaceAll("gün","").replaceAll(" ","");
				try{eklenecekgün=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("saat")){yyazı2=yyazı2.replaceAll("saat","").replaceAll(" ","");
				try{ekleneceksaat=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("dakika")){yyazı2=yyazı2.replaceAll("dakika","").replaceAll(" ","");
				try{eklenecekdakika=Integer.parseInt(yyazı2);}catch(Error e){}
			}
			if(yyazı2.contains("saniye")){yyazı2=yyazı2.replaceAll("saniye","").replaceAll(" ","");
				try{ekleneceksaniye=Integer.parseInt(yyazı2);}catch(Error e){}
			}
		}
		
		if(yazı1!=null){sebep=yazı1.getSingle(a0).toString();
			if(sebepyazısı==1&&yazı2!=null){sebep=yazı2.getSingle(a0).toString();}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		
		if(eklenecekyüzyıl>0){eklenecekyüzyıl=eklenecekyüzyıl*100;calendar.add(Calendar.YEAR, eklenecekyüzyıl);}
		if(eklenecekyıl>0){calendar.add(Calendar.YEAR, eklenecekyıl);}
		if(eklenecekay>0){calendar.add(Calendar.MONTH, eklenecekay);}
		if(eklenecekhafta>0){calendar.add(Calendar.WEEK_OF_MONTH, eklenecekhafta);}
		if(eklenecekgün>0){calendar.add(Calendar.DAY_OF_MONTH, eklenecekgün);}
		if(ekleneceksaat>0){calendar.add(Calendar.HOUR, ekleneceksaat);}
		if(eklenecekdakika>0){calendar.add(Calendar.MINUTE, ekleneceksaat);}
		if(ekleneceksaniye>0){calendar.add(Calendar.SECOND, ekleneceksaniye);}
		sdf.format(calendar.getTime());

        sonsüre=calendar.getTime();

		Bukkit.getBanList(Type.NAME).addBan(oyuncu.getSingle(a0).toString().replaceAll(Pattern.quote("\""),""), sebep, sonsüre, null);
		
	  }
}
