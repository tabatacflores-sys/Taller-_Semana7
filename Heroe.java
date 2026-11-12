package ligajusticia;

public class Heroe {
    private int id;
    private String nombre;
    private String superpoder;
    private String mision;
    private int nivel;
    private double pagoMensual;

    //Constructor por defecto
    public Heroe() {
        this.id = 0;
        this.nombre = "Sin nombre";
        this.superpoder = "Sin superpoder";
        this.mision = "Sin mision";
        this.nivel = 1;
        this.pagoMensual = 0;
    }

    //Constructor con parámetros
    public Heroe(int id, String nombre, String superpoder, String mision, int nivel, double pagoMensual) {
        this.id = id;
        this.nombre = nombre;
        this.superpoder = superpoder;
        this.mision = mision;
        this.nivel = nivel;
        this.pagoMensual = pagoMensual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSuperpoder() {
        return superpoder;
    }

    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public double getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(double pagoMensual) {
        this.pagoMensual = pagoMensual;
    }

    //aporte al Fondo de Héroes: 10% del pago mensual
    public double calcularAporte() {
        return pagoMensual * 0.10;
    }

    //pago anual: pago mensual por los 12 meses
    public double calcularPagoAnual() {
        return pagoMensual * 12;
    }

    //impuesto del gobierno (anual)
    public double calcularImpuestoAnual() {
        double anual = calcularPagoAnual();
        if (anual <= 60000) {
            return 0;                              // 0%
        } else if (anual <= 120000) {
            return (anual - 60000) * 0.12;         // 12% del exceso a 60,000
        } else if (anual <= 240000) {
            return (anual - 120000) * 0.25;        // 25% del exceso a 120,000
        } else {
            return (anual - 240000) * 0.35;        // 35% del exceso a 240,000
        }
    }

    //impuesto anual se reparte en los 12 meses
    public double calcularImpuestoMensual() {
        return calcularImpuestoAnual() / 12;
    }

    //pago neto = pago mensual - aporte al fondo - impuesto mensual
    public double calcularPagoNeto() {
        return pagoMensual - calcularAporte() - calcularImpuestoMensual();
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" + "Nombre: " + nombre + "\n" + "Superpoder: " + superpoder + "\n" +
                "Misión: " + mision + "\n" + "Nivel de dificultad: " + nivel + "\n" +
                "Pago mensual: $" + String.format("%.2f", pagoMensual) + "\n";
    }
}
