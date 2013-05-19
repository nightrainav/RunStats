package es.fjpd.runstats3.vista;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import es.fjpd.runstats3.*;
import es.fjpd.runstats3.logica.*;
import java.util.*;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 *
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment
{
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber)
	{
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment()
	{
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.estadistica_view, container, false);

		int idEstadistica = this.mPageNumber;       

		// Titulo y descripcion de estadistica
		TextView tituloEstadistica = (TextView)rootView.findViewById(R.id.TxtTituloEstadistica);
		TextView descEstadistica = (TextView)rootView.findViewById(R.id.TxtDescEstadistica);

		tituloEstadistica.setText(RelacionEstadisticas.getRelacion().get(idEstadistica).getTitulo());
		descEstadistica.setText(RelacionEstadisticas.getRelacion().get(idEstadistica).getDescripcion());


		rootView.addView(new EstadisticaViewLayout(Utilidades.getAppContext()).getView(RelacionEstadisticas.getRelacion().get(idEstadistica)));
		//}
		//catch (Exception e)
		//{
		//	Toast.makeText(Utilidades.getAppContext(), "error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
		//	Log.e("ERROR", e.getLocalizedMessage());
		//}
        // Set the title view to show the page number.
        /*((TextView) rootView.findViewById(android.R.id.text1)).setText(
		 getString(R.string.title_template_step, mPageNumber + 1));*/

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber()
	{
        return mPageNumber;
    }

}
