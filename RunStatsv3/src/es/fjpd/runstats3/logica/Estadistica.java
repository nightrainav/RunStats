package es.fjpd.runstats3.logica;

import es.fjpd.runstats3.*;

public class Estadistica
{

    // Título de la estadística
    String titulo="";
    // Descripción de la estadística
    String descripcion="";
    // Consulta SQL que genera la estadística
    String consultaSQL="";

	// para poder reestablecerla (eliminar filtros y orden indicados por usuario)
	String consultaSQLOriginal = "";

	// indicadores de si se ha indicado un filtro o un orden para saber si debemos habilitar
	// las opciones de reestablecer
	boolean filtroAplicado=false;
	boolean ordenAplicado=false;

    // Títulos que queremos para las columnas
    String[] titulosCols;

    // Tipos de datos para las columnas
    // El tipo especial Entero_sportType se usa para identificar esta columna
    // y poder convertirla a su nombre, ya que no están en tabla
    public enum Tipo_Columna
	{TC_Entero_sportType, TC_Entero, TC_Entero_Ms_Horas, TC_Entero_Ms_Hora, TC_Entero_Ms_Dia, TC_Entero_Mes, TC_Real, TC_Real_Metros, TC_Cadena};
    // Tipos de las columnas
    Tipo_Columna[] tiposCols;

	// de momento solo vamos a permitir order por un campo
	// esta variable tendra la forma "c1 asc"
	String camposOrden = null;

	// para realizar filtros sencillos sobre la consulta, de momento por un solo campo
	// tendra la forma " c1 = 4 "
	String camposFiltro = null;

	// para cada consulta indicamos las columnas por las que
	// tiene sentido filtrar
	String[] columnasFiltro;

    public Estadistica()
    {

    }

    public Estadistica(String pTitulo, String pDescripcion, String pConsultaSQL)
    {
		titulo = pTitulo;
		descripcion = pDescripcion;
		consultaSQL = pConsultaSQL;
    }


    /**
     * Valida que estén rellenos los datos de la estadística y que la consulta SQL sea correcta
     * @return Verdadero si es correcta y falso en caso contrario
     */
    public boolean validaEstadistica()
    {
		if (titulo.trim().equals(""))
		{
			return false;
		}

		if (descripcion.trim().equals(""))
		{
			return false;
		}

		if (consultaSQL.trim().equals(""))
		{
			return false;
		}

		if (!validaConsultaSQL())
		{
			return false;
		}

		return true;
    }

    private boolean validaConsultaSQL()
    {
		boolean resultado=true;

		// ejecutar consulta SQL para ver si es valida

		return resultado;
    }

    public void setTitulo(String titulo)
	{
		this.titulo = titulo;
    }

