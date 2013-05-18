package es.fjpd.runstats.vista;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import es.fjpd.runstats.*;
import es.fjpd.runstats.logica.*;
import java.io.*;
import java.util.*;
import android.graphics.*;

public class MainActivityView extends Activity
{

	private ArrayList<Estadistica> datos = RelacionEstadisticas.getRelacion();
    private String fakeDatos[] = RelacionEstadisticas.getTitulos();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// ActionBar - Personalizacion
		/*ActionBar actBar = getActionBar();
		 actBar.setTitle("Estadisticas de Runtastic");*/

		// inicializamos una variable estatica con el contexto
		Utilidades.setAppContext(MainActivityView.this);

    	AdaptadorRelEstadisticas adaptador =
			new AdaptadorRelEstadisticas(this);

    	ListView lstOpciones = (ListView)findViewById(R.id.LstOpciones);

    	lstOpciones.setAdapter(adaptador);

		//	lstOpciones.setOnItemClickListener( new AdapterView.OnItemClickListener());
		
		if (BaseDatos.getRutaBD().equals(""))
		{
			Toast.makeText(MainActivityView.this, "No se ha encontrado la Base de Datos de Runtastic en la SD. Cambie la ubicacion en Runtastic (Opciones generales / Ubicacion de datos)", Toast.LENGTH_LONG).show();
		}

		lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position, long id) 
				{
					//Acciones necesarias al hacer click
					// debemos crear una instancia de la vista de estadísticas pasandole el ID (posicion) de la opción seleccionada
					// excepto para a ultima opcion que es salir
					if (position == datos.size() - 1)
					{
						BaseDatos.cierraBaseDatos();
						finish();
					}
					// si no hemos encontrado la bd no asociamos codigo al click 
					else if (!BaseDatos.getRutaBD().equals(""))
					{
						Intent intent = new Intent(MainActivityView.this, EstadisticaHTMLView.class);
						Bundle bundle = new Bundle();
						bundle.putInt("ID_ESTADISTICA", position);
						intent.putExtras(bundle);
						try
						{
							startActivity(intent);
						}
						catch (Exception e)
						{
							Toast.makeText(MainActivityView.this, "error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG);
						}
					}

				}
			});
		
		//Toast.makeText(MainActivity.this, "Ruta: "+getRutaBD(), Toast.LENGTH_LONG).show();
	}




	class AdaptadorRelEstadisticas extends ArrayAdapter
	{

		Activity context;

		AdaptadorRelEstadisticas(Activity context)
		{
			super(context, R.layout.listitem_relestadisticas, fakeDatos);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.listitem_relestadisticas, null);

			TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
			lblTitulo.setText(datos.get(position).getTitulo());

			TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
			lblSubtitulo.setText(datos.get(position).getDescripcion());
			
			// para el ultimo elemento (Salir) usamos un color de texto diferente
			if (position == datos.size() - 1)
			{
				lblTitulo.setTextColor(Color.MAGENTA);
				lblSubtitulo.setTextColor(Color.MAGENTA);
			}

			return(item);
		}
	}


}

