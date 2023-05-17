package EjercicioSimulacroExamen;

public class EmisoraTradicional extends Emisora {
    private Frecuencia frecuencia;
    private double num_frecuencia;
    final double CONSTANTE_EMISORA_TRADICIONAL=0.023;

    public EmisoraTradicional(int num_emisora, String nombre_emisora, int num_oyentes, String frecuencia, double num_frecuencia) throws AccionInvalida {
        super(num_emisora, nombre_emisora, num_oyentes);
        this.frecuencia = Frecuencia.valueOf(frecuencia.toUpperCase());
        this.num_frecuencia = num_frecuencia;
    }

    @Override
    public double ganancias() {
        return num_oyentes*CONSTANTE_EMISORA_TRADICIONAL;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = Frecuencia.valueOf(frecuencia.toUpperCase());
    }

    public double getNum_frecuencia() {
        return num_frecuencia;
    }

    public void setNum_frecuencia(double num_frecuencia) {
        this.num_frecuencia = num_frecuencia;
    }

    @Override
    public String toString() {
        return "EmisoraTradicional{" +
                "frecuencia=" + frecuencia +
                ", num_frecuencia=" + num_frecuencia +
                ", num_emisora=" + num_emisora +
                ", nombre_emisora='" + nombre_emisora + '\'' +
                ", emitiendo=" + emitiendo +
                ", beneficios=" + beneficios +
                ", num_oyentes=" + num_oyentes +
                '}';
    }
}
