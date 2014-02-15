package es.fjpd.runstats3donate.logica;

import android.app.*;
import android.content.*;
import android.preference.*;
import android.content.res.*;
import android.widget.*;
import java.util.*;

public class RunStats extends Application
{

	private static final String version = "3";

	private static Context context;

	public void onCreate()
	{
		super.onCreate();
		
		context = this; //getApplicationContext();
		
		actualizaLocaleSiNecesario();
		
		context = this;
	}

	public static Context getAppContext()
	{
		return context;
	}


	public static String getVersion()
	{
		return version;
	}

	public static void actualizaLocaleSiNecesario()
	{

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(RunStats.getAppContext());

		String idioma = pref.getString("idioma", "");	

		Configuration currConfig = RunStats.getAppContext().getResources().getConfiguration();

		//Toast.makeText(context, "Actual: " + currConfig.locale.getLanguage() + " pref: " + pref.getString("idioma", "**") , Toast.LENGTH_LONG).show();


		// si se ha seleccionado algun idioma lo establecemos
		if (!idioma.equals("") && !currConfig.locale.getLanguage().equals(idioma))
		{
			Locale myLocale = new Locale(idioma);
			Locale.setDefault(myLocale);
			android.content.res.Configuration config = new android.content.res.Configuration();
			config.locale = myLocale;

			RunStats.getAppContext().getResources().updateConfiguration(config, RunStats.getAppContext().getResources().getDisplayMetrics());

			//getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

			//RunStats.context.getResources().updateConfiguration(config, RunStats.context.getResources().getDisplayMetrics());

			//Toast.makeText(context, "CAMBIADO !!! - Actual: " + currConfig.locale.getLanguage() + " pref: " + pref.getString("idioma", "**") , Toast.LENGTH_LONG).show();


		}
	}
	
	
}
