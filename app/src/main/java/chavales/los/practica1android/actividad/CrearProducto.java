package chavales.los.practica1android.actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.FirebaseDatabase;

import chavales.los.practica1android.R;

public class CrearProducto extends AppCompatActivity {

    private FirebaseDatabase db;

    private GoogleMap mapa;
    private SupportMapFragment mapFragment;

    private ScrollView sv;
    private LinearLayout layoutDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        db = FirebaseDatabase.getInstance();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMapa);
        mapFragment.getMapAsync(this::onMapReady);

        layoutDetalles = findViewById(R.id.layoutDetalles);
        findViewById(R.id.botonAnadirDetalle).setOnClickListener(v -> {
            layoutDetalles.addView(getLayoutInflater().inflate(R.layout.item_detalle_crear_producto, null), 0);
        });
    }

    private void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);
        // mapa.setOnMapLongClickListener(this::onMapLongClick);

        mapa.setOnCameraMoveStartedListener(
            i -> mapFragment.getView().getParent().requestDisallowInterceptTouchEvent(true)
        );

        mapa.setOnCameraIdleListener(
            () -> mapFragment.getView().getParent().requestDisallowInterceptTouchEvent(false)
        );
    }
}