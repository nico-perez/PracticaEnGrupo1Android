package chavales.los.practica1android;

public enum Calidad {
    EXCELENTE("🔵 Excelente"), // Emoji círculo azul
    BUENO("🟢 Bueno"), // Emoji círculo verde
    REGULAR("🟡 Regular"), // Emoji círculo amarillo
    MEDIOCRE("🟠 Mediocre"), // Emoji círculo naranja
    MALO("🔴 Malo"); // Emoji círculo rojo

    private final String texto;

    Calidad(String texto) {
        this.texto = texto;
    }

    public String getString() {
        return texto;
    }
}
