package EjercicioSimulacroExamen;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc=new Scanner(System.in);
    static Emisora [] listado_emisoras;
    static ArrayList<Emisora> arraylist_emisoras=new ArrayList<Emisora>();
    static Connection conn=null;
    public static void main(String[] args) throws SQLException, AccionInvalida {
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
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root","admin");
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
                    EmisoraOnline e=new EmisoraOnline(1,"Los 40", 1000,"www.los40.com");
                    /*System.out.println("introduzca el identificador de la emisora");
                    int num_emisora=sc.nextInt();
                    System.out.println("introduzca el nombrede la emisora");
                    String nombre_emisora=sc.next();
                    System.out.println("introduzca el numero de oyentes de la emisora");
                    int num_oyentes=sc.nextInt();
                    System.out.println("introduzca la url de la emisora");
                    String url=sc.next();
                    EmisoraOnline e1=new EmisoraOnline(num_emisora,nombre_emisora,num_oyentes,url);
                    */
                    insertar_emisora(e);

                    break;
                case 4:
                    System.out.println("Introduzca el limite de numero de oyentes");
                    buscar_añadir_array_estatico(sc.nextInt());
                    //System.out.println(Arrays.toString(listado_emisoras));
                    for (int i=0;i<listado_emisoras.length;i++){
                        System.out.println(listado_emisoras[i].toString());
                    }
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

    private static void buscar_añadir_array_estatico(int num_oyentes) throws SQLException, AccionInvalida {
        asignar_bd();
        PreparedStatement ps=conn.prepareStatement("SELECT COUNT(*) FROM emisoraonline WHERE num_oyentes<?");
        ps.setInt(1,num_oyentes);
        ResultSet rs=ps.executeQuery();
        int dimension=0;
        while (rs.next()){
            dimension=rs.getInt(1);
        }
        listado_emisoras= new EmisoraOnline[dimension];
        ps=conn.prepareStatement("SELECT * FROM emisoraonline WHERE num_oyentes<?");
        ps.setInt(1,num_oyentes);
        rs=ps.executeQuery();
        int i=0;
        while (rs.next()){
                Emisora e = new EmisoraOnline(rs.getInt(1), rs.getString(2), rs.getInt(5), rs.getString(6));
                listado_emisoras[i]=e;
                i++;
        }
    }

    private static void añadir_arraylist_beneficios() throws SQLException, AccionInvalida {
        // solo añadiremos las radios cuyos beneficios superen los 300 euros
        asignar_bd();
        final int CONSTANTE_BENEFICIOS=3000;
        PreparedStatement ps=conn.prepareStatement( "SELECT * FROM emisoraonline");
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            if (rs.getDouble(4)>CONSTANTE_BENEFICIOS){
            Emisora e=new EmisoraOnline(rs.getInt(1),rs.getString(2),rs.getInt(5),rs.getString(6));
            arraylist_emisoras.add(e);
            }
        }


    }

    private static void insertar_emisora(EmisoraOnline e) throws SQLException {
        asignar_bd();
        PreparedStatement ps= conn.prepareStatement("INSERT INTO emisoraonline VALUES (?,?,?,?,?,?)");
        ps.setInt(1,e.getNum_emisora());
        ps.setString(2,e.getNombre_emisora());
        ps.setBoolean(3,e.isEmitiendo());
        ps.setDouble(4,e.getBeneficios());
        ps.setInt(5,e.getNum_oyentes());
        ps.setString(6,e.getUrl());
        ps.executeUpdate();
        System.out.println("Emisora insertada correctamente");
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

