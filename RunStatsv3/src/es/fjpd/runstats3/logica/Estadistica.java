package es.fjpd.runstats3.logica;

public class Estadistica {

    // Título de la estadística
    String titulo="";
    // Descripción de la estadística
    String descripcion="";
    // Consulta SQL que genera la estadística
    String consultaSQL="";

    // Títulos que queremos para las columnas
    String[] titulosCols;


    // Tipos de datos para las columnas
    // El tipo especial Entero_sportType se usa para identificar esta columna
    // y poder convertirla a su nombre, ya que no están en tabla
    public enum Tipo_Columna {TC_Entero_sportType, TC_Entero, TC_Entero_Ms_Horas, TC_Entero_Ms_Hora, TC_Entero_Ms_Dia, TC_Entero_Mes, TC_Real, TC_Real_Metros, TC_Cadena};
    // Tipos de las columnas
    Tipo_Columna[] tiposCols;

	// de momento solo vamos a permitir order por un campo
	// esta variable tendra la forma "c1 asc"
	String camposOrden = null;
	
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

    public void setTitulo(String titulo) {
		this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
    }

    public void setConsultaSQL(String consultaSQL) {
		this.consultaSQL = consultaSQL;
		transformaSQLConCamposOrden();
    }

    public String getTitulo() {
		return titulo;
    }
    public String getConsultaSQL() {
		return consultaSQL;
    }
    public String getDescripcion() {
		return descripcion;
    }

    public String[] getTitulosCols() {
		return titulosCols;
    }

    public void setTitulosCols(String[] titulosCols) {
		this.titulosCols = titulosCols;
    }

    public Tipo_Columna[] getTiposCols() {
		return tiposCols;
    }

    public void setTiposCols(Tipo_Columna[] tiposCols) {
		this.tiposCols = tiposCols;
    }
	

	public void setCamposOrden(String camposOrden)
	{
		this.camposOrden = camposOrden;
		transformaSQLConCamposOrden();
	}

	public String getCamposOrden()
	{
		return camposOrden;
	}
	
	private void transformaSQLConCamposOrden()
	{
		if (camposOrden!=null && !consultaSQL.equals(""))
		{
			int posOrderBy=consultaSQL.indexOf("order by");
			if (posOrderBy==-1) posOrderBy=consultaSQL.length();
			consultaSQL=consultaSQL.substring(0, posOrderBy);
			consultaSQL+=" order by "+camposOrden;
		}
		
	}
	
}


