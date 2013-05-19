package es.fjpd.runstats3.vista;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.text.format.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import es.fjpd.runstats3.*;
import es.fjpd.runstats3.logica.*;
import es.fjpd.runstats3.logica.Estadistica.*;
import java.util.*;


public class EstadisticaViewLayout extends LinearLayout
{


	Estadistica estadistica = null;


	/*public EstadisticaViewGenerator(Estadistica pEstadistica)
	{
		this.estadistica = pEstadistica;
		generaView();
	}*/

	public EstadisticaViewLayout(Context context)
    {
        super(context);
    }

    public EstadisticaViewLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

	/*public void setEstadistica(Estadistica estadistica)
	{
		this.estadistica = estadistica;
	}

	public Estadistica getEstadistica()
	{
		return estadistica;
	}*/

	/**
	 * Método para generar el HTML correspondiente a la consulta
	 */
	private void generaView()
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



		if (errorConsulta)
		{
			/*tituloEstadistica.setText("Consulta incorrecta:");
			 descEstadistica.setText(mensajeError);*/
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

				Context cntx = Utilidades.getAppContext();

				TableLayout tablaCabecera = new TableLayout(cntx);
				tablaCabecera.setId(R.id.HeaderTable);

                tablaCabecera.setLayoutParams(new LinearLayout.LayoutParams(
										 LinearLayout.LayoutParams.MATCH_PARENT,
										 LinearLayout.LayoutParams.WRAP_CONTENT, 0));
										 
				TableRow cabecera = new TableRow(cntx);
				cabecera.setLayoutParams(new LinearLayout.LayoutParams(
												  LinearLayout.LayoutParams.MATCH_PARENT,
												  LinearLayout.LayoutParams.WRAP_CONTENT, 0));
				// Cabecera de la tabla

				for (int index=0;index < cursor.getColumnCount();index++)
				{

					// para evitar desbordamientos si el array de títulos de columnas
					// no está bien definido con respecto a las columnas que devuelve la consulta
					if (estadistica.getTitulosCols().length > index)
					{ tituloColumna = estadistica.getTitulosCols()[index]; }
					else
					{ tituloColumna = "[FALTA]"; }

					TextView titulo = new TextView(cntx);
					titulo.setText(tituloColumna);
					/*titulo.setLayoutParams(new LinearLayout.LayoutParams(
											   LinearLayout.LayoutParams.WRAP_CONTENT,
											   LinearLayout.LayoutParams.MATCH_PARENT, 1));*/

					titulo.setWidth(estadistica.getAnchoCols()[index]);
					cabecera.addView(titulo);

				}
				tablaCabecera.addView(cabecera);


				// Datos de la tabla

				// Creamos un scrollView y dentro una nueva tabla

				ScrollView scroll = new ScrollView(cntx);

                scroll.setLayoutParams(new LinearLayout.LayoutParams(
												  LinearLayout.LayoutParams.MATCH_PARENT,
												  LinearLayout.LayoutParams.WRAP_CONTENT, 1));
				
				TableLayout tablaDatos = new TableLayout(cntx);

                tablaDatos.setLayoutParams(new LinearLayout.LayoutParams(
												  LinearLayout.LayoutParams.WRAP_CONTENT,
												  LinearLayout.LayoutParams.WRAP_CONTENT, 1));
												  
				tablaDatos.setId(R.id.BodyTable);

				String claseCol = "par";
				boolean isNumero = false;


				while (cursor.moveToNext())
				{

					TableRow fila = new TableRow(cntx);
					fila.setLayoutParams(new LinearLayout.LayoutParams(
												 LinearLayout.LayoutParams.MATCH_PARENT,
												 LinearLayout.LayoutParams.WRAP_CONTENT, 0));

					if (claseCol.equals("par")) claseCol = "impar"; else claseCol = "par";

					//codigo.append("<tr class='" + claseCol + "'>\n");
					// Comprobación de tipos

					for (int index=0;index < cursor.getColumnCount();index++)
					{

						TextView celda = new TextView(cntx);
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
									datoPintar = getSportType(cursor.getLong(index));
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
									datoPintar = getNombreMes(cursor.getLong(index));
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

						//codigo.append("<td class='" + claseCelda + "'>" + datoPintar + "</td>");
						celda.setText(datoPintar);
						celda.setWidth(estadistica.getAnchoCols()[index]);
						/*celda.setLayoutParams(new LinearLayout.LayoutParams(
												   LinearLayout.LayoutParams.WRAP_CONTENT,
												   LinearLayout.LayoutParams.WRAP_CONTENT, 1));*/
						
						fila.addView(celda);

					}

					tablaDatos.addView(fila);
				}

				scroll.addView(tablaDatos);

				cursor.close();

				this.addView(tablaCabecera);
				this.addView(scroll);		
				
				this.setOrientation(LinearLayout.VERTICAL);
                this.setLayoutParams(new LinearLayout.LayoutParams(
												   LinearLayout.LayoutParams.MATCH_PARENT,
												   LinearLayout.LayoutParams.MATCH_PARENT, 1));

				/*rootView.addView(tablaCabecera);
				 rootView.addView(scroll);*/

			}
			catch (SQLException e)
			{
				//codigo.append(e.getMessage());
			}
		}

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

	// MODIFICADO
	private String getSportType(long valor)
	{
		if (valor == 0)
		{ return "Desconocido"; }
		else if (valor == 1)
		{ return "Correr"; }
		else if (valor == 19)
		{ return "Andar"; }
		else if (valor == 7)
		{ return "Senderismo"; }
		else if (valor == 4)
		{ return "Ciclismo de montaña"; }
		else
		{ return valor + " ¿? "; }
	}


	// NUEVO
	private String getNombreMes(long valor)
	{

		if (valor == 1)
		{ return "Enero"; }
		else if (valor == 2)
		{ return "Febrero"; }
		else if (valor == 3)
		{ return "Marzo"; }
		else if (valor == 4)
		{ return "Abril"; }
		else if (valor == 5)
		{ return "Mayo"; }
		else if (valor == 6)
		{ return "Junio"; }
		else if (valor == 7)
		{ return "Julio"; }
		else if (valor == 8)
		{ return "Agosto"; }
		else if (valor == 9)
		{ return "Septiembre"; }
		else if (valor == 10)
		{ return "Octubre"; }
		else if (valor == 11)
		{ return "Noviembre"; }
		else if (valor == 12)
		{ return "Diciembre"; }
		else
		{ return " mes incorrecto "; }
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

		resultado = DateUtils.formatDateTime(Utilidades.getAppContext(), ms, DateUtils.FORMAT_SHOW_TIME);

		return resultado;

	}


	private String getDiaDeMilisegundos(long ms)
	{
		String resultado = "";

		resultado = DateUtils.formatDateTime(Utilidades.getAppContext(), ms, DateUtils.FORMAT_SHOW_DATE).substring(0, 2).trim();

		return resultado;

	}

	// NUEVO
	private String getFloatRedondeado(double num, int numDecimales)
	{

		return String.valueOf(Math.round(num * Math.pow(10, numDecimales)) / Math.pow(10, numDecimales)).replace('.', ',') ;

	}

	/*private String cambiaCaracteresEspeciales(String c)
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
	}*/

	public LinearLayout getView(Estadistica pEstadistica)
	{
		this.estadistica = pEstadistica;
		generaView();
		return this;
	}


