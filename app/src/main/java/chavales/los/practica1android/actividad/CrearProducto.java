package chavales.los.practica1android.actividad;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Calidad;
import chavales.los.practica1android.modelo.Nutricion;
import chavales.los.practica1android.modelo.Producto;

public class CrearProducto extends AppCompatActivity {

    private static final int REQ_IMAGEN = 285, REQ_PERMS_IMG = 383;

    private FirebaseDatabase db;
    private FirebaseStorage stg;

    private EditText nombre, marca;
    private TextView notaNum;
    private SeekBar nota;
    private ImageButton botonAniadirDetalle;

    private Button botonBuscarImagen;
    private Uri imagen;

    private Button botonConfirmar;

    private static final List<Calidad> CALIDADES = Collections.unmodifiableList(Arrays.asList(Calidad.values()));

    // Los detalles que aun no se han añadido
    private final List<Nutricion> detalles_disponibles = new ArrayList<>(Arrays.asList(Nutricion.values()));
    // Los detalles que sí se han añadido, con el ID de la vista como clave
    private final SortedMap<Integer, Nutricion> detalles_aniadidos = new TreeMap<>();

    private GoogleMap mapa;
    private MapView mapView;
    private List<Marker> todos = new ArrayList<>();
    private Marker markerSelec = null;
    private EditText markerNombre;
    private FloatingActionButton fabEliminarMarker;

    private ScrollView sv;
    private LinearLayout layoutDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        db = FirebaseDatabase.getInstance();
        stg = FirebaseStorage.getInstance();

        nombre = findViewById(R.id.etNombreProducto);
        marca = findViewById(R.id.etMarcaProducto);

        mapView = findViewById(R.id.googleMapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);
        markerNombre = findViewById(R.id.etNombreMarker);
        fabEliminarMarker = findViewById(R.id.fabEliminarMarker);
        fabEliminarMarker.setOnClickListener(v -> {
            if (markerSelec != null && mapa != null) {
                markerSelec.remove();
                markerNombre.setText("");
            }
        });

        botonBuscarImagen = findViewById(R.id.botonBuscarImagen);
        botonBuscarImagen.setOnClickListener(v -> {

            try {
                if (ActivityCompat.checkSelfPermission(CrearProducto.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CrearProducto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMS_IMG);
                } else {
                    intentGaleria();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        botonConfirmar = findViewById(R.id.botonConfirmar);
        botonConfirmar.setOnClickListener(this::comprobarYCrear);

        notaNum = findViewById(R.id.tvNotaEnNumero);
        nota = findViewById(R.id.sbNotaProducto);
        nota.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String texto = Integer.toString(progress);
                if (progress <= 20) texto += " 🤢";
                else if (progress <= 40) texto += " 😰";
                else if (progress <= 60) texto += " 😐";
                else if (progress <= 80) texto += " 😊";
                else texto += " 😄";

                notaNum.setText(texto);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        layoutDetalles = findViewById(R.id.layoutDetalles);
        botonAniadirDetalle = findViewById(R.id.botonAnadirDetalle);
        botonAniadirDetalle.setOnClickListener(
                new View.OnClickListener() {

                    private int numVistasAniadidas = 0;

                    private Spinner desplegableDetalles;
                    private Spinner desplegableCalidad;
                    private EditText edContiene;
                    private EditText edNumero;
                    private TextView nombreDetalle;
                    private TextView unidadDetalle;

                    private void setearCampos(int id) {
                        Nutricion seleccionado = detalles_aniadidos.get(id);
                        findViewById(id).findViewById(R.id.desplegableDetalles).setBackgroundResource(seleccionado.getImg());
                        ((TextView) findViewById(id).findViewById(R.id.tvNombreDetalle)).setText(seleccionado.getNombreUd());
                        ((TextView) findViewById(id).findViewById(R.id.tvUnidadDetalle)).setText(seleccionado.getUdMedicion());

                        //desplegableDetalles.setBackgroundResource(seleccionado.getImg());
                        //nombreDetalle.setText(seleccionado.getNombreUd());
                        //unidadDetalle.setText(seleccionado.getUdMedicion());
                    }

                    @Override
                    public void onClick(View v) {

                        if (detalles_disponibles.size() == 1) {
                            botonAniadirDetalle.setVisibility(View.GONE);
                        }

                        View item = getLayoutInflater().inflate(R.layout.item_detalle_crear_producto, null);
                        item.setId(++numVistasAniadidas);

                        desplegableDetalles = item.findViewById(R.id.desplegableDetalles);
                        desplegableCalidad = item.findViewById(R.id.desplegableCalidad);
                        edContiene = item.findViewById(R.id.edContiene);
                        edNumero = item.findViewById(R.id.edNumero);
                        nombreDetalle = item.findViewById(R.id.tvNombreDetalle);
                        unidadDetalle = item.findViewById(R.id.tvUnidadDetalle);

                        item.findViewById(R.id.botonEliminarDetalle).setOnClickListener(w -> {
                            layoutDetalles.removeView(item);
                            detalles_disponibles.add(detalles_aniadidos.remove(item.getId()));
                            --numVistasAniadidas;
                            botonAniadirDetalle.setVisibility(View.VISIBLE);
                        });


                        desplegableDetalles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                View vistaDetalle = (View) view.getParent().getParent();

                                int idVistaDetalle = vistaDetalle.getId();

                                Nutricion temp = detalles_disponibles.remove(pos);

                                Nutricion anterior = detalles_aniadidos.put(idVistaDetalle, temp);
                                if (anterior != null) {
                                    detalles_disponibles.add(anterior);
                                }

                                setearCampos(idVistaDetalle);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                int count = parent.getCount();
                            }
                        });

                        ArrayAdapter<Nutricion> detalleAdapter = new ArrayAdapter<>(CrearProducto.this, android.R.layout.simple_spinner_item, detalles_disponibles);
                        detalleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        desplegableDetalles.setAdapter(detalleAdapter);

                        ArrayAdapter<Calidad> calidadAdapter = new ArrayAdapter<>(CrearProducto.this, android.R.layout.simple_spinner_item, CALIDADES);
                        calidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        desplegableCalidad.setAdapter(calidadAdapter);

                        edContiene.setSelectAllOnFocus(true);
                        edNumero.setSelectAllOnFocus(true);

                        layoutDetalles.addView(item, layoutDetalles.getChildCount() - 1);
                    }
                });
    }

