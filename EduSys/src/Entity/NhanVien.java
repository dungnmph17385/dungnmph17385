/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Admin
 */
public class NhanVien {
    private String maNV;
    private String tenNV;
    private String matKhau;
    private boolean vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String matKhau, boolean vaiTro) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNV=" + maNV + ", tenNV=" + tenNV + ", matKhau=" + matKhau + ", vaiTro=" + vaiTro + '}';
    }
    
    
}
