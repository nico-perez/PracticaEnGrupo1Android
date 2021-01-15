package chavales.los.practica1android;

import java.util.Calendar;

public class Producto {

    private String nombreProducto;
    private String marcaProducto;
    private int imagenProducto;
    private Calidad calidadProducto;
    private Calendar ultimaConsulta;

    // esto no se sinceramente
    private String designacionProducto;
    private String supermercado;

    public Producto(String nombreProducto, String marcaProducto, int imagenProducto, Calidad calidadProducto, Calendar ultimaConsulta) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
    }

    public Producto(String nombreProducto, String marcaProducto, int imagenProducto, Calidad calidadProducto, Calendar ultimaConsulta, String designacionProducto, String supermercado) {
        this.nombreProducto = nombreProducto;
        this.marcaProducto = marcaProducto;
        this.imagenProducto = imagenProducto;
        this.calidadProducto = calidadProducto;
        this.ultimaConsulta = ultimaConsulta;
        this.designacionProducto = designacionProducto;
        this.supermercado = supermercado;
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

    public int getImagen() {
        return imagenProducto;
    }

    public void setImagen(int imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Calidad getCalidad() {
        return calidadProducto;
    }

    public void setCalidad(Calidad calidadProducto) {
        this.calidadProducto = calidadProducto;
    }
}