    public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
    }

    public void setConsultaSQL(String pconsultaSQL)
	{

		// establecemos  la consulta SQL original si no lo esta
		if (this.consultaSQLOriginal.equals(""))
		{ this.consultaSQLOriginal = pconsultaSQL; }
		
		this.consultaSQL = pconsultaSQL;
		transformaSQLConCamposOrden();
		transformaSQLConCamposFiltro();
    }

    public String getTitulo()
	{
		return titulo;
    }
    public String getConsultaSQL()
	{
		return consultaSQL;
    }
    public String getDescripcion()
	{
		return descripcion;
    }

    public String[] getTitulosCols()
	{
		return titulosCols;
    }

    public void setTitulosCols(String[] titulosCols)
	{
		this.titulosCols = titulosCols;
    }

	public String[] getColumnasFiltro()
	{
		return columnasFiltro;
    }

    public void setColumnasFiltro(String[] columnasFiltro)
	{
		this.columnasFiltro = columnasFiltro;
    }

    public Tipo_Columna[] getTiposCols()
	{
		return tiposCols;
    }

    public void setTiposCols(Tipo_Columna[] tiposCols)
	{
		this.tiposCols = tiposCols;
    }


	public void setCamposOrden(String camposOrden)
	{

		// si nos llega null es porque se quiere resetear el campo
		// sin afectar a nada mas. Se usa para que el orden por defecto
		// de cada consulta no sea persitente
		
		if (camposOrden==null) 
		{
			this.camposOrden=null;
			return;
		}
		
		// podria haber ya un order by por lo que concatenamos el que nos viene
		String tmp = (this.camposOrden==null?"":this.camposOrden+", ") +camposOrden;

		ordenAplicado = true;
		
		// quitamos el orden que hubiera
		reestableceOrden();

		// aplicamos el nuevo
		this.camposOrden = tmp;
		
		transformaSQLConCamposOrden();
	}

	public String getCamposOrden()
	{
		return camposOrden;
	}

	public void setCamposFiltro(String camposFiltro)
	{
		// podria haber ya un filtro por lo que concatenamos el que nos viene
		String tmp = (this.camposFiltro==null?"":this.camposFiltro+" and ") +camposFiltro;
		
		filtroAplicado = true;
		
		// quitamos el filtro que hubiera
		reestableceFiltro();
		
		// aplicamos el nuevo
		this.camposFiltro = tmp;
		
		transformaSQLConCamposFiltro();
	}

	public String getCamposFiltro()
	{
		return camposFiltro;
	}

	private void transformaSQLConCamposFiltro()
	{
		if (camposFiltro != null && !consultaSQL.equals(""))
		{
			int posMarcador=consultaSQL.indexOf("1=1");
			String tmpRestoSQL=consultaSQL.substring(posMarcador + 3);
			consultaSQL = consultaSQL.substring(0, posMarcador + 3);
			consultaSQL += " and " + camposFiltro + "  " + tmpRestoSQL;
		}

	}

	private void transformaSQLConCamposOrden()
	{
		if (camposOrden != null && !consultaSQL.equals(""))
		{
			int posOrderBy=consultaSQL.indexOf("order by");
			if (posOrderBy == -1) posOrderBy = consultaSQL.length();
			consultaSQL = consultaSQL.substring(0, posOrderBy);
			consultaSQL += " order by " + camposOrden+ " ";
		}

	}	

	// para reestablecer consulta original (quitar filtro y orden de usuario)
	public void reestableceConsulta()
	{
		consultaSQL = consultaSQLOriginal;
		camposOrden = null;
		camposFiltro = null;
		filtroAplicado = false;
		ordenAplicado=false;
	}

	// para reestablecer el filtro de la consulta original (quitar filtro de usuario)
	public void reestableceFiltro()
	{
		// recuperamos consulta original y si hay camposOrden los aplicamos
		camposFiltro = null;
		filtroAplicado=false;
		consultaSQL = consultaSQLOriginal;
		transformaSQLConCamposOrden();
	}


	// para reestablecer el orden de la consulta original (quitar orden de usuario)
	public void reestableceOrden()
	{
		// recuperamos consulta original y si hay camposFiltro los aplicamos
		camposOrden = null;
		ordenAplicado=false;
		consultaSQL = consultaSQLOriginal;
		transformaSQLConCamposFiltro();
	}

	// calculamos el literal a mostrar al usuario a partir del
	// campo camposFiltro (formato year=2012 and month=3 and sportType=4)
	public String getLiteralCamposFiltro()
	{
		String literal = "";
		int tmpPos=0;
		String tmpValor = "";
		String tmpDato = "";

		if (camposFiltro == null) return "";

		try
		{
			literal = camposFiltro;

			literal = literal.replaceAll("and", "\n");
			literal = literal.replaceAll("year", "Año");

			if (literal.contains("month"))
			{
				tmpPos = literal.indexOf("month");
				tmpValor = literal.substring(tmpPos + 6, tmpPos + 7);
				tmpDato = Utilidades.getNombreMes(Integer.valueOf(tmpValor).intValue());
				literal = literal.replaceAll("month=" + tmpValor, "Mes = " + tmpDato);
			}

			if (literal.contains("sportType"))
			{
				tmpPos = literal.indexOf("sportType");
				tmpValor = literal.substring(tmpPos + 10, tmpPos + 11);
				tmpDato = Utilidades.getSportType(Integer.valueOf(tmpValor).intValue());
				literal = literal.replaceAll("sportType=" + tmpValor, "Tipo de deporte = " + tmpDato);
			}

		}
		catch (Exception e)
		{
			literal = "ERROR: " + e.getMessage();
		}

		return literal;
	}

	// calculamos el literal a mostrar al usuario a partir del
	// campo camposOrden (formato order by c1 desc, c2 asc)
	public String getLiteralCamposOrden()
	{
		String literal = "";

		if (camposOrden == null) return "";

		try
		{
			literal = camposOrden;

			literal = literal.replaceAll(",", "\n");
			
			literal = literal.replaceAll("desc", RunStats.getAppContext().getString(R.string.order_desc) );
			literal = literal.replaceAll("asc", RunStats.getAppContext().getString(R.string.order_asc));

			if (literal.contains("c0"))
			{
				literal = literal.replaceAll("c0", titulosCols[0]);
			}
			if (literal.contains("c1"))
			{
				literal = literal.replaceAll("c1", titulosCols[1]);
			}
			if (literal.contains("c2"))
			{
				literal = literal.replaceAll("c2", titulosCols[2]);
			}
			if (literal.contains("c3"))
			{
				literal = literal.replaceAll("c3", titulosCols[3]);
			}
			if (literal.contains("c4"))
			{
				literal = literal.replaceAll("c4", titulosCols[4]);
			}
			if (literal.contains("c5"))
			{
				literal = literal.replaceAll("c5", titulosCols[5]);
			}
			if (literal.contains("c6"))
			{
				literal = literal.replaceAll("c6", titulosCols[6]);
			}
			if (literal.contains("c7"))
			{
				literal = literal.replaceAll("c7", titulosCols[7]);
			}
			if (literal.contains("c8"))
			{
				literal = literal.replaceAll("c8", titulosCols[8]);
			}
			if (literal.contains("c9"))
			{
				literal = 	literal.replaceAll("c9", titulosCols[9]);
			}
			if (literal.contains("cc10"))
			{
				literal = 	literal.replaceAll("cc10", titulosCols[10]);
			}
			if (literal.contains("cc11"))
			{
				literal = 	literal.replaceAll("cc11", titulosCols[11]);
			}
			if (literal.contains("cc12"))
			{
				literal = 	literal.replaceAll("cc12", titulosCols[12]);
			}
		}
		catch (Exception e)
		{
			literal = "ERROR: " + e.getMessage();
		}
		return literal;
	}

	
	public void estableceConsultaOriginal()
	{
		this.consultaSQLOriginal = this.consultaSQL;
	}
}


