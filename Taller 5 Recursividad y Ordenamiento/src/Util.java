import java.lang.reflect.Array;

public class Util {

    private static long inicio;

    public static Object redimensionar(Object arreglo, int nuevoTamano) {
        Class<?> tipo = arreglo.getClass().getComponentType();
        Object nuevo = Array.newInstance(tipo, nuevoTamano);
        System.arraycopy(arreglo, 0, nuevo, 0,
                Math.min(Array.getLength(arreglo), nuevoTamano));
        return nuevo;
    }

    public static void iniciarCronometro() {
        inicio = System.nanoTime();
    }

    // Devuelve el tiempo transcurrido con formato HH:MM:SS.cc
    public static String obtenerTextoTiempoCronometro() {
        long ms = (System.nanoTime() - inicio) / 1_000_000;
        long horas = ms / 3_600_000;
        long minutos = (ms / 60_000) % 60;
        long segundos = (ms / 1000) % 60;
        long centesimas = (ms % 1000) / 10;
        return String.format("%02d:%02d:%02d.%02d", horas, minutos, segundos, centesimas);
    }
}
