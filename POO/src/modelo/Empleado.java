/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author david
 */
public class Empleado extends Persona {
    private String codigo, puesto;
    private int id;
    Conexion cn;
    public Empleado(){}

    public Empleado(int id, String codigo, String puesto, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.codigo = codigo;
        this.puesto = puesto;
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void agregar(){
        try{
            PreparedStatement parametro1;
            PreparedStatement parametro2;
            ResultSet consulta;
            cn = new Conexion();
            cn.abrir_conexion();
            String query1 = "SELECT * FROM puestos where puesto = ?";
            parametro1 = (PreparedStatement) cn.conexionBD.prepareStatement(query1);
            parametro1.setString(1, getPuesto());
            consulta = parametro1.executeQuery();
            consulta.next();
            String id_puesto = consulta.getString("id_puesto");
            
            String query2 = "INSERT INTO empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto) VALUES (?,?,?,?,?,?,?)";
            parametro2 = (PreparedStatement) cn.conexionBD.prepareStatement(query2);
            parametro2.setString(1, getCodigo());
            parametro2.setString(2, getNombres());
            parametro2.setString(3, getApellidos());
            parametro2.setString(4, getDireccion());
            parametro2.setString(5, getTelefono());
            parametro2.setString(6, getFecha_nacimiento());
            parametro2.setString(7, id_puesto);
            int ejecutar = parametro2.executeUpdate();
            cn.cerrar_conexion();
             JOptionPane.showMessageDialog(null,Integer.toHexString(ejecutar)+ " Registros agregado","Agregar",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    }
    
    public ArrayList<String> llenar(){
        ArrayList<String> lista = new ArrayList<String>();
        ResultSet resultado;
        try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query = "SELECT * FROM puestos;";
        resultado = cn.conexionBD.createStatement().executeQuery(query);
        while(resultado.next()){
                lista.add(resultado.getString("puesto"));
            }
        cn.cerrar_conexion();
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        return lista;
    }
    
    public DefaultTableModel mostrar(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "Select e.id_empleado as id,e.codigo,e.nombres,e.apellidos,e.direccion,e.telefono,e.fecha_nacimiento,p.puesto from empleados as e inner join puestos as p on e.id_puesto = p.id_puesto order by id asc;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            
            String encabezado []={"id","codigo","nombres","apellido","direccion","telefono","nacimiento","puesto"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[8];
            while(consulta.next()){
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("puesto");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error..."+ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public void actualizar(){
        try{
            PreparedStatement parametro1;
            PreparedStatement parametro2;
            ResultSet consulta;
            cn = new Conexion();
            cn.abrir_conexion();
            String query1 = "SELECT * FROM puestos where puesto = ?";
            parametro1 = (PreparedStatement) cn.conexionBD.prepareStatement(query1);
            parametro1.setString(1, getPuesto());
            consulta = parametro1.executeQuery();
            consulta.next();
            String id_puesto = consulta.getString("id_puesto");
            String query2 = "update empleados set codigo=?,nombres=?,apellidos=?,direccion=?,telefono=?,fecha_nacimiento=?,id_puesto=? "+
                    "where id_empleado=?";
            parametro2 = (PreparedStatement) cn.conexionBD.prepareStatement(query2);
            parametro2.setString(1, getCodigo());
            parametro2.setString(2, getNombres());
            parametro2.setString(3, getApellidos());
            parametro2.setString(4, getDireccion());
            parametro2.setString(5, getTelefono());
            parametro2.setString(6, getFecha_nacimiento());
            parametro2.setString(7, id_puesto);
            parametro2.setInt(8, getId());
            int ejecutar = parametro2.executeUpdate();
            cn.cerrar_conexion();
             JOptionPane.showMessageDialog(null,Integer.toHexString(ejecutar)+ " Registros actualizados","Agregar",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    }
    
    @Override
    public void eliminar(){
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query2 = "delete from empleados where id_empleado=?";         
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query2);
            parametro.setInt(1, getId());
            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
             JOptionPane.showMessageDialog(null,Integer.toHexString(ejecutar)+ " Registro Eliminado","Agregar",JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            System.out.println("Error..."+ex.getMessage());
        }
    }

    
}
