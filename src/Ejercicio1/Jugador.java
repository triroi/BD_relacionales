package Ejercicio1;

public class Jugador {
    private String dni;
    private String nombre;
    private int dorsal;
    private double salario;
    private int edad;

    public Jugador(String dni, String nombre, int dorsal, double salario, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.salario = salario;
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dorsal=" + dorsal +
                ", salario=" + salario +
                ", edad=" + edad +
                '}';
    }
}
