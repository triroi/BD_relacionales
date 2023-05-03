import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    static Connection conn=null;
    public static void main(String[] args) throws SQLException {
        int opcion=0;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("0.Salir del programa");
            System.out.println("1.Establecer conexion con sgbd");
            System.out.println("2.Crear la bd ut14");
            System.out.println("3.Crear la tabla paciente");
            System.out.println("Introduzca una opcion por favor");
            opcion=sc.nextInt();
            switch (opcion){
                case 0:
                    System.out.println("Gracias por usar el programa");
                    break;
                case 1:
                    establecer_conexion();
                    break;
                case 2:
                    crear_bd();
                    break;
                case 3:
                    crear_tabla();
                    break;
            }


        }while (opcion!=0);
        //conexion al sistema gestor de bbdd
        /*
        String url="jdbc:mysql://localhost:3306/ut14";
        String user="root";
        String pwd="admin";
        Connection conn= DriverManager.getConnection(url,user,pwd);
        System.out.println("Conexion establecida");
         */


    }

    private static void crear_tabla() throws SQLException {
        Statement st=conn.createStatement();
        //crear la tabla
        String query="create table paciente("+
                "dni varchar(9) primary key,"+
                "nombre varchar(30) not null,"+
                "apellidos varchar(100),"+
                "n_operaciones int)";
        st.executeUpdate(query);
        System.out.println("Tabla creada correctamente");
    }

    private static void crear_bd() throws SQLException {
        String query="create database ut14";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("BD creada correctamente");

        //asignar la bbdd
        query="use ut14";
        st.executeUpdate(query);
        System.out.println("En uso la bbdd ut14");
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