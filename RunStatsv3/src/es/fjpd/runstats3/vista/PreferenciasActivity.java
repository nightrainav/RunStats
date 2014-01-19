package es.fjpd.runstats3.vista;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import es.fjpd.runstats3.R;
import android.content.*;
import java.util.*;
import android.widget.*;
import es.fjpd.runstats3.logica.*;

public class PreferenciasActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferencias);
	}

	/*@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
	{
		// si se ha cambiado la preferencia de idioma ...
		Toast.makeText(PreferenciasActivity.this, key+" "+prefs.getString(key,"**") , Toast.LENGTH_LONG).show();
		
		if (key.equals("idioma"))
		{
			String idioma = prefs.getString("idioma", "");	

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

	}*/
}
