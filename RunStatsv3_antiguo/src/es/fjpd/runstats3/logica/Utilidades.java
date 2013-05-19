package es.fjpd.runstats3.logica;

import android.content.*;
import android.telephony.*;

public class Utilidades
{

    private static final String version = "2";
	
	private static Context contexto=null;

	public static boolean isTablet()
	{
		TelephonyManager manager = 
			(TelephonyManager)contexto.getSystemService(Context.TELEPHONY_SERVICE);
		if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE)
		{
			//Tablet
			return true;
		}
		else
		{
			//Mobile
			return false; 
		}
	}
	
	public static void setAppContext(Context cont)
	{
		contexto = cont;
	}
	
	public static Context getAppContext()
	{
		return contexto;
	}
	
	public static String getVersion()
	{
		return version;
	}
}