    private void intentGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Imagen para el producto"), REQ_IMAGEN);
    }

    private void comprobarYCrear(View view) {

        String nom = nombre.getText().toString();
        if (nom.equals("")) {
            nombre.requestFocus();
            nombre.setError("El nombre no puede estar vacío.");
            return;
        }

        String img;
        if (imagen != null) {
            img = String.valueOf(Math.abs(imagen.hashCode())) + String.valueOf(Calendar.getInstance().getTimeInMillis());
            //StorageReference imgs = storageRef.child("prods/" + imagen.hashCode() + Calendar.getInstance().getTimeInMillis());
            //imgs.putFile(imagen);
        } else {
            botonBuscarImagen.requestFocus();
            botonBuscarImagen.setError("Se necesita una imagen que subir.");
            return;
        }

        String mrc = marca.getText().toString();
        if (mrc.equals("")) {
            marca.requestFocus();
            marca.setError("La marca no puede estar vacía");
            return;
        }

        int not = nota.getProgress();

        List<Producto.Detalle> detalles = new ArrayList<>();
        detalles_aniadidos.forEach((i, n) -> {
            View item = findViewById(i);
            EditText edContiene = item.findViewById(R.id.edContiene);
            EditText edNumero = item.findViewById(R.id.edNumero);
            Spinner calidad = item.findViewById(R.id.desplegableCalidad);

            String bajoAltoEn = edContiene.getText().toString();
            if (bajoAltoEn.equals("")) return;

            float num = 0f;
            try {
                num = Float.parseFloat(edNumero.getText().toString());
            } catch (NumberFormatException e) {
                return;
            }

            Calidad cal = Calidad.valueOf(calidad.getSelectedItem().toString());

            Producto.Detalle d = new Producto.Detalle(bajoAltoEn, n, num, cal);
            detalles.add(d);
        });

        if (!markerNombre.getText().toString().equals("")) {
            markerSelec.setTitle(markerNombre.getText().toString());
        }
        List<MarkerOptions> ubi = new ArrayList<>();
        for(Marker m : todos) {
            ubi.add(new MarkerOptions().position(m.getPosition()).title(m.getTitle()));
        }

        // en principio tooodo ok? subir movidas

        Producto prod = new Producto(nom, mrc, img, Calidad.deNota(not), null, not, detalles, null, ubi);

        stg.getReference("prods").child(img).putFile(imagen);

        prod.subirAFirebase(this, db, stg);

        Toast.makeText(this, "Subiendo producto al internet...", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_IMAGEN && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "No se ha podido abrir la imagen", Toast.LENGTH_SHORT).show();
            } else {
                imagen = data.getData();

                Cursor returnCursor = getContentResolver().query(imagen, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                String name = returnCursor.getString(nameIndex);
                returnCursor.close();

                if (name.length() > 20) {
                    name = name.substring(name.length() - 20);
                }

                botonBuscarImagen.setText(" ... " + name);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMS_IMG:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentGaleria();
                } else {
                    Toast.makeText(this, "Para añadir una imagen es necesario acceder a la galería", Toast.LENGTH_LONG).show();
                    finish();
                }
            default: return;
        }
    }

    private void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);

        mapa.setOnMapLongClickListener(this::onMapLongClick);
        mapa.setOnMarkerClickListener(this::onMarkerClick);

        mapa.setOnCameraMoveStartedListener(
            i -> mapView.getParent().requestDisallowInterceptTouchEvent(true)
        );

        mapa.setOnCameraIdleListener(
            () -> mapView.getParent().requestDisallowInterceptTouchEvent(false)
        );
    }

    private void guardarMarkerActual() {
        String texto = markerNombre.getText().toString();
        if (markerSelec != null && texto != null && !texto.equals("")) {
            markerSelec.setTitle(texto);
        }
    }

    private boolean onMarkerClick(Marker markerNuevo) {
        guardarMarkerActual();

        markerSelec = markerNuevo;
        String markerTitle = markerNuevo.getTitle();
        if (markerTitle != null && !markerTitle.equals("")) {
            markerNombre.clearFocus();
        }
        markerNombre.setText(markerTitle);
        markerNuevo.showInfoWindow();
        return true;
    }

    private void onMapLongClick(LatLng latLng) {
        guardarMarkerActual();
        markerSelec = mapa.addMarker(new MarkerOptions().position(latLng));
        todos.add(markerSelec);
        markerNombre.setText(markerSelec.getTitle());
        markerNombre.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
}