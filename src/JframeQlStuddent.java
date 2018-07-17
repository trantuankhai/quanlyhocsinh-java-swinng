
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class JframeQlStuddent extends javax.swing.JFrame {

    /**
     * Creates new form JframeQlStuddent
     */
    ArrayList<Student> listsv = getdatebasestudent();
    Connection con;
    int index;
    DefaultTableModel model = new DefaultTableModel();
//    String linkanh = "";

    public JframeQlStuddent() {
        initComponents();
        index = 1;
        model = (DefaultTableModel) jTable2.getModel();
        settable();
        showdetail(index);
        btnsave.setEnabled(false);

    }

    public void settable() {
        model.setRowCount(0);
        for (Student x : listsv) {
            model.addRow(new Object[]{x.getID(), x.getName(), x.getEmail(), x.getPhone(), x.getSex(), x.getAddress(), x.getAvartar()});
        }

    }

    public void showdetail(int index) {
        Student get = listsv.get(index);
        tfmasv.setText(get.getID());
        tfemail.setText(get.getEmail());
        tfname.setText(get.getName());
        tfphone.setText(get.getPhone());
        int gt = get.getSex();
        if (gt == 0) {
            rdo1.setSelected(true);
        } else {
            rdo2.setSelected(true);
        }
        textarea.setText(get.getAddress());
        jTable2.setRowSelectionInterval(index, index);
        lblanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Avartar/" + get.getAvartar())));

    }

    public void deletedtb() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "DELETE FROM STUDENT WHERE MASV = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
            pr.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            listsv = getdatebasestudent();
            settable();
            clear();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể xóa, Lỗi xung đột dữ liệu với bảng điểm");
        }
    }

    public ArrayList<Student> getdatebasestudent() {
        ArrayList<Student> l = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();

            String sql1 = "SELECT * FROM STUDENT";

            ResultSet rs1 = st.executeQuery(sql1);
            while (rs1.next()) {
                l.add(new Student(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getInt(5), rs1.getString(6), rs1.getString(7)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public void insertdtb() {
        String linkanh = JOptionPane.showInputDialog("Nhập link Avatar");
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "insert into STUDENT values(?,?,?,?,?,?,?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfmasv.getText());
            pr.setString(2, tfname.getText());
            pr.setString(3, tfemail.getText());
            pr.setString(4, tfphone.getText());
            pr.setInt(5, getgt());
            pr.setString(6, textarea.getText());
            pr.setString(7, linkanh);
            pr.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, " Thêm đối tượng thành công");
            listsv = getdatebasestudent();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updaatedatabase() {

        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE STUDENT SET MASV = ? ,NAME = ? , EMAIL = ? , PHONE = ? , GIOITINH = ? ,DIACHI = ?  WHERE MASV = ?  ";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfmasv.getText());
            pr.setString(2, tfname.getText());
            pr.setString(3, tfemail.getText());
            pr.setString(4, tfphone.getText());
            pr.setInt(5, getgt());
            pr.setString(6, textarea.getText());
//            pr.setString(7,linkanh );

            pr.setString(7, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
            pr.executeUpdate();

            con.close();
            JOptionPane.showMessageDialog(this, "Cập nhập thành công");
            listsv = getdatebasestudent();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        tfemail.setText("");
        tfmasv.setText("");
        tfname.setText("");
        tfphone.setText("");
        textarea.setText("");
        btndelete.setEnabled(false);
        btnupdate.setEnabled(false);
        btnsave.setEnabled(true);
        lblanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Avartar/" + "")));
    }

    public boolean validate1() {
        if (tfmasv.getText().equals("")) {
            tfmasv.requestFocus();
            JOptionPane.showMessageDialog(this, "Mã SV không được rỗng");
            return false;

        } else if (tfname.getText().equals("")) {
            tfname.requestFocus();;
            JOptionPane.showMessageDialog(this, "Tên không được rỗng");
            return false;
        } else if (!tfemail.getText().matches("\\w+@\\w+\\.\\w+")) {
            tfemail.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa đúng định dạng email: abc@gmail.com");
            return false;

        } else if (tfphone.getText().length() < 9 || tfphone.getText().length() > 11) {

            tfphone.requestFocus();
            JOptionPane.showMessageDialog(this, "Số điện thoại từ 10 đến 1  số ");
            return false;

        } else if (!rdo1.isSelected() && !rdo2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính");
            return false;

        } else if (textarea.getText().equals("")) {
            textarea.getText().equals("");
            JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng");
            return false;

        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfmasv = new javax.swing.JTextField();
        tfname = new javax.swing.JTextField();
        tfemail = new javax.swing.JTextField();
        tfphone = new javax.swing.JTextField();
        rdo1 = new javax.swing.JRadioButton();
        rdo2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        lblanh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(5, 5));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản Lý Sinh Viên");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Mã Sv :");

        jLabel3.setText("Họ Và Tên : ");

        jLabel4.setText("Email :");

        jLabel5.setText("Số Điện Thoại :");

        jLabel6.setText("Giới Tính :");

        jLabel7.setText("Địa Chỉ :");

        tfemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfemailActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo1);
        rdo1.setText("Nam");

        buttonGroup1.add(rdo2);
        rdo2.setText("Nữ");

        textarea.setColumns(20);
        textarea.setRows(5);
        jScrollPane1.setViewportView(textarea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(tfmasv)
                    .addComponent(tfname)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdo2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tfemail)
                    .addComponent(tfphone))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfmasv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdo1)
                    .addComponent(rdo2))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ và Tên", "Email", "SDT", "Giới Tính", "Địa Chỉ", "Avatar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_add.png"))); // NOI18N
        btnnew.setText("New");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_save.png"))); // NOI18N
        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_delete.png"))); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Transfermanage.png"))); // NOI18N
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnnew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btndelete))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnsave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnupdate)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnew)
                    .addComponent(btndelete))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdate)
                    .addComponent(btnsave))
                .addContainerGap())
        );

        lblanh.setMaximumSize(new java.awt.Dimension(5, 5));
        lblanh.setMinimumSize(new java.awt.Dimension(5, 5));
        lblanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblanhMouseClicked(evt);
            }
        });

        jButton1.setText("Thay Avatar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(101, 101, 101))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfemailActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            updaatedatabase();
        }

    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không", "xóa", JOptionPane.YES_NO_OPTION, 3);
        if (chon == JOptionPane.YES_OPTION) {
            deletedtb();
        }

    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnnewActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        index = jTable2.getSelectedRow();
        showdetail(index);
        btnupdate.setEnabled(true);
        btndelete.setEnabled(true);
        btnsave.setEnabled(false);


    }//GEN-LAST:event_jTable2MouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            insertdtb();
        }

    }//GEN-LAST:event_btnsaveActionPerformed

    private void lblanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblanhMouseClicked
        // TODO add your handling code here:
        String anh = JOptionPane.showInputDialog("Nhận Link Ảnh");

