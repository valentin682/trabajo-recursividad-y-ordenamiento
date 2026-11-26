import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FrmOrdenamiento extends JFrame {

    JTable tblDatos;
    JComboBox<String> cmbCriterio;
    JTextField txtTiempo;
    String nombreArchivo = System.getProperty("user.dir") + "/src/Datos/Datos.csv";

    public FrmOrdenamiento() {
        setSize(640, 480);
        setTitle("Ordenamiento");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton btnBurbuja = new JButton("Burbuja");
        btnBurbuja.setBounds(10, 10, 85, 30);
        getContentPane().add(btnBurbuja);
        btnBurbuja.addActionListener(evt -> ordenar(0));

        JButton btnRapido = new JButton("Rápido");
        btnRapido.setBounds(100, 10, 85, 30);
        getContentPane().add(btnRapido);
        btnRapido.addActionListener(evt -> ordenar(1));

        JButton btnInsercion = new JButton("Inserción");
        btnInsercion.setBounds(190, 10, 95, 30);
        getContentPane().add(btnInsercion);
        btnInsercion.addActionListener(evt -> ordenar(2));

        JButton btnSeleccion = new JButton("Selección");
        btnSeleccion.setBounds(290, 10, 100, 30);
        getContentPane().add(btnSeleccion);
        btnSeleccion.addActionListener(evt -> ordenar(3));

        JButton btnMezcla = new JButton("Mezcla");
        btnMezcla.setBounds(395, 10, 90, 30);
        getContentPane().add(btnMezcla);
        btnMezcla.addActionListener(evt -> ordenar(4));

        JButton btnRecargar = new JButton("Recargar");
        btnRecargar.setBounds(490, 10, 120, 30);
        getContentPane().add(btnRecargar);
        btnRecargar.addActionListener(evt -> {
            Documento.obtenerLista(nombreArchivo);
            Documento.mostrarLista(tblDatos);
            txtTiempo.setText("");
        });

        cmbCriterio = new JComboBox<>(new String[]{"Nombre - Documento", "Documento - Nombre"});
        cmbCriterio.setBounds(10, 50, 200, 25);
        getContentPane().add(cmbCriterio);

        txtTiempo = new JTextField();
        txtTiempo.setBounds(220, 50, 120, 25);
        txtTiempo.setEditable(false);
        getContentPane().add(txtTiempo);

        tblDatos = new JTable();
        JScrollPane scroll = new JScrollPane(tblDatos);
        scroll.setBounds(10, 85, 600, 340);
        getContentPane().add(scroll);

        Documento.obtenerLista(nombreArchivo);
        Documento.mostrarLista(tblDatos);
    }

    // algoritmo: 0 = burbuja, 1 = rápido, 2 = inserción, 3 = selección, 4 = mezcla
    private void ordenar(int algoritmo) {
        int criterio = cmbCriterio.getSelectedIndex();
        if (criterio < 0 || Documento.documentos == null) {
            return;
        }
        String tiempo;
        switch (algoritmo) {
            case 0:  tiempo = Documento.ordenarBurbuja(criterio);   break;
            case 1:  tiempo = Documento.ordenarRapido(criterio);    break;
            case 2:  tiempo = Documento.ordenarInsercion(criterio); break;
            case 3:  tiempo = Documento.ordenarSeleccion(criterio); break;
            default: tiempo = Documento.ordenarMezcla(criterio);    break;
        }
        txtTiempo.setText(tiempo);
        Documento.mostrarLista(tblDatos);
    }
}
