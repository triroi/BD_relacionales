package Ejercicio1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static Connection conn=null;
    public static Scanner sc=new Scanner(System.in);
    static Jugador [] array_estatico_jugadores;
    static Jugador j;
    static ArrayList<Jugador> arrayList=new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        int dorsal;
        String dni;
        int opcion;
        do {
            System.out.println("""
                    0. Salir del programa
                    1. Crea base de datos 'EjercicioPractico'
                    2. Crea tabla jugador
                    3. Vaciar tabla
                    4. Eliminar tabla
                    5. Insertar un jugador
                    6. Buscar jugador por dorsal y lo almacenamos en un objeto jugador e imprimimos el objeto
                    7. Insertar registros en array estatico
                    8. Insertar registros en array dinamico
                    9. Metadatos
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
                    crear_tabla_jugador();
                    break;
                case 3:
                    vaciar_tabla_jugador();
                    break;
                case 4:
                    eliminar_tabla();
                    break;
                case 5:
                    System.out.println("Introduzca su dni");
                    dni=sc.next();
                    System.out.println("Introduzca su nombre");
                    String nombre=sc.next();
                    System.out.println("Introduzca su numero dorsal");
                    dorsal=sc.nextInt();
                    System.out.println("Introduzca su salario");
                    double salario=sc.nextDouble();
                    System.out.println("Introduzca su edad");
                    int edad=sc.nextInt();
                    j=new Jugador(dni,nombre,dorsal,salario,edad);
                    insertar_jugador(j);
                    break;
                case 6:
                    System.out.println("Inserte el dorsal del jugador que quieras buscar");
                    dorsal=sc.nextInt();
                    j=buscar_por_dorsal(dorsal);
                    System.out.println(j.toString());
                    break;
                case 7:
                    array_estatico_jugadores=almacenar_array_estatico();
                    System.out.println(Arrays.toString(array_estatico_jugadores));
                    break;
                case 8:
                    arrayList=array_dinamico();
                    for (Jugador ju: arrayList) {
                        System.out.println(ju.toString());
                    }
                    break;
                case 9:
                    mostrar_metadatos();
                    break;
            }


        } while (opcion > 0);
    }

    private static void mostrar_metadatos() throws SQLException {
        asignar_bd();
        DatabaseMetaData databaseMetaDatos=conn.getMetaData();
        ResultSet rs=databaseMetaDatos.getTables("EjercicioPractico","EjercicioPractico",null,null);
        while (rs.next()) {
            System.out.println(rs.getString(3));
        }
    }

    private static ArrayList<Jugador> array_dinamico() throws SQLException {
        asignar_bd();
        PreparedStatement ps=conn.prepareStatement("SELECT * FROM jugador");
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            arrayList.add(new Jugador(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getDouble(4),rs.getInt(5)));
        }
        return arrayList;
    }

    private static Jugador[] almacenar_array_estatico() throws SQLException {
        asignar_bd();
        int registros=0;
        PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) FROM jugador");
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            registros=rs.getInt(1);
        }
        ps=conn.prepareStatement("SELECT * FROM jugador");
        rs=ps.executeQuery();
        array_estatico_jugadores=new Jugador[registros];
        int contador=0;
        while (rs.next()){
            j = new Jugador(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getInt(5));
            array_estatico_jugadores[contador] = j;
            contador++;
        }
        return array_estatico_jugadores;
    }

    private static Jugador buscar_por_dorsal(int dorsal) throws SQLException {
        asignar_bd();
        Jugador j=null;
        PreparedStatement ps=conn.prepareStatement("SELECT * FROM jugador WHERE dorsal=?");
        ps.setInt(1,dorsal);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            j=new Jugador(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getDouble(4),rs.getInt(5));
        }
        return j;
    }

    private static void eliminar_jugador(String dni) throws SQLException {
        asignar_bd();
        PreparedStatement ps=conn.prepareStatement("DELETE FROM jugador WHERE dni=?");
        ps.setString(1,dni);
        ps.executeUpdate();
        System.out.println("jugador eliminado correctamente");
    }

    private static void insertar_jugador(Jugador jugador) throws SQLException {
        asignar_bd();
        PreparedStatement ps=conn.prepareStatement("INSERT INTO jugador VALUES(?,?,?,?,?)");
        ps.setString(1,jugador.getDni());
        ps.setString(2,jugador.getNombre());
        ps.setInt(3,jugador.getDorsal());
        ps.setDouble(4,jugador.getSalario());
        ps.setInt(5,jugador.getEdad());
        ps.executeUpdate();
        System.out.println("Jugador insertado correctamente");
    }

    private static void eliminar_tabla() throws SQLException {
        asignar_bd();
        String query="DROP TABLE jugador";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
    }

    private static void vaciar_tabla_jugador() throws SQLException {
        asignar_bd();
        String query="TRUNCATE jugador";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla vaciada correctamente");

    }

    private static void crear_tabla_jugador() throws SQLException {
        asignar_bd();
        Statement st=conn.createStatement();
        String query="CREATE TABLE jugador(" +
                "dni VARCHAR(9)," +
                "nombre VARCHAR(100)," +
                "dorsal INT," +
                "salario DOUBLE," +
                "edad INT,"+
                "CONSTRAINT pk_jugador PRIMARY KEY(dni))";
        st.executeUpdate(query);
        System.out.println("Tabla creada correctamente");

    }

    private static void crear_bd() throws SQLException {
        establecer_conexion();
        String query="CREATE DATABASE EjercicioPractico";
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
    private static void asignar_bd() throws SQLException {
        String query="USE EjercicioPractico";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
    }

}