//        if (!anh.equals("")) {
//            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
//            String username = "sa";
//            String password = "123";
//            try {
//                Class.forName(driver);
//                con = DriverManager.getConnection(url, username, password);
//                String sql = "UPDATE STUDENT SET AVATAR = ? WHERE MASV = ?  ";
//                PreparedStatement pr = con.prepareStatement(sql);
//                pr.setString(1, anh);
//                pr.setString(2, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
//                JOptionPane.showMessageDialog(this, "Cập nhập ảnh thành công");
//                con.close();
//                listsv = getdatebasestudent();
//                settable();
//                showdetail(index);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }//GEN-LAST:event_lblanhMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String anh = JOptionPane.showInputDialog("Nhận Link Ảnh");

        if (!anh.equals("")  ) {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
            String username = "sa";
            String password = "123";
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);
                String sql = "UPDATE STUDENT SET AVATAR = ? WHERE MASV = ?";
                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1, anh);
                pr.setString(2, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
                pr.executeUpdate();
                con.close();
                listsv = getdatebasestudent();
                settable();
                index = 0;
                showdetail(index);
                JOptionPane.showMessageDialog(this, "Cập nhập avatar thành công");
                
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    

    public int getgt() {
        int gt;
        if (rdo1.isSelected()) {
            gt = 0;
        } else {
            gt = 1;
        }
        return gt;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JframeQlStuddent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JframeQlStuddent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JframeQlStuddent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JframeQlStuddent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JframeQlStuddent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblanh;
    private javax.swing.JRadioButton rdo1;
    private javax.swing.JRadioButton rdo2;
    private javax.swing.JTextArea textarea;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfmasv;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfphone;
    // End of variables declaration//GEN-END:variables
}
