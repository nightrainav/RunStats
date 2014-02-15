package es.fjpd.runstats3donate.vista;

import android.preference.PreferenceActivity;
import android.os.Bundle;
import es.fjpd.runstats3donate.R;
import android.content.*;
import java.util.*;
import android.widget.*;
import es.fjpd.runstats3donate.logica.*;

public class PreferenciasActivity extends PreferenceActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		RunStats.actualizaLocaleSiNecesario();
		
		addPreferencesFromResource(R.xml.preferencias);
	}

	
}
