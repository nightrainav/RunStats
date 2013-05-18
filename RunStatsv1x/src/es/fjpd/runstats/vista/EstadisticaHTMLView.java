package es.fjpd.runstats.vista;

import android.app.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import es.fjpd.runstats.*;
import es.fjpd.runstats.logica.*;

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
		/*ActionBar actBar = getActionBar();
		actBar.setTitle(RelacionEstadisticas.getRelacion().get(idEstadistica).getTitulo());*/
		
		// asociamos codigo al boton volver
		ImageButton btn = (ImageButton)findViewById(R.id.btnVolver);

		btn.setOnClickListener(new ImageButton.OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					//Acciones necesarias al hacer click
					finish();

				}
			});

		// Obtenemos el HTML
		String html;
		try
		{

			html = new EstadisticaHTML(RelacionEstadisticas.getRelacion().get(idEstadistica)).getHtml();


			webView = (WebView) findViewById(R.id.webView1);
			webView.getSettings().setJavaScriptEnabled(false);

			//webView.loadData(html, "text/html", "UTF-8");
			
			// por problemas en versiones 2.x de android debemos usar este metodo
			webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);



		}
		catch (Exception e)
		{
			Toast.makeText(EstadisticaHTMLView.this, "error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

		}



    }

}





