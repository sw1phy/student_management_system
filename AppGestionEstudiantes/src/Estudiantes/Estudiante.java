package Estudiantes;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Estudiante {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    //get table max row
    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from student");
            while (rs.next()) {
                id = rs.getInt(1);
                id = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;

    }

    //insertar datos en la tabla de estudiantes
    public void insert(int id, String name, String date, String gender, String email, String phone, String father, String mother, String adress1, String adress2, String imagePath) {
        String sql = "insert into student values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareCall(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, father);
            ps.setString(8, mother);
            ps.setString(9, adress1);
            ps.setString(10, adress2);
            ps.setString(11, imagePath);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Estudiante agregado con éxito");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //revisar si el email del estudiante ya existe
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from student where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //revisar si el número celular ya existe
    public boolean isPhoneExist(String phone) {
        try {
            ps = con.prepareStatement("select * from student where phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //revisar si el número celular ya existe
    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //obtener todos los datos de la tabla students
    public void getStudentValue(JTable table, String searchValue) {
        String sql = "select * from student where concat(id,name,email,phone)like ? order by id desc";
        try {
            ps = con.prepareCall(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[11];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //actualizar los datos del estudiante
    public void update(int id, String name, String date, String gender, String email, String phone, String father, String mother, String adress1, String adress2, String imagePath) {
        String sql = "update student set name=?,date_of_birth=?,gender=?,email=?,phone=?,father_name=?,mother_name=?,address1=?,address2=?,image_path=? where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, father);
            ps.setString(7, mother);
            ps.setString(8, adress1);
            ps.setString(9, adress2);
            ps.setString(10, imagePath);
            ps.setInt(11, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "La información del estudiante se ha actualizado con éxito");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //borrar registro de estudiante
    public void delete(int id) {
        int yesOrNO = JOptionPane.showConfirmDialog(null, "Los datos personales y el historial académico del estudiante serán eliminados", "Eliminar Registro de Estudiante", JOptionPane.OK_CANCEL_OPTION, 0);
        if (yesOrNO == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from student where id = ? ");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "El registro del estudiante se ha eliminado con éxito");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
