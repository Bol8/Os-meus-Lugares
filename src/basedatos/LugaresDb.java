package basedatos;

import java.util.ArrayList;
import java.util.Vector;

import lugar.Lugar;

import categoria.Categoria;

import com.example.osmeuslugares.R;

import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
	private static int version = 3;
	private Resources res;
	private TypedArray listaIconos;
	private static String sql;

	public LugaresDb(Context context) {
		super(context, nombre, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {

			crearTablaLugar(db);

			crearTablaCategoria(db);

			// Insertar datos de prueba
			insertarLugaresPrueba();

		} catch (SQLException e) {
			Log.e(LOGTAG, "Error al crear las tablas");
		}

	}

	private void crearTablaCategoria(SQLiteDatabase db) {
		sql = "CREATE TABLE Categoria("
				+ "cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat_nombre TEXT NOT NULL, " + "cat_icono TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_cat_nombre ON Categoria(cat_nombre ASC)";
		db.execSQL(sql);
	}

	private void crearTablaLugar(SQLiteDatabase db) {
		sql = "CREATE TABLE lugar("
				+ "lug_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "lug_nombre TEXT NOT NULL, "
				+ "lug_categoria_id INTEGER NOT NULL," + "lug_direccion TEXT,"
				+ "lug_ciudad TEXT," + "lug_telefono TEXT, " + "lug_url TEXT,"
				+ "lug_comentario TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_lug_nombre ON Lugar(lug_nombre ASC)";
		db.execSQL(sql);
	}

	private void insertarLugaresPrueba() {
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('PLAYAS','cartelplaya')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('RESTAURANTES','cartelrestaurante')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('HOTELES','iconhotel')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('OTROS','iconhospital')");

		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia de Riazor',1, 'Riazor','A Coruña','981000000','www.praiariazor.com','Playa con mucho oleaje.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia do Orzan',1, 'Orzan','A Coruña','981000000','www.praiaorzan.com','Preciosas vistas a las discotecas...')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Mesón Suso',2, 'Av de Madrid,6','Lugo','981000000','www.mesonsuso.com','Un buen sitio para comer carne a la brasa.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Hotel Paraiso',3, 'Av Buenos Aires,10','Vigo','986123456','www.hparaiso.com','Un lugar perfecto para dormir una siesta.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Club El Descanso',4, 'Carretera de Orense, s/n','Ourense','988000000','www.clubdescanso.com','Un lugar muy acojedor...')");
		Log.i("INFO", "Registros de prueba insertados");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("INFO", "Base de datos: onUpgrade" + oldVersion + "->"
				+ newVersion);
		if (newVersion > oldVersion) {
			try {
				db.execSQL("DROP TABLE IF EXISTS lugar");
				db.execSQL("DROP INDEX IF EXISTS idx_lug_nombre");
				db.execSQL("DROP TABLE IF EXISTS categoria");
				db.execSQL("DROP INDEX IF EXISTS idx_cat_nombre");
			} catch (Exception e) {
				Log.e(this.getClass().toString(), e.getMessage());
			}
			onCreate(db);

			Log.i(this.getClass().toString(),
					"Base de datos actualizada. versión 2");
		}

	}

	public Vector<Lugar> cargarLugaresDesdeBD() {
		Lugar lugar;
		Vector<Lugar> listaLugares = new Vector<Lugar>();

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db
					.rawQuery(
							"SELECT Lugar.*, cat_nombre, cat_icono "
									+ "FROM Lugar join Categoria on lug_categoria_id = cat_id",
							null);

			while (cursor.moveToNext()) {
				lugar = new Lugar();
				lugar.setId(cursor.getInt(cursor.getColumnIndex(Lugar.C_ID)));
				// System.out.println("Asignar "+cursor.getInt(cursor.getColumnIndex(Lugar.C_ID)));
				lugar.setNombre(cursor.getString(cursor
						.getColumnIndex(Lugar.C_NOMBRE)));
				int idCategoria = cursor.getInt(cursor
						.getColumnIndex(Lugar.C_CATEGORIA_ID));
				String nombreCategoria = cursor.getString(cursor
						.getColumnIndex(Categoria.C_NOMBRE));
				String iconoCategoria = cursor.getString(cursor
						.getColumnIndex(Categoria.C_ICONO));
				lugar.setCategoria(new Categoria(idCategoria, nombreCategoria,
						iconoCategoria));
				lugar.setDireccion(cursor.getString(cursor
						.getColumnIndex(Lugar.C_DIRECCION)));
				lugar.setCiudad(cursor.getString(cursor
						.getColumnIndex(Lugar.C_CIUDAD)));
				lugar.setTelefono(cursor.getString(cursor
						.getColumnIndex(Lugar.C_TELEFONO)));
				lugar.setUrl(cursor.getString(cursor
						.getColumnIndex(Lugar.C_URL)));
				lugar.setComentario(cursor.getString(cursor
						.getColumnIndex(Lugar.C_COMENTARIO)));
				listaLugares.add(lugar);
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error al cargar los datos lugares");
			// e.printStackTrace();
		}

		return listaLugares;
	}

	public Vector<Categoria> cargarCategoriasDesdeBD() {
		Categoria categoria;
		Vector<Categoria> listaCategorias = new Vector<Categoria>();

		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					"SELECT * FROM Categoria ORDER By cat_id", null);
			
			listaCategorias.add(new Categoria(0, "Seleccionar...", "icono_nd"));
			
			while (cursor.moveToNext()) {
				categoria = new Categoria();
				categoria.setId(cursor.getInt(cursor
						.getColumnIndex(Categoria.C_ID)));
				categoria.setNombre(cursor.getString(cursor
						.getColumnIndex(Categoria.C_NOMBRE)));
				categoria.setIcono(cursor.getString(cursor
						.getColumnIndex(Categoria.C_ICONO)));
				listaCategorias.add(categoria);
			}
		} catch (Exception e) {
			Log.e(LOGTAG, "Error al cargar los datos Categoria");
			e.printStackTrace();

		}
		return listaCategorias;
	}

	public TypedArray cargarIconos(Activity activity) {
		res = activity.getResources();
		listaIconos = res.obtainTypedArray(R.array.iconos_categorias);

		return listaIconos;
	}

}
