package es.fjpd.runstats3.logica;

import android.content.*;
import android.telephony.*;
import es.fjpd.runstats3.*;

public class Utilidades
{

    
	
	public static boolean isTablet()
	{
		TelephonyManager manager = 
			(TelephonyManager)RunStats.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
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
	
	public static String getSportType(long valor)
    {
		Context contexto = RunStats.getAppContext();
		
		if (valor == 0)
		{ return " ¿? "; }
		else if (valor == 1)
		{ return contexto.getString(R.string.tipod_correr); }
		else if (valor == 19)
		{ return contexto.getString(R.string.tipod_andar); }
		else if (valor == 7)
		{ return contexto.getString(R.string.tipod_senderismo); }
		else if (valor == 4)
		{ return contexto.getString(R.string.tipod_mtb); }
		else if (valor == 3)
		{ return contexto.getString(R.string.tipod_ciclismo); }
		else
		{ return valor + " ¿? "; }
    }


    public static String getNombreMes(long valor)
    {

		Context contexto = RunStats.getAppContext();
		
		if (valor == 1)
		{ return contexto.getString(R.string.mes_enero); }
		else if (valor == 2)
		{ return contexto.getString(R.string.mes_febrero); }
		else if (valor == 3)
		{ return contexto.getString(R.string.mes_marzo); }
		else if (valor == 4)
		{ return contexto.getString(R.string.mes_abril); }
		else if (valor == 5)
		{ return contexto.getString(R.string.mes_mayo); }
		else if (valor == 6)
		{ return contexto.getString(R.string.mes_junio); }
		else if (valor == 7)
		{ return contexto.getString(R.string.mes_julio); }
		else if (valor == 8)
		{ return contexto.getString(R.string.mes_agosto); }
		else if (valor == 9)
		{ return contexto.getString(R.string.mes_septiembre); }
		else if (valor == 10)
		{ return contexto.getString(R.string.mes_octubre); }
		else if (valor == 11)
		{ return contexto.getString(R.string.mes_noviembre); }
		else if (valor == 12)
		{ return contexto.getString(R.string.mes_diciembre); }
		else
		{ return " mes incorrecto "; }
    }

	
}
