package chavales.los.practica1android.modelo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Producto implements Parcelable {

    private String nombreProducto;
    private String marcaProducto;
    private String imagenProducto;
    private Calidad calidadProducto;
    private Long ultimaConsulta;

    // esto?
    private String designacionProducto;
    private List<MarkerOptions> ubicaciones;

    private List<Detalle> detalles;
    private int puntuacion;

    public static final Map<String, File> CACHE_IMAGENES = new TreeMap<>();

    /** por si necesito usar resources otra vez */
    private int _res = 0;
    public int getRes() { return _res; }
    public Producto(String nombreProducto, String marcaProducto, int res, Calidad calidadProducto, Long ultimaConsulta, int puntuacion, List<Detalle> detalles) {
        this(nombreProducto, marcaProducto, (String) null, calidadProducto, ultimaConsulta, puntuacion, detalles);
        _res = res;
    }

    public Producto(String nombreProducto, String marcaProducto, String imagenProducto, Calidad calidadProducto, Long ultimaConsulta, int puntuacion, List<Detalle> detalles) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
        this.puntuacion = puntuacion;
        this.detalles = detalles;
    }

    public Producto(String nombreProducto, String marcaProducto, String imagenProducto, Calidad calidadProducto, Long ultimaConsulta, int puntuacion, List<Detalle> detalles, String designacionProducto, List<MarkerOptions> ubicaciones) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
        this.puntuacion = puntuacion;
        this.detalles = detalles;
        this.designacionProducto = designacionProducto;
        this.ubicaciones = ubicaciones;
    }

    public String getNombre() {
        return nombreProducto;
    }

    public void setNombre(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getMarca() {
        return marcaProducto;
    }

    public void setMarca(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public String getImagen() {
        return imagenProducto;
    }

    public void setImagen(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Calidad getCalidad() {
        return calidadProducto;
    }

    public void setCalidad(Calidad calidadProducto) {
        this.calidadProducto = calidadProducto;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setUltimaConsulta(Long ultimaConsulta) {
        this.ultimaConsulta = ultimaConsulta;
    }

    public Long getUltimaConsulta() {
        return ultimaConsulta;
    }

    public void setUbicaciones(List<MarkerOptions> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<MarkerOptions> getUbicaciones() {
        return ubicaciones;
    }

    /**
     * Clase para representar los detalles de los productos.
     */
    public static class Detalle implements Parcelable {

        private String bajoAltoEn; // p.ej. «Alto en», «Con un poco de»
        private Nutricion ingrediente; // el ingrediente que describe este detalle
        private float cantidad; // p.ej. «5,3 g, «80 ml», etc.
        private Calidad calidad; // cómo de buena o mala es la cantidad descrita por este detalle

        public Detalle() {}

        public Detalle(String bajoAltoEn, Nutricion ingrediente, float cantidad, Calidad calidad) {
            this.bajoAltoEn = bajoAltoEn;
            this.ingrediente = ingrediente;
            this.cantidad = cantidad;
            this.calidad = calidad;
        }

        protected Detalle(Parcel in) {
            bajoAltoEn = in.readString();
            ingrediente = Nutricion.valueOf(in.readString());
            cantidad = in.readFloat();
            calidad = Calidad.valueOf(in.readString());
        }

        public static final Creator<Detalle> CREATOR = new Creator<Detalle>() {
            @Override
            public Detalle createFromParcel(Parcel in) {
                return new Detalle(in);
            }

            @Override
            public Detalle[] newArray(int size) {
                return new Detalle[size];
            }
        };

        public Nutricion getIngrediente() {
            return ingrediente;
        }

        public float getCantidad() {
            return cantidad;
        }

        public String getBajoAltoEn() {
            return bajoAltoEn;
        }

        public Calidad getCalidad() {
            return calidad;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bajoAltoEn);
            dest.writeString(ingrediente.name());
            dest.writeFloat(cantidad);
            dest.writeString(calidad.name());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Hay que implementar parcelable para añadir una instancia
     * de Producto a un intent, así que aquí estamos
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreProducto);
        dest.writeString(marcaProducto);
        dest.writeString(imagenProducto);
        dest.writeValue(calidadProducto);
        dest.writeValue(ultimaConsulta);
        dest.writeInt(puntuacion);
        dest.writeString(designacionProducto);

        dest.writeList(ubicaciones);

        dest.writeList(detalles);
    }

    /** extrae del snapshot un producto */
    public static Producto recuperarDeFirebase(DataSnapshot data, FirebaseStorage bin) {
        String nombre = data.child("nombre").getValue(String.class);
        String marca = data.child("marca").getValue(String.class);
        String imagen = data.child("imagen").getValue(String.class);
        Calidad calidad = Calidad.valueOf(data.child("calidad").getValue(String.class));

        List<MarkerOptions> ubicaciones = new ArrayList<>();
        for (DataSnapshot s : data.child("ubicaciones").getChildren()) {
            ubicaciones.add(new MarkerOptions()
                    .position(new LatLng(s.child("lat").getValue(Double.class), s.child("long").getValue(Double.class)))
                    .title(s.child("titulo").getValue(String.class)));
        }

        List<Detalle> detalles = data.child("detalles").getValue(new GenericTypeIndicator<List<Detalle>>(){});
        int puntuacion = data.child("puntuacion").getValue(Integer.class);

        Producto producto = new Producto(nombre, marca, imagen, calidad,null,puntuacion, detalles, null, ubicaciones);

        return producto;
    }

    /** sube los datos de este producto a firebase */
    public void subirAFirebase(Context context, FirebaseDatabase json, FirebaseStorage bin) {
        DatabaseReference dbrefProductos = json.getReference("productos");
        dbrefProductos.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                Long num = (Long) mutableData.child("num").getValue(Long.class);
                if (num == null) {
                    mutableData.child("num").setValue(1);
                    num = 1L;
                } else {
                    num++;
                    mutableData.child("num").setValue((long) num);
                }

                MutableData mutableDataNthChild = mutableData.child(num.toString());
                mutableDataNthChild.child("nombre").setValue(nombreProducto);
                mutableDataNthChild.child("marca").setValue(marcaProducto);
                mutableDataNthChild.child("imagen").setValue(imagenProducto);
                mutableDataNthChild.child("calidad").setValue(calidadProducto);

                if (ubicaciones != null && ubicaciones.size() > 0) {
                    for (int i = 0; i < ubicaciones.size(); ++i) {
                        MutableData ubic = mutableDataNthChild.child("ubicaciones").child(String.valueOf(i));
                        ubic.child("lat").setValue(ubicaciones.get(i).getPosition().latitude);
                        ubic.child("long").setValue(ubicaciones.get(i).getPosition().longitude);
                        ubic.child("titulo").setValue(ubicaciones.get(i).getTitle());
                    }
                } else {
                    int rand = (int) (Math.random() * 4) + 1;

                    final double lat1 = Math.random() * 140d - 70d;
                    final double lng1 = Math.random() * 340d - 160d;

                    for (int i = 0; i < rand; ++i) {

                        final double lat2 = Math.random() * 40d - 20d;
                        final double lng2 = Math.random() * 40d - 20d;
                        String tit = "Autogenerado " + (i + 1);

                        MutableData ubic =  mutableDataNthChild.child("ubicaciones").child(String.valueOf(i));
                        ubic.child("lat").setValue(lat1 + lat2);
                        ubic.child("long").setValue(lng1 + lng2);
                        ubic.child("titulo").setValue(tit);
                    }
                }

                mutableDataNthChild.child("detalles").setValue(detalles);
                mutableDataNthChild.child("puntuacion").setValue(puntuacion);

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel source) {

            Producto aDevolver = new Producto(source.readString(),
                                     source.readString(),
                                     source.readString(),
                                     (Calidad) source.readValue(null),
                                     (Long) source.readValue(null),
                                     source.readInt(),
                                     null,
                                     source.readString(),
                                     null);

            List<MarkerOptions> ubicaciones = new ArrayList<>();
            source.readList(ubicaciones, MarkerOptions.class.getClassLoader());
            aDevolver.setUbicaciones(ubicaciones);

            List<Detalle> detalles = new ArrayList<>();
            source.readList(detalles, Detalle.class.getClassLoader());
            aDevolver.setDetalles(detalles);

            return aDevolver;
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
