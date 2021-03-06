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
import android.preference.*;
import android.content.res.*;

public class MainActivityView extends Activity
{

	private ArrayList<Estadistica> datos = RelacionEstadisticas.getRelacion();;
    private String fakeDatos[] = RelacionEstadisticas.getTitulos(MainActivityView.this);

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);

		//actualizaLocaleSiNecesario();
		
		setContentView(R.layout.main);

		// si no es tablet forzamos orientacion apaisada
		/*if (!Utilidades.isTablet())
		 { setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); }*/

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
					// excepto para a ultima opcion que es salir SOLO EN VERSION 1

					// si no hemos encontrado la bd no asociamos codigo al click 
					if (!BaseDatos.getRutaBD().equals(""))
					{
						Intent intent = new Intent(MainActivityView.this, ScreenSlideActivity.class);

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
		//actualizaLocaleSiNecesario();
	}

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
	}


	private void actualizaLocaleSiNecesario()
	{

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(RunStats.getAppContext());

		String idioma = pref.getString("idioma", "");	

		Configuration currConfig = getResources().getConfiguration();
		
		Toast.makeText(MainActivityView.this, "Actual: "+ currConfig.locale.getDisplayLanguage()+" pref: "+ pref.getString("idioma", "**") , Toast.LENGTH_LONG).show();


		// si se ha seleccionado algun idioma lo establecemos
		if (!idioma.equals(""))
		{
			Locale myLocale = new Locale(idioma);
			Locale.setDefault(myLocale);
			android.content.res.Configuration config = new android.content.res.Configuration();
			config.locale = myLocale;
			
			getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
			
		}
	}

}

