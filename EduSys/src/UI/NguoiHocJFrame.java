/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DAO.NguoiHocDAO;
import Entity.NguoiHoc;
import Utils.Auth;
import Utils.MsgBox;
import Utils.XDate;
import java.awt.HeadlessException;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class NguoiHocJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ChuyenDeJFrame
     */
    public NguoiHocJFrame() {
        initComponents();
        init();
    }
    int row = -1;
    NguoiHocDAO dao = new NguoiHocDAO();

    void init() {
        setTitle("EduSys - Quản lý người học");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NguoiHoc> list = dao.selectByKeyword(keyword);
            for (NguoiHoc nh : list) {
                Object[] row = {
                    nh.getMaNH(),
                    nh.getHoTen(),
                    nh.isGioiTinh() ? "Nam" : "Nữ",
                    nh.getNgaySinh(),
                    nh.getDienThoai(),
                    nh.getEmail(),
                    nh.getMaNV(),
                    nh.getNgayDK()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (txtMaNguoiHoc.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Không được để trống!");
            txtMaNguoiHoc.requestFocus();
        } else {
            if (txtTenNguoiHoc.getText().trim().isEmpty()) {
                MsgBox.alert(this, "Không được để trống!");
                txtTenNguoiHoc.requestFocus();
            } else {
                if (XDate.isDate(txtNgaySinhNguoiHoc.getText())) {
                    if (txtNgaySinhNguoiHoc.getText().trim().isEmpty()) {
                        MsgBox.alert(this, "Không được để trống!");
                        txtNgaySinhNguoiHoc.requestFocus();
                    } else {
                        if (txtDienThoaiNguoiHoc.getText().trim().isEmpty()) {
                            MsgBox.alert(this, "Không được để trống!");
                            txtDienThoaiNguoiHoc.requestFocus();
                        } else {
                            if (txtEmailNguoiHoc.getText().trim().isEmpty()) {
                                try {
                                    dao.insert(getForm());
                                    fillTable();
                                    clearForm();
                                    MsgBox.alert(this, "Thêm thành công!");
                                } catch (Exception e) {
                                    MsgBox.alert(this, "Thêm thất bại");
                                    e.printStackTrace();
                                }
                            } else {
                                MsgBox.alert(this, "Không được để trống!");
                            }
                        }

                    }
                } else {
                    MsgBox.alert(this, "date must be dd-MM-yyyy");
                }
            }
        }
    }

    void update() {
        NguoiHoc nh = getForm();
        try {
            dao.update(nh);
            this.fillTable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa người học!");
        } else {
            if (MsgBox.comfirm(this, "Bạn thực sự muốn xóa người học này?")) {
                String manh = txtMaNguoiHoc.getText();
                try {
                    dao.detele(manh);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công!");
                } catch (HeadlessException e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            }
        }
    }

    void clearForm() {
        NguoiHoc nh = new NguoiHoc();
        this.setForm(nh);
        nh.setMaNV(Auth.user.getMaNV());
        nh.setNgayDK(XDate.now());
        this.row = -1;
        this.updateStatus();
    }

    

    void setForm(NguoiHoc nh) {
        txtMaNguoiHoc.setText(nh.getMaNH());
        txtTenNguoiHoc.setText(nh.getHoTen());
        if (nh.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtNgaySinhNguoiHoc.setText("");
        txtDienThoaiNguoiHoc.setText(nh.getDienThoai());
        txtEmailNguoiHoc.setText(nh.getEmail());
        txtGhiChuNguoiHoc.setText(nh.getGhiChu());
    }

    NguoiHoc getForm() {
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtMaNguoiHoc.getText());
        nh.setHoTen(txtTenNguoiHoc.getText());
        nh.setGioiTinh(rdoNam.isSelected());
        nh.setNgaySinh(XDate.toDate(txtNgaySinhNguoiHoc.getText()));
        nh.setDienThoai(txtDienThoaiNguoiHoc.getText());
        nh.setEmail(txtEmailNguoiHoc.getText());
        nh.setGhiChu(txtGhiChuNguoiHoc.getText());
        nh.setMaNV(Auth.user.getMaNV());
        nh.setNgayDK(XDate.now());
        return nh;
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNguoiHoc.getRowCount() - 1);
        txtMaNguoiHoc.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        btnDau.setEnabled(edit && !first);
        btnLui.setEnabled(edit && !first);
        btnCuoi.setEnabled(edit && !last);
        btnTien.setEnabled(edit && !last);
    }
    public void updateForm() {
        txtMaNguoiHoc.setText((String) tblNguoiHoc.getValueAt(row, 0));
        txtTenNguoiHoc.setText((String) tblNguoiHoc.getValueAt(row, 1));
        if ("Nam".equals(tblNguoiHoc.getValueAt(row, 2).toString())) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtNgaySinhNguoiHoc.setText(XDate.toString((Date) tblNguoiHoc.getValueAt(row, 3), "dd-MM-yyyy"));
        txtEmailNguoiHoc.setText((String) tblNguoiHoc.getValueAt(row, 5));
        txtDienThoaiNguoiHoc.setText((String) tblNguoiHoc.getValueAt(row, 4));
        txtGhiChuNguoiHoc.setText((String) tblNguoiHoc.getValueAt(row, 8));
        updateStatus();
    }

    private void timKiem() {
        this.fillTable();
        this.clearForm();
        this.row = -1;
        updateStatus();
    }

    void dau() {
        row = 0;
        updateForm();
        updateStatus();
    }

    void cuoi() {
        row = tblNguoiHoc.getRowCount() - 1;
        updateForm();
        updateStatus();
    }

    void tien() {
        if (row < tblNguoiHoc.getRowCount() - 1) {
            row++;
            updateForm();
            updateStatus();
        }
    }

    void lui() {
        if (row > 0) {
            row--;
            updateForm();
            updateStatus();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNguoiHoc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenNguoiHoc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDienThoaiNguoiHoc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChuNguoiHoc = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnCuoi = new javax.swing.JButton();
        btnTien = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btnDau = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNgaySinhNguoiHoc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmailNguoiHoc = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ NGƯỜI HỌC");

        jLabel4.setText("Mã người học ");

        jLabel5.setText("Họ và tên");

        jLabel6.setText("Giới tính");

        jLabel7.setText("Điện thoại");

        jLabel8.setText("Ghi chú");

        txtGhiChuNguoiHoc.setColumns(20);
        txtGhiChuNguoiHoc.setRows(5);
        txtGhiChuNguoiHoc.setText("\n");
        jScrollPane1.setViewportView(txtGhiChuNguoiHoc);

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

        jLabel2.setText("Ngày sinh");

        jLabel3.setText("Email");

        txtEmailNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailNguoiHocActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                        .addComponent(btnDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLui)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCuoi))
                    .addComponent(txtMaNguoiHoc)
                    .addComponent(txtTenNguoiHoc)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDienThoaiNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(txtNgaySinhNguoiHoc)
                            .addComponent(txtEmailNguoiHoc, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgaySinhNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoaiNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmailNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCuoi)
                    .addComponent(btnTien)
                    .addComponent(btnLui, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDau, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnMoi)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Edit.png")), jPanel2); // NOI18N

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày DK", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.setRowHeight(24);
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiHoc);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon/Search.png"))); // NOI18N
        jButton1.setText("Tìm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtEmailNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailNguoiHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailNguoiHocActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.fillTable();
        this.updateStatus();
    }//GEN-LAST:event_formWindowOpened

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNguoiHoc.rowAtPoint(evt.getPoint());
            if (this.row >= 0) {
                updateForm();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblNguoiHocMouseClicked

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.timKiem();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(NguoiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new NguoiHocJFrame().setVisible(true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtDienThoaiNguoiHoc;
    private javax.swing.JTextField txtEmailNguoiHoc;
    private javax.swing.JTextArea txtGhiChuNguoiHoc;
    private javax.swing.JTextField txtMaNguoiHoc;
    private javax.swing.JTextField txtNgaySinhNguoiHoc;
    private javax.swing.JTextField txtTenNguoiHoc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
