package chavales.los.practica1android.actividad;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Calidad;
import chavales.los.practica1android.modelo.Nutricion;
import chavales.los.practica1android.modelo.Producto;
import chavales.los.practica1android.util.AdaptadorDeProductos;
import chavales.los.practica1android.util.SqliteParaUltimasVisualizaciones;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private AdaptadorDeProductos adaptadorDeProductos;
    private FirebaseDatabase db;
    private FirebaseStorage stg;

    /*pkg*/ static SqliteParaUltimasVisualizaciones sqlite;

    public static final int REQ_CODE_CAMARA = 123;
    public static final int REQ_CODE_INSPEC = 456;

    private List<Producto> productos = new ArrayList<>(43);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.recyclerView);
        lista.setAdapter(adaptadorDeProductos = new AdaptadorDeProductos(this, productos));
        lista.setLayoutManager(new LinearLayoutManager(this));

        boolean primeroRuneo = db == null;
        db = FirebaseDatabase.getInstance();
        if (primeroRuneo) db.setPersistenceEnabled(true);
        stg = FirebaseStorage.getInstance();

/*
        for (Producto p : productosA) {
            Resources resources = getResources();
            Uri uri = new Uri.Builder()
                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                    .authority(resources.getResourcePackageName(p.getRes()))
                    .appendPath(resources.getResourceTypeName(p.getRes()))
                    .appendPath(resources.getResourceEntryName(p.getRes()))
                    .build();

            final String nombreImagen = new StringBuilder(String.valueOf(Math.abs(uri.hashCode()))).append(String.valueOf(Calendar.getInstance().getTimeInMillis())).toString();

            stg.getReference("prods").child(nombreImagen).putFile(uri);

            p.setImagen(nombreImagen);

            p.subirAFirebase(this, db, stg);
        }
*/

        db.getReference("productos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!dataSnapshot.getKey().equals("num")) {
                    Producto p = Producto.recuperarDeFirebase(dataSnapshot, stg);
                    productos.add(p);
                    if (adaptadorDeProductos != null) adaptadorDeProductos.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println("CHILD CHANGED");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("CHILD REMOVED");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println("CHILD MOVED");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("CHILD CANCELLED");
            }
        });

        // abrir o crear la base de datos
       /* sqlite = new SqliteParaUltimasVisualizaciones(this, "bdvis", null, 4, productos.size());

        try (SQLiteDatabase db = sqlite.getReadableDatabase()) {
            Cursor cursor = db.rawQuery("select * from visualizacion where fecha is not null", null);
            while (cursor.moveToNext()) {
                productos.get(cursor.getInt(0)).setUltimaConsulta(cursor.getLong(1));
            }
        } */



        findViewById(R.id.fabAbrirCamara).setOnClickListener(v -> {
            Intent actividadCamara = new Intent(this, Camara.class);
            startActivity(actividadCamara);
        });

        findViewById(R.id.fabAniadirProducto).setOnClickListener(v -> {
            Intent actividadAniadir = new Intent(this, CrearProducto.class);
            startActivity(actividadAniadir);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_CAMARA:
                // Resultado de la cámara pero no queremos hacer nada
                break;
            case REQ_CODE_INSPEC:
                // Resultado de inspeccionar un producto, actualiza el timestamp de ultima vez visitado
                if (data != null) {
                    int i = data.getIntExtra("indice", -1);
                    if (i > -1) {
                      /*  long justoAhora = Calendar.getInstance().getTimeInMillis();
                        productos.get(i).setUltimaConsulta(justoAhora);
                        sqlite.getWritableDatabase()
                                .execSQL("update visualizacion set fecha=? where indiceProducto=?",
                                        new Object[]{justoAhora, i});
                        adaptadorDeProductos.notifyDataSetChanged(); */ // TODO sqliterss
                    }
                }
                break;
            default: break;
        }
    }
}