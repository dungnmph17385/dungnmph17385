/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NguoiHoc;
import Entity.NhanVien;
import Utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String>{
    private static final String INSERT_SQL = "INSERT INTO NHANVIEN(MaNV, MatKhau, HoTen, VaiTro) VALUES (?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE NHANVIEN SET MatKhau = ?, HoTen=?, VaiTro=? WHERE MaNV = ?";
    private static final String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MaNV=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MaNV = ?";
    private static final String SELECT_BY_ID_AND_PASSWD = "SELECT * FROM NHANVIEN WHERE MaNV=? and MatKhau=?";
    
    JdbcHelper jdbcHelper;

    public NhanVienDAO() {
        jdbcHelper = new JdbcHelper();
    }

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getMatKhau(), entity.getTenNV(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getMatKhau(), entity.getTenNV(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void detele(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        if (this.selectBySql(SELECT_BY_ID_SQL, id).isEmpty()) {
            return null;
        }
        return this.selectBySql(SELECT_BY_ID_SQL, id).get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> ls = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);

            while (rs.next()) {
                String Manv = rs.getString(1);
                String MatKhau = rs.getString(2);
                String TenNv = rs.getString(3);
                boolean VaiTro = rs.getBoolean(4);
                ls.add(new NhanVien(Manv, TenNv, MatKhau, VaiTro));
            }
        } catch (SQLException ex) {
            System.out.println("Error selectbySQL in NhanVienDao");
            ex.printStackTrace();
        }
        if (ls.isEmpty()) {
            return null;
        }
        return ls;
    }

    public NhanVien selectByIdAndPasswd(String id, String passwd) {
        try {
            if (this.selectBySql(SELECT_BY_ID_AND_PASSWD, id, passwd).isEmpty()) {
                System.out.println("not empty list");
            }
        } catch (Exception e) {
            return null;
        }
        return this.selectBySql(SELECT_BY_ID_AND_PASSWD, id, passwd).get(0);
    }

    
    
}
