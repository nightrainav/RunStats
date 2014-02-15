package es.fjpd.runstats3donate.logica;

import android.database.*;
import android.database.sqlite.*;
import es.fjpd.runstats3donate.logica.Estadistica.*;
import android.text.format.*;
import java.text.*;
import android.content.*;
import android.preference.*;
import es.fjpd.runstats3donate.R;


/**
 * @author f009958h
 * Servlet que sirve para ejecutar consultas SQL de manera dinámica
 */
public class EstadisticaHTML
{

    Estadistica estadistica = null;
    String html = "";
	//Context context;

    public EstadisticaHTML(Estadistica pEstadistica)
    {
		this.estadistica = pEstadistica;
    }


    /**
     * Método para generar el HTML de tipo tabla general correspondiente a la consulta
     */
    private String generaHTMLTipoTablaGeneral()
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
			codigo.append("  th {color: white; background-color:#4F81BD; font-weight: bold; font-family: Arial, Verdana; font-size: 90%; padding:3px;}\n ");
			codigo.append("  tr.par {background-color:#EEE; padding:2px; font-family: Arial, Verdana; font-size: 80%; }\n ");
			codigo.append("  tr.impar {color: black; padding:2px; font-family: Arial, Verdana; font-size: 80%; background-color:white;}\n");
			codigo.append("  td {padding:2px; }\n ");
			codigo.append("  td.numero {text-align:right;}\n ");
			//codigo.append("  tbody {height:100px; overflow:auto; display:block; width:100%}\n "); 
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
			codigo.append("  th {color: white; background-color:#4F81BD; font-weight: bold; font-family: Arial, Verdana; font-size: 60%; padding:2px;}\n ");
			codigo.append("  tr.par {background-color:#EEE; padding:2px; font-family: Arial, Verdana; font-size: 60%; }\n ");
			codigo.append("  tr.impar {color: black; padding:2px; font-family: Arial, Verdana; font-size: 60%; background-color:white;}\n");
			codigo.append("  td {padding:2px; }\n ");
			codigo.append("  td.numero {text-align:right;}\n ");
			//codigo.append("  tbody {height:100px; overflow:auto; display:block; width:100%}\n ");
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

				// preferencia unidades km/mi
				boolean isPrefKm = true;

				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(RunStats.getAppContext());
				isPrefKm = pref.getString("unidades", "km").equals("km");

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

