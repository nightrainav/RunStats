package es.fjpd.runstats3.vista;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import es.fjpd.runstats3.*;
import es.fjpd.runstats3.logica.*;
import java.util.*;

public class MainActivityView extends Activity
{

	private ArrayList<Estadistica> datos = RelacionEstadisticas.getRelacion();
    private String fakeDatos[] = RelacionEstadisticas.getTitulos();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);

		// inicializamos una variable estatica con el contexto
		Utilidades.setAppContext(MainActivityView.this);

		// si no es tablet forzamos orientacion apaisada
		/*if (!Utilidades.isTablet())
		 { setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); }*/

		// ActionBar - Personalizacion
		if (Build.VERSION.RELEASE.startsWith("4"))
		{
			ActionBar actBar = getActionBar();
			actBar.setTitle("Estadisticas de Runtastic");
			actBar.setSubtitle("Seleccione una estadística");
		}

    	AdaptadorRelEstadisticas adaptador =
			new AdaptadorRelEstadisticas(this);

    	ListView lstOpciones = (ListView)findViewById(R.id.LstOpciones);

    	lstOpciones.setAdapter(adaptador);

		//	lstOpciones.setOnItemClickListener( new AdapterView.OnItemClickListener());

		if (BaseDatos.getRutaBD().equals(""))
		{
			Toast.makeText(MainActivityView.this, "No se ha encontrado la Base de Datos de Runtastic en la SD. Cambie la ubicacion en Runtastic (Opciones generales / Ubicacion de datos)", Toast.LENGTH_LONG).show();
		}
		else
		{ 
		    BaseDatos.getConn();
		}

		lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position, long id) 
				{
					// Acciones necesarias al hacer click
					// debemos crear una instancia de la vista de estadísticas pasandole el ID (posicion) de la opción seleccionada
					// excepto para a ultima opcion que es salir SOLO EN VERSION 1
					if ((position == datos.size() - 1) && (Utilidades.getVersion().equals("1")))
					{
						//BaseDatos.cerrar();
						finish();
					}
					// si no hemos encontrado la bd no asociamos codigo al click 
					else if (!BaseDatos.getRutaBD().equals(""))
					{
						Intent intent = new Intent(MainActivityView.this, ScreenSlideActivity.class);

						Bundle bundle = new Bundle();
						bundle.putInt("ID_ESTADISTICA", position);
						intent.putExtras(bundle);

						try
						{
							startActivity(intent);
							Log.d("DEBUG", "Lanzada actividad");
						}
						catch (Exception e)
						{
							Toast.makeText(MainActivityView.this, "error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG);
							Log.e("ERROR", e.getLocalizedMessage());
						}

						/*Intent intent = new Intent(MainActivityView.this, EstadisticaHTMLView.class);
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
						 }*/
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

			// en version 1 la ultima entrada del listview es una opcion salir
			// y la pintamos de otro color
			if (Utilidades.getVersion().equals("1"))
			{
				if (position == datos.size() - 1)
				{
					lblTitulo.setTextColor(Color.MAGENTA);
					lblSubtitulo.setTextColor(Color.MAGENTA);
				}
			}

			return(item);
		}
	}


	// menu

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // inflamos el menu
		super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
		{
            case R.id.action_exit:
				//BaseDatos.cerrar();
				finish();
				return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

