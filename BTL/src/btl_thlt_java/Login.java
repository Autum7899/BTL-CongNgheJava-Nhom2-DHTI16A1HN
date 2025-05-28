
package btl_thlt_java;

import java.awt.Color;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;



public class Login extends javax.swing.JFrame {

//    private static final String DB_URL = "jdbc:mysql://localhost:3307/qlthuvien";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
    
    
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
       
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_tk = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_mk = new javax.swing.JPasswordField();
        Login_btn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        switchSignUp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setIconImage(new ImageIcon(getClass().getResource("/Icon/Title.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(0, 51, 102));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Phần mềm quản lý ");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Logo-DH-Kinh-te-Ky-thuat-Cong-nghiep-UNETI (1).png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Thư viện");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                .addGap(0, 36, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(40, 40, 40))
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(63, 63, 63)
                        .addComponent(jLabel10))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addGap(32, 32, 32)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(255, 255, 255));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("ĐĂNG NHẬP");

        jLabel2.setBackground(new java.awt.Color(102, 102, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tài khoản");

        txt_tk.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_tk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tkActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mật khẩu");

        txt_mk.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txt_mk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_mkActionPerformed(evt);
            }
        });

        Login_btn.setBackground(new java.awt.Color(51, 102, 255));
        Login_btn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Login_btn.setForeground(new java.awt.Color(255, 255, 255));
        Login_btn.setText("Đăng nhập");
        Login_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Login_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Login_btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Login_btnMouseExited(evt);
            }
        });
        Login_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login_btnActionPerformed(evt);
            }
        });

        jLabel8.setText("Chưa có tài khoản?");

        switchSignUp.setForeground(new java.awt.Color(0, 0, 153));
        switchSignUp.setText("Đăng kí");
        switchSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        switchSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switchSignUpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                switchSignUpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                switchSignUpMouseExited(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel1))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_mk, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(txt_tk)
                            .addGroup(LeftLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(switchSignUp))
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(Login_btn))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_tk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_mk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(Login_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(switchSignUp))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tkActionPerformed

    private void Login_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login_btnActionPerformed
        String username = txt_tk.getText();
        String password = new String(txt_mk.getPassword()); // Mật khẩu thô người dùng nhập

        // Kiểm tra rỗng
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Gọi phương thức kiểm tra đăng nhập (giờ so sánh mật khẩu thô)
        if (checkLogin(username, password)) {
            // Đăng nhập thành công
            UserInfo.loggedInUsername = username; // Lưu username nếu cần cho các form khác
            HomePage homePage = new HomePage();
            homePage.setVisible(true);

            this.dispose();

        } else {
            // Đăng nhập thất bại
            // checkLogin trả về false nếu không tìm thấy username HOẶC mật khẩu không khớp
            // Thông báo lỗi sai tài khoản/mật khẩu
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không đúng", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Login_btnActionPerformed

    private void switchSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchSignUpMouseClicked
        // Xử lý khi click vào Label "Đăng ký" (ví dụ)
        SignUp SignUpFrame = new SignUp(); // Tạo instance form Đăng ký
        SignUpFrame.setVisible(true); // Hiển thị form Đăng ký
        SignUpFrame.pack(); // Điều chỉnh kích thước form
        SignUpFrame.setLocationRelativeTo(null); // Đặt form Đăng ký ở giữa màn hình

        this.dispose(); // Đóng form Đăng nhập hiện tại
    }//GEN-LAST:event_switchSignUpMouseClicked

    private void txt_mkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_mkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_mkActionPerformed

    private void Login_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Login_btnMouseEntered
       setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // TODO add your handling code here:
    }//GEN-LAST:event_Login_btnMouseEntered

    private void switchSignUpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchSignUpMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (switchSignUp != null) { // Kiểm tra biến đã được khởi tạo chưa
             switchSignUp.setForeground(Color.BLACK);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_switchSignUpMouseEntered

    private void Login_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Login_btnMouseExited
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));// TODO add your handling code here:
    }//GEN-LAST:event_Login_btnMouseExited

    private void switchSignUpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_switchSignUpMouseExited
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (switchSignUp != null) { // Kiểm tra biến đã được khởi tạo chưa
            switchSignUp.setForeground(Color.BLUE); // Giả sử màu mặc định là BLUE
        }
    }//GEN-LAST:event_switchSignUpMouseExited

    /**
     * @param args the command line arguments
     */
        private boolean checkLogin(String username, String password) {
        String sql = "SELECT password, quyen_han FROM nguoi_dung WHERE username = ?";

try (Connection con = KN.KNDL();
     PreparedStatement pst = con.prepareStatement(sql)) {

    pst.setString(1, username);

    try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            String storedPassword = rs.getString("password");

            // So sánh mật khẩu người dùng nhập với mật khẩu đã lưu
            if (password.equals(storedPassword)) {
                // Lấy quyền hạn và lưu vào biến static
                UserInfo.loggedInRole = rs.getString("quyen_han");

                return true; // Đăng nhập thành công
            } else {
                return false; // Sai mật khẩu
            }
        } else {
            return false; // Username không tồn tại
        }
    }

} catch (SQLException e) {
    JOptionPane.showMessageDialog(null,
                                  "Lỗi CSDL khi kiểm tra đăng nhập: " + e.getMessage(),
                                  "Lỗi Database",
                                  JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
    return false;
    }}
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JButton Login_btn;
    private javax.swing.JPanel Right;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel switchSignUp;
    private javax.swing.JPasswordField txt_mk;
    private javax.swing.JTextField txt_tk;
    // End of variables declaration//GEN-END:variables
}


