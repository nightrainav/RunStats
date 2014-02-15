package es.fjpd.runstats3.vista;

import android.app.*;
import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import es.fjpd.runstats3.*;  
import es.fjpd.runstats3.logica.*;

// Obsoleta - Sustituida por ScreenSlideActivity

public class EstadisticaHTMLView extends Activity
{

    private WebView webView;


    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estadistica_html_view);

		// recuperamos parámetros

		Bundle bundle = getIntent().getExtras();
		int idEstadistica = bundle.getInt("ID_ESTADISTICA");       

		// ActionBar - Personalizacion
		ActionBar actBar = getActionBar();
		//actBar.setTitle(RelacionEstadisticas.getRelacion().get(idEstadistica).getTitulo());
		actBar.setTitle("Estadisticas de Runtastic");
		actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(idEstadistica).getDescripcion());

		// si la estadistica no permite orientacion vertical forzamos la apaisada
		// excepto en tablets, en cuyo caso no la forzamos
		if (!RelacionEstadisticas.getRelacion().get(idEstadistica).isPermiteOrientacionVertical()
			&& !Utilidades.isTablet())
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
		
		actBar.setDisplayHomeAsUpEnabled(true);

		// Obtenemos el HTML
		String html;
		try
		{

			html = new EstadisticaHTML(RelacionEstadisticas.getRelacion().get(idEstadistica)).getHtml();

			webView = (WebView) findViewById(R.id.webView1);
			
			//webView.loadData(html, "text/html", "UTF-8");
	
			//webView.setWebViewClient(new WebViewClient());
			/*{
					@Override
					public void onPageFinished(WebView view, String url)
					{
						onPageFinished(view, url);
						view.addJavascriptInterface(new WebAppUpdater(view.getContext()), "Updater");
						
					}
			}
			);*/
			
			
			//webView.setWebChromeClient(new WebChromeClient());
				/*{
				   @Override
				   public boolean onJsAlert(WebView view, String url, String message, JsResult result)
				   {
					   return super.onJsAlert(view, url, message, result);
				   }
					
				   
				}
			);*/
			
			//webView.getSettings().setJavaScriptEnabled(true);
			//webView.addJavascriptInterface(new WebAppUpdater(this), "Updater");

			webView.loadData(html, "text/html", "UTF-8");
			
			html=null;



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





