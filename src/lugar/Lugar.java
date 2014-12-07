package lugar;

import categoria.Categoria;
import android.content.ContentValues;
import android.os.Bundle;


public class Lugar {
	
	private int id;
	private String nombre;
	private Categoria categoria;
	private String direccion;
	private String ciudad;
	private String url;
	private String telefono;
	private String comentario;
	
	//Mapeo para recuperar los datos de la BD
	public static final String C_ID ="lug_id";
	public static final String C_NOMBRE ="lug_nombre";
	public static final String C_CATEGORIA_ID ="lug_categoria_id";
	public static final String C_DIRECCION ="lug_direccion";
	public static final String C_CIUDAD ="lug_ciudad";
	public static final String C_TELEFONO ="lug_telefono";
	public static final String C_URL ="lug_url";
	public static final String C_COMENTARIO ="lug_comentario";
	
	
	
	public Lugar() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + "\nCategoria: "
				+ categoria.getNombre() + "\nDireccion: " + direccion + "\nCiudad: " + ciudad
				+ "\nURL: " + url + "\nTelefono: " + telefono + "\nComentario: "
				+ comentario;
	}
	
	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		
		
		bundle.putString(C_NOMBRE, nombre);
		bundle.putLong(C_CATEGORIA_ID, categoria.getId());
		bundle.putString(Categoria.C_NOMBRE, categoria.getNombre());
		bundle.putString(C_DIRECCION, direccion);
		bundle.putString(C_CIUDAD,ciudad);
		bundle.putString(C_TELEFONO,telefono);
		bundle.putString(C_URL,url);
		bundle.putString(C_COMENTARIO,comentario);
		bundle.putInt(C_ID, id);
				
		return bundle;
		
	}
	
	public void setBundle(Bundle bundle) {

		
		nombre=bundle.getString(C_NOMBRE);		
		categoria=new Categoria(bundle.getInt(C_CATEGORIA_ID),
				bundle.getString(Categoria.C_NOMBRE),bundle.getString(Categoria.C_ICONO));
		direccion=bundle.getString(C_DIRECCION);
		ciudad=bundle.getString(C_CIUDAD);
		telefono=bundle.getString(C_TELEFONO);
		url = bundle.getString(C_URL);
		comentario=bundle.getString(C_COMENTARIO);
		id = bundle.getInt(C_ID);
	}
	
	public ContentValues getContentValues() {
		ContentValues registro = new ContentValues();
		
		registro.put(C_NOMBRE, nombre);
		registro.put(C_CATEGORIA_ID, categoria.getId());
		registro.put(C_DIRECCION, direccion);
		registro.put(C_CIUDAD, ciudad);
		registro.put(C_TELEFONO, telefono);
		registro.put(C_URL, url);
		registro.put(C_COMENTARIO, comentario);
		
		return registro;
	}
	
	





	
	
	
}
