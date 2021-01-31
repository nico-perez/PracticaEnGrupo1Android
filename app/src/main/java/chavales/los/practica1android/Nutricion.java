package chavales.los.practica1android;

/**
 * Un enum que guarda los detalles de los productos: sus imagenes, nombres, etc
 */
public enum Nutricion {
    /** los de nico (malos, no se usan, son tonterias) */
    AMIANTO(R.drawable.amianto, "Amianto", "fibras de amianto", "fib"),
    //AZUCAR(R.drawable.azucar, "Azúcar", "azúcares añadidos", "g"),
    ENERGIA(R.drawable.calorias, "Valor energético", "calorías", "kcal"),
    FERTILIZANTE(R.drawable.fertilizante, "Fertilizante", "fertilizante", "mg"),
    GASOLINA(R.drawable.gasolina, "Gasolina", "gasolina", "l"),
    //HIERRO(R.drawable.hierro, "Hierro", "hierro", "mg"),
    NUECES(R.drawable.nueces, "Frutos secos", "frutos secos", "g"),
    //PROTEINAS(R.drawable.proteina, "Valor proteico", "proteínas", "mg"),
    RAMEN(R.drawable.ramen, "Ramen", "fideos", "bol"),
    //SODIO(R.drawable.sodio, "Cantidad de sal", "sodio", "mg"),
    TACOS(R.drawable.tacos, "Comida mexicana", "tacos", "tacos"),
    VENENO(R.drawable.veneno, "Veneno", "toxicidad", "ml"),
    VITAMINAS(R.drawable.vitaminas, "Valor vitamínico", "vitaminas", "mg"),
    WEBOS(R.drawable.webos, "Huevos de codorniz", "huevos", "huevos"),

    /** los  de manu (buenos, parecne casi de verdad y todo) */
    CALORIAS(R.drawable.calorias, "Calorias", "calorias", "uds"),
    GRASA(R.drawable.grasa, "Grasas totales", "grasas saturadas", "g"),
    COLESTEROL(R.drawable.colesterol, "Colesterol", "colesterol", "mg"),
    SODIO(R.drawable.sodio, "Cantidad de sodio", "sodio", "mg"),
    POTASIO(R.drawable.sodio, "Cantidad de potasio", "potasio", "mg"),
    Hidratos(R.drawable.hidratos, "Hidratos de Carbono", "hidratos", "g"),
    PROTEINAS(R.drawable.proteina, "Valor proteico", "proteínas", "mg"),
    AZUCAR(R.drawable.azucar, "Azúcar", "azúcares añadidos", "g"),
    VALOR_ENERGETICO(R.drawable.energetico, "Valor energético", "KJ/g", "kcal"),
    HIERRO(R.drawable.hierro, "Hierro", "hierro", "mg"),
    MAGNESIO(R.drawable.magnesio, "Cantidadd de magnesio", "magnesio", "mg"),
    SAL(R.drawable.vita, "sal", "sales", "g"),
    FIBRA(R.drawable.webos, "Fibra alimentaria aportada", "fibra", "g");

    private final int img;
    private final String nombre;
    private final String nombreUd;
    private final String udMedicion;

    Nutricion(int img, String nombre, String nombreUd, String udMedicion) {
        this.img = img;
        this.nombre = nombre;
        this.nombreUd = nombreUd;
        this.udMedicion = udMedicion;
    }

    public int getImg() {
        return img;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreUd() {
        return nombreUd;
    }

    public String getUdMedicion() {
        return udMedicion;
    }
}
