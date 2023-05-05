import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.UIManager.getString;

public class Main {
    static Connection conn=null;
    public static Scanner sc=new Scanner(System.in);
    public static ArrayList<Paciente> al_paciente=new ArrayList<Paciente>();


    public static void main(String[] args) throws SQLException {
        int opcion=0;
        do{
            System.out.println("0. Salir del programa");
            System.out.println("1. Establecer conexion con sgbd");
            System.out.println("2. Crear la bd ut14");
            System.out.println("3. Crear la tabla paciente");
            System.out.println("4. Eliminar la tabla paciente");
            System.out.println("5. Insertar paciente en la tabla paciente");
            System.out.println("6. Vacia la tabla paciente");
            System.out.println("7. Eliminar por dni introducido por teclado");
            System.out.println("8. Actualizar numero de operaciones");
            System.out.println("9. Consultar datos de la tabla paciente");
            System.out.println("10. Insertar paciente en la tabla paciente mediante objeto");
            System.out.println("11. Almacenar en un arraylist los pacientes de la tabla");

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
                case 4:
                    borrar_tabla();
                    break;
                case 5:
                    insertar_registro();
                    break;
                case 6:
                    truncate_table();
                    break;
                case 7:
                    borrar_registro();
                    break;

                case 9:
                    consultar_tabla();
                    break;
                case 10:
                    insertar_registro_objeto(pedir_info());
                    break;
                case 11:
                    almacenar_arraylist();
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

    private static void almacenar_arraylist() throws SQLException {
        asignar();
        PreparedStatement ps=conn.prepareStatement("select * from paciente");
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            al_paciente.add(new Paciente(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        for(Paciente p:al_paciente){
            System.out.println(p.toString());
        }
    }

    private static Paciente pedir_info() {
        //generar un objeto de Paciente con los datos introducidos por teclado
        System.out.println("Introduzca el dni");
        String dni=sc.next();
        System.out.println("Introduzca el nombre");
        String nombre=sc.next();
        System.out.println("Introduzca el apellido");
        String apellido=sc.next();
        System.out.println("Introduzca el numero de operaciones");
        int n_operaciones=sc.nextInt();
        Paciente p=new Paciente(dni,nombre,apellido,n_operaciones);
        return p;
    }
    private static void insertar_registro_objeto(Paciente p) throws SQLException {
        asignar();
        PreparedStatement ps=conn.prepareStatement("insert into paciente values(?,?,?,?)");
        ps.setString(1,p.getDni());
        ps.setString(2,p.getNombre());
        ps.setString(3,p.getApellido());
        ps.setInt(4,p.getN_operaciones());

        ps.executeUpdate();
        System.out.println("Registro insertado correctamente");

    }

    private static void consultar_tabla() throws SQLException {
        asignar();
        //System.out.println("Indicame el usuario que quiere imprimir por pantalla");
        //String dni=sc.next();
        //PreparedStatement ps=conn.prepareStatement("select * from paciente where dni=?");
        PreparedStatement ps=conn.prepareStatement("select * from paciente");

        //ps.setString(1,dni);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            System.out.println("El paciente con dni "+rs.getString(1)+
                    " nombre: "+rs.getString(2)+" y apellido: "+rs.getString(3)+
                    " con un total de "+rs.getInt(4)+" operaciones");
        }
    }

    private static void actualizar_registro() throws SQLException {
        asignar();
        System.out.println("Introduzca el dni del paciente a actualizar");
        String dni=sc.next();
        System.out.println("Introduzca el valor corecto del numero de operaciones");
        int n_operaciones=sc.nextInt();
        PreparedStatement ps=conn.prepareStatement("update paciente set n_operaciones=? where dni=?");
        ps.setInt(1,n_operaciones);
        ps.setString(2,dni);
        ps.executeUpdate();
        System.out.println("Registro actualizado correctamente");
    }

    private static void borrar_registro() throws SQLException {
        asignar();
        System.out.println("Introduzca el dni del paciente a borrar");
        String dni=sc.next();
        PreparedStatement ps=conn.prepareStatement("delete from paciente where dni=?");
        ps.setString(1,dni);
        ps.executeUpdate();
        System.out.println("Registro borrado correctamente");
    }

    private static void truncate_table() throws SQLException {
        asignar();
        String query="truncate table paciente";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla vaciada correctamente");

    }

    private static void insertar_registro() throws SQLException {
        asignar();
        PreparedStatement ps=conn.prepareStatement("insert into paciente values(?,?,?,?)");
        System.out.println("Introduzca el dni");
        String dni=sc.next();
        System.out.println("Introduzca el nombre");
        String nombre=sc.next();
        System.out.println("Introduzca el apellido");
        String apellido=sc.next();
        System.out.println("Introduzca el numero de operaciones");
        int n_operaciones=sc.nextInt();
        ps.setString(1,dni);
        ps.setString(2,nombre);
        ps.setString(3,apellido);
        ps.setInt(4,n_operaciones);
        ps.executeUpdate();
        System.out.println("Registro insertado correctamente");

    }

    private static void borrar_tabla() throws SQLException {
        asignar();
        String query="drop table paciente";
        Statement st=conn.createStatement();
        st.executeUpdate(query);
        System.out.println("Tabla borrada correctamente");
    }

    private static void crear_tabla() throws SQLException {
        asignar();
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
        asignar();

    }
    private static void asignar() throws SQLException {
        //asignar la bbdd
        String query="use ut14";
        Statement st=conn.createStatement();
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