package EjercicioSimulacroExamen;

import java.sql.*;
import java.util.Scanner;

public class MainEmisoraTradicional {
    static Connection conn=null;
    static Scanner sc=new Scanner(System.in);
    static EmisoraTradicional [] listado_emisoras_tradicional;

    public static void main(String[] args) throws SQLException, AccionInvalida {
        int opcion=0;
        conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "admin");
        do {
            System.out.println("""
                    0. Salir del programa
                    1. Crea base de datos 'SimulacroExamen'
                    2. Crea tabla emisoratradicionl
                    3. Insertar emisora tradicional mediante un array estatico            
                    """
            );
            opcion = sc.nextInt();
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root","admin");
            switch (opcion){
                case 0:
                    System.out.println("Gracias por utilizar el programa");
                    break;
                case 1:
                    crear_bd();
                    break;
                case 2:
                    crear_tabla_emisoratradicional();
                    break;
                case 3:
                    insertar_emisoratradicional();
                    break;
            }


        } while (opcion > 0);
    }

    private static void insertar_emisoratradicional() throws AccionInvalida, SQLException {
        asignar_bd();
        System.out.println("Inserta cuántas emisoras quieres añadir");
        int dimension=sc.nextInt();
        listado_emisoras_tradicional=new EmisoraTradicional[dimension];
        for (int i=0;i<dimension;i++){
            System.out.println("introduzca el identificador de la emisora");
            int num_emisora=sc.nextInt();
            System.out.println("introduzca el nombre de la emisora");
            String nombre_emisora=sc.next();
            System.out.println("introduzca el numero de oyentes de la emisora");
            int num_oyentes=sc.nextInt();
            System.out.println("introduzca la frecuencia de la emisora");
            String frecuencia=sc.next();
            System.out.println("introduzca el numero de frecuencia");
            double num_frecuencia=sc.nextDouble();
            EmisoraTradicional e1=new EmisoraTradicional(num_emisora,nombre_emisora,num_oyentes,frecuencia,num_frecuencia);
            listado_emisoras_tradicional[i]=e1;
        }

        for (int i=0;i< listado_emisoras_tradicional.length;i++){
            PreparedStatement ps=conn.prepareStatement("insert into emisoratradicional values(?,?,?,?,?,?,?)");
            ps.setInt(1,listado_emisoras_tradicional[i].getNum_emisora());
            ps.setString(2,listado_emisoras_tradicional[i].getNombre_emisora());
            ps.setBoolean(3,listado_emisoras_tradicional[i].isEmitiendo());
            ps.setDouble(4,listado_emisoras_tradicional[i].getBeneficios());
            ps.setInt(5,listado_emisoras_tradicional[i].getNum_oyentes());
            ps.setString(6,listado_emisoras_tradicional[i].getFrecuencia().toString());
            ps.setDouble(7,listado_emisoras_tradicional[i].getNum_frecuencia());
            ps.executeUpdate();
        }
    }


    private static void crear_tabla_emisoratradicional() throws SQLException {
        asignar_bd();
        String query="create table emisoratradicional("+
                "num_emisora int primary key,"+
                "nombre_emisora varchar(50) not null,"+
                "emitiendo boolean,"+
                "beneficios double,"+
                "num_oyentes int," +
                "frecuencia varchar(2)," +
                "num_frecuencia DOUBLE)";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla emisoratradicional creada");
    }

    private static void crear_bd() throws SQLException {
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
