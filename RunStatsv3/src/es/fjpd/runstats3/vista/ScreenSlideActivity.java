package es.fjpd.runstats3.vista;


import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.view.*;
import es.fjpd.runstats3.*;
import es.fjpd.runstats3.logica.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because {@link ViewPager}
 * automatically plays such an animation when calling {@link ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 * @see ScreenSlidePageFragment
 */
public class ScreenSlideActivity extends FragmentActivity 
{
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static  int NUM_PAGES = RelacionEstadisticas.getRelacion().size();

	/**
	 * Posicion inicial. Primera pagina solicitada en el menu
	 */
	private int posicionInicial=0;


	/**
	 * Posicion inicial. Primera pagina solicitada en el menu
	 */
	private int posicionActual=0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

	// auxiliar para guardar el indice del campo elegido para el filtro
	int indiceFiltro=0;

	// auxiliares para ir construyendo la clasusula de ordenacion y filtro
	String tmpOrderBy="";
	String tmpWhere="";

	private Context cntx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

		// si no es tablet forzamos orientacion apaisada
		if (!Utilidades.isTablet())		
		{ setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}

		// recuperamos parámetros
		Bundle bundle = getIntent().getExtras();
		posicionInicial = bundle.getInt("ID_ESTADISTICA");     
		if (posicionActual == 0) posicionActual = posicionInicial;

		// ActionBar - Personalizacion


		if (Build.VERSION.RELEASE.startsWith("4"))
		{
			ActionBar actBar = getActionBar();
			actBar.setTitle(RunStats.getAppContext().getString(R.string.app_title));
			actBar.setDisplayHomeAsUpEnabled(true);
			actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(posicionInicial).getDescripcion());
		}

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
		// colocamos el viewpager en la posicion inicial (la que se corresponde con la opcion elegida por el usuario en el menu)
		mPager.setCurrentItem(posicionInicial, true);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position)
				{
					// When changing pages, reset the action bar actions since they are dependent
					// on which page is currently active. An alternative approach is to have each
					// fragment expose actions itself (rather than the activity exposing actions),
					// but for simplicity, the activity provides the actions in this sample.

					setPosicionActual(position);

					if (Build.VERSION.RELEASE.startsWith("4"))
					{
						invalidateOptionsMenu();

						ActionBar actBar = getActionBar();
						actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(position).getDescripcion());
					}

					/*Toast.makeText(cntx, 
					 RelacionEstadisticas.getRelacion().get(posicionActual).getLiteralCamposFiltro()+"***"+
					 RelacionEstadisticas.getRelacion().get(posicionActual).getLiteralCamposOrden(),
					 Toast.LENGTH_SHORT).show();*/
				}
			});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
        // inflamos el menu
		if (Build.VERSION.RELEASE.startsWith("4"))
		{

			getMenuInflater().inflate(R.menu.menu_screen_slide, menu);

			MenuItem item = menu.findItem(R.id.action_previous);
			item.setEnabled(mPager.getCurrentItem() > 0);
			if (mPager.getCurrentItem() <= 0)
			{
				item.setIcon(R.drawable.ic_action_previous_item_des);
			}

			item = menu.findItem(R.id.action_next);
			item.setEnabled(mPager.getCurrentItem() < mPagerAdapter.getCount() - 1);
			if (mPager.getCurrentItem() >= mPagerAdapter.getCount() - 1)
			{ item.setIcon(R.drawable.ic_action_next_item_des); }

			// Si la estadistica actual tiene campos de tipo filtro 
			// mostramos la opcion de filtro habilitada en caso contrario deshabilitada
			item = menu.findItem(R.id.action_filters);
			item.setEnabled(RelacionEstadisticas.getRelacion().get(posicionActual).getColumnasFiltro() != null);
			if (RelacionEstadisticas.getRelacion().get(posicionActual).getColumnasFiltro() == null)
			{
				item.setIcon(R.drawable.ic_action_search_des);
			}
		}
		else
		{
			MenuItem item = menu.add(Menu.NONE, android.R.id.home, Menu.NONE, R.string.action_back);

		}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
		{
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivityView.class));
                return true;

			case R.id.action_back:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivityView.class));
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;

				// AÑADIR ORDENACION
			case R.id.action_addsort:

				final String[] items = RelacionEstadisticas.getRelacion().get(posicionActual).getTitulosCols(); 
				// mostramos dialogo para elegir campo
				AlertDialog.Builder dialogo = new AlertDialog.Builder(cntx);
				dialogo.setTitle(getString(R.string.select_field));
				dialogo.setIcon(R.drawable.ic_action_import_export);
				dialogo.setItems(items, 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface p1, int p2)
						{
							//RelacionEstadisticas.getRelacion().get(posicionActual).setCamposOrden("c" + p2);
							
							if (p2<10)
							{
								tmpOrderBy = "c" + p2;
							}
							else
							{
								tmpOrderBy = "cc" + p2;
							}
							
							p1.dismiss();

							// mostramos dialogo para elegir orden
							final String[] items2 = new String[]{getString(R.string.order_asc), getString(R.string.order_desc)};
							AlertDialog.Builder dialogo2 = new AlertDialog.Builder(cntx);
							dialogo2.setTitle(getString(R.string.select_sortorder));
							dialogo2.setIcon(R.drawable.ic_action_import_export);
							dialogo2.setItems(items2, 
								new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface p1, int p2)
									{
										if (p2 == 0) //ASC
										{
											tmpOrderBy += " asc ";
										}
										else
										{
											tmpOrderBy += " desc ";
										}
										RelacionEstadisticas.getRelacion().get(posicionActual).setCamposOrden(tmpOrderBy);
										p1.dismiss();

										mPager.setAdapter(mPagerAdapter);										
										mPager.setCurrentItem(posicionActual, true);
										//Toast.makeText(cntx, RelacionEstadisticas.getRelacion().get(mPager.getCurrentItem() ).getConsultaSQL(), Toast.LENGTH_LONG).show();

									}

								});

							dialogo2.create().show();

						}

					});

				dialogo.create().show();


				return true;

				// INFORMACION FILTROS
			case R.id.action_infofilter:

				// mostramos dialogo para elegir campo
				AlertDialog.Builder dialogoif = new AlertDialog.Builder(cntx);
				dialogoif.setTitle(getString(R.string.filter_applied));
				dialogoif.setMessage(RelacionEstadisticas.getRelacion().get(posicionActual).getLiteralCamposFiltro());
				dialogoif.setIcon(R.drawable.ic_action_info);

				dialogoif.setPositiveButton(R.string.action_back,
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface p1, int p2)
						{
							p1.dismiss();
						}

					});

				dialogoif.create().show();


				return true;

				// INFORMACION ORDENACION
			case R.id.action_infosort:

				// mostramos dialogo para elegir campo
				AlertDialog.Builder dialogois = new AlertDialog.Builder(cntx);
				dialogois.setTitle(getString(R.string.sort_applied));
				dialogois.setMessage(RelacionEstadisticas.getRelacion().get(posicionActual).getLiteralCamposOrden());
				dialogois.setIcon(R.drawable.ic_action_info);

				dialogois.setPositiveButton(R.string.action_back,
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface p1, int p2)
						{
							p1.dismiss();
						}

					});

				dialogois.create().show();


				return true;

				// AÑADIR FILTRO
			case R.id.action_addfilter:

				final String[] itemsf = RelacionEstadisticas.getRelacion().get(posicionActual).getColumnasFiltro(); 
				// mostramos dialogo para elegir campo
				AlertDialog.Builder dialogof = new AlertDialog.Builder(cntx);
				dialogof.setTitle(getString(R.string.select_field));
				dialogof.setIcon(R.drawable.ic_action_search);
				dialogof.setItems(itemsf, 
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface p1, int p2)
						{

							// de momento es la mejor manera que he encontrado de hacer esto
							if (RelacionEstadisticas.getRelacion().get(posicionActual).getColumnasFiltro()[p2].equals("Año"))
							{indiceFiltro = 0;}
							else if (RelacionEstadisticas.getRelacion().get(posicionActual).getColumnasFiltro()[p2].equals("Mes"))
							{indiceFiltro = 1;}
							else
							{indiceFiltro = 2;}

							// ya sabemos que campo ha elegido el usuario, vamos montando el where
							// "and campo="
							tmpWhere = " " + FiltrosSQLDisponibles.getListaFiltros().get(indiceFiltro).getNombreCampoBD() + "=";

							p1.dismiss();

							// mostramos dialogo para elegir valor
							final String[] itemsf2 = FiltrosSQLDisponibles.getListaFiltros().get(indiceFiltro).getListaValores();
							AlertDialog.Builder dialogof2 = new AlertDialog.Builder(cntx);
							dialogof2.setTitle(getString(R.string.select_value));
							dialogof2.setIcon(R.drawable.ic_action_search);
							dialogof2.setItems(itemsf2, 
								new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface p1, int p2)
									{
										// completamos la calusula where con el valor
										tmpWhere += FiltrosSQLDisponibles.getListaFiltros().get(indiceFiltro).getValoresPosibles().get(p2).getValorCampo();
										RelacionEstadisticas.getRelacion().get(posicionActual).setCamposFiltro(tmpWhere);
										//Toast.makeText(cntx, RelacionEstadisticas.getRelacion().get(posicionActual ).getConsultaSQL(), Toast.LENGTH_LONG).show();

										p1.dismiss();

										mPager.setAdapter(mPagerAdapter);										
										mPager.setCurrentItem(posicionActual, true);
									}

								});

							dialogof2.create().show();

						}

					});

				dialogof.create().show();


				return true;

			case R.id.action_discardfilter:

				RelacionEstadisticas.getRelacion().get(posicionActual).reestableceFiltro();

				mPager.setAdapter(mPagerAdapter);										
				mPager.setCurrentItem(posicionActual, true);

				return true;


			case R.id.action_discardsort:

				RelacionEstadisticas.getRelacion().get(posicionActual).reestableceOrden();

				mPager.setAdapter(mPagerAdapter);										
				mPager.setCurrentItem(posicionActual, true);

				return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
	{
        public ScreenSlidePagerAdapter(FragmentManager fm)
		{
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
		{

            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount()
		{
            return NUM_PAGES;
        }
    }

	private void setPosicionActual(int pos)
	{
		posicionActual = pos;
	}

	private void setIndiceFiltro(int indice)
	{
		indiceFiltro = indice;
	}
}
