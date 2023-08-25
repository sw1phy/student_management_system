package Estudiantes;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Score {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    //get table max row
    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);
                id = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;

    }

    public boolean getDetails(int sid, int semesterNo) {
        try {
            ps = con.prepareStatement("select * from course where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.jTextField14.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField16.setText(String.valueOf(rs.getInt(3)));
                Home.jTextCourse1.setText(rs.getString(4));
                Home.jTextCourse2.setText(rs.getString(5));
                Home.jTextCourse3.setText(rs.getString(6));
                Home.jTextCourse4.setText(rs.getString(7));
                Home.jTextCourse5.setText(rs.getString(8));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "La ID o el número de semestre del estudiante no existe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

}