					// si preferencia unidades = millas -> reemplazamos en los titulos Km por mi
					if (!isPrefKm)
					{
						tituloColumna = tituloColumna.replaceFirst("Km", "Mi");
					}

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
									//datoPintar = String.valueOf(cursor.getLong(index));
									datoPintar = getEnteroFormateado(cursor.getLong(index));
									claseCelda = "numero";
								}
								catch (SQLException e)
								{
									datoPintar = "TIPO INCORRECTO";
									claseCelda = "";
								}
							}
							else if (tipoColumna == Tipo_Columna.TC_Entero_Anio)
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
									//datoPintar = getNombreMesJava((int)cursor.getLong(index));
									// por un bug en android 2.2 podria no funcionar lo anterior. 
									// en tal caso usamos metodo alternativo basado en strings.xml
									//if (datoPintar.equals(""))
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
									//datoPintar = String.valueOf(getFloatRedondeado(cursor.getDouble(index) / 1000, 2));
									if (isPrefKm) // km
										datoPintar = getFloatFormateado(cursor.getDouble(index) / 1000, 2);
									else // millas
										datoPintar = getFloatFormateado(cursor.getDouble(index) / 1610, 2);

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
							else if (tipoColumna == Tipo_Columna.TC_Entero_Ms_HorasPorDistancia)
							{
								try
								{
									if (isPrefKm)
										datoPintar = getMilisegundosFormateados(cursor.getLong(index));
									else
										datoPintar = getMilisegundosFormateados((long)(1.61*cursor.getLong(index)));
										
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
     * Método para generar el HTML de tipo Fichas correspondiente a la consulta
     */
    private String generaHTMLTipoFichas()
	{

		boolean errorConsulta=false;
		String mensajeError = "";
		Cursor cursor = null;
		//Context cntx=RunStats.getAppContext();

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
		}
		else
		{
		}

		codigo.append("<style type='text/CSS'>\n");
		codigo.append("  body {background-color:#FFF; padding:0px;}\n ");
		codigo.append("  h1 {color: white; background-color:#AAA; font-weight: bold; font-family: Arial, Verdana; font-size: 120%; padding:3px;}\n ");
		codigo.append("  h2 {color: black; background-color:#EEE; font-weight: bold; font-family: Arial, Verdana; font-size: 90%; padding:3px;}\n ");
		codigo.append(".c632\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:3px; color:black; \n");
		codigo.append("	font-size:11pt; font-weight:400; font-style:normal; font-family:Calibri, sans-serif; text-align:left; \n");
		codigo.append("	vertical-align:top; border-bottom:1pt solid #DBE5F1; background:#C5D9F1; }\n");
		codigo.append(".c642\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black;\n");
		codigo.append("	font-size:11pt; font-weight:400; font-style:normal; font-family:Calibri, sans-serif; text-align:general;\n");
		codigo.append("	vertical-align:bottom; background:white; white-space:nowrap;}\n");
		codigo.append(".c652\n");
		codigo.append("	{padding-top:1px; padding-right:3px; padding-left:3px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:right; vertical-align:top;\n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c662\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black;	font-size:11pt; font-weight:400; \n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:top; \n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c672\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:3px; color:black; font-size:10pt;\n");
		codigo.append("	font-weight:400; font-style:normal; font-family:Calibri, sans-serif; text-align:left; vertical-align:middle;\n");
		codigo.append("	border-right:1pt solid white; border-bottom:1pt solid white; background:#F2F2F2; }\n");
		codigo.append(".c972\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:3px; color:black; font-size:10pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:middle;\n");
		codigo.append("	border:1pt solid #66E; background:#FFF; }\n");
		codigo.append(".c682\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:10pt;\n");
		codigo.append("	font-weight:400; font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:middle;\n");
		codigo.append("	border-top:2pt solid white; border-right:2pt solid white; background:#F2F2F2; }\n");
		codigo.append(".c692\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:10pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:middle;\n");
		codigo.append("	border-bottom:2pt solid white; background:#F2F2F2; }\n");
		codigo.append(".c702\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:10pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:middle;\n");
		codigo.append("	border-right:2pt solid white; border-bottom:2pt solid white; background:#F2F2F2; }\n");
		codigo.append(".c712\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:12pt; font-weight:400;\n");
		codigo.append("	font-style:normal; text-align:center; vertical-align:middle; border-right:1pt solid #DBE5F1; \n");
		codigo.append("	border-bottom:1pt solid white; background:#F2F2F2;  background-image:url('file:///android_asset/ic_action_edit.png'); \n");
		codigo.append("	background-repeat:no-repeat; background-position: center center;}\n");
		codigo.append(".c912\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:12pt; font-weight:400;\n");
		codigo.append("	font-style:normal; text-align:center; vertical-align:middle; border-top:2pt solid white; \n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-left:2pt solid white; background:#F2F2F2;  \n");
		codigo.append("	background-image:url('file:///android_asset/ic_action_save.png');	background-repeat:no-repeat; background-position: center center;}\n");
		codigo.append(".c722\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:12pt; font-weight:400;\n");
		codigo.append("	font-style:normal; text-align:center; vertical-align:middle; border-right:1pt solid #DBE5F1; \n");
		codigo.append("	border-left:2pt solid white; background:#F2F2F2; }\n");
		codigo.append(".c732\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:3px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:left; vertical-align:top;\n");
		codigo.append("	border-bottom:1pt solid white; background:#C5D9F1;	}\n");
		codigo.append(".c742\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:3px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:left; vertical-align:top; border-top:1pt solid white;\n");
		codigo.append("	border-bottom:1pt solid white; background:#C5D9F1; }\n");
		codigo.append(".c752\n");
		codigo.append("	{padding-top:1px; padding-right:3px; padding-left:3px; color:black; font-size:11pt;	font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:right; vertical-align:top; border-top:0pt solid white;\n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c762\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:top; \n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c772\n");
		codigo.append("	{padding-top:1px; padding-right:3px; padding-left:3px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:right; vertical-align:top; border-top:1pt solid #DBE5F1;\n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c782\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:black; font-size:11pt; font-weight:400;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:general; vertical-align:top; border-top:1pt solid #DBE5F1;\n");
		codigo.append("	border-right:1pt solid #DBE5F1; border-bottom:1pt solid #DBE5F1; background:white; }\n");
		codigo.append(".c792\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:11pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top; \n");
		codigo.append("	background:#4F81BD;  border-top:1pt solid #4F81BD;}\n");
		codigo.append(".c802\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:10pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top; background:#4F81BD;\n");
		codigo.append("	 border-right:1pt solid #4F81BD; border-top:1pt solid #4F81BD;}\n");
		codigo.append(".c812\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:10pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top;\n");
		codigo.append("	border-right:1pt solid #C5D9F1; background:#4F81BD; }\n");
		codigo.append(".c822\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:12pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top;\n");
		codigo.append("	border-bottom:1pt solid white; background:#4F81BD; }\n");
		codigo.append(".c832\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:12pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top;\n");
		codigo.append("	border-right:1pt solid #4F81BD; border-bottom:1pt solid white; background:#4F81BD; }\n");
		codigo.append(".c842\n");
		codigo.append("	{padding-top:1px; padding-right:1px; padding-left:1px; color:white; font-size:12pt; font-weight:700;\n");
		codigo.append("	font-style:normal; font-family:Calibri, sans-serif; text-align:center; vertical-align:top;\n");
		codigo.append("	border-right:1pt solid #C5D9F1; border-bottom:2pt solid white; background:#4F81BD; }\n");
		codigo.append("</style>\n");


		codigo.append("<title>" + estadistica.getTitulo() + "</title>\n");

		codigo.append("</head>");
		codigo.append("<body>\n");

		codigo.append("<script>\n");
		codigo.append("function trataEdicion(id)\n");
		codigo.append("{\n");
		//codigo.append("		alert ('por aqui antes');\n");
		codigo.append("  var texto='';\n");
		codigo.append("  if (document.getElementById('boton'+id).className=='c712')\n");
		codigo.append("  {\n");
		codigo.append("		document.getElementById('boton'+id).className='c912';\n");
		codigo.append("		texto = document.getElementById('desc'+id).innerHTML;\n");
		codigo.append("		document.getElementById('desc'+id).innerHTML=\"<input id='texto\"+id+\"' class='c972' type ='text' size='44' maxlength=100 value='\"+texto+\"'/>\";\n");
		codigo.append("		document.getElementById('texto'+id).focus();\n");
		codigo.append("  }\n");
		codigo.append("  else if (document.getElementById('boton'+id).className=='c912')\n");
		codigo.append("  {\n");
		codigo.append("		document.getElementById('boton'+id).className='c712';\n");
		codigo.append("		texto = document.getElementById('texto'+id).value;\n");
		codigo.append("		document.getElementById('desc'+id).innerHTML=texto;\n");
		//codigo.append("		alert ('por aqui antes');\n");
		codigo.append("		try {window.Updater.pruebaInterface(texto);} catch (e) {alert(e.getMessage());}\n");
		//codigo.append("		alert ('por aqui');\n");
		codigo.append("  }\n");
		codigo.append("  else\n");
		codigo.append("  {\n");
		codigo.append("    alert('ERROR');\n");
		codigo.append("  }\n");
		codigo.append("}\n");
		codigo.append("</script>\n");

		codigo.append("<div align=center >\n");

		codigo.append("<table border=0 cellpadding=0 cellspacing=0 width=534 style='border-collapse:collapse;table-layout:fixed;width:401pt'>\n");
		codigo.append(" <col width=80 style='width:60pt'>\n");
		codigo.append(" <col width=210 style='width:158pt'>\n");
		codigo.append(" <col width=80 style='width:60pt'>\n");
		codigo.append(" <col width=43 style='width:32pt'>\n");
		codigo.append(" <col width=41 style='width:31pt'>\n");
		codigo.append(" <col width=80 style='width:60pt'>\n");
		codigo.append(" <tr height=20 style='height:15pt'>\n");
		codigo.append("  <td height=20 class=c642 width=80 >&nbsp;</td>\n");
		codigo.append("  <td class=c642 width=210 style='width:158pt'>&nbsp;</td>\n");
		codigo.append("  <td class=c642 width=80 style='width:60pt'>&nbsp;</td>\n");
		codigo.append("  <td class=c642 width=43 style='width:32pt'>&nbsp;</td>\n");
		codigo.append("  <td class=c642 width=41 style='width:31pt'>&nbsp;</td>\n");
		codigo.append("  <td class=c642 width=80 style='width:60pt'></td>\n");



		if (errorConsulta)
		{
			codigo.append("<h1> Consulta incorrecta: </h1>");
			codigo.append("<h2>" + mensajeError + "</h2>");
		}
		else
		{    
			try
			{

				String datoPintar="";

				// preferencia unidades km/mi
				boolean isPrefKm = true;

				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(RunStats.getAppContext());
				isPrefKm = pref.getString("unidades", "km").equals("km");

				codigo.append("<h1>" + cambiaCaracteresEspeciales(estadistica.getTitulo()) + "</h1>");
				codigo.append("<h2>" + cambiaCaracteresEspeciales(estadistica.getDescripcion()) + "</h2>");

				// Datos de la tabla


				while (cursor.moveToNext())
				{
					codigo.append(" <tr height=21 style='height:16pt'>\n");
					codigo.append("  <td height=21 class=c642>&nbsp;</td>\n");
					
					// Tipo de deporte
					datoPintar = Utilidades.getSportType(cursor.getLong(5));
					
					codigo.append("  <td class=c792 width=210>"+datoPintar+"</td>\n");
					
					// Fecha, hora inicio, hora fin
					datoPintar = getFechaDeMilisegundos(cursor.getLong(2));
					datoPintar+=" "+getHoraDeMilisegundos(cursor.getLong(2));
					datoPintar+=" - "+getHoraDeMilisegundos(cursor.getLong(4));
					
					codigo.append("  <td colspan=3 class=c802 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=26 style='height:20pt'>\n");
					codigo.append("  <td height=26 class=c642>&nbsp;</td>\n");
					
					// Duracion
					datoPintar = getMilisegundosFormateados(cursor.getLong(7));
					
					codigo.append("  <td class=c822 width=210>"+datoPintar+"</td>\n");
					
					// Distancia
					if (isPrefKm) // km
						datoPintar = getFloatFormateado(cursor.getDouble(6) / 1000, 2)+ " Km";
					else // millas
						datoPintar = getFloatFormateado(cursor.getDouble(6) / 1610, 2)+ " Mi";
					
					
					codigo.append("  <td colspan=3 class=c832 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=21 style='height:16pt'>\n");
					codigo.append("  <td height=21 class=c642>&nbsp;</td>\n");
					
					//Descripcion
					datoPintar = ""+cursor.getString(13)+"";
					if (datoPintar.equals("")) datoPintar="["+RunStats.getAppContext().getString(R.string.no_description)+"]";
					
					codigo.append("  <td colspan=3 rowspan=2 class=c672 width=333 ><span id='desc"+cursor.getInt(14)+"'>"+datoPintar+"</span></td>\n");
					codigo.append("  <td id='boton"+cursor.getInt(14)+"' rowspan=2 class=c712 width=41  \n");
					codigo.append("  onclick=\"javascript:trataEdicion('"+cursor.getInt(14)+"');\"></td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=21 style='height:16pt'>\n");
					codigo.append("  <td height=21 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=21 style='height:16pt'>\n");
					codigo.append("  <td height=21 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c732 width=210>"+RunStats.getAppContext().getString(R.string.col_ritmo_media)+"</td>\n");
					
					// Ritmo promedio
					if (isPrefKm)
						datoPintar = getMilisegundosFormateados(cursor.getLong(9))+" min/Km";
					else
						datoPintar = getMilisegundosFormateados((long)(1.61*cursor.getLong(9)))+" min/Mi";
					
					codigo.append("  <td colspan=3 class=c752 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=20 style='height:15pt'>\n");
					codigo.append("  <td height=20 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c742 width=210 >"+RunStats.getAppContext().getString(R.string.col_velocidad_media)+"</td>\n");
					
					// Velocidad media
					if (isPrefKm) // km
						datoPintar = getFloatFormateado(cursor.getDouble(8) / 1000, 2)+ " Km/h";
					else // millas
						datoPintar = getFloatFormateado(cursor.getDouble(8) / 1610, 2)+ " Mi/h";
						
					codigo.append("  <td colspan=3 class=c772 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=20 style='height:15pt'>\n");
					codigo.append("  <td height=20 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c742 width=210 >"+RunStats.getAppContext().getString(R.string.col_elevacion_ganada)+"</td>\n");
					
					//Elevacion ganada
					if (isPrefKm) // metros
						datoPintar = getEnteroFormateado(cursor.getInt(11))+" m";
					else // pies
						datoPintar = getFloatRedondeado(cursor.getInt(11)*3.2808, 0)+" ft";
						
					codigo.append("  <td colspan=3 class=c772 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=20 style='height:15pt'>\n");
					codigo.append("  <td height=20 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c742 width=210 >"+RunStats.getAppContext().getString(R.string.col_elevacion_perdida)+"</td>\n");
					
					//Elevacion perdida
					if (isPrefKm) // metros
						datoPintar = getEnteroFormateado(cursor.getInt(12))+" m";
					else // pies
						datoPintar = getFloatRedondeado(cursor.getInt(12)*3.2808, 0)+" ft";
					
					
					codigo.append("  <td colspan=3 class=c772 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=20 style='height:15pt'>\n");
					codigo.append("  <td height=20 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c742 width=210 >"+RunStats.getAppContext().getString(R.string.col_calorias)+"</td>\n");
					
					// Calorias
					datoPintar = getEnteroFormateado(cursor.getLong(10))+" kcal";
					
					codigo.append("  <td colspan=3 class=c772 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=21 style='height:16pt'>\n");
					codigo.append("  <td height=21 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c632>"+RunStats.getAppContext().getString(R.string.col_pausa)+"</td>\n");
					
					// Pausa
					datoPintar = getMilisegundosFormateados(cursor.getLong(15))+" ";
					
					codigo.append("  <td colspan=3 class=c652 width=164>"+datoPintar+"</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");
					codigo.append(" <tr height=20 style='height:15pt'>\n");
					codigo.append("  <td height=20 class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append("  <td class=c642>&nbsp;</td>\n");
					codigo.append(" </tr>\n");


				}

				cursor.close();

			}
			catch (SQLException e)
			{
				codigo.append(e.getMessage());
			}
		}


		codigo.append("</table>\n");
		codigo.append("</div>\n");
		codigo.append("</body>\n");
		codigo.append("</html>\n");

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
		tmp = String.valueOf((ms / (1000 * 60 * 60))).trim();
		resultado += (tmp.equals("0") ?"": tmp + ":");

		// minutos
		tmp = String.valueOf((ms / (1000 * 60)) % 60).trim();
		resultado += (tmp.length() == 1 ?"0" + tmp: tmp) + ":";

		//segundos
		tmp = String.valueOf((ms / 1000) % 60).trim();
		resultado += (tmp.length() == 1 ?"0" + tmp: tmp) ;

		return resultado;


    }


    private String getHoraDeMilisegundos(long ms)
    {
		String resultado = "";

		resultado = DateUtils.formatDateTime(RunStats.getAppContext(), ms, DateUtils.FORMAT_SHOW_TIME);

		return resultado;

    }

	private String getFechaDeMilisegundos(long ms)
    {
		String resultado = "";

		resultado = DateUtils.formatDateTime(RunStats.getAppContext(), ms, DateUtils.FORMAT_NUMERIC_DATE);

		return resultado;

    }

    private String getDiaDeMilisegundos(long ms)
    {
		String resultado = "";

		resultado = DateUtils.formatDateTime(RunStats.getAppContext(), ms, DateUtils.FORMAT_SHOW_DATE).substring(0, 2).trim();

		return resultado;

    }

	private String getFloatFormateado(double num, int numDecimales) 
	{ 
		double tmp = Math.round(num * Math.pow(10, numDecimales)) / Math.pow(10, numDecimales); 
		String cadenaDecimales=""; 
		if (numDecimales > 0) 
		{ 
			cadenaDecimales = "."; 
			for (int c=0;c < numDecimales;c++) 
			{ 
				cadenaDecimales += "0"; 
			} 
		} 

		DecimalFormat formato = new DecimalFormat("###,###,###,###" + cadenaDecimales); 
		return formato.format(num); 

	}

	private String getNombreMesJava(int mes)
	{
		return new DateFormatSymbols().getMonths()[mes-1];
	}
	
	private String getEnteroFormateado(long num)
	{
		return getFloatFormateado(num, 0);
	}

    // NUEVO
    private String getFloatRedondeado(double num, int numDecimales)
    {

		return String.valueOf(Math.round(num * Math.pow(10, numDecimales)) / Math.pow(10, numDecimales)).replace('.', ',') ;

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
			if (estadistica.getTipo() == Tipo_Estadistica.TE_Tabla_General)
			{ 
				html = generaHTMLTipoTablaGeneral();
			}
			else if (estadistica.getTipo() == Tipo_Estadistica.TE_Fichas)
			{ 
				html = generaHTMLTipoFichas();
			}
		}

		return html;
    }

}
