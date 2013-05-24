package es.fjpd.runstats3.logica;

import android.app.*;
import android.content.*;

public class RunStats extends Application
{

	private static final String version = "3";
	
	private static Context context;
	
	public void onCreate()
	{
		super.onCreate();
		RunStats.context = getApplicationContext();
	}
	
	public static Context getAppContext()
	{
		return RunStats.context;
	}
	

	public static String getVersion()
	{
		return version;
	}
}
