package es.fjpd.runstats3.logica;

import android.content.*;
import es.fjpd.runstats3.*;
import es.fjpd.runstats3.logica.Estadistica.*;
import java.util.*;


public class RelacionEstadisticas
{

    private static ArrayList<Estadistica> relacion = null;

    private static String[] titulos = null;
	
	private static Context cntx=null;

    public static ArrayList<Estadistica> getRelacion()
	{

		cntx = RunStats.getAppContext();
		
		if (relacion == null)
		{
			Estadistica una = null;

			relacion = new ArrayList<Estadistica>();

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Datos globales
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_total));
			una.setDescripcion(cntx.getString(R.string.desc_est_total));
			una.setConsultaSQL("select count(_ID) as c0, sum(distance) as c1, sum (runtime) as c2, sum (calories) as c3 from session where 1=1 ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_distancia), 
							  			     cntx.getString(R.string.col_tiempo), cntx.getString(R.string.col_calorias)});
			una.setColumnasFiltro(null);
			una.estableceConsultaOriginal();
			una.setIsPermiteOrientacionVertical(true);
			
			relacion.add(una);  
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Por tipo de deporte
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_portipodeporte) );
			una.setDescripcion(cntx.getString(R.string.desc_est_portipodeporte));
			una.setConsultaSQL(" select sportType as c0, count(_ID) as c1, sum(distance) as c2, avg(distance) as c3," +
							   "sum(runtime) as c4, avg(runtime) as c5, sum(distance)/(sum(runtime)/1000.0/60.0/60.0) as c6, (sum(runtime))/(sum(distance)/1000.0) as c7,"+
							   "sum(calories) as c8, avg (calories) as c9 from session where 1=1 group by sportType "); // order by c0");
			una.setCamposOrden(" c0 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
							   
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_tipodeporte), cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_kmtotal),
								   cntx.getString(R.string.col_kmmedia), cntx.getString(R.string.col_tiempototal), cntx.getString(R.string.col_tiempomedio), 
								   cntx.getString(R.string.col_kmhora), cntx.getString(R.string.col_minutoskm),
								   			 cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia)});
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_tipodeporte)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Por años
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_poranios));
			una.setDescripcion(cntx.getString(R.string.desc_est_poranios));
			una.setConsultaSQL("select year as c0, count(_ID) as c1, sum(distance) as c2, avg(distance) as c3, " +
							   "sum(runtime) as c4, avg(runtime) as c5, sum(distance)/(sum(runtime)/1000.0/60.0/60.0) as c6, "+
							   "(sum(runtime))/(sum(distance)/1000.0) as c7, sum(calories) as c8, avg (calories) as c9 from session where 1=1 group by year "); //order by c0 desc");
			una.setCamposOrden(" c0 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
			
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_kmtotal),
								   cntx.getString(R.string.col_kmmedia), cntx.getString(R.string.col_tiempototal), cntx.getString(R.string.col_tiempomedio), 
								   cntx.getString(R.string.col_kmhora), cntx.getString(R.string.col_minutoskm), 
								   cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia)});
			
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Por años y tipo de deporte
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_poraniostipodeporte));
			una.setDescripcion(cntx.getString(R.string.desc_est_poraniostipodeporte));
			una.setConsultaSQL("select year as c0, sportType as c1, count(_ID) as c2, sum(distance) as c3, avg(distance) as c4," +
							   "sum(runtime) as c5, avg(runtime) as c6, sum(distance)/(sum(runtime)/1000.0/60.0/60.0) as c7, (sum(runtime))/(sum(distance)/1000.0) as c8,"+
							   "sum(calories) as c9, avg (calories) as cc10 from session where 1=1 group by year, sportType "); //order by c0 desc");
			una.setCamposOrden(" c0 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
			
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });


			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_tipodeporte), cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_kmtotal),
								   cntx.getString(R.string.col_kmmedia), cntx.getString(R.string.col_tiempototal), cntx.getString(R.string.col_tiempomedio), 
								   cntx.getString(R.string.col_kmhora), cntx.getString(R.string.col_minutoskm), 
								   cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia)});
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_tipodeporte)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Por meses
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_pormeses));
			una.setDescripcion(cntx.getString(R.string.desc_est_pormeses));
			una.setConsultaSQL("select year as c0, month as c1, count(_ID) as c2, sum(distance) as c3, avg(distance) as c4," +
							   "sum(runtime) as c5, avg(runtime) as c7, sum(distance)/(sum(runtime)/1000.0/60.0/60.0) as c8, (sum(runtime))/(sum(distance)/1000.0) as c9,"+
							   "sum(calories) as cc10, avg (calories) as cc11 from session where 1=1 group by year, month "); //order by c0 desc, c1 desc ");
			una.setCamposOrden(" c0 desc, c1 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_kmtotal),
								   cntx.getString(R.string.col_kmmedia), cntx.getString(R.string.col_tiempototal), cntx.getString(R.string.col_tiempomedio), 
								   cntx.getString(R.string.col_kmhora), cntx.getString(R.string.col_minutoskm),
								   cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia)});
			
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Por meses y tipo de deporte
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_pormesestipodeporte));
			una.setDescripcion(cntx.getString(R.string.desc_est_pormesestipodeporte));
			una.setConsultaSQL("select year as c0, month as c1, sportType as c2, count(_ID) as c3, sum(distance) as c4, avg(distance) as c5," +
							   "sum(runtime) as c6, avg(runtime) as c7, sum(distance)/(sum(runtime)/1000.0/60.0/60.0) as c8, (sum(runtime))/(sum(distance)/1000.0) as c9,"+
							   "sum(calories) as cc10, avg (calories) as cc11 from session where 1=1 group by year, month, sportType ");// order by c0, c1 desc ");
			una.setCamposOrden(" c0 desc, c1 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
			
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_tipodeporte), cntx.getString(R.string.col_sesiones), cntx.getString(R.string.col_kmtotal),
								   cntx.getString(R.string.col_kmmedia), cntx.getString(R.string.col_tiempototal), cntx.getString(R.string.col_tiempomedio), 
								   cntx.getString(R.string.col_kmhora), cntx.getString(R.string.col_minutoskm), 
								   cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia)});
			
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_tipodeporte)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Calorias por tipo de deporte
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_caloriastipodeporte));
			una.setDescripcion(cntx.getString(R.string.desc_est_caloriastipodeporte));
			una.setConsultaSQL("select sportType as c0, count(_ID) as c1, sum(calories) as c2, avg(calories) as c3, sum(calories)/(sum(distance)/1000.0) as c4," +
							   "sum(calories)/(sum(runtime)/1000.0/60.0/60.0) as c5 from session where 1=1 group by c0 ");
			una.setCamposOrden(" c0 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
							
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,

							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_tipodeporte), cntx.getString(R.string.col_sesiones), 
								 			 cntx.getString(R.string.col_caloriastotal), cntx.getString(R.string.col_caloriasmedia), cntx.getString(R.string.col_caloriaskm), cntx.getString(R.string.col_caloriashora) });
								   
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_tipodeporte)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Relacion completa
			una.setTipo(Tipo_Estadistica.TE_Tabla_General);
			una.setTitulo(cntx.getString(R.string.titulo_est_relacioncompleta));
			una.setDescripcion(cntx.getString(R.string.desc_est_relacioncompleta));
			una.setConsultaSQL("select year as c0, month as c1, startTime as c2, startTime as c3, endTime as c4, sportType as c5, distance as c6," +
							   "runtime as c7, distance/(runtime/1000.0/60.0/60.0) as c8, runtime/(distance/1000.0) as c9, calories as cc10 from session where 1=1 "); //order by c2 desc ");
			una.setCamposOrden(" c0 desc, c1 desc, c2 desc, c3 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);
			
			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero_Ms_Dia,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_dia), cntx.getString(R.string.col_inicio),cntx.getString(R.string.col_fin),
											 cntx.getString(R.string.col_tipodeporte), 
								   			 cntx.getString(R.string.col_distancia), cntx.getString(R.string.col_tiempo), cntx.getString(R.string.col_kmhora),
								   			 cntx.getString(R.string.col_minutoskm), cntx.getString(R.string.col_calorias) });
			
			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_tipodeporte)});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			// Relacion completa - FICHAS
			una.setTipo(Tipo_Estadistica.TE_Fichas);
			una.setTitulo(cntx.getString(R.string.titulo_est_relacioncompleta_fichas));
			una.setDescripcion(cntx.getString(R.string.desc_est_relacioncompleta_fichas));
			una.setConsultaSQL("select year as c0, month as c1, startTime as c2, startTime as c3, endTime as c4, sportType as c5, distance as c6," +
							   "runtime as c7, distance/(runtime/1000.0/60.0/60.0) as c8, runtime/(distance/1000.0) as c9, calories as cc10, "+
							   "elevationGain as cc11, elevationLoss as cc12, note as cc13, _ID as cc14, pauseInMillis as cc15 from session where 1=1 "); //order by c2 desc ");
			una.setCamposOrden(" c0 desc, c1 desc, c2 desc, c3 desc ");
			una.estableceConsultaOriginal();
			una.setCamposOrden(null);

			una.setTiposCols(new Tipo_Columna[]
   							   { Tipo_Columna.TC_Entero_Anio,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero_Ms_Dia,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_HorasPorDistancia,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Cadena,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero_Ms_Horas
							 });

			una.setTitulosCols(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_dia), cntx.getString(R.string.col_inicio),cntx.getString(R.string.col_fin),
								   cntx.getString(R.string.col_tipodeporte), 
								   cntx.getString(R.string.col_distancia), cntx.getString(R.string.col_tiempo), cntx.getString(R.string.col_kmhora),
								   cntx.getString(R.string.col_minutoskm), cntx.getString(R.string.col_calorias), "", "", "" });

			una.setColumnasFiltro(new String[] {cntx.getString(R.string.col_anio), cntx.getString(R.string.col_mes), cntx.getString(R.string.col_tipodeporte)});
			una.setIsPermiteOrientacionVertical(true);
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/
			

			return relacion;

		}
		else
		{
			return relacion;
		}
    }

    public static String[] getTitulos(Context pcntx)
	{

		if (titulos == null)
		{
			if (relacion == null)
			{
				getRelacion();
			}

			titulos = new String[getNumeroEstadisticas()];

			for (int index=0;index < titulos.length;index++)
			{
				titulos[index] = getRelacion().get(index).getTitulo();
			}

			return titulos;

		}
		else
		{
			return titulos;
		}
	}

    public static int getNumeroEstadisticas()
    {
		if (relacion == null)
		{
			return 0;
		}
		else
		{
			// en version 1 el ultimo elemento no es una estadistica sino la opcion salir
			return relacion.size(); 
		}
    }


}


