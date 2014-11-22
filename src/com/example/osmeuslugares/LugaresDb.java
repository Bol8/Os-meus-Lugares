package com.example.osmeuslugares;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LugaresDb extends SQLiteOpenHelper {

	private static String LOGTAG = "LugaresDb";
	private SQLiteDatabase db;
	private static String nombre = "lugares.db";
	private static CursorFactory factory = null;
	private static int version = 1;

	public LugaresDb(Context context) {
		super(context, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			db.execSQL(sqlCrearTablaLugar());

			String sql = "CREATE UNIQUE INDEX idx_lug_nombre ON Lugar(lug_nombre ASC)";
			db.execSQL(sql);

			db.execSQL(sqlCrearTablaCategoria());

			sql = "CREATE UNIQUE INDEX idx_cat_nombre ON Categoria(cat_nombre ASC)";
			db.execSQL(sql);

			// Insertar datos de prueba
			insertarLugaresPrueba();

		} catch (SQLException e) {
			Log.e(LOGTAG,"Error al crear las tablas");
		}

	}

	private String sqlCrearTablaCategoria() {
		String sql;
		sql = "CREATE TABLE Categoria("
				+ "cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat_nombre TEXT NOT NULL);";
		return sql;
	}

	private String sqlCrearTablaLugar() {
		String sql = "CREATE TABLE lugar("
				+ "lug_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "lug_nombre TEXT NOT NULL, "
				+ "lug_categoria_id INTEGER NOT NULL," + "lug_direccion TEXT,"
				+ "lug_ciudad TEXT," + "lug_telefono TEXT, " + "lug_url TEXT,"
				+ "lug_comentario TEXT);";
		return sql;
	}

	private void insertarLugaresPrueba() {
		try {
			db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Playas')");
			db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Restaurantes')");
			db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Hoteles')");
			db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Otros')");

			db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
					+ "VALUES('Praia de Riazor',1, 'Riazor','A Coru–a','981000000','Email@xxx','Playa de Coruña')");
			db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
					+ "VALUES('Praia do Orzan',1, 'Orzan','A Coru–a','981000000','Email@xxx','Playa de Coruña')");
			
			Log.i(LOGTAG,"Datos insertados correctamente");
			
		} catch (SQLException e) {
			Log.e(LOGTAG,"Error al insertar datos en las tablas");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Vector<Lugar> cargarLugaresDesdeBD() {
		Lugar lugar;
		Vector<Lugar> listaLugares = new Vector<Lugar>();
	
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db
					.rawQuery(
							"SELECT Lugar.*,cat_nombre "
									+ "FROM Lugar inner join Categoria on lug_categoria_id = cat_id",
							null);
			
			while (cursor.moveToNext()) {
				lugar = new Lugar();
				lugar.setId(cursor.getInt(cursor.getColumnIndex(Lugar.C_ID)));
				System.out.println("Asignar " + lugar.getId());
				lugar.setNombre(cursor.getString(cursor.getColumnIndex(Lugar.C_NOMBRE)));
				Long idCategoria = cursor.getLong(cursor.getColumnIndex(Lugar.C_CATEGORIA_ID));
				String nombreCategoria = cursor.getString(cursor.getColumnIndex(Categoria.C_NOMBRE));
				lugar.setCategoria(new Categoria(idCategoria, nombreCategoria));
				lugar.setDireccion(cursor.getString(cursor.getColumnIndex(Lugar.C_DIRECCION)));
				lugar.setCiudad(cursor.getString(cursor.getColumnIndex(Lugar.C_CIUDAD)));
				lugar.setTelefono(cursor.getString(cursor.getColumnIndex(Lugar.C_TELEFONO)));
				lugar.setUrl(cursor.getString(cursor.getColumnIndex(Lugar.C_URL)));
				lugar.setComentario(cursor.getString(cursor.getColumnIndex(Lugar.C_COMENTARIO)));
				listaLugares.add(lugar);
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error al cargar los datos");
			//e.printStackTrace();
		}
		
		return listaLugares;
	}

	public Vector<Categoria> cargarCategoriasDesdeBD() {
		Categoria categoria;
		Vector<Categoria> listaCategorias = new Vector<Categoria>();
		
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT * FROM Categoria ORDER By cat_id",
					null);
			while (cursor.moveToNext()) {
				categoria = new Categoria();
				categoria.setId(cursor.getLong(cursor.getColumnIndex(Categoria.C_ID)));
				categoria.setNombre(cursor.getString(cursor.getColumnIndex(Categoria.C_NOMBRE)));
				listaCategorias.add(categoria);
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error al cargar los datos Categoria");
			e.printStackTrace();
			
		}
		return listaCategorias;
	}
}
