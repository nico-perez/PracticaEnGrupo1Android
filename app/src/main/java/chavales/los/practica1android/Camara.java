package chavales.los.practica1android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * tutorial cortesía de megacorporación gogle:
 * https://codelabs.developers.google.com/codelabs/camerax-getting-started#0
 */
public class Camara extends AppCompatActivity {

    // El camExecutor es el hilo que maneja los fotogramas de la cámara y los pasa
    // al PreviewView
    private ExecutorService camExecutor;
    private PreviewView viewFinder;

    private static final int REQUEST_CODE_PERMISSIONS = 2823;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        viewFinder = findViewById(R.id.previewView);

        // Si tenemos permiso, inicia la camara, si no, los pide
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA }, REQUEST_CODE_PERMISSIONS);
        }

        camExecutor = Executors.newSingleThreadExecutor();
    }

    /**
     * Aquí es donde se le indica al executor que tiene
     * que pasarle los fotogramas al PreviewView
     */
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> camProviderFuture = ProcessCameraProvider.getInstance(this);
        camProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider camProvider =  camProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.createSurfaceProvider());

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                camProvider.unbindAll();
                camProvider.bindToLifecycle(this, cameraSelector, preview);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camExecutor.shutdown();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this,
                        "Para usar la cámara necesitas proporcionar permiso",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * Comprueba permisos de cámara
     */
    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}