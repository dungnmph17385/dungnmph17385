/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class ThongKeDAO {

    JdbcHelper jdbcHelper;
    
    public ThongKeDAO() {
        jdbcHelper = new JdbcHelper();
    }
    
    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        
        List<Object[]> ls = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                ls.add(vals);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public List<Object[]> getBangDiem(Integer makh) {
        String sql = "{CALL sp_BangDiem(?)}";
        String[] cols = {"MaNH", "HoTen", "Diem"};
        return this.getListOfArray(sql, cols, makh);
    }

    public List<Object[]> getLuongNguoiHoc() {
        String sql = "{CALL sp_ThongKeNguoiHoc}";
        String[] cols = {"Nam", "SoLuong", "DauTien", "CuoiCung"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDiemChuyenDe() {
        String sql = "{CALL sp_ThongKeDiem}";
        String[] cols = {"ChuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int nam) {
        String sql = "{CALL sp_ThongKeDoanhThu(?)}";
        String[] cols = {"ChuyenDe", "SoKH", "SoHV", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols, nam);
    }
}
