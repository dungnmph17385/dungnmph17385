/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.ChuyenDe;
import Utils.JdbcHelper;
import Utils.MsgBox;
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
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String>{
    private static final String INSERT_SQL = "INSERT INTO CHUYENDE(MaCD,TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE CHUYENDE SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD = ?";
    private static final String DELETE_SQL = "DELETE FROM CHUYENDE WHERE MaCD=?";
    private static final String SELECT_ALL_SQL = "select * from CHUYENDE";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM CHUYENDE WHERE MaCD = ?";
    JdbcHelper jdbcHelper;
    
    public ChuyenDeDAO() {
        jdbcHelper = new JdbcHelper();
    }
    
    @Override
    public void insert(ChuyenDe entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
        } catch (Exception e) {
            MsgBox.alert(null, "Mã nhân viên đã được sử dụng");
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(ChuyenDe entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    } 
    
    @Override
    public void detele(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }
    
    @Override
    public ChuyenDe selectById(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id).get(0);
    }
    
    @Override
    public List<ChuyenDe> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    
    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> ls = new ArrayList<>();
        
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                String maCD = rs.getString(1);
                String tenCD = rs.getString(2);
                float hocPhi = rs.getFloat(3);
                int thoiLuong = rs.getInt(4);
                String hinh = rs.getString(5);
                String moTa = rs.getString(6);
                
                ls.add(new ChuyenDe(maCD, tenCD, hocPhi, thoiLuong, hinh, moTa));
            }
        } catch (SQLException ex) {
            System.out.println("Error in select by sql");
            ex.printStackTrace();
        }
        return ls;
    }

    
    
    
}
