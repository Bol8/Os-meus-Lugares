package lugar;


import basedatos.TablaLugar;
import categoria.CategoriaAdapter;
import categoria.EditCategoriaActivity;

import com.example.osmeuslugares.R;
import com.example.osmeuslugares.R.id;
import com.example.osmeuslugares.R.layout;
import com.example.osmeuslugares.R.menu;
import com.example.osmeuslugares.R.string;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EditLugarActivity extends Activity {

	private Spinner spinnerTipo;
	private boolean añadir;
	private EditText editNombre;
	private EditText editDireccion;
	private EditText editTelefono;
	private EditText editUrl;
	private EditText editComentario;
	private Lugar editLugar;
	private CategoriaAdapter adaptador;
	//private ImageButton btnImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);

		referenciarEditText();
		cargarCategoriaAdapter();
		añadir = mostrarDatos();
		//btnImg = (ImageButton) findViewById(R.id.imageButton1);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
			Toast.makeText(this, "No se han podido guardar los datos",
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

		spinnerTipo = (Spinner) findViewById(R.id.spinnerCategoria);
		adaptador = new CategoriaAdapter(this);
		spinnerTipo.setAdapter(adaptador);
	}

	/**
	 * -Método llamado por [mostrarDatos] -Método que cargar la info. del lugar
	 * en los campos. -Recibe como parámetro un Bundle.
	 * 
	 * @param info
	 */
	private void cargarDatosEnCampos(Bundle info) {
		editLugar = new Lugar();
		int position;
		editLugar.setBundle(info);

		// System.out.println(editLugar.getNombre());

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
		editDireccion = (EditText) findViewById(R.id.editTexNombreCategoria);
		editTelefono = (EditText) findViewById(R.id.editTextTel);
		editUrl = (EditText) findViewById(R.id.editTextURL);
		editComentario = (EditText) findViewById(R.id.editTexTComentario);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.guardarLugar:
			if (añadir) {
				guardarDatosEnTabla();
			} else {
				editarDatosEnTabla();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void pulsaImageView(View v) {
		lanzarEditCategoria();
	}
	
	private void lanzarEditCategoria() {
		Intent i = new Intent(this,EditCategoriaActivity.class);
		startActivity(i);
		
	}

}


