package es.fjpd.runstats3.logica;

import android.database.sqlite.*;
import android.os.*;
import java.io.*;
import android.util.*;

public class BaseDatos
{

	private static String urlBD=null;
	private static SQLiteDatabase conn=null;

	public static SQLiteDatabase getConn()
	{
		if (conn == null)
		{
			return abreBaseDatos();
		}
		else
		{
			return conn;
		}
	}

	private static SQLiteDatabase abreBaseDatos()
	{

		String rutaBD = getRutaBD();

		if (rutaBD.equals(""))
		{
			conn = null; 
		}
		else
		{
			conn = SQLiteDatabase.openDatabase(rutaBD, null, SQLiteDatabase.OPEN_READONLY);
		}

		return conn;				
	}

	public static String getRutaBD()	
	{
		String resultado="";

		if (urlBD != null)
		{
			resultado = urlBD;
		}
		else
		{
			resultado = Environment.getExternalStorageDirectory().getAbsolutePath();

			File file = new File(resultado + "/runtastic/data/db");
			if (file.exists())
			{
				resultado = resultado + "/runtastic/data/db";
			}
			else
			{
				file = new File(resultado + "/runtasticPRO/data/db");
				if (file.exists())
				{
					resultado = resultado + "/runtasticPRO/data/db";
				}
				else
				{
					resultado = "";
				}
			}

			urlBD = resultado;
		}

		return resultado;
	}

	public static void cerrar()
	{
		try 
		{
			conn.close();
		}
		catch (Exception e)
		{
			Log.e("ERROR", "al cerrar bd "+e.getLocalizedMessage());
		}		
	}
}
