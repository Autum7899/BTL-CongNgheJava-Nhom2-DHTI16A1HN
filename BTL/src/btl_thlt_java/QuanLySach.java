/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package btl_thlt_java;

import static btl_thlt_java.MuonTra.setupTableAppearance;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Khanh
 */
public class QuanLySach extends javax.swing.JFrame {
    private int mouseX, mouseY;
    /**
     * Creates new form QuanLySach
     */
    public QuanLySach() {
        initComponents();
        setLocationRelativeTo(null);
        displayUsername.setText(UserInfo.loggedInUsername);
        setupTableAppearance(tb_qlsach);
        getContentPane().setBackground(new Color(251,249,228));
    }
    
    public void loadTableData() { // Bỏ throws SQLException, xử lý lỗi bên trong
         String sql = "SELECT ma_sach, tua_de, tac_gia, nam_xb, gia, so_luong, ma_nxb, ngon_ngu, tinh_trang FROM sach";

    try (Connection con = KN.KNDL();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        DefaultTableModel dtm = (DefaultTableModel) tb_qlsach.getModel();
        dtm.setRowCount(0); // Xóa dữ liệu cũ

        while (rs.next()) {
            Object[] row = {
                rs.getString("ma_sach"),      // Mã sách
                rs.getString("tua_de"),       // Tên sách
                rs.getString("tac_gia"),      // Tác giả
                rs.getInt("nam_xb"),         // Năm xuất bản (hiển thị dạng java.sql.Date)
                rs.getDouble("gia"),          // Giá
                rs.getInt("so_luong"),        // Số lượng
                rs.getString("ma_nxb"),       // Mã NXB
                rs.getString("ngon_ngu"),     // Ngôn ngữ
                rs.getString("tinh_trang")    // Tình trạng
            };
            dtm.addRow(row);
        }

    } catch (SQLException ex) {
        Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu sách: " + ex.getMessage(),
                "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void them() {
      String ma = txt_maS.getText().toUpperCase().trim();
    String tuaDe = txt_tenS.getText().trim();
    String tacGia = txt_tg.getText().trim();
    String namXBStr = txt_namXB.getText().trim();
    String giaStr = txt_gia.getText().trim();
    String slStr = txt_sl.getText().trim();
    String ngonNgu = txt_nn.getText().trim();
    String tinhTrang = txt_tt.getText().trim();
    String maNXB = txt_maNXB.getText().toUpperCase().trim();

    if (ma.isEmpty() || tuaDe.isEmpty() || tacGia.isEmpty() || namXBStr.isEmpty() || giaStr.isEmpty() || slStr.isEmpty() || ngonNgu.isEmpty() || tinhTrang.isEmpty() || maNXB.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int namXB, sl;
    double gia;
    try {
        if (!namXBStr.matches("\\d{4}")) {
            throw new NumberFormatException("Năm không đúng định dạng 4 chữ số.");
        }
        namXB = Integer.parseInt(namXBStr);
        gia = Double.parseDouble(giaStr);
        sl = Integer.parseInt(slStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập năm (YYYY), giá và số lượng đúng định dạng.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }
try (Connection con = KN.KNDL();
     PreparedStatement checkNXB = con.prepareStatement("SELECT 1 FROM nha_xuat_ban WHERE ma_nxb = ?")) {
    checkNXB.setString(1, maNXB);
    ResultSet rs = checkNXB.executeQuery();
    if (!rs.next()) {
        JOptionPane.showMessageDialog(this, "Mã NXB không tồn tại trong hệ thống.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra mã NXB: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
    return;
}
    String sql = "INSERT INTO sach (ma_sach, tua_de, tac_gia, nam_xb, gia, so_luong, ngon_ngu, tinh_trang, ma_nxb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = KN.KNDL();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, ma);
        pst.setString(2, tuaDe);
        pst.setString(3, tacGia);
        pst.setInt(4, namXB);  // Gán đúng kiểu INT cho cột YEAR
        pst.setDouble(5, gia);
        pst.setInt(6, sl);
        pst.setString(7, ngonNgu);
        pst.setString(8, tinhTrang);
        pst.setString(9, maNXB);

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm sách thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        if (ex.getSQLState().startsWith("23")) {
            JOptionPane.showMessageDialog(this, "Mã sách hoặc mã NXB không hợp lệ (trùng hoặc không tồn tại).", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi thêm sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    
    public void sua() {
     String ma = txt_maS.getText().toUpperCase().trim();
    String tuaDe = txt_tenS.getText().trim();
    String tacGia = txt_tg.getText().trim();
    String namXBStr = txt_namXB.getText().trim();
    String giaStr = txt_gia.getText().trim();
    String slStr = txt_sl.getText().trim();
    String ngonNgu = txt_nn.getText().trim();
    String tinhTrang = txt_tt.getText().trim();
    String maNXB = txt_maNXB.getText().toUpperCase().trim();

    if (ma.isEmpty() || tuaDe.isEmpty() || tacGia.isEmpty() || namXBStr.isEmpty() || giaStr.isEmpty() || slStr.isEmpty() || ngonNgu.isEmpty() || tinhTrang.isEmpty() || maNXB.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách để sửa.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int namXB, sl;
    double gia;
    try {
        if (!namXBStr.matches("\\d{4}")) {
            throw new NumberFormatException("Năm không đúng định dạng 4 chữ số.");
        }
        namXB = Integer.parseInt(namXBStr);
        gia = Double.parseDouble(giaStr);
        sl = Integer.parseInt(slStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập năm (YYYY), giá và số lượng đúng định dạng.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }
    try (Connection con = KN.KNDL();
     PreparedStatement checkNXB = con.prepareStatement("SELECT 1 FROM nha_xuat_ban WHERE ma_nxb = ?")) {
    checkNXB.setString(1, maNXB);
    ResultSet rs = checkNXB.executeQuery();
    if (!rs.next()) {
        JOptionPane.showMessageDialog(this, "Mã NXB không tồn tại trong hệ thống.", "Lỗi Nhập liệu", JOptionPane.WARNING_MESSAGE);
        return;
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra mã NXB: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
    return;
} 
    String sql = "UPDATE sach SET tua_de = ?, tac_gia = ?, nam_xb = ?, gia = ?, so_luong = ?, ngon_ngu = ?, tinh_trang = ?, ma_nxb = ? WHERE ma_sach = ?";

    try (Connection con = KN.KNDL();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, tuaDe);
        pst.setString(2, tacGia);
        pst.setInt(3, namXB);  // Gán INT cho YEAR
        pst.setDouble(4, gia);
        pst.setInt(5, sl);
        pst.setString(6, ngonNgu);
        pst.setString(7, tinhTrang);
        pst.setString(8, maNXB);
        pst.setString(9, ma);  // WHERE ma_sach = ?

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy Mã sách để cập nhật.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Lỗi CSDL khi sửa sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void xoa() {
            int selectedRow = tb_qlsach.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong bảng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String maCanXoa = tb_qlsach.getValueAt(selectedRow, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa sách có mã '" + maCanXoa + "'?",
            "Xác nhận Xóa",
            JOptionPane.YES_NO_OPTION);

    if (confirm != JOptionPane.YES_OPTION) return;

    String sql = "DELETE FROM sach WHERE ma_sach = ?";

    try (Connection con = KN.KNDL();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, maCanXoa);
        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy Mã sách để xóa.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, null, ex);
        if (ex.getSQLState().startsWith("23")) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể xóa sách vì đang được tham chiếu (ví dụ: đang có trong phiếu mượn).", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi CSDL khi xóa sách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }
        }
    
    // --- Phương thức xử lý click vào dòng trong bảng (cải tiến tbmouseClick()) ---
    // Đổi tên phương thức để rõ ràng hơn và bỏ throws SQLException
    public void populateFieldsFromTable() {
        int row = tb_qlsach.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (row == -1) {
            // Không làm gì nếu không có dòng nào được chọn
            return;
        }

        try {
            // Lấy dữ liệu từ các cột của dòng được chọn và hiển thị lên JTextFields
            // Đảm bảo thứ tự cột và tên cột trong bảng khớp với dữ liệu bạn lấy từ CSDL
            // và gán vào JTextFields tương ứng. Sử dụng toString() để đảm bảo là String.
            txt_maS.setText(tb_qlsach.getValueAt(row, 0).toString());
            txt_tenS.setText(tb_qlsach.getValueAt(row, 1).toString());
            txt_tg.setText(tb_qlsach.getValueAt(row, 2).toString());
            txt_namXB.setText(tb_qlsach.getValueAt(row, 3).toString());
            txt_gia.setText(tb_qlsach.getValueAt(row, 4).toString()); // Cột giá
            txt_sl.setText(tb_qlsach.getValueAt(row, 5).toString());   // Cột số lượng
            txt_maNXB.setText(tb_qlsach.getValueAt(row, 6).toString());
            txt_nn.setText(tb_qlsach.getValueAt(row, 7).toString());
            txt_tt.setText(tb_qlsach.getValueAt(row, 8).toString());

        } catch (Exception ex) { // Bắt Exception chung hơn phòng lỗi get/toString
            Logger.getLogger(QuanLySach.class.getName()).log(Level.SEVERE, "Lỗi khi lấy dữ liệu từ bảng", ex);
            // Tùy chọn: Thông báo lỗi cho người dùng nếu có lỗi khi đọc từ bảng
             JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ bảng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearInputFields() {
        txt_maS.setText("");
        txt_tenS.setText("");
        txt_tg.setText("");
        txt_namXB.setText("");
        txt_gia.setText("");
        txt_sl.setText("");
        txt_maNXB.setText("");
        txt_nn.setText("");
        txt_tt.setText("");
        // Tùy chọn: Bỏ chọn dòng trong bảng
        tb_qlsach.clearSelection();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnReturn = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        signout = new javax.swing.JLabel();
        displayUsername = new javax.swing.JLabel();
        txt_maS = new javax.swing.JTextField();
        txt_tenS = new javax.swing.JTextField();
        txt_tg = new javax.swing.JTextField();
        txt_namXB = new javax.swing.JTextField();
        txt_gia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_sl = new javax.swing.JTextField();
        txt_maNXB = new javax.swing.JTextField();
        txt_nn = new javax.swing.JTextField();
        txt_tt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_qlsach = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý sách");
        setIconImage(new ImageIcon(getClass().getResource("/Icon/Title.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setForeground(new java.awt.Color(0, 51, 102));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnReturn.setBackground(new java.awt.Color(0, 51, 102));
        btnReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/back.png"))); // NOI18N
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn.setOpaque(true);
        btnReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReturnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReturnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReturnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnReturnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnReturnMouseReleased(evt);
            }
        });
        jPanel2.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 60));

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 5, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Quản lý sách");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, 60));

        close.setBackground(new java.awt.Color(0, 51, 102));
        close.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        close.setText("X");
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.setOpaque(true);
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeMouseExited(evt);
            }
        });
        jPanel2.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 0, 60, 60));

        signout.setBackground(new java.awt.Color(0, 51, 102));
        signout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        signout.setForeground(new java.awt.Color(255, 255, 255));
        signout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/colorful-icons/arrow.png"))); // NOI18N
        signout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signout.setOpaque(true);
        signout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signoutMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signoutMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                signoutMouseReleased(evt);
            }
        });
        jPanel2.add(signout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 0, 60, 60));

        displayUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        displayUsername.setForeground(new java.awt.Color(255, 255, 255));
        displayUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/adminIcons/male_user_50px.png"))); // NOI18N
        displayUsername.setText("Username");
        jPanel2.add(displayUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 200, 60));

        txt_maS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maSActionPerformed(evt);
            }
        });

        txt_tenS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenSActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Mã sách");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Tên sách");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Tác giả");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Năm xuất bản");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Giá");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setText("Số lượng");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Mã NXB");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("Ngôn ngữ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Tình trạng");

        txt_sl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_slActionPerformed(evt);
            }
        });

        tb_qlsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Tác giả", "Năm xuất bản", "Giá", "Số lượng", "Mã NXB", "Ngôn ngữ", "Tình trạng"
            }
        ));
        tb_qlsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_qlsachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb_qlsach);

        btnThem.setBackground(new java.awt.Color(51, 102, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setPreferredSize(new java.awt.Dimension(100, 27));
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemMouseExited(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(51, 102, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setPreferredSize(new java.awt.Dimension(100, 27));
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaMouseExited(evt);
            }
        });
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(51, 102, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Làm mới");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new java.awt.Dimension(100, 27));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnResetMouseExited(evt);
            }
        });
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(51, 102, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSua.setPreferredSize(new java.awt.Dimension(100, 27));
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSuaMouseExited(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_gia, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_tenS, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tg, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_maS, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_maNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nn, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_tt, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_sl, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(75, 75, 75))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1150, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_gia, txt_maNXB, txt_maS, txt_namXB, txt_nn, txt_sl, txt_tenS, txt_tg, txt_tt});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_maS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tenS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_tg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_namXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_gia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_sl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_maNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_nn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_tt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_gia, txt_maNXB, txt_maS, txt_namXB, txt_nn, txt_sl, txt_tenS, txt_tg, txt_tt});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tenSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenSActionPerformed

    private void txt_maSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maSActionPerformed

    private void txt_slActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_slActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_slActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // Khi form mở, tải dữ liệu vào bảng
        loadTableData(); // Gọi phương thức tải dữ liệu đã cải tiến

        // Tùy chọn: Thiết lập Header và màu cho bảng ngay khi form activated
        setupTableAppearance(tb_qlsach);
         // Đảm bảo bạn đã import java.awt.Color và java.awt.Font
    }//GEN-LAST:event_formWindowActivated

    private void tb_qlsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_qlsachMouseClicked
        populateFieldsFromTable();
    }//GEN-LAST:event_tb_qlsachMouseClicked

    private void btnReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseClicked

        new HomePage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnReturnMouseClicked

    private void btnReturnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseEntered
        btnReturn.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_btnReturnMouseEntered

    private void btnReturnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseExited
        btnReturn.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_btnReturnMouseExited

    private void btnReturnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMousePressed
        btnReturn.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnReturnMousePressed

    private void btnReturnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReturnMouseReleased
        btnReturn.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_btnReturnMouseReleased

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseEntered
        close.setBackground(new Color(255,0,0));
    }//GEN-LAST:event_closeMouseEntered

    private void closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseExited
        close.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_closeMouseExited

    private void signoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseClicked
        new Login() .setVisible(true);
        this.dispose();
    }//GEN-LAST:event_signoutMouseClicked

    private void signoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseEntered
        signout.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_signoutMouseEntered

    private void signoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseExited
        signout.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_signoutMouseExited

    private void signoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMousePressed
        signout.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_signoutMousePressed

    private void signoutMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signoutMouseReleased
        signout.setBackground(new Color(0,51,102));
    }//GEN-LAST:event_signoutMouseReleased

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetMouseEntered

    private void btnResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResetMouseExited

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txt_maS.setText("");
        txt_gia.setText("");
        txt_maNXB.setText("");
        txt_namXB.setText("");
        txt_nn.setText("");
        txt_sl.setText("");
        txt_tenS.setText("");
        txt_tg.setText("");
        txt_tt.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        sua();
    }//GEN-LAST:event_btnSuaActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel btnReturn;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel close;
    private javax.swing.JLabel displayUsername;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel signout;
    private javax.swing.JTable tb_qlsach;
    private javax.swing.JTextField txt_gia;
    private javax.swing.JTextField txt_maNXB;
    private javax.swing.JTextField txt_maS;
    private javax.swing.JTextField txt_namXB;
    private javax.swing.JTextField txt_nn;
    private javax.swing.JTextField txt_sl;
    private javax.swing.JTextField txt_tenS;
    private javax.swing.JTextField txt_tg;
    private javax.swing.JTextField txt_tt;
    // End of variables declaration//GEN-END:variables
}
