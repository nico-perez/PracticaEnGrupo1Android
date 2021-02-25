package chavales.los.practica1android.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteParaUltimasVisualizaciones extends SQLiteOpenHelper {

    private int capacidad;

    public SqliteParaUltimasVisualizaciones(Context contexto, String nombre,
                                            SQLiteDatabase.CursorFactory factory, int version, int capacidadInicial) {
        super(contexto, nombre, factory, version);
        this.capacidad = capacidadInicial;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table visualizacion(indiceProducto integer primary key, fecha integer)");
        db.beginTransaction();
        try {
            // al crear la tabla, la llena de nulls para hacernos más fácil la vida
            for (int i = 0; i < capacidad; ++i) {
                db.execSQL("insert into visualizacion values (?, null)", new Object[]{i});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists visualizacion");
        onCreate(db);
    }
}