/*	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		List<Integer> colWidths = new LinkedList<Integer>();

		TableLayout header = (TableLayout) findViewById(R.id.HeaderTable);
		TableLayout body = (TableLayout) findViewById(R.id.BodyTable);

		for (int rownum = 0; rownum < body.getChildCount(); rownum++)
		{
			TableRow row = (TableRow) body.getChildAt(rownum);
			for (int cellnum = 0; cellnum < row.getChildCount(); cellnum++)
			{
				View cell = row.getChildAt(cellnum);
				Integer cellWidth = cell.getWidth();
				if (colWidths.size() <= cellnum)
				{
					colWidths.add(cellWidth);
				}
				else
				{
					Integer current = colWidths.get(cellnum);
					if (cellWidth > current)
					{
						colWidths.remove(cellnum);
						colWidths.add(cellnum, cellWidth);
					}
				}
			}
		}

		for (int colnum = 0; colnum < header.getChildCount(); colnum++)
		{
			TableRow row = (TableRow) header.getChildAt(colnum);
			for (int cellnum = 0; cellnum < row.getChildCount(); cellnum++)
			{
				View cell = row.getChildAt(cellnum);
				TableRow.LayoutParams params = (TableRow.LayoutParams)cell.getLayoutParams();
				
				// si la anchura maxima de la columna es mayor que la columna cabecera, ampliamos esta
				if (params.width<colWidths.get(cellnum))
				{
					params.width = colWidths.get(cellnum);
				}
			}
		}
	}*/

}





