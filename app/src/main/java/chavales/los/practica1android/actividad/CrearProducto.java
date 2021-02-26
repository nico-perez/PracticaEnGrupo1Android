package chavales.los.practica1android.actividad;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import chavales.los.practica1android.R;
import chavales.los.practica1android.modelo.Calidad;
import chavales.los.practica1android.modelo.Nutricion;

public class CrearProducto extends AppCompatActivity {

    private FirebaseDatabase db;

    private EditText nombre, marca;
    private TextView notaNum;
    private SeekBar nota;

    private static final List<Calidad> CALIDADES = Collections.unmodifiableList(Arrays.asList(Calidad.values()));

    // TODO usar Set en vez de List
    private final List<Nutricion> detalles_disponibles = new ArrayList<Nutricion>(Arrays.asList(Nutricion.values()));
    private final List<Nutricion> detalles_aniadidos = new ArrayList<>();

    private GoogleMap mapa;
    private MapView mapView;

    private ScrollView sv;
    private LinearLayout layoutDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        db = FirebaseDatabase.getInstance();

        mapView = findViewById(R.id.googleMapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::onMapReady);

        layoutDetalles = findViewById(R.id.layoutDetalles);
        findViewById(R.id.botonAnadirDetalle).setOnClickListener(
                new View.OnClickListener() {

                    // TODO esto esta mal
                    private Nutricion seleccionado;

                    private Spinner desplegableDetalles;
                    private Spinner desplegableCalidad;
                    private EditText edContiene;
                    private EditText edNumero;
                    private TextView nombreDetalle;
                    private TextView unidadDetalle;

                    private void setearCampos() {
                        desplegableDetalles.setBackgroundResource(seleccionado.getImg());
                        nombreDetalle.setText(seleccionado.getNombreUd());
                        unidadDetalle.setText(seleccionado.getUdMedicion());
                    }

                    @Override
                    public void onClick(View v) {
                        View item = getLayoutInflater().inflate(R.layout.item_detalle_crear_producto, null);

                        desplegableDetalles = item.findViewById(R.id.desplegableDetalles);
                        desplegableCalidad = item.findViewById(R.id.desplegableCalidad);
                        edContiene = item.findViewById(R.id.edContiene);
                        edNumero = item.findViewById(R.id.edNumero);
                        nombreDetalle = item.findViewById(R.id.tvNombreDetalle);
                        unidadDetalle = item.findViewById(R.id.tvUnidadDetalle);

                        /*
                        seleccionado = (Nutricion) desplegableDetalles.getSelectedItem();
                        detalles_disponibles.remove(seleccionado);
                        detalles_aniadidos.add(seleccionado);
                        setearCampos();
                         */

                        item.findViewById(R.id.botonEliminarDetalle).setOnClickListener(w -> {
                            layoutDetalles.removeView(item);
                            detalles_disponibles.add(seleccionado);
                            detalles_aniadidos.remove(seleccionado);
                        });

                        desplegableDetalles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                Nutricion temp =  detalles_disponibles.remove(pos);

                                if (seleccionado != null) {
                                    detalles_disponibles.add(seleccionado);
                                    detalles_aniadidos.remove(seleccionado);
                                }

                                seleccionado = temp;
                                detalles_aniadidos.add(seleccionado);

                                setearCampos();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

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

    private void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);

        // TODO aaaaaa
        // mapa.setOnMapLongClickListener(this::onMapLongClick);

        mapa.setOnCameraMoveStartedListener(
            i -> mapView.getParent().requestDisallowInterceptTouchEvent(true)
        );

        mapa.setOnCameraIdleListener(
            () -> mapView.getParent().requestDisallowInterceptTouchEvent(false)
        );
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