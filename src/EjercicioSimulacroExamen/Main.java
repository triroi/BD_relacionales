package EjercicioSimulacroExamen;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);
    static Emisora [] listado_emisoras;
    static ArrayList<Emisora> arraylist_emisoras=new ArrayList<Emisora>();
    static Connection conn=null;
    public static void main(String[] args) throws SQLException {
        int opcion=0;
        do {
            System.out.println("""
                    0. Salir del programa
                    1. Crea base de datos 'SimulacroExamen'
                    2. Crea tabla emisoraonline
                    3. Insertar una emisora online                
                    4. Buscar emisora por url introducida por teclado y añadirlo a un array estatico
                    5. Almacenar en array dinamico emisoras con beneficios superiores a 4000€
                    6. Metadatos. Sacar la version del SGBD
                    """
            );
            opcion = sc.nextInt();
            switch (opcion){
                case 0:
                    System.out.println("Gracias por utilizar el programa");
                    break;
                case 1:
                    crear_bd();
                    break;
                case 2:
                    crear_tabla_emisora();
                    break;
                case 3:

                    insertar_emisora(e);
                    break;
                case 4:
                    buscar_añadir_array_estatico(url);
                    break;
                case 5:
                    añadir_arraylist_beneficios();
                    break;
                case 6:
                    String versionSGBD=sacar_versionSGBD();
                    System.out.println(versionSGBD);
                    break;
            }


        } while (opcion > 0);
    }

    private static void crear_tabla_emisora() throws SQLException {
        asignar_bd();
        Statement st=conn.createStatement();
        //crear la tabla
        String query="create table emisoraonline("+
                "num_emisora int primary key,"+
                "nombre_emisora varchar(50) not null,"+
                "emitiendo boolean,"+
                "beneficios double,"+
                "num_oyentes int," +
                "url varchar(100))";
        st.executeUpdate(query);
        System.out.println("Tabla creada correctamente");
    }

    private static String sacar_versionSGBD() throws SQLException {
        asignar_bd();
        DatabaseMetaData metadata=conn.getMetaData();
        return metadata.getDriverVersion();
    }

    private static void crear_bd() throws SQLException {
        asignar_bd();
        String query="CREATE DATABASE SimulacroExamen";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Base de datos creada");
    }

    private static void asignar_bd() throws SQLException {
        String query="USE SimulacroExamen";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
    }

}

