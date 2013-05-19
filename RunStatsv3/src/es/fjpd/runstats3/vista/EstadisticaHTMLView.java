package es.fjpd.runstats2.vista;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import es.fjpd.runstats2.*;
import es.fjpd.runstats2.logica.*;

// Obsoleta - Sustituida por ScreenSlideActivity

public class EstadisticaHTMLView extends Activity
{

    private WebView webView;


    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estadistica_html_view);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		// recuperamos parámetros

		Bundle bundle = getIntent().getExtras();
		int idEstadistica = bundle.getInt("ID_ESTADISTICA");       

		// ActionBar - Personalizacion
		ActionBar actBar = getActionBar();
		//actBar.setTitle(RelacionEstadisticas.getRelacion().get(idEstadistica).getTitulo());
		actBar.setTitle("Estadisticas de Runtastic");
		actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(idEstadistica).getDescripcion());
		
		
		actBar.setDisplayHomeAsUpEnabled(true);

		// Obtenemos el HTML
		String html;
		try
		{

			html = new EstadisticaHTML(RelacionEstadisticas.getRelacion().get(idEstadistica)).getHtml();


			webView = (WebView) findViewById(R.id.webView1);
			webView.getSettings().setJavaScriptEnabled(false);

			webView.loadData(html, "text/html", "UTF-8");



		}
		catch (Exception e)
		{
			Toast.makeText(EstadisticaHTMLView.this, "error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

		}



    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				// Alternativa  a estudiar
				/*Intent intent = new Intent(this, MainActivityView.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(intent);*/
				break;
		}
		return true;
	}
}





