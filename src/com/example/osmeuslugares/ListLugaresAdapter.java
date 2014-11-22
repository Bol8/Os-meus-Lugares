package com.example.osmeuslugares;

import java.util.Vector;

import android.app.Activity;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListLugaresAdapter extends BaseAdapter {

	private final Activity activity;
	private Vector<Lugar> lista;
	private LugaresDb lugaresDb;

	/**
	 * @param activity
	 * @param lista
	 */
	public ListLugaresAdapter(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Lugar>();
		actualizarDesdeDB();
	}

	public void actualizarDesdeDB() throws SQLException{
		lugaresDb = new LugaresDb(activity);
		this.lista = lugaresDb.cargarLugaresDesdeBD();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista, null, true);
		
		cargaDatosEnCampos(position, view);

		return view;
	}

	private void cargaDatosEnCampos(int position, View view) {
		TextView textViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
		TextView txtNombre = (TextView) view.findViewById(R.id.textView2);
		TextView txtLugar = (TextView) view.findViewById(R.id.textView1);
		TextView txtDireccion = (TextView) view.findViewById(R.id.textView3);
		TextView txtCiudad = (TextView) view.findViewById(R.id.textView4);
		TextView txtUrl = (TextView) view.findViewById(R.id.textView5);
		TextView txtTelf = (TextView) view.findViewById(R.id.textView7);
		TextView txtComent = (TextView) view.findViewById(R.id.textView9);
		
		Lugar lugar = (Lugar)lista.elementAt(position);
		
		textViewTitulo.setText(lugar.getNombre());
		txtNombre.setText(lugar.getNombre());
		txtLugar.setText(lugar.getCategoria().getNombre());
		txtDireccion.setText(lugar.getDireccion());
		txtCiudad.setText(lugar.getCiudad());
		txtUrl.setText(lugar.getUrl());
		txtTelf.setText(lugar.getTelefono());
		txtComent.setText(lugar.getComentario());
		
	}
}
