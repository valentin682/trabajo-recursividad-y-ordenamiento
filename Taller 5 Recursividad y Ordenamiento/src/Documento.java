import java.io.BufferedReader;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Documento {

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String tipoDocumento;

    public Documento() {
        nombre = "";
        apellido1 = "";
        apellido2 = "";
        tipoDocumento = "";
    }

    public Documento(String apellido1, String apellido2, String nombre, String tipoDocumento) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombreCompleto() {
        return apellido1 + " " + apellido2 + " " + nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    //********** Atributos y Métodos estáticos **********
    public static Documento[] documentos;

    public static void agregar(Documento d) {
        if (documentos == null) {
            documentos = new Documento[1];
        } else {
            documentos = (Documento[]) Util.redimensionar(documentos, documentos.length + 1);
        }
        documentos[documentos.length - 1] = d;
    }

    public static void obtenerLista(String nombreArchivo) {
        documentos = null; // reinicia la lista (permite recargar los datos originales)
        try {
            BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
            String linea = br.readLine(); // encabezado
            linea = br.readLine();
            while (linea != null) {
                String[] textos = linea.split(";");
                if (textos.length >= 4) {
                    agregar(new Documento(textos[0], textos[1], textos[2], textos[3]));
                }
                linea = br.readLine();
            }
            br.close();
        } catch (Exception ex) {
            System.out.println("Error leyendo el archivo: " + ex.getMessage());
        }
    }

    public static void mostrarLista(JTable tbl) {
        String[] encabezados = new String[]{"Nombre", "Tipo Documento"};
        int n = (documentos == null) ? 0 : documentos.length;
        String[][] datos = new String[n][encabezados.length];
        for (int i = 0; i < n; i++) {
            datos[i][0] = documentos[i].getNombreCompleto();
            datos[i][1] = documentos[i].getTipoDocumento();
        }
        tbl.setModel(new DefaultTableModel(datos, encabezados));
    }

    private static void intercambiar(int origen, int destino) {
        Documento t = documentos[origen];
        documentos[origen] = documentos[destino];
        documentos[destino] = t;
    }

    // criterio 0: Nombre y luego Tipo de Documento
    // criterio 1: Tipo de Documento y luego Nombre
    private static boolean esMayor(Documento dI, Documento dJ, int criterio) {
        if (criterio == 0) {
            return (dI.getNombreCompleto().compareTo(dJ.getNombreCompleto()) > 0)
                    || (dI.getNombreCompleto().compareTo(dJ.getNombreCompleto()) == 0
                    && dI.getTipoDocumento().compareTo(dJ.getTipoDocumento()) > 0);
        } else {
            return (dI.getTipoDocumento().compareTo(dJ.getTipoDocumento()) > 0)
                    || (dI.getTipoDocumento().compareTo(dJ.getTipoDocumento()) == 0
                    && dI.getNombreCompleto().compareTo(dJ.getNombreCompleto()) > 0);
        }
    }

    //******************** BURBUJA
    public static String ordenarBurbuja(int criterio) {
        Util.iniciarCronometro();
        for (int i = 0; i < documentos.length - 1; i++) {
            for (int j = i + 1; j < documentos.length; j++) {
                if (esMayor(documentos[i], documentos[j], criterio)) {
                    intercambiar(i, j);
                }
            }
        }
        return Util.obtenerTextoTiempoCronometro();
    }

    //******************** RÁPIDO (Quicksort)
    public static String ordenarRapido(int criterio) {
        Util.iniciarCronometro();
        _ordenarRapido(0, documentos.length - 1, criterio);
        return Util.obtenerTextoTiempoCronometro();
    }

    private static void _ordenarRapido(int inicio, int fin, int criterio) {
        if (inicio >= fin) {
            return;
        }
        int posicionPivote = ubicarPivote(inicio, fin, criterio);
        _ordenarRapido(inicio, posicionPivote - 1, criterio);
        _ordenarRapido(posicionPivote + 1, fin, criterio);
    }

    private static int ubicarPivote(int inicio, int fin, int criterio) {
        Documento dPivote = documentos[inicio];
        int posicionPivote = inicio;
        for (int i = inicio + 1; i < fin + 1; i++) {
            if (esMayor(dPivote, documentos[i], criterio)) {
                posicionPivote++;
                if (i != posicionPivote) {
                    intercambiar(i, posicionPivote);
                }
            }
        }
        if (inicio != posicionPivote) {
            intercambiar(inicio, posicionPivote);
        }
        return posicionPivote;
    }

    //******************** INSERCIÓN
    public static String ordenarInsercion(int criterio) {
        Util.iniciarCronometro();
        for (int i = 1; i < documentos.length; i++) {
            Documento actual = documentos[i];
            int j = i - 1;
            while (j >= 0 && esMayor(documentos[j], actual, criterio)) {
                documentos[j + 1] = documentos[j];
                j--;
            }
            documentos[j + 1] = actual;
        }
        return Util.obtenerTextoTiempoCronometro();
    }

    //******************** SELECCIÓN
    public static String ordenarSeleccion(int criterio) {
        Util.iniciarCronometro();
        for (int i = 0; i < documentos.length - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < documentos.length; j++) {
                if (esMayor(documentos[indiceMenor], documentos[j], criterio)) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                intercambiar(i, indiceMenor);
            }
        }
        return Util.obtenerTextoTiempoCronometro();
    }

    //******************** MEZCLA (Merge Sort)
    public static String ordenarMezcla(int criterio) {
        Util.iniciarCronometro();
        if (documentos.length > 1) {
            Documento[] auxiliar = new Documento[documentos.length];
            _ordenarMezcla(0, documentos.length - 1, criterio, auxiliar);
        }
        return Util.obtenerTextoTiempoCronometro();
    }

    private static void _ordenarMezcla(int inicio, int fin, int criterio, Documento[] auxiliar) {
        if (inicio >= fin) {
            return;
        }
        int medio = (inicio + fin) / 2;
        _ordenarMezcla(inicio, medio, criterio, auxiliar);
        _ordenarMezcla(medio + 1, fin, criterio, auxiliar);
        mezclar(inicio, medio, fin, criterio, auxiliar);
    }

    private static void mezclar(int inicio, int medio, int fin, int criterio, Documento[] auxiliar) {
        int i = inicio;
        int j = medio + 1;
        int k = inicio;
        while (i <= medio && j <= fin) {
            if (esMayor(documentos[i], documentos[j], criterio)) {
                auxiliar[k++] = documentos[j++];
            } else {
                auxiliar[k++] = documentos[i++];
            }
        }
        while (i <= medio) {
            auxiliar[k++] = documentos[i++];
        }
        while (j <= fin) {
            auxiliar[k++] = documentos[j++];
        }
        for (k = inicio; k <= fin; k++) {
            documentos[k] = auxiliar[k];
        }
    }
}
