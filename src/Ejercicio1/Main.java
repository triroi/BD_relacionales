package Ejercicio1;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static Connection conn=null;
    public static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        int opcion;
        do {
            System.out.println("""
                    0. Salir del programa
                    1. Crea base de datos 'EjercicioPractico'
                    2. Crea tabla jugador
                    3. Vaciar tabla
                    4. Eliminar tabla
                    5. Insertar un jugador
                    6. Eliminar jugador por dni
                    7. Actualizar salario de jugador por dni
                    8. Mostrar datos de jugador por dni
                    9. Insertar todos los juadores en una lista de la clase jugador y mostrarlos ordenador por mayor a menor salario
                    """
            );
            opcion = sc.nextInt();
            switch (opcion){
                case 0:
                    crear_bd();
                    break;
                case 1:
                    crear_tabla_jugador();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
            }


        } while (opcion > 0);
    }

    private static void crear_tabla_jugador() {
        Statement st=conn.createStatement();

    }

    private static void crear_bd() throws SQLException {
        establecer_conexion();
        String query="create database EjercicioPractico";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("BD creada correctamente");

    }
    private static void establecer_conexion() throws SQLException {
        //conexion al sgbd creando la bbdd en java
        String url="jdbc:mysql://localhost:3306/";
        String user="root";
        String pwd="admin";
        conn= DriverManager.getConnection(url,user,pwd);
        System.out.println("Conexion establecida");
    }

}
