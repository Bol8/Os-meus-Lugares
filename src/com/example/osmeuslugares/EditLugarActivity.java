package com.example.osmeuslugares;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditLugarActivity extends Activity {

	private Spinner spinnerTipo;
	private boolean añadir;
	private EditText editNombre;
	private Button btnEliminar;
	private Button btnGuardar;
	private EditText editDireccion;
	private EditText editTelefono;
	private EditText editUrl;
	private EditText editComentario;
	private Lugar editLugar;
	private CategoriaAdapter adaptador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);

		referenciarEditText();
		cargarCategoriaAdapter();
		if (añadir = mostrarDatos()) {
			btnEliminar.setVisibility(Button.INVISIBLE);
		}else {
			btnGuardar.setText(R.string.botonEditar);
		}

	}

	/**
	 * -Método que llama a [cargarDatosEnCampos]. -Mostramos los datos en los
	 * campos si el bundle trae un "Add" = false y si no dejamos los campos
	 * vacios. Devolvemos el valor del bundle en esa etiqueta
	 * 
	 * @return boolean
	 */
	private boolean mostrarDatos() {
		Bundle infoLugar = new Bundle();
		infoLugar = getIntent().getExtras();

		if (infoLugar.getBoolean("Add")) {
			Toast.makeText(this, "Modo Añadir", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			Toast.makeText(this, "Modo Editar", Toast.LENGTH_SHORT).show();
			cargarDatosEnCampos(infoLugar);
			return false;
		}
	}

	/**
	 * -Desde aquí se llama al método "guardarDAtosEnTabla" o
	 * "EditarDatosEnTabla" dependiendo del valor añadir. 
	 * -Recibe como parámetro un View que será una referencia al botón
	 * 
	 * @param v
	 */
	public void botonGuardar(View v) {

		if (añadir) {
			guardarDatosEnTabla();
		} else {
			editarDatosEnTabla();
		}
	}

	/**
	 * -Método asiciado al botón eliminar.
	 * 
	 * @param v
	 */
	public void botonEliminar(View v) {
		eliminarDatosEnTabla();
	}

	/**
	 * -Método que llama al método "editarLugar" de la clase "TablaLugar".
	 * -Donde se hará la actualización del objeto que se le pasa como parámetro.
	 */
	private void editarDatosEnTabla() {
		TablaLugar tb = new TablaLugar(this);

		if (tb.editarLugar(crearLugar())) {
			Toast.makeText(this, "Se han modificado los datos correctamente",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "No se pudieron modificar los datos",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * -Método que llama al método "guardarLugar" de la clase "TablaLugar".
	 * -Donde se guardarán los datos en la BD, creando un nuevo lugar.
	 */
	private void guardarDatosEnTabla() {
		TablaLugar tb = new TablaLugar(this);

		if (tb.guardarLugar(crearLugar())) {
			Toast.makeText(this, "Se han guardado los datos correctamente",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Ha habido un error al guardar los datos",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * -Método que llama al método "eliminarLugar" de la clase "TablaLugar".
	 * -Donde se eliminará el registro que se le pasa.
	 */
	private void eliminarDatosEnTabla() {
		TablaLugar tb = new TablaLugar(this);

		if (tb.eliminarLugar(crearLugar())) {
			Toast.makeText(this, "Se ha eliminado el registro correctamente",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "No se ha podido eliminar el registro",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * -Método que crea un lugar a partir de los datos que se introduzcan en los
	 * campos o de datos ya existentes
	 * 
	 * @return lugar
	 */
	private Lugar crearLugar() {
		Lugar lugar = new Lugar();
		int position = spinnerTipo.getSelectedItemPosition();

		if (!añadir) {
			lugar.setId(editLugar.getId());
		}
		lugar.setNombre(editNombre.getText().toString());
		lugar.setCategoria(adaptador.getItem(position));
		lugar.setDireccion(editDireccion.getText().toString());
		lugar.setTelefono(editTelefono.getText().toString());
		lugar.setUrl(editUrl.getText().toString());
		lugar.setComentario(editComentario.getText().toString());

		return lugar;

	}

	/**
	 * Cargamos el spinner con el adaptador de "CategoriaAdapter"
	 */
	private void cargarCategoriaAdapter() {

		spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
		adaptador = new CategoriaAdapter(this);
		spinnerTipo.setAdapter(adaptador);
	}

	/**
	 * -Método llamado por [mostrarDatos] 
	 * -Método que cargar la info. del lugar
	 * en los campos. 
	 * -Recibe como parámetro un Bundle.
	 * 
	 * @param info
	 */
	private void cargarDatosEnCampos(Bundle info) {
		editLugar = new Lugar();
		int position;
		editLugar.setBundle(info);

		position = adaptador.getPositionById(editLugar.getCategoria().getId());

		spinnerTipo.setSelection(position);
		editNombre.setText(editLugar.getNombre());
		editDireccion.setText(editLugar.getDireccion());
		editTelefono.setText(editLugar.getTelefono());
		editUrl.setText(editLugar.getUrl());
		editComentario.setText(editLugar.getComentario());

	}

	/**
	 * -Referenciamos los campos con los xmls para poder trabajar con ellos.
	 */
	private void referenciarEditText() {

		editNombre = (EditText) findViewById(R.id.editNombre);
		editDireccion = (EditText) findViewById(R.id.editDireccion);
		editTelefono = (EditText) findViewById(R.id.editTelefono);
		editUrl = (EditText) findViewById(R.id.editURL);
		editComentario = (EditText) findViewById(R.id.editComentario);
		btnEliminar = (Button) findViewById(R.id.buttonEliminar);
		btnGuardar = (Button) findViewById(R.id.buttonGuardar);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
