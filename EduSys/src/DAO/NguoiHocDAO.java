/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.NguoiHoc;
import Utils.JdbcHelper;
import Utils.MsgBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String>{
    private static final String INSERT_SQL = "INSERT INTO NGUOIHOC(MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu,MaNV, NgayDK) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE NGUOIHOC SET HoTen=?, GioiTinh=?, NgaySinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=?, NgayDK=? WHERE MaNH = ?";
    private static final String DELETE_SQL = "DELETE FROM NGUOIHOC WHERE MaNH=?";
    private static final String SELECT_ALL_SQL = "select * from NGUOIHOC";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM NGUOIHOC WHERE MaNH = ?";
    private static final String SELECT_BY_KEY_WORD = "SELECT * FROM NGUOIHOC WHERE HoTen like ?";

    JdbcHelper jdbcHelper;

    public NguoiHocDAO() {
        jdbcHelper = new JdbcHelper();
    }

    @Override
    public void insert(NguoiHoc entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaNH(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiHoc entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getNgaySinh(),entity.isGioiTinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK(), entity.getMaNH());
    }

    @Override
    public void detele(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id).get(0);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> ls = new ArrayList<>();

        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                String MaNH = rs.getString(1);
                String HoTen = rs.getString(2);
                Date NgaySinh = rs.getDate(3);
                boolean GioiTinh = rs.getBoolean(4);
                String DienThoai = rs.getString(5);
                String Email = rs.getString(6);
                String GhiChu = rs.getString(7);
                String MaNV = rs.getString(8);
                Date NgayDK = rs.getDate(9);
                NguoiHoc nh = new NguoiHoc(MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV, NgayDK);
                ls.add(nh);
            }
        } catch (SQLException e) {
            MsgBox.alert(null, "Error select by sql in NguoiHocDao class");
            e.printStackTrace();
        }
        return ls;
    }

    public List<NguoiHoc> selectByKeyWord(String keyWord) {
        return selectBySql(SELECT_BY_KEY_WORD, "%" + keyWord + "%");
    }

    public List<NguoiHoc> selectNotInCourse(int maKH, String keyWord){
        String sql = "SELECT * FROM NGUOIHOC "
                + "WHERE HoTen LIKE ? AND "
                + "MaNH NOT IN (SELECT MaNH FROM HOCVIEN WHERE MaKH = ?)";
        return this.selectBySql(sql, "%" + keyWord + "%", maKH);
    }

    
    
}
