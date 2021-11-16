/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.HocVien;
import Entity.NguoiHoc;
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
public class HocVienDAO extends EduSysDAO<HocVien, Integer>{
    private static final String INSERT_SQL = "INSERT INTO HOCVIEN(MaKH, MaNH, Diem) VALUES (?,?,?)";
    private static final String UPDATE_SQL = "UPDATE HOCVIEN SET Diem=? WHERE MaHV=?";
    private static final String DELETE_SQL = "DELETE FROM HOCVIEN WHERE MaHV=?";
    private static final String SELECT_ALL_SQL = "select * from HOCVIEN";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM HOCVIEN WHERE MaHV = ?";
    
    JdbcHelper jdbcHelper;

    public HocVienDAO() {
        jdbcHelper = new JdbcHelper();
    }

    @Override
    public void insert(HocVien entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getDiem(), entity.getMaHV());
    }

    @Override
    public void detele(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        return selectBySql(SELECT_BY_ID_SQL, id).get(0);
    }

    @Override
    public List<HocVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> ls = new ArrayList<>();

        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                int mahv = rs.getInt(1);
                int makh = rs.getInt(2);
                String manh = rs.getString(3);
                float diem = rs.getFloat(4);
                ls.add(new HocVien(mahv, makh, manh, diem));
            }
        } catch (SQLException ex) {
            System.out.println("Error in hocvienDao -> selectbySQL()");
            ex.printStackTrace();
        }
        return ls;
    }

    public List<HocVien> selectByKhoaHoc(int maKH) {
        String sql = "select * from HOCVIEN where MaKH = ?";
        return this.selectBySql(sql, maKH);
    }
    
}
