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
			una.setConsultaSQL("select count(_ID) as c0, sum(distance) as c1, sum (runtime) as c2, sum (calories) as c3 from session ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero,
								 Tipo_Columna.TC_Real_Metros,
								 Tipo_Columna.TC_Entero_Ms_Horas,
								 Tipo_Columna.TC_Entero
							 });

			una.setTitulosCols(new String[] {"Sesiones", "Distancia", "Tiempo", "Calorías"});

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
			una.setConsultaSQL(" select sportType as c0, count(_ID) as c1, sum(distance) as c2, avg(distance) as c3," +
							   "sum(runtime) as c4, avg(runtime) as c5, sum(calories) as c6, avg (calories) as c7 from session group by sportType order by c0");

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

			una.setTitulosCols(new String[] {"Tipo deporte", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

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
			una.setConsultaSQL("select year as c0, count(_ID) as c1, sum(distance) as c2, avg(distance) as c3, " +
							   "sum(runtime) as c4, avg(runtime) as c5, sum(calories) as c6, avg (calories) as c7 from session group by year order by c0 desc");

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
			una.setConsultaSQL("select year as c0, sportType as c1, count(_ID) as c2, sum(distance) as c3, avg(distance) as c4," +
							   "sum(runtime) as c5, avg(runtime) as c6, sum(calories) as c7, avg (calories) as c8 from session group by year, sportType order by c0 desc");

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

			una.setTitulosCols(new String[] {"Año", "Tipo deporte", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

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
			una.setConsultaSQL("select year as c0, month as c1, count(_ID) as c2, sum(distance) as c3, avg(distance) as c4," +
							   "sum(runtime) as c5, avg(runtime) as c6, sum(calories) as c7, avg (calories) as c8 from session group by year, month order by c0 desc, c1 desc ");

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
			una.setConsultaSQL("select year as c0, month as c1, sportType as c2, count(_ID) as c3, sum(distance) as c4, avg(distance) as c5," +
							   "sum(runtime) as c6, avg(runtime) as c7, sum(calories) as c8, avg (calories) as c9 from session group by year, month, sportType order by c0, c1 desc ");

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

			una.setTitulosCols(new String[] {"Año", "Mes", "Tipo deporte", "Sesiones", "Km total", "Km media", "Tiempo total", "Tiempo medio", "Calorías total", "Calorías media"});

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
			una.setConsultaSQL("select sportType as c0, count(_ID) as c1, avg(calories) as c2, sum(calories)/(sum(distance)/1000) as c3," +
							   "sum(calories)/(sum(runtime)/1000/60/60) as c4 from session group by c0 ");

			una.setTiposCols(new Tipo_Columna[]
   							 { Tipo_Columna.TC_Entero_sportType,
								 Tipo_Columna.TC_Entero,    
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,
								 Tipo_Columna.TC_Real,

							 });

			una.setTitulosCols(new String[] {"Tipo deporte", "Sesiones", "Calorías media", "Calorías/km", "Calorías/hora"});

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
			una.setConsultaSQL("select year as c0, month as c1, startTime as c2, startTime as c3, endTime as c4, sportType as c5, distance as c6," +
							   "runtime as c7, calories as c8 from session order by c2 desc ");

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

			una.setTitulosCols(new String[] {"Año", "Mes", "Dia" , "Inicio", "Fin", "Tipo deporte", "Distancia", "Tiempo", "Calorías "});

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


