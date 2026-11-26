import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Archivo {

    public static BufferedReader abrirArchivo(String nombreArchivo) throws IOException {
        return Files.newBufferedReader(Paths.get(nombreArchivo), StandardCharsets.ISO_8859_1);
    }
}
