package ligajusticia;

import java.util.ArrayList;
import java.util.List;

public class LigaJusticia {
    List<Heroe> heroes;  //aquí se declara la lista con el nombre

    public LigaJusticia() {
        heroes = new ArrayList<Heroe>(); // se crea la lista
    }

    public boolean isEmpty() {
        return heroes.isEmpty();
    }

    public int getTotalHeroes() {
        return heroes.size();
    }

    //búsqueda lineal por ID: recorre la lista uno por uno
    //devuelve la posición del héroe, o -1 si no lo encuentra
    public int buscarPorId(int id) {
        for (int i = 0; i < heroes.size(); i++) {
            if (heroes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    //valida todos los datos del héroe
    public void validarDatos(Heroe h) throws Exception {
        if (h.getId() <= 0) {
            throw new Exception("El ID debe ser un número mayor a 0");
        }
        if (h.getNombre().isBlank()) {
            throw new Exception("Ingrese el nombre del héroe");
        }
        if (!h.getNombre().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El nombre solo debe tener letras");
        }
        if (h.getSuperpoder().isBlank()) {
            throw new Exception("Ingrese el superpoder principal");
        }
        if (h.getMision().isBlank()) {
            throw new Exception("Ingrese la misión asignada");
        }
        if (h.getNivel() < 1 || h.getNivel() > 5) {
            throw new Exception("El nivel de dificultad debe estar entre 1 y 5");
        }
        if (h.getPagoMensual() <= 0) {
            throw new Exception("El pago mensual debe ser mayor a 0");
        }
    }

    //revisa que el ID no esté repetido en la lista
    public void validarIdNuevo(int id) throws Exception {
        if (buscarPorId(id) != -1) {
            throw new Exception("Ya existe un héroe con el ID " + id);
        }
    }

    //Registrar un héroe, solo llama a los otros métodos en orden
    public void registrar(Heroe dato) throws Exception {
        validarDatos(dato);
        validarIdNuevo(dato.getId());
        heroes.add(dato); // aquí se agrega al final de la lista
    }

    //busca un héroe por su ID y lo devuelve
    public Heroe buscarHeroe(int id) throws Exception {
        if (isEmpty()) {
            throw new Exception("No hay héroes registrados");
        }
        int posicion = buscarPorId(id);
        if (posicion == -1) {
            throw new Exception("No existe un héroe con el ID " + id);
        }
        return heroes.get(posicion);
    }

    //copia los datos nuevos en el héroe que ya está en la lista (el ID no cambia)
    public void actualizarDatos(Heroe h, Heroe datosNuevos) {
        h.setNombre(datosNuevos.getNombre());
        h.setSuperpoder(datosNuevos.getSuperpoder());
        h.setMision(datosNuevos.getMision());
        h.setNivel(datosNuevos.getNivel());
        h.setPagoMensual(datosNuevos.getPagoMensual());
    }

    //Modificar un héroe usando su ID, solo llama a los otros métodos en orden
    public void modificar(Heroe datosNuevos) throws Exception {
        validarDatos(datosNuevos);
        Heroe h = buscarHeroe(datosNuevos.getId());
        actualizarDatos(h, datosNuevos);
    }


    public String sacarLista() throws Exception {
        if (isEmpty()) {
            throw new Exception("No hay héroes registrados");
        }
        String resultado = "LISTA DE HÉROES REGISTRADOS\n\n";
        for (Heroe h : heroes) {
            resultado += h + "\n";
        }
        return resultado;
    }

    //informe con el aporte al fondo, el impuesto y el pago neto de cada héroe
    //arma el texto del informe de UN héroe
    public String sacarInformeHeroe(Heroe h) {
        String resultado = "Nombre: " + h.getNombre() + "\n";
        resultado += "Superpoder principal: " + h.getSuperpoder() + "\n";
        resultado += "Pago mensual: $" + String.format("%.2f", h.getPagoMensual()) + "\n";
        resultado += "Aporte al Fondo de Héroes (10%): $" + String.format("%.2f", h.calcularAporte()) + "\n";
        resultado += "Impuesto del gobierno (mensual): $" + String.format("%.2f", h.calcularImpuestoMensual())
                + "   [anual: $" + String.format("%.2f", h.calcularImpuestoAnual()) + "]\n";
        resultado += "Pago neto a recibir: $" + String.format("%.2f", h.calcularPagoNeto()) + "\n";
        return resultado;
    }

    //suma los aportes al fondo de todos los héroes
    public double calcularTotalAportes() {
        double total = 0;
        for (Heroe h : heroes) {
            total += h.calcularAporte();
        }
        return total;
    }

    //suma los impuestos mensuales de todos los héroes
    public double calcularTotalImpuestos() {
        double total = 0;
        for (Heroe h : heroes) {
            total += h.calcularImpuestoMensual();
        }
        return total;
    }

    //suma los pagos netos de todos los héroes
    public double calcularTotalNeto() {
        double total = 0;
        for (Heroe h : heroes) {
            total += h.calcularPagoNeto();
        }
        return total;
    }

    //R4: informe de pagos, junta el informe de cada héroe y los totales
    public String sacarInforme() throws Exception {
        if (isEmpty()) {
            throw new Exception("No hay héroes registrados");
        }
        String resultado = "INFORME DE PAGOS - LIGA DE LA JUSTICIA\n\n";
        for (Heroe h : heroes) {
            resultado += sacarInformeHeroe(h) + "\n";
        }
        resultado += "Total aportes al fondo: $" + String.format("%.2f", calcularTotalAportes()) + "\n";
        resultado += "Total impuestos (mensual): $" + String.format("%.2f", calcularTotalImpuestos()) + "\n";
        resultado += "Total pagos netos: $" + String.format("%.2f", calcularTotalNeto()) + "\n";
        return resultado;
    }
}
