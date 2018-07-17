/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;

/**
 *
 * @author Admin
 */
public class DBconector {

    // Đối tượng connection để tạo kết nối
    private Connection con;

    public void openconnection() throws ClassNotFoundException {
        //Chuỗi kết nối với CƠ Sở dữ liệu
        String URL = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String username = "sa";
        String password = "123";
        try {
            //Mở kết nối
            Class.forName(driver);
            //Kiểm tra thông tin kết nối
            con = DriverManager.getConnection(URL, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBconector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //viết hàm tạo lất tất cả các giá trị của DTB
    public ArrayList<student> getall() {
        ArrayList<student> re = new ArrayList<>();
        //tạo câu truy vấn
        String sql = "select *from STUDENT";
        try {
            //mở kết nối
            openconnection();
            //tạo đối tượng statement để chạy câu lệnh truy vấn
            Statement st = con.createStatement();
            //Chạy câu lệnh truy cấn trả về resault
            ResultSet rs = st.executeQuery(sql);
            //lấy ra từng bản ghi sinh viên
            while (rs.next()) {
                re.add(new student(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
            }
            // Đóng kết nối
            st.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return re;
    }

    public void inse(String ID, String name, String emmail, String phone, int gt,
            String diachi, String avatar) {
        String sql = "insert into STUSENT values(?,?,?,?,?,?,?)";
        try {
            //mở kết nối
            openconnection();
            //tạo đối tượng statement để chạy câu lệnh truy vấn
            PreparedStatement st = con.prepareStatement(sql);
            //thêm tham số
            st.setString(1, ID);
            st.setString(2,name );
            st.setString(3, emmail);
            st.setString(4, phone);
            st.setInt(5, gt);
            st.setString(6, diachi);
            st.setString(7, avatar);
            st.execute();

            //Chạy câu lệnh truy cấn trả về resault
//            ResultSet rs = st.executeQuery(sql);
            //lấy ra từng bản ghi sinh viên
//            while (rs.next()) {
//                re.add(new student(rs.getString(1), rs.getString(2), rs.getString(3),
//                        rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
//            }
            // Đóng kết nối
            st.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
      
    }

}
