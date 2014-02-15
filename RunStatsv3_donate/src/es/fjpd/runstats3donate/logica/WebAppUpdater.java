package es.fjpd.runstats3donate.logica;
import android.content.*;
import android.widget.*;
import android.webkit.*;
import android.util.*;

public class WebAppUpdater
{
	Context mContext;
	
	public WebAppUpdater(Context c)
	{
		mContext = c;
	}
	
	@JavascriptInterface
	public String pruebaInterface(String texto)
	{
		Log.d("Runstats", "Ha entrado: "+texto);
		Toast.makeText(mContext, "texto: "+texto, Toast.LENGTH_LONG).show(); 
		return "Ejecutado";
	}
}
