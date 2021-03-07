package chavales.los.practica1android.modelo;

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

    public static Calidad deNota(int not) {
        if (not <= 20) return MALO;
        else if (not <= 40) return MEDIOCRE;
        else if (not <= 60) return REGULAR;
        else if (not <= 80) return BUENO;
        else return EXCELENTE;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getString() {
        return emoji + " " + texto;
    }
}
