/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import Utils.MsgBox;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
import Utils.Auth;

public class NhanVienJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChuyenDeJFrame
     */
    public NhanVienJFrame() {
        initComponents();
        init();
    }
    NhanVienDAO dao = new NhanVienDAO();
    int row = -1;

    void init() {
        setTitle("EduSys - Quản lý Nhân viên");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.filltable();
        this.row = -1;
        this.updateStatus();
        rdoNhanVien.setSelected(true);
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectAll();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNV(), nv.getMatKhau(), nv.getTenNV(), nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên"};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Loi truy van du lieu");
        }
    }

    void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getTenNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtXacNhan.setText(nv.getMatKhau());
        rdoNhanVien.setSelected(!nv.isVaiTro());
        rdoTruongPhong.setSelected(nv.isVaiTro());
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtHoTen.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setVaiTro(rdoTruongPhong.isSelected());
        return nv;
    }

    void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
    }

    void edit() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = dao.selectById(manv);
        this.setForm(nv);
        tabs.setSelectedIndex(0);
        this.updateStatus();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNhanVien.getRowCount() - 1);
        txtMaNV.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnDau.setEnabled(edit && !first);
        btnLui.setEnabled(edit && !first);
        btnCuoi.setEnabled(edit && !last);
        btnTien.setEnabled(edit && !last);
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Ban khong co quyen xoa nhan vien");
        } else {
            String manv = txtMaNV.getText();
            if (manv.equals(Auth.user.getMaNV())) {
                MsgBox.alert(this, "Ban khong duoc xoa chinh ban");
            } else if (MsgBox.comfirm(this, "Ban thuc su muon xoa nhan vien nay?")) {
                try {
                    dao.detele(manv);
                    this.filltable();
                    this.clearForm();
                    MsgBox.alert(this, "Xoa thanh cong");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xoa that bai");
                }
            }
        }
    }

    void insert() {
        NhanVien nv = getForm();
        String mk2 = new String(txtXacNhan.getPassword());
        if (!txtMaNV.getText().trim().isEmpty()) {
            if (!txtHoTen.getText().trim().isEmpty()) {
                if (!mk2.equals(nv.getMatKhau())) {
                    MsgBox.alert(this, "Xac nhan mat khau khong dung");
                } else {
                    try {
                        dao.insert(nv);
                        this.filltable();
                        this.clearForm();
                        MsgBox.alert(this, "Thêm thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Them moi that bai");
                    }
                }
            } else {
                MsgBox.alert(this, "Không để trống tên nhân viên!");
            }
        } else {
            MsgBox.alert(this, "Không để trống mã nhân viên!");
        }
    }

    void update() {
        NhanVien nv = getForm();
        String mk2 = new String(txtXacNhan.getPassword());
        if (!txtMaNV.getText().trim().isEmpty()) {
            if (!txtHoTen.getText().trim().isEmpty()) {
                if (!mk2.equals(nv.getMatKhau())) {
                    MsgBox.alert(this, "Xac nhan mat khau khong dung");
                } else {
                    try {
                        dao.update(nv);
                        this.filltable();
                        MsgBox.alert(this, "Cập nhật thành công !");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cap nhat that bai");
                    }
                }
            } else {
                MsgBox.alert(this, "Không để trống tên nhân viên!");
            }
        } else {
            MsgBox.alert(this, "Không để trống mã nhân viên!");
        }
    }

    void dau() {
        this.row = 0;
        this.edit();
    }

    void cuoi() {
        this.row = tblNhanVien.getRowCount() - 1;
        this.edit();
    }

    void tien() {
        if (this.row < tblNhanVien.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void lui() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        tab1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        btnTien = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btnDau = new javax.swing.JButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        rdoTruongPhong = new javax.swing.JRadioButton();
        txtMatKhau = new javax.swing.JPasswordField();
        txtXacNhan = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        jLabel4.setText("Mã nhân viên ");

        jLabel5.setText("Mật khẩu");

        jLabel6.setText("Xác nhận mật khẩu");

        jLabel7.setText("Họ tên");

        jLabel8.setText("Vai trò");

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Add to basket.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Notes.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Unordered list.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnCuoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/cuoi.png"))); // NOI18N
        btnCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoiActionPerformed(evt);
            }
        });

        btnTien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/tien.png"))); // NOI18N
        btnTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTienActionPerformed(evt);
            }
        });

        btnLui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/lui.png"))); // NOI18N
        btnLui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiActionPerformed(evt);
            }
        });

        btnDau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/dau.png"))); // NOI18N
        btnDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDauActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân viên");

        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setText("Trưởng phòng");

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                        .addComponent(btnDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLui)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCuoi))
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(tab1Layout.createSequentialGroup()
                                .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdoTruongPhong))
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(txtMatKhau)
                            .addComponent(txtXacNhan, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNhanVien)
                            .addComponent(rdoTruongPhong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnThem)
                                .addComponent(btnSua)
                                .addComponent(btnXoa)
                                .addComponent(btnMoi))
                            .addComponent(btnCuoi, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnTien, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLui, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDau, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(73, 73, 73))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Edit.png")), tab1); // NOI18N

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã NV", "Mật khẩu", "Họ tên", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(24);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tabs.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/icon/icon/List.png")), jPanel1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauActionPerformed
        // TODO add your handling code here:
        this.dau();
    }//GEN-LAST:event_btnDauActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        // TODO add your handling code here:
        this.lui();
    }//GEN-LAST:event_btnLuiActionPerformed

    private void btnTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTienActionPerformed
        // TODO add your handling code here:
        this.tien();
    }//GEN-LAST:event_btnTienActionPerformed

    private void btnCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiActionPerformed
        // TODO add your handling code here:
        this.cuoi();
    }//GEN-LAST:event_btnCuoiActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.filltable();
        this.updateStatus();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCuoi;
    private javax.swing.JButton btnDau;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTien;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JPanel tab1;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtXacNhan;
    // End of variables declaration//GEN-END:variables
}
