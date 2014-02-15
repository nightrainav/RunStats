package es.fjpd.runstats3donate.logica;

import java.util.*;

public class DatosFiltro
{

	String tituloCol="";
	String nombreCampoBD="";

	ArrayList<ValorCampoFiltro> valoresPosibles=new ArrayList<ValorCampoFiltro>();
	

	public DatosFiltro(String titulo, String campo)
	{
		tituloCol = titulo;
		nombreCampoBD = campo;
	}

	public void addValor(int valor, String pliteral)
	{
		ValorCampoFiltro v = new ValorCampoFiltro(valor, pliteral);
		valoresPosibles.add(v);
	}
	
	public  String[] getListaValores()
	{

		String[] valores = new String[valoresPosibles.size()];

		for (int i=0;i<valores.length;i++)
		{
			valores[i]=valoresPosibles.get(i).getLiteral();
		}

		return valores;
	}

	public class ValorCampoFiltro
	{
		int valorCampo=0;
		String literal="";

		public ValorCampoFiltro(int valor, String pliteral)
		{
			valorCampo = valor;
			literal = pliteral;
		}

		public void setValorCampo(int valorCampo)
		{
			this.valorCampo = valorCampo;
		}

		public int getValorCampo()
		{
			return valorCampo;
		}

		public void setLiteral(String literal)
		{
			this.literal = literal;
		}

		public String getLiteral()
		{
			return literal;
		}
	}

	public void setNombreCampoBD(String nombreCampoBD)
	{
		this.nombreCampoBD = nombreCampoBD;
	}

	public String getNombreCampoBD()
	{
		return nombreCampoBD;
	}

	public void setTituloCol(String tituloCol)
	{
		this.tituloCol = tituloCol;
	}

	public String getTituloCol()
	{
		return tituloCol;
	}

	public void setValoresPosibles(ArrayList<ValorCampoFiltro> valoresPosibles)
	{
		this.valoresPosibles = valoresPosibles;
	}

	public ArrayList<ValorCampoFiltro> getValoresPosibles()
	{
		return valoresPosibles;
	}

	
}
