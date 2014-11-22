package com.example.osmeuslugares;

public class Categoria {
	private Long id;
	private String nombre;
	// private String icon;

	// Mapeo para base de datos
	public final static String C_NOMBRE = "cat_nombre";
	public final static String C_ID = "cat_id";

	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param nombre
	 */
	public Categoria(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the Nombre to set
	 */
	public void setCategoria(String nombre) {
		this.nombre = nombre;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + "]";
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Categoria) {
			Categoria temp = (Categoria) obj;

			if (this.getId() == temp.getId()) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

}
