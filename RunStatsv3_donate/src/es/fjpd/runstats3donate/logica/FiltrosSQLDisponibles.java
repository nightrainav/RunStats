package es.fjpd.runstats3donate.logica;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;
import android.content.*;

public class FiltrosSQLDisponibles
{


	private static ArrayList<DatosFiltro> listaFiltros=null;
	
	public static ArrayList<DatosFiltro> getListaFiltros()
	{
		if (listaFiltros==null)
		{
			construyeListaFiltros();	
		}
		
		return listaFiltros;
	}
	

	private static void construyeListaFiltros()
	{
		
		int tmpInt;
		String tmpString;
		
		listaFiltros = new ArrayList<DatosFiltro>();
		DatosFiltro fltAnio = new DatosFiltro ("Año","year");
		Cursor cr = getAniosDiferentes();
		
		while (cr.moveToNext())
		{
			tmpInt = cr.getInt(0);
			tmpString = String.valueOf(tmpInt).trim();
			fltAnio.addValor(tmpInt, tmpString);
		}
		cr.close();

		listaFiltros.add(fltAnio);

		DatosFiltro fltMes = new DatosFiltro("Mes","month");
		
		cr = getMesesDiferentes();

		while (cr.moveToNext())
		{
			tmpInt = cr.getInt(0);
			tmpString = Utilidades.getNombreMes(tmpInt).trim();
			fltMes.addValor(tmpInt, tmpString);
		}
		cr.close();

		

		listaFiltros.add(fltMes);

		DatosFiltro fltTipoDeporte = new DatosFiltro("Tipo deporte","sportType");

		cr = getTiposDeporteDiferentes();

		while (cr.moveToNext())
		{
			tmpInt = cr.getInt(0);
			tmpString = Utilidades.getSportType(tmpInt).trim();
			fltTipoDeporte.addValor(tmpInt, tmpString);
		}
		cr.close();

		listaFiltros.add(fltTipoDeporte);
		
	}

	
    private static Cursor getAniosDiferentes() throws SQLException
    {

		SQLiteDatabase conn = null;
		Cursor rs = null;
		String consulta = " Select distinct year from session ";

		try
		{
		    conn = BaseDatos.getConn();

			rs = conn.rawQuery(consulta, null);
		}
		catch (SQLException e)
		{
			throw (e);
		}

		return rs;
    }

    private static Cursor getMesesDiferentes() throws SQLException
    {

		SQLiteDatabase conn = null;
		Cursor rs = null;
		String consulta = " Select distinct month from session ";

		try
		{
		    conn = BaseDatos.getConn();

			rs = conn.rawQuery(consulta, null);
		}
		catch (SQLException e)
		{
			throw (e);
		}

		return rs;
    }
	
	private static Cursor getTiposDeporteDiferentes() throws SQLException
    {

		SQLiteDatabase conn = null;
		Cursor rs = null;
		String consulta = " Select distinct sportType from session ";

		try
		{
		    conn = BaseDatos.getConn();

			rs = conn.rawQuery(consulta, null);
		}
		catch (SQLException e)
		{
			throw (e);
		}

		return rs;
    }
	
	
	
}
