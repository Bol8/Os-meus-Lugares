package vistas;

import java.io.IOException;

import lugar.ListLugaresActivity;


import basedatos.LugaresDb;

import com.example.osmeuslugares.R;
import com.example.osmeuslugares.R.id;
import com.example.osmeuslugares.R.layout;
import com.example.osmeuslugares.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageButton imgButon;
	MediaPlayer musica;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("On Create");

		// TEST Crear BBDD
		try {
			LugaresDb lugaresDb = new LugaresDb(getBaseContext());

		} catch (Exception e) {
			Log.e(getClass().toString(), e.getMessage());
		}
		
	}

	private void leerPreferenciaMusica() {
		/* Leer preferencia de música */
		boolean reproducirMusica = getPreferenciaMusica();
		if (reproducirMusica) {
			musica.start();
		} else {
			musica.stop();
		}
	}

	public boolean getPreferenciaMusica() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("musica", false);

	}

	@Override
	protected void onStart() {
		System.out.println("On Start");
		if(musica == null) {
			this.musica = MediaPlayer.create(this, R.raw.musica_fondo);	
		}
		leerPreferenciaMusica();
		super.onStart();
	}

	//
	// @Override
	// protected void onRestart() {
	// System.out.println("On ReStart");
	// leerPreferenciaMusica();
	// super.onRestart();
	// }
	//
	@Override
	protected void onDestroy() {
		System.out.println("On Destroy");
		musica.stop();
		super.onDestroy();
	}

	
//	 @Override
//	 protected void onResume() {
//	 System.out.println("On Resume");
//	 leerPreferenciaMusica();
//	 super.onResume();
//	 }

	public void pulsaBoton(View v) {
		lanzarListadoLugares();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_settings: {
			lanzarPreferencias();
			break;
		}

		case R.id.acerca_de: {
			lanzarAcercaDe();
			break;
		}

		}

		return super.onOptionsItemSelected(item);
	}

	private void lanzarListadoLugares() {
		Intent i = new Intent(this, ListLugaresActivity.class);
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
