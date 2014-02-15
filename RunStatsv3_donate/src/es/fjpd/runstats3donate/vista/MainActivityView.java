package es.fjpd.runstats3donate.vista;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import es.fjpd.runstats3donate.*;
import es.fjpd.runstats3donate.logica.*;
import java.util.*;
import android.preference.*;
import android.content.res.*;

public class MainActivityView extends Activity
{

	private ArrayList<Estadistica> datos = RelacionEstadisticas.getRelacion();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		RunStats.actualizaLocaleSiNecesario();
		
		// ActionBar - Personalizacion
		if (Build.VERSION.RELEASE.startsWith("4"))
		{
			ActionBar actBar = getActionBar();
			actBar.setTitle(RunStats.getAppContext().getString(R.string.app_title));
			actBar.setSubtitle(RunStats.getAppContext().getString(R.string.select_stat));
		}
	 	AdaptadorRelEstadisticas adaptador = new AdaptadorRelEstadisticas(MainActivityView.this, RelacionEstadisticas.getTitulos(MainActivityView.this));

    	ListView lstOpciones = (ListView)findViewById(R.id.LstOpciones);

    	lstOpciones.setAdapter(adaptador); 


		if (BaseDatos.getRutaBD().equals(""))
		{
			//Toast.makeText(MainActivityView.this, RunStats.getAppContext().getString(R.string.db_not_found) , Toast.LENGTH_LONG).show();

			AlertDialog.Builder alertBld = new AlertDialog.Builder(MainActivityView.this);

			alertBld.setTitle(RunStats.getAppContext().getString(R.string.db_not_found_title));
			alertBld.setMessage(RunStats.getAppContext().getString(R.string.db_not_found));
			
			alertBld.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dlg, int id)
					{
						dlg.cancel();
						finish();
					}
				});

			AlertDialog alerta = alertBld.create();

			alerta.show();
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

					// si no hemos encontrado la bd no asociamos codigo al click 
					if (!BaseDatos.getRutaBD().equals(""))
					{
						Intent intent = new Intent(MainActivityView.this, ScreenSlideActivity.class);
						//Intent intent = new Intent(RunStats.getAppContext(), ScreenSlideActivity.class);
						
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
							Log.e("ERROR", e.getLocalizedMessage());
						}

					}

				}
			});

		//Toast.makeText(MainActivity.this, "Ruta: "+getRutaBD(), Toast.LENGTH_LONG).show();
	}




	class AdaptadorRelEstadisticas extends ArrayAdapter
	{

		Activity context;

		AdaptadorRelEstadisticas(Activity context, String[] data)
		{
			super(context, R.layout.listitem_relestadisticas, data);
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
				System.exit(0);
				return true;
			case R.id.action_prefs:
				startActivity(new Intent(MainActivityView.this, PreferenciasActivity.class));
				return true;
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
	}

}

