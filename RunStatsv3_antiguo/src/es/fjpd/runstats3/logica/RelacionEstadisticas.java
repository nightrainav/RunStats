package es.fjpd.runstats3.logica;

import java.util.ArrayList;

import es.fjpd.runstats3.logica.Estadistica.*;


public class RelacionEstadisticas
{

    private static ArrayList<Estadistica> relacion = null;

    private static String[] titulos = null;

    public static ArrayList<Estadistica> getRelacion()
	{


		if (relacion == null)
		{
			Estadistica una = null;

			relacion = new ArrayList<Estadistica>();

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Total");
			una.setDescripcion("Datos globales de sesiones");
			una.setConsultaSQL("select count(_ID), sum(distance), sum (runtime), sum (calories) from session ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Sesiones", "Distancia (km)", "Tiempo", "Calorías"});
			una.setAnchoCols(new int[] {70, 100, 70, 70});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Por tipo de deporte");
			una.setDescripcion("Datos globales de sesiones por tipo de deporte");
			una.setConsultaSQL(" select sportType, count(_ID), sum(distance) , avg(distance)," +
							   "sum(runtime), avg(runtime), sum(calories), avg (calories) from session group by sportType");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Tipo actividad", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

			una.setAnchoCols(new int[] {150, 70, 70, 70, 100, 100, 100, 100});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Por años");
			una.setDescripcion("Datos globales de sesiones por años");
			una.setConsultaSQL("select year, count(_ID), sum(distance), avg(distance)," +
							   "sum(runtime), avg(runtime), sum(calories), avg (calories) from session group by year order by year desc");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Año", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});
			una.setAnchoCols(new int[] {50, 70, 70, 70, 100, 100, 100 , 100});

			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Por años y tipo de deporte");
			una.setDescripcion("Datos globales de sesiones por años y tipo de deporte");
			una.setConsultaSQL("select year, sportType, count(_ID), sum(distance), avg(distance)," +
							   "sum(runtime), avg(runtime), sum(calories), avg (calories) from session group by year, sportType order by year desc");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Año", "Tipo actividad", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

			una.setAnchoCols(new int[] {50, 150, 70, 70, 70, 100, 100, 100, 100});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Por meses");
			una.setDescripcion("Datos globales de sesiones por meses");
			una.setConsultaSQL("select year, month, count(_ID), sum(distance), avg(distance)," +
							   "sum(runtime), avg(runtime), sum(calories), avg (calories) from session group by year, month order by year desc, month desc ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Año", "Mes", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});
			una.setAnchoCols(new int[] {50, 80, 70, 70, 70, 100, 100, 100, 100});

			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/


			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Por meses y tipo de deporte");
			una.setDescripcion("Datos globales de sesiones por meses y tipo de deporte");
			una.setConsultaSQL("select year, month, sportType, count(_ID), sum(distance), avg(distance)," +
							   "sum(runtime), avg(runtime), sum(calories), avg (calories) from session group by year, month, sportType order by year desc, month desc ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Año", "Mes", "Tipo actividad", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

			una.setAnchoCols(new int[] {50, 80, 150, 70, 70, 70, 100, 100, 100, 100});
			
			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Calorías por tipo de deporte");
			una.setDescripcion("Consumo de calorías por tipo de deporte");
			una.setConsultaSQL("select sportType, count(_ID), avg(calories) , sum(calories)/(sum(distance)/1000)," +
							   "sum(calories)/(sum(runtime)/1000/60/60)  from session group by sportType");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,

							 });

			una.setTitulosCols(new String[] {"Tipo actividad", "Sesiones", "Calorías de media", "Calorías por km", "Calorías por hora"});
			una.setAnchoCols(new int[] {150, 70, 120, 120, 120});

			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/*************************************/
			una = new Estadistica();

			una.setTitulo("Relación completa");
			una.setDescripcion("Relación completa de sesiones");
			una.setConsultaSQL("select year, month, startTime, startTime, endTime, sportType, distance," +
							   "runtime, calories from session order by startTime desc ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Entero_Mes,
								 Tipo_Columna.TC_Entero_Ms_Dia,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_Ms_Hora,
								 Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Año", "Mes", "Dia" , "Hora inicio", "Hora fin", "Tipo actividad", "Km", "Tiempo", "Calorías "});
			una.setAnchoCols(new int[] {50, 80, 40, 80, 80, 150, 60, 70, 70});

			relacion.add(una);
			/*************************************/
			/**** Fin elemento de la lista ****/
			/*************************************/

			/*************************************/
			/**** Inicio elemento de la lista ****/
			/**** Para la opcion de salir    ****/
			/*************************************/
			// solo mostramos esta opcion en version 1
			if (Utilidades.getVersion().equals("1"))
			{
				una = new Estadistica();

				una.setTitulo("Salir");
				una.setDescripcion("Salir de la aplicación");

				relacion.add(una);
			}
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

    public static String[] getTitulos()
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
			if (Utilidades.getVersion().equals("1"))
			{ return relacion.size()-1; }
			else
			{ return relacion.size(); }
		}
    }


}


