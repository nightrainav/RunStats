package es.fjpd.runstats3.logica;

import android.database.*;
import android.database.sqlite.*;
import es.fjpd.runstats3.logica.Estadistica.*;
import android.text.format.*;
import java.text.*;


/**
 * @author f009958h
 * Servlet que sirve para ejecutar consultas SQL de manera dinámica
 */
public class EstadisticaHTML
{

    Estadistica estadistica = null;
    String html = "";

    public EstadisticaHTML(Estadistica pEstadistica)
    {
		this.estadistica = pEstadistica;
    }


    /**
     * Método para generar el HTML correspondiente a la consulta
     */
    private String generaHTML()
	{

		boolean errorConsulta=false;
		String mensajeError = "";
		Cursor cursor = null;

		try
		{
			cursor = ejecutaConsulta();
		}
		catch (SQLException e)
		{
			errorConsulta = true;
			mensajeError = e.getLocalizedMessage();
		}

		StringBuilder codigo = new StringBuilder();

		codigo.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' \n");
		codigo.append("'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>\n");
		codigo.append("<html lang='es' xmlns='http://www.w3.org/1999/xhtml' xml:lang='es'><head>" + "\n");

		// Estilos de la página

		// diferencias para tablet o phone

		if (Utilidades.isTablet())
		{
			codigo.append("<style type=\"text/CSS\">\n");
			codigo.append("  body {background-color:#FFF; padding:0px;}\n ");
			codigo.append("  h1 {color: white; background-color:#AAA; font-weight: bold; font-family: Arial, Verdana; font-size: 120%; padding:3px;}\n ");
			codigo.append("  h2 {color: black; background-color:#EEE; font-weight: bold; font-family: Arial, Verdana; font-size: 90%; padding:3px;}\n ");
			codigo.append("  table {border-width:2px solid transparent;}\n ");
			codigo.append("  th {color: white; background-color:blue; font-weight: bold; font-family: Arial, Verdana; font-size: 90%; padding:3px;}\n ");
			codigo.append("  tr.par {background-color:#EEE; padding:2px; font-family: Arial, Verdana; font-size: 80%; }\n ");
			codigo.append("  tr.impar {color: black; padding:2px; font-family: Arial, Verdana; font-size: 80%; background-color:white;}\n");
			codigo.append("  td {padding:2px; }\n ");
			codigo.append("  td.numero {text-align:right;}\n ");
			codigo.append("  tbody {height:100px; overflow-y:auto; overflow-x:hidden; }\n "); 
			codigo.append(" \n ");
			codigo.append("</style>\n");	
		}
		else
		{
			codigo.append("<style type=\"text/CSS\">\n");
			codigo.append("  body {background-color:#FFF; padding:0px;}\n ");
			codigo.append("  h1 {color: white; background-color:#AAA; font-weight: bold; font-family: Arial, Verdana; font-size: 100%; padding:2px;}\n ");
			codigo.append("  h2 {color: black; background-color:#EEE; font-weight: bold; font-family: Arial, Verdana; font-size: 60%; padding:2px;}\n ");
			codigo.append("  table {border-width:2px solid transparent;}\n ");
			codigo.append("  th {color: white; background-color:blue; font-weight: bold; font-family: Arial, Verdana; font-size: 60%; padding:2px;}\n ");
			codigo.append("  tr.par {background-color:#EEE; padding:2px; font-family: Arial, Verdana; font-size: 60%; }\n ");
			codigo.append("  tr.impar {color: black; padding:2px; font-family: Arial, Verdana; font-size: 60%; background-color:white;}\n");
			codigo.append("  td {padding:2px; }\n ");
			codigo.append("  td.numero {text-align:right;}\n ");
			codigo.append("  tbody {height:100px; overflow-y:auto; overflow-x:hidden;}\n ");
			codigo.append(" \n ");
			codigo.append("</style>\n");
		}
		
		codigo.append("<title>" + estadistica.getTitulo() + "</title>\n");

		codigo.append("</head>");
		codigo.append("<body>\n");

		
		if (errorConsulta)
		{
			codigo.append("<h1> Consulta incorrecta: </h1>");
			codigo.append("<h2>" + mensajeError + "</h2>");
		}
		else
		{    
			try
			{

				// variables auxiliares para tratar cada celda de la tabla
				String tituloColumna="";
				Tipo_Columna tipoColumna;

				String datoPintar="";
				String claseCelda="";

				codigo.append("<h1>" + cambiaCaracteresEspeciales(estadistica.getTitulo()) + "</h1>");
				codigo.append("<h2>" + cambiaCaracteresEspeciales(estadistica.getDescripcion()) + "</h2>");

				
				codigo.append("<table >");
				codigo.append("<thead>\n");
				codigo.append("<tr>\n");

				// Cabecera de la tabla

				for (int index=0;index < cursor.getColumnCount();index++)
				{

					// para evitar desbordamientos si el array de títulos de columnas
					// no está bien definido con respecto a las columnas que devuelve la consulta
					if (estadistica.getTitulosCols().length > index)
					{ tituloColumna = this.cambiaCaracteresEspeciales(estadistica.getTitulosCols()[index]); }
					else
					{ tituloColumna = "[FALTA]"; }

					codigo.append("<th>" + tituloColumna + "</th>\n");

				}

				codigo.append("</tr>\n");
				codigo.append("</thead>\n");
				codigo.append("<tbody>\n");
				
				// Datos de la tabla

				String claseCol = "par";
				boolean isNumero = false;


				while (cursor.moveToNext())
				{

					if (claseCol.equals("par")) claseCol = "impar"; else claseCol = "par";

					codigo.append("<tr class='" + claseCol + "'>\n");
					// Comprobación de tipos

					for (int index=0;index < cursor.getColumnCount();index++)
					{
						if (estadistica.getTiposCols().length > index)
						{
							tipoColumna = estadistica.getTiposCols()[index];

							if (tipoColumna == Tipo_Columna.TC_Cadena)
							{
								try
								{
									datoPintar = cursor.getString(index);
									claseCelda = "";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero)
							{
								try
								{
									datoPintar = String.valueOf(cursor.getLong(index));
									claseCelda = "numero";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_sportType)
							{
								try
								{
									datoPintar = Utilidades.getSportType(cursor.getLong(index));
									claseCelda = "";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_Mes)
							{
								try
								{
									datoPintar = Utilidades.getNombreMes(cursor.getLong(index));
									claseCelda = "";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Real)
							{
								try
								{
									datoPintar = getFloatRedondeado(cursor.getDouble(index), 2);
									claseCelda = "numero";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Real_Metros)
							{
								try
								{
									datoPintar = String.valueOf(getFloatRedondeado(cursor.getDouble(index) / 1000, 2));
									claseCelda = "numero";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_Ms_Horas)
							{
								try
								{
									datoPintar = getMilisegundosFormateados(cursor.getLong(index));
									claseCelda = "numero";    
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_Ms_Hora)
							{
								try
								{
									datoPintar = getHoraDeMilisegundos(cursor.getLong(index));
									claseCelda = "numero";    
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_Ms_Dia)
							{
								try
								{
									datoPintar = getDiaDeMilisegundos(cursor.getLong(index));
									claseCelda = "numero";    
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
						}
						else
						{ datoPintar = "[TIPO NO DEFINIDO]"; }

						codigo.append("<td class='" + claseCelda + "'>" + datoPintar + "</td>");

					}

					codigo.append("</tr>\n");

				}
				codigo.append("</tbody>\n");
				codigo.append("</table>");
				
				
				cursor.close();

			}
			catch (SQLException e)
			{
				codigo.append(e.getMessage());
			}
		}

		codigo.append("</body>");
		codigo.append("</html>");

		return codigo.toString();
    }


    /**
     * Ejecuta la consulta SQL.
     * @return Devuelve el resultado de la ejecución.
     */
    private Cursor  ejecutaConsulta() throws SQLException
    {

		SQLiteDatabase conn = null;
		Cursor rs = null;

		try
		{
		    conn = BaseDatos.getConn();

			rs = conn.rawQuery(estadistica.getConsultaSQL(), null);
		}
		catch (SQLException e)
		{
			throw (e);
		}

		return rs;
    }

    private String valorNull(String valor)
    {
		String resultado="";

		if (valor == null || valor.equals(null))
		{
			resultado = "(NULL)";
		}
		else
		{
			resultado = valor;
		}

		return resultado;
    }

    // NUEVO
    private String getMilisegundosFormateados(long ms)
    {
		String resultado = "";
		String tmp ="";

		// horas
		tmp = String.valueOf((ms / (1000 * 60 * 60)));
		resultado += tmp.trim() + ":";

		// minutos
		tmp = String.valueOf((ms / (1000 * 60)) % 60);
		resultado += tmp.trim() + ":";

		//segundos
		tmp = String.valueOf((ms / 1000) % 60);
		resultado += tmp.trim();

		return resultado;
		

    }
	

    private String getHoraDeMilisegundos(long ms)
    {
		String resultado = "";

		resultado = DateUtils.formatDateTime(RunStats.getAppContext(),ms,DateUtils.FORMAT_SHOW_TIME);
		
		return resultado;

    }
	

    private String getDiaDeMilisegundos(long ms)
    {
		String resultado = "";

		resultado = DateUtils.formatDateTime(RunStats.getAppContext(),ms,DateUtils.FORMAT_SHOW_DATE).substring(0,2).trim();
		
		return resultado;

    }

    // NUEVO
    private String getFloatRedondeado(double num, int numDecimales)
    {

		return String.valueOf( Math.round(num * Math.pow(10, numDecimales)) / Math.pow(10, numDecimales) ).replace ('.',',') ;
		
    }

	private String cambiaCaracteresEspeciales(String c)
	{
		String resultado = c;

		resultado = resultado.replaceAll("á", "&aacute;");
		resultado = resultado.replaceAll("é", "&eacute;");
		resultado = resultado.replaceAll("í", "&iacute;");
		resultado = resultado.replaceAll("ó", "&oacute;");
		resultado = resultado.replaceAll("ú", "&uacute;");
		resultado = resultado.replaceAll("ñ", "&ntilde;");
		resultado = resultado.replaceAll("Ñ", "&Ntilde;");

		return resultado;
	}

    public String getHtml()
	{

		if (html.equals(""))
		{
			html = generaHTML();
		}

		return html;
    }

}
