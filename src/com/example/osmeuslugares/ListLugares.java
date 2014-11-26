package com.example.osmeuslugares;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListLugares extends ListActivity {
	private ListLugaresAdapter listLugaresAdapter;
	private Button btnAñadir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);
		btnAñadir = (Button) findViewById(R.id.buttonGuardar);
		
		listLugaresAdapter = new ListLugaresAdapter(this);
		setListAdapter(listLugaresAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {	
		super.onListItemClick(l, v, position, id);
		
		Lugar lugar = (Lugar) getListAdapter().getItem(position);
		int lug_id = lugar.getId();
		Bundle datosLugar = lugar.getBundle();
		datosLugar.putBoolean("Add", false);
		iniciarEditLugarAdd(datosLugar);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_lugares, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.edit_lugares:
			iniciarEditLugarAdd();
			break;
			
		case R.id.action_settings:
			lanzarPreferencias();
			break;

		default:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void iniciarEditLugarAdd() {
		Intent i = new Intent(this, EditLugarActivity.class);
		i.putExtra("Add", true);
		startActivity(i);
	}
	
	private void iniciarEditLugarAdd(Bundle extras) {
		Intent i = new Intent(this, EditLugarActivity.class);
		i.putExtras(extras);
		startActivity(i);
	}
	
	private void lanzarPreferencias() {
		Intent i = new Intent(this, PreferenciasActivity.class);
		startActivity(i);
	}
	
	public void añadir(View v) {
		iniciarEditLugarAdd();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		listLugaresAdapter.actualizarDesdeDB();
		listLugaresAdapter.notifyDataSetChanged();
		
	}
	

	
	
}
