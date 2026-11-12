package ligajusticia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLigaJusticia {
    private JPanel Ventana;
    private JTextField textId;
    private JTextField textNombre;
    private JTextField textSuperpoder;
    private JTextField textMision;
    private JTextField textNivel;
    private JTextField textPago;
    private JButton registrarButton;
    private JButton buscarButton;
    private JButton modificarButton;
    private JButton listarButton;
    private JButton informeButton;
    private JButton limpiarButton;
    private JTextArea textArea1;
    private JLabel lblTotal;

    public LigaJusticia liga = new LigaJusticia();

    public FormLigaJusticia() {
        // 1: Registrar héroe
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Heroe h = leerHeroe();
                    liga.registrar(h);
                    actualizarPantalla();
                    JOptionPane.showMessageDialog(null, "Héroe registrado");
                    limpiar();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID y el nivel deben ser números enteros, y el pago mensual un número (use punto para decimales)");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Buscar por ID: carga los datos del héroe en los campos para poder modificarlos
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textId.getText().isBlank()) {
                        JOptionPane.showMessageDialog(null, "Ingrese el ID del héroe que desea buscar");
                    } else {
                        int id = Integer.parseInt(textId.getText().trim());
                        Heroe h = liga.buscarHeroe(id);
                        cargarDatos(h);
                        JOptionPane.showMessageDialog(null, "Héroe encontrado:\n" + h + "\nCambie los datos y presione Modificar");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número entero");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // R2: Modificar héroe usando el ID (los campos vacíos conservan el dato anterior)
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textId.getText().isBlank()) {
                        JOptionPane.showMessageDialog(null, "Ingrese el ID del héroe que desea modificar");
                    } else if (noHayCambios()) {
                        JOptionPane.showMessageDialog(null, "Escriba al menos un dato para cambiar");
                    } else {
                        int id = Integer.parseInt(textId.getText().trim());
                        Heroe actual = liga.buscarHeroe(id);
                        Heroe h = leerCambios(actual);
                        liga.modificar(h);
                        actualizarPantalla();
                        JOptionPane.showMessageDialog(null, "Héroe con ID " + id + " modificado");
                        limpiar();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID y el nivel deben ser números enteros, y el pago mensual un número (use punto para decimales)");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // 3: Listar héroes
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea1.setText(liga.sacarLista());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // 4: Informe de pagos (aporte al fondo, impuesto y pago neto)
        informeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea1.setText(liga.sacarInforme());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
    }

    //lee los campos del formulario y crea el héroe
    //si el ID, el nivel o el pago no son números, Integer.parseInt o Double.parseDouble lanzan NumberFormatException
    private Heroe leerHeroe() throws Exception {
        if (hayCamposVacios()) {
            throw new Exception("Debe llenar todos los campos");
        }
        int id = Integer.parseInt(textId.getText().trim());
        String nombre = textNombre.getText().trim();
        String superpoder = textSuperpoder.getText().trim();
        String mision = textMision.getText().trim();
        int nivel = Integer.parseInt(textNivel.getText().trim());
        double pago = Double.parseDouble(textPago.getText().trim());
        return new Heroe(id, nombre, superpoder, mision, nivel, pago);
    }

    //revisa si algún campo del formulario quedó vacío
    private boolean hayCamposVacios() {
        return textId.getText().isBlank() || textNombre.getText().isBlank() || textSuperpoder.getText().isBlank()
                || textMision.getText().isBlank() || textNivel.getText().isBlank() || textPago.getText().isBlank();
    }

    //revisa si todos los campos de datos (menos el ID) están vacíos
    private boolean noHayCambios() {
        return textNombre.getText().isBlank() && textSuperpoder.getText().isBlank() && textMision.getText().isBlank()
                && textNivel.getText().isBlank() && textPago.getText().isBlank();
    }

    //crea el héroe con los cambios: si un campo está vacío, se queda el dato que ya tenía
    private Heroe leerCambios(Heroe actual) {
        String nombre = actual.getNombre();
        String superpoder = actual.getSuperpoder();
        String mision = actual.getMision();
        int nivel = actual.getNivel();
        double pago = actual.getPagoMensual();

        if (!textNombre.getText().isBlank()) {
            nombre = textNombre.getText().trim();
        }
        if (!textSuperpoder.getText().isBlank()) {
            superpoder = textSuperpoder.getText().trim();
        }
        if (!textMision.getText().isBlank()) {
            mision = textMision.getText().trim();
        }
        if (!textNivel.getText().isBlank()) {
            nivel = Integer.parseInt(textNivel.getText().trim());
        }
        if (!textPago.getText().isBlank()) {
            pago = Double.parseDouble(textPago.getText().trim());
        }
        return new Heroe(actual.getId(), nombre, superpoder, mision, nivel, pago);
    }

    //pone los datos de un héroe en los campos del formulario
    private void cargarDatos(Heroe h) {
        textNombre.setText(h.getNombre());
        textSuperpoder.setText(h.getSuperpoder());
        textMision.setText(h.getMision());
        textNivel.setText("" + h.getNivel());
        textPago.setText("" + h.getPagoMensual());
    }

    //muestra la lista actualizada y el total de héroes
    private void actualizarPantalla() throws Exception {
        textArea1.setText(liga.sacarLista());
        lblTotal.setText("" + liga.getTotalHeroes());
    }

    //deja los campos en blanco
    private void limpiar() {
        textId.setText("");
        textNombre.setText("");
        textSuperpoder.setText("");
        textMision.setText("");
        textNivel.setText("");
        textPago.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rol de Misiones - Liga de la Justicia");
        frame.setContentPane(new FormLigaJusticia().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
