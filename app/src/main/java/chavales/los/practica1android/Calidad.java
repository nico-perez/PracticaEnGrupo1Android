package chavales.los.practica1android;

/** Calidad */
public enum Calidad {
    EXCELENTE("🔵", "Excelente"), // Emoji círculo azul
    BUENO("🟢", "Bueno"), // Emoji círculo verde
    REGULAR("🟡", "Regular"), // Emoji círculo amarillo
    MEDIOCRE("🟠", "Mediocre"), // Emoji círculo naranja
    MALO("🔴", "Malo"); // Emoji círculo rojo

    private final String emoji; // emoji
    private final String texto;

    Calidad(String emoji, String texto) {
        this.emoji = emoji;
        this.texto = texto;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getString() {
        return emoji + " " + texto;
    }
}
