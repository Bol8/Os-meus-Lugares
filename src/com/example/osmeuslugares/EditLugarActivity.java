package com.example.osmeuslugares;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EditLugarActivity extends Activity {

	private Spinner spinnerTipo;
	boolean a�adir;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);		
		spinnerTipo = (Spinner)findViewById(R.id.spinnerTipo);
		a�adir = getIntent().getBooleanExtra("A�adir",false);
		
		if(a�adir) {
			Toast.makeText(this,"Modo A�adir", Toast.LENGTH_SHORT).show();
		}
		//String []listaTipos = {"Praias", "Restaurantes", "Outros"};
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		//		this, android.R.layout.simple_spinner_item,
		//		listaTipos);
		//spinnerTipo.setAdapter(adapter);
		
		
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
