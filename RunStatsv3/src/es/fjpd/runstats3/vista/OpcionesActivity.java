package es.fjpd.runstats3.vista;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import es.fjpd.runstats3.R;

public class OpcionesActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferencias);
	}
}
