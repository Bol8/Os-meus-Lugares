package com.example.osmeuslugares;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TEST Crear BBDD
		try {
			LugaresDb lugaresDb = new LugaresDb(getBaseContext());
			
			Toast.makeText(getBaseContext(), "Base de datos preparada",
					Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			Log.e(getClass().toString(), e.getMessage());
		}

		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.listLugares: {
			lanzarListadoLugares();
			break;
		}

		case R.id.action_settings: {
			lanzarPreferencias();
			break;
		}

		case R.id.acerca_de: {
			//Toast.makeText(this, "Acerca De", Toast.LENGTH_SHORT).show();
			 lanzarAcercaDe();
			break;
		}

		case R.id.salir: {
			finish();
			break;
		}
		}

		return super.onOptionsItemSelected(item);
	}

	private void lanzarListadoLugares() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, ListLugares.class);
		startActivity(i);

	}

	private void lanzarPreferencias() {
		Intent i = new Intent(this, PreferenciasActivity.class);
		startActivity(i);
	}
	
	private void lanzarAcercaDe() {
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);
	}
}
