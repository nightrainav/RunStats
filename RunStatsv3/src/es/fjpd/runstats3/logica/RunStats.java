package es.fjpd.runstats3.logica;

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
		RunStats.context = getApplicationContext();
		actualizaLocaleSiNecesario();
	}

	public static Context getAppContext()
	{
		return RunStats.context;
	}


	public static String getVersion()
	{
		return version;
	}
	private void actualizaLocaleSiNecesario()
	{

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(RunStats.getAppContext());

		String idioma = pref.getString("idioma", "");	

		Configuration currConfig = getResources().getConfiguration();

		//Toast.makeText(context, "Actual: " + currConfig.locale.getDisplayLanguage() + " pref: " + pref.getString("idioma", "**") , Toast.LENGTH_LONG).show();


		// si se ha seleccionado algun idioma lo establecemos
		if (!idioma.equals(""))
		{
			Locale myLocale = new Locale(idioma);
			Locale.setDefault(myLocale);
			android.content.res.Configuration config = new android.content.res.Configuration();
			config.locale = myLocale;

			getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

		}
	}
	
}
