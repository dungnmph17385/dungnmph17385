/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.ChuyenDeDAO;
import DAO.KhoaHocDAO;
import Entity.ChuyenDe;
import Entity.KhoaHoc;
import Utils.Auth;
import Utils.MsgBox;
import Utils.XDate;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class KhoaHocJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChuyenDeJFrame
     */
    public KhoaHocJFrame() {
        initComponents();
        setTitle("EduSys - Quản lý khóa học");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init();
    }
    int row ;
    KhoaHocDAO dao ;
    ChuyenDeDAO cddao ;

    public void init() {
        dao = new KhoaHocDAO();
        cddao = new ChuyenDeDAO();
        row = -1;
        fillComboBox();
    }

    void fillTable() {
//        DefaultTableModel model = (DefaultTableModel) tblKH.getModel();
//        model.setRowCount(0);
//        try {
//            ChuyenDe cd = (ChuyenDe) cboChuyenDeKH.getSelectedItem();
//            List<KhoaHoc> list = dao.selectByChuyenDe(cd);
//            for (KhoaHoc kh : list) {
//                Object[] row = {
//                    kh.getMaKH(),
//                    kh.getHocPhi(),
//                    kh.getThoiLuong(),
//                    kh.getNgayKG(),
//                    kh.getMaNV(),
//                    kh.getNgayTao()
//                };
//                model.addRow(row);
//            }
//            tblKH.setModel(model);
//        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!1");
//        }
            DefaultTableModel model = (DefaultTableModel) tblKH.getModel();
        model.setRowCount(0);
        ChuyenDe cd = (ChuyenDe) cboChuyenDeKH.getSelectedItem();
        List<KhoaHoc> ls = dao.selectByChuyenDe(cd);
        System.out.println("ls khoa hoc:" + ls);
        if (ls != null) {
            for (KhoaHoc l : ls) {
                model.addRow(new Object[]{
                    l.getMaKH(), l.getHocPhi(), l.getThoiLuong(), l.getNgayKG(), l.getMaNV(), l.getNgayTao()
                });
            }
            tblKH.setModel(model);
        }
    }

    void insert() {
        if (txtNgayKGKH.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Ngay khai giang khong duoc de trong");
            txtNgayKGKH.requestFocus();
        } else {
            if (XDate.isDate(txtNgayKGKH.getText())) {
                ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDeKH.getSelectedItem();
                if (txtNgayTaoKH.getText().isEmpty()) {
                    dao.insert(new KhoaHoc(0, chuyenDe.getMaCD(), chuyenDe.getHocPhi(), chuyenDe.getThoiLuong(), XDate.toDate(txtNgayKGKH.getText(), "dd-MM-yyyy"), txtGhiChuKH.getText(), Auth.user.getMaNV(), XDate.toDate(XDate.timeNow(), "dd-MM-yyyy")));
                } else {
                    dao.insert(new KhoaHoc(0, chuyenDe.getMaCD(), chuyenDe.getHocPhi(), chuyenDe.getThoiLuong(), XDate.toDate(txtNgayKGKH.getText(), "dd-MM-yyyy"), txtGhiChuKH.getText(), Auth.user.getMaNV(), XDate.toDate(txtNgayTaoKH.getText(), "dd-MM-yyyy")));
                }
                fillTable();
                clearForm();
                MsgBox.alert(this, "Them thanh cong");
            } else {
                MsgBox.alert(this, "ngay khai giang khong dung dinh dang dd-MM-yyyy");
            }
        }
    }

    void update() {
        if (txtNgayKGKH.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Ngay khai giang khong duoc de trong");
            txtNgayKGKH.requestFocus();
        } else {
            if (XDate.isDate(txtNgayKGKH.getText())) {
                ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDeKH.getSelectedItem();
                dao.update(new KhoaHoc(Integer.parseInt(tblKH.getValueAt(row, 0).toString()), chuyenDe.getMaCD(), chuyenDe.getHocPhi(), chuyenDe.getThoiLuong(), XDate.toDate(txtNgayKGKH.getText(), "dd-MM-yyyy"), txtGhiChuKH.getText(), txtMaNVKH.getText(), XDate.toDate(txtNgayTaoKH.getText(), "dd-MM-yyyy")));
                fillTable();
                clearForm();
                MsgBox.alert(this, "cap nhat thanh cong");
            } else {
                MsgBox.alert(this, "ngay khai giang khong dung dinh dang dd-MM-yyyy");
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học!");
        } else {
            if (MsgBox.comfirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
                String id = String.valueOf(cboChuyenDeKH.getToolTipText());
                try {
                    dao.detele(tblKH.getValueAt(row, 0).toString());
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void clearForm() {
        this.row = -1;
        txtNgayKGKH.setText("");
        txtGhiChuKH.setText("");
        updateStatus();
    }

    void edit() {
        int id = (int) tblKH.getValueAt(this.row, 0);
        KhoaHoc kh = dao.selectById(String.valueOf(id));
        this.setForm(kh);
        this.updateStatus();
    }

    void setForm(KhoaHoc kh) {
//        lblChuyenDe.setText(kh.toString());
        txtNgayKGKH.setText(XDate.toString(kh.getNgayKG(), "dd-MM-yyyy"));
        txtMaNVKH.setText(kh.getMaNV());
        txtNgayTaoKH.setText(XDate.toString(kh.getNgayTao(), "dd-MM-yyyy"));
        txtGhiChuKH.setText(kh.getGhiChu());
        txtThoiLuongKH.setText(String.valueOf(kh.getThoiLuong()));
        txtHocPhiKH.setText(String.valueOf(kh.getHocPhi()));
    }



    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKH.getRowCount() - 1);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnDau.setEnabled(edit && !first);
        btnLui.setEnabled(edit && !first);
        btnCuoi.setEnabled(edit && !last);
        btnTien.setEnabled(edit && !last);
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDeKH.getModel();
        model.removeAllElements();
        List<ChuyenDe> list = cddao.selectAll();
        for (ChuyenDe cd : list) {
            model.addElement(cd);
        }

    }

    private void chonChuyenDe() {
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDeKH.getSelectedItem();
        txtThoiLuongKH.setText(String.valueOf(chuyenDe.getThoiLuong()));
        txtHocPhiKH.setText(String.valueOf(chuyenDe.getHocPhi()));
        lblChuyenDe.setText(chuyenDe.getTenCD());

        this.fillTable();
        this.row = -1;
        this.updateStatus();
        tabs.setSelectedIndex(1);

    }

    void dau() {
        this.row = 0;
        this.edit();
    }

    void cuoi() {
        this.row = tblKH.getRowCount() - 1;
        this.edit();
    }

    void tien() {
        if (this.row < tblKH.getRowCount() - 1) {
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

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHocPhiKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMaNVKH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChuKH = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        btnTien = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btnDau = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNgayTaoKH = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNgayKGKH = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtThoiLuongKH = new javax.swing.JTextField();
        lblChuyenDe = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        cboChuyenDeKH = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        jLabel4.setText("Chuyên đề");

        jLabel5.setText("Học phí");

        jLabel6.setText("MaNV");

        jLabel8.setText("Ghi chú");

        txtGhiChuKH.setColumns(20);
        txtGhiChuKH.setRows(5);
        txtGhiChuKH.setText("\n");
        jScrollPane1.setViewportView(txtGhiChuKH);

        btnThem.setIcon(new javax.swing.ImageIcon("D:\\DuAnMau\\EduSys\\src\\icon\\icon\\Add.png")); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon("D:\\DuAnMau\\EduSys\\src\\icon\\icon\\Notes.png")); // NOI18N
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

        jLabel2.setText("Ngày tạo");

        jLabel9.setText("Ngày khai giảng");

        jLabel10.setText("Thời lượng(giờ)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHocPhiKH, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNVKH, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txtNgayTaoKH, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(txtNgayKGKH)
                            .addComponent(jLabel10)
                            .addComponent(txtThoiLuongKH)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)
                        .addGap(169, 169, 169)
                        .addComponent(btnDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLui)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCuoi)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayKGKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHocPhiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiLuongKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaNVKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTaoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnMoi))
                    .addComponent(btnDau)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(btnLui))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Edit.png")), jPanel2); // NOI18N

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Học phí", "Thời lượng", "Khai giảng", "MaNV", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKH.setRowHeight(24);
        tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKH);

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHUYÊN ĐỀ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        cboChuyenDeKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChuyenDeKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboChuyenDeKHItemStateChanged(evt);
            }
        });
        cboChuyenDeKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDeKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDeKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
//        this.fillComboBox();
////        this.load();
////        this.clear();
//        this.updateStatus();
    }//GEN-LAST:event_formWindowOpened

    private void tblKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblKH.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblKHMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
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

    private void cboChuyenDeKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeKHActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboChuyenDeKHActionPerformed

    private void cboChuyenDeKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboChuyenDeKHItemStateChanged
        // TODO add your handling code here:
        chonChuyenDe();
    }//GEN-LAST:event_cboChuyenDeKHItemStateChanged

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
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhoaHocJFrame().setVisible(true);
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
    private javax.swing.JComboBox<String> cboChuyenDeKH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblKH;
    private javax.swing.JTextArea txtGhiChuKH;
    private javax.swing.JTextField txtHocPhiKH;
    private javax.swing.JTextField txtMaNVKH;
    private javax.swing.JTextField txtNgayKGKH;
    private javax.swing.JTextField txtNgayTaoKH;
    private javax.swing.JTextField txtThoiLuongKH;
    // End of variables declaration//GEN-END:variables
}
