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
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

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

		// ActionBar - Personalizacion


		if (Build.VERSION.RELEASE.startsWith("4"))
		{
			ActionBar actBar = getActionBar();
			actBar.setTitle("Estadisticas de Runtastic");
			actBar.setDisplayHomeAsUpEnabled(true);
			actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(posicionInicial).getDescripcion());
		}

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
		//	mPager.setPageTransformer(true, new DepthPageTransformer());
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


					if (Build.VERSION.RELEASE.startsWith("4"))
					{
						invalidateOptionsMenu();

						ActionBar actBar = getActionBar();
						actBar.setSubtitle(RelacionEstadisticas.getRelacion().get(position).getDescripcion());
					}
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

			menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

			// Add either a "next" or "finish" button to the action bar, depending on which page
			// is currently selected.
			MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
									 (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
									 ? R.string.action_next
									 : R.string.action_next);

			menu.findItem(R.id.action_next).setEnabled(mPager.getCurrentItem() < mPagerAdapter.getCount() - 1);



			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
}
