/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.ChuyenDe;
import Entity.KhoaHoc;
import Utils.JdbcHelper;
import Utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, String>{
    private static final String INSERT_SQL = "INSERT INTO KHOAHOC(MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE KHOAHOC SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=?, NgayTao=? WHERE MaKH = ?";
    private static final String DELETE_SQL = "DELETE FROM KHOAHOC WHERE MaKH=?";
    private static final String SELECT_ALL_SQL = "select * from KHOAHOC";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM KHOAHOC WHERE MaKH = ?";
    private static final String SELECT_BY_MaCD = "SELECT * FROM KHOAHOC WHERE MaCD = ?";
    JdbcHelper jdbcHelper;

    public KhoaHocDAO() {
        jdbcHelper = new JdbcHelper();
    }

    @Override
    public void insert(KhoaHoc entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao(), entity.getMaKH());
    }

    @Override
    public void detele(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public KhoaHoc selectById(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id).get(0);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> ls = new ArrayList<>();

        ResultSet rs;
        try {
            rs = jdbcHelper.query(sql, args);

            while (rs.next()) {
                int maKH = rs.getInt(1);
                String maCD = rs.getString(2);
                float hocPhi = rs.getFloat(3);
                int thoiLuong = rs.getInt(4);
                Date ngayKG = rs.getDate(5);
                String ghiChu = rs.getString(6);
                String maNV = rs.getString(7);
                Date ngayTao = rs.getDate(8);
                ls.add(new KhoaHoc(maKH, maCD, hocPhi, thoiLuong, ngayKG, ghiChu, maNV, ngayTao));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (ls.isEmpty()) {
            return null;
        }
        return ls;
    }

    public List<KhoaHoc> selectByChuyenDe(ChuyenDe s) {
        return selectBySql(SELECT_BY_MaCD, s.getMaCD());
    }
    
    public List<Integer> selectYears(){
        String sql = "select distinct year(NgayKG) as yearr from KHOAHOC order by yearr DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
}
