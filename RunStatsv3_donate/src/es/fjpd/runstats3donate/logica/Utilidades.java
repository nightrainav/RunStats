package es.fjpd.runstats3donate.logica;

import android.content.*;
import android.telephony.*;
import es.fjpd.runstats3donate.*;

public class Utilidades
{

	/**
	/* Nos dice si estamos ejecutando en un tablet o no
	/**/
	public static boolean isTablet()
	{
		TelephonyManager manager = (TelephonyManager)RunStats.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
			
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

	/**
	/* Nos devuelve el literal correspondiente a un tipo de deporte dado su codigo.
	/* Para ello hace uso del fichero strings.xml correspondiente
	/**/
	public static String getSportType(long valor)
    {
		if (valor == 0)
		{ return " ¿? "; }
		else if (valor == 1)
		{ return RunStats.getAppContext().getString(R.string.tipod_correr); }
		else if (valor == 19)
		{ return RunStats.getAppContext().getString(R.string.tipod_andar); }
		else if (valor == 7)
		{ return RunStats.getAppContext().getString(R.string.tipod_senderismo); }
		else if (valor == 4)
		{ return RunStats.getAppContext().getString(R.string.tipod_mtb); }
		else if (valor == 3)
		{ return RunStats.getAppContext().getString(R.string.tipod_ciclismo); }
		else
		{ return valor + " ¿? "; }
    }


	/**
	/* Nos devuelve el literal correspondiente a un mes dado su numero de orden.
	/* Para ello hace uso del fichero strings.xml correspondiente
	/* Podriamos usar las funciones de fecha de java para esto pero parece que en versiones
	/* 2.2 y anteriores de Android hay un bug y no funcionan correctamente para este proposito
	/**/	
    public static String getNombreMes(long valor)
    {

		//Context contexto = RunStats.getAppContext();
		
		if (valor == 1)
		{ return RunStats.getAppContext().getString(R.string.mes_enero); }
		else if (valor == 2)
		{ return RunStats.getAppContext().getString(R.string.mes_febrero); }
		else if (valor == 3)
		{ return RunStats.getAppContext().getString(R.string.mes_marzo); }
		else if (valor == 4)
		{ return RunStats.getAppContext().getString(R.string.mes_abril); }
		else if (valor == 5)
		{ return RunStats.getAppContext().getString(R.string.mes_mayo); }
		else if (valor == 6)
		{ return RunStats.getAppContext().getString(R.string.mes_junio); }
		else if (valor == 7)
		{ return RunStats.getAppContext().getString(R.string.mes_julio); }
		else if (valor == 8)
		{ return RunStats.getAppContext().getString(R.string.mes_agosto); }
		else if (valor == 9)
		{ return RunStats.getAppContext().getString(R.string.mes_septiembre); }
		else if (valor == 10)
		{ return RunStats.getAppContext().getString(R.string.mes_octubre); }
		else if (valor == 11)
		{ return RunStats.getAppContext().getString(R.string.mes_noviembre); }
		else if (valor == 12)
		{ return RunStats.getAppContext().getString(R.string.mes_diciembre); }
		else
		{ return " mes incorrecto "; }
    }

	
}
