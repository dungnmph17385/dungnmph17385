CREATE DATABASE EduSys
GO
USE EduSys
GO
CREATE TABLE NhanVien(
MaNV NVARCHAR(50) NOT NULL,
MatKhau NVARCHAR(50) NOT NULL,
HoTen NVARCHAR(50) NOT NULL,
VaiTro BIT NOT NULL,
PRIMARY KEY (MaNV)
)
GO
CREATE TABLE ChuyenDe(
MaCD NCHAR(5) NOT NULL,
TenCD NVARCHAR(50) NOT NULL,
HocPhi FLOAT NOT NULL,
ThoiLuong INT NOT NULL,
Hinh NVARCHAR(50) NOT NULL,
MoTa NVARCHAR(255) NOT NULL,
PRIMARY KEY (MaCD) 
)
GO
CREATE TABLE KhoaHoc(
MaKH INT IDENTITY(1,1) NOT NULL,
MaCD NCHAR(5) NOT NULL,
HocPhi FLOAT NOT NULL,
ThoiLuong INT NOT NULL,
NgayKG DATE NOT NULL,
GhiChu NVARCHAR(50) NULL,
MaNV NVARCHAR(50) NOT NULL,
NgayTao DATE NOT NULL,
PRIMARY KEY(MaKH),
FOREIGN KEY(MaCD) REFERENCES ChuyenDe(MaCD) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY(MaNV) REFERENCES NhanVien(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
)
GO
CREATE TABLE NguoiHoc(
MaNH NCHAR(7) NOT NULL,
HoTen NVARCHAR(50) NOT NULL,
NgaySinh DATE NOT NULL,
GioiTinh BIT NOT NULL,
DienThoai NVARCHAR(50) NOT NULL,
Email NVARCHAR(50) NOT NULL,
GhiChu NVARCHAR(MAX) NULL,
MaNV NVARCHAR(50) NOT NULL,
NgayDK DATE  NOT NULL,
PRIMARY KEY (MaNH),
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
)
GO
CREATE TABLE HocVien(
MaHV INT IDENTITY(1,1) NOT NULL,
MaKH INT NOT NULL,
MaNH NCHAR(7) NOT NULL,
Diem FLOAT NOT NULL,
PRIMARY KEY (MaHV),
FOREIGN KEY (MaNH) REFERENCES NguoiHoc(MaNH) ON DELETE NO ACTION ON UPDATE CASCADE,
FOREIGN KEY (MaKH) REFERENCES KhoaHoc(MaKH) ON DELETE NO ACTION ON UPDATE NO ACTION 
)
GO
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [VaiTro]) VALUES (N'DungNM', N'123456', N'Nguyễn Minh Dũng', 0)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [VaiTro]) VALUES (N'ANV', N'123456', N'Nguyễn Văn A', 0)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [VaiTro]) VALUES (N'NamNH', N'123456', N'Nguyễn Hải Nam', 1)

INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV01', N'Lập trình Java cơ bản', 300, 90, N'HTCS.jpg', N'JAV01 - Lập trình Java cơ bản')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV02', N'Lập trình Java nâng cao', 300, 90, N'HTCS.jpg', N'JAV02 - Lập trình Java nâng cao')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV03', N'Lập trình mạng với Java', 200, 70, N'INMA.jpg', N'JAV03 - Lập trình mạng với Java')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV04', N'Lập trình desktop với Swing', 200, 70, N'LAYO.jpg', N'JAV04 - Lập trình desktop với Swing')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO01', N'Dự án với công nghệ MS.NET MVC', 300, 90, N'MOWE.png', N'PRO01 - Dự án với công nghệ MS.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO02', N'Dự án với công nghệ Spring MVC', 300, 90, N'Subject.png', N'PRO02 - Dự án với công nghệ Spring MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO03', N'Dự án với công nghệ Servlet/JSP', 300, 90, N'GAME.png', N'PRO03 - Dự án với công nghệ Servlet/JSP')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO04', N'Dự án với AngularJS & WebAPI', 300, 90, N'HTCS.jpg', N'PRO04 - Dự án với AngularJS & WebAPI')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO05', N'Dự án với Swing & JDBC', 300, 90, N'INMA.jpg', N'PRO05 - Dự án với Swing & JDBC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO06', N'Dự án với WindowForm', 300, 90, N'LAYO.jpg', N'PRO06 - Dự án với WindowForm')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB01', N'Cơ sở dữ liệu SQL Server', 100, 50, N'MOWE.png', N'RDB01 - Cơ sở dữ liệu SQL Server')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB02', N'Lập trình JDBC', 150, 60, N'Subject.png', N'RDB02 - Lập trình JDBC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB03', N'Lập trình cơ sở dữ liệu Hibernate', 250, 80, N'GAME.png', N'RDB03 - Lập trình cơ sở dữ liệu Hibernate')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER01', N'Lập trình web với Servlet/JSP', 350, 100, N'HTCS.jpg', N'SER01 - Lập trình web với Servlet/JSP')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER02', N'Lập trình Spring MVC', 400, 110, N'INMA.jpg', N'SER02 - Lập trình Spring MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER03', N'Lập trình MS.NET MVC', 400, 110, N'LAYO.jpg', N'SER03 - Lập trình MS.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER04', N'Xây dựng Web API với Spring MVC & ASP.NET MVC', 200, 70, N'MOWE.png', N'SER04 - Xây dựng Web API với Spring MVC & ASP.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB01', N'Thiết kế web với HTML và CSS', 200, 70, N'Subject.png', N'WEB01 - Thiết kế web với HTML và CSS')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB02', N'Thiết kế web với Bootstrap', 0, 40, N'GAME.png', N'WEB02 - Thiết kế web với Bootstrap')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB03', N'Lập trình front-end với JavaScript và jQuery', 150, 60, N'HTCS.jpg', N'WEB03 - Lập trình front-end với JavaScript và jQuery')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB04', N'Lập trình AngularJS', 250, 80, N'INMA.jpg', N'WEB04 - Lập trình AngularJS')

SET IDENTITY_INSERT [dbo].[KhoaHoc] ON 
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1, N'JAV01', 300, 90, '2021-03-19', N'', N'DungNM', '2020-03-19')
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (2, N'PRO03', 300, 90, '2021-03-19', N'', N'NamNH', '2020-03-19')
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (3, N'RDB01', 100, 50, '2021-03-19', N'', N'DungNM', '2020-03-19')
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (4, N'JAV01', 250, 80, '2021-03-19', N'', N'NamNH', '2020-03-19')
SET IDENTITY_INSERT [dbo].[KhoaHoc] OFF 
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS01638', N'LỮ HUY CƯỜNG', '2002-03-19', 1, N'0928768265', N'PS01638@fpt.edu.vn', N'0928768265 - LỮ HUY CƯỜNG', N'DungNM', '2021-09-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02037', N'ĐỖ VĂN MINH', '2002-03-29', 1, N'0968095685', N'PS02037@fpt.edu.vn', N'0968095685 - ĐỖ VĂN MINH', N'DungNM', '2021-01-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02771', N'NGUYỄN TẤN HIẾU', '2001-07-19', 1, N'0927594734', N'PS02771@fpt.edu.vn', N'0927594734 - NGUYỄN TẤN HIẾU', N'DungNM', '2021-04-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02867', N'NGUYỄN HỮU TRÍ', '2000-01-17', 1, N'0946984711', N'PS02867@fpt.edu.vn', N'0946984711 - NGUYỄN HỮU TRÍ', N'NamNH', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02930', N'TRẦN VĂN NAM', '2000-01-17', 1, N'0924774498', N'PS02930@fpt.edu.vn', N'0924774498 - TRẦN VĂN NAM', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02979', N'ĐOÀN TRẦN NHẬT VŨ', '2000-01-17', 1, N'0912374818', N'PS02979@fpt.edu.vn', N'0912374818 - ĐOÀN TRẦN NHẬT VŨ', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02983', N'NGUYỄN HOÀNG THIÊN PHƯỚC', '2000-01-17', 1, N'0912499836', N'PS02983@fpt.edu.vn', N'0912499836 - NGUYỄN HOÀNG THIÊN PHƯỚC', N'NamNH', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS02988', N'HỒ HỮU HẬU', '2000-01-17', 1, N'0924984876', N'PS02988@fpt.edu.vn', N'0924984876 - HỒ HỮU HẬU', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03031', N'PHAN TẤN VIỆT', '2000-01-17', 1, N'0924832716', N'PS03031@fpt.edu.vn', N'0924832716 - PHAN TẤN VIỆT', N'NamNH', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03046', N'NGUYỄN CAO PHƯỚC', '2000-01-17', 1, N'0977117727', N'PS03046@fpt.edu.vn', N'0977117727 - NGUYỄN CAO PHƯỚC', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03080', N'HUỲNH THANH HUY', '2000-01-17', 1, N'0916436052', N'PS03080@fpt.edu.vn', N'0916436052 - HUỲNH THANH HUY', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03088', N'NGUYỄN HOÀNG TRUNG','2000-01-17', 1, N'0938101529', N'PS03088@fpt.edu.vn', N'0938101529 - NGUYỄN HOÀNG TRUNG', N'NamNH', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03096', N'ĐOÀN HỮU KHANG', '2000-01-17', 1, N'0945196719', N'PS03096@fpt.edu.vn', N'0945196719 - ĐOÀN HỮU KHANG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03104', N'LÊ THÀNH PHƯƠNG', '2000-01-17', 1, N'0922948096', N'PS03104@fpt.edu.vn', N'0922948096 - LÊ THÀNH PHƯƠNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03120', N'PHẠM NGỌC NHẬT TRƯỜNG', '2000-01-17', 1, N'0994296169', N'PS03120@fpt.edu.vn', N'0994296169 - PHẠM NGỌC NHẬT TRƯỜNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03130', N'ĐẶNG BẢO VIỆT', '2000-01-17', 1, N'0917749344', N'PS03130@fpt.edu.vn', N'0917749344 - ĐẶNG BẢO VIỆT', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03134', N'LÊ DUY BẢO', '2000-01-17', 1, N'0926714368', N'PS03134@fpt.edu.vn', N'0926714368 - LÊ DUY BẢO', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03172', N'NGUYỄN ANH TUẤN', '2000-01-17', 1, N'0920020472', N'PS03172@fpt.edu.vn', N'0920020472 - NGUYỄN ANH TUẤN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03202', N'PHAN QUỐC QUI', '2000-01-17', 1, N'0930649274', N'PS03202@fpt.edu.vn', N'0930649274 - PHAN QUỐC QUI', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03203', N'ĐẶNG LÊ QUANG VINH', '2000-01-17', 1, N'0920197355', N'PS03203@fpt.edu.vn', N'0920197355 - ĐẶNG LÊ QUANG VINH', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03205', N'NGUYỄN MINH SANG', '2000-01-17', 1, N'0967006218', N'PS03205@fpt.edu.vn', N'0967006218 - NGUYỄN MINH SANG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03222', N'TRẦM MINH MẪN', '2000-01-17', 1, N'0911183649', N'PS03222@fpt.edu.vn', N'0911183649 - TRẦM MINH MẪN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03230', N'NGUYỄN PHẠM MINH HÂN', '2000-01-17', 1, N'0983469892', N'PS03230@fpt.edu.vn', N'0983469892 - NGUYỄN PHẠM MINH HÂN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03233', N'LƯU KIM HOÀNG DUY', '2000-01-17', 1, N'0938357735', N'PS03233@fpt.edu.vn', N'0938357735 - LƯU KIM HOÀNG DUY', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03251', N'TRẦN QUANG VŨ', '2000-01-17', 1, N'0914531913', N'PS03251@fpt.edu.vn', N'0914531913 - TRẦN QUANG VŨ', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03304', N'BÙI NGỌC THUẬN', '2000-01-17', 1, N'0999794115', N'PS03304@fpt.edu.vn', N'0999794115 - BÙI NGỌC THUẬN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03306', N'HỒ VĂN HÀNH', '2000-01-17', 1, N'0912277868', N'PS03306@fpt.edu.vn', N'0912277868 - HỒ VĂN HÀNH', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03308', N'TRẦN VIẾT HÙNG', '2000-01-17', 1, N'0916050164', N'PS03308@fpt.edu.vn', N'0916050164 - TRẦN VIẾT HÙNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03325', N'NGUYỄN HOÀNG MINH ĐỨC', '2000-01-17', 1, N'0912234437', N'PS03325@fpt.edu.vn', N'0912234437 - NGUYỄN HOÀNG MINH ĐỨC', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03346', N'PHAN THANH PHONG', '2000-01-17', 1, N'0937891282', N'PS03346@fpt.edu.vn', N'0937891282 - PHAN THANH PHONG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03383', N'TRẦN VŨ LUÂN', '2000-01-17', 1, N'0962030316', N'PS03383@fpt.edu.vn', N'0962030316 - TRẦN VŨ LUÂN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03389', N'VŨ ĐỨC TUẤN', '2000-01-17', 1, N'0911637415', N'PS03389@fpt.edu.vn', N'0911637415 - VŨ ĐỨC TUẤN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03410', N'TRẦN  NHẠT', '2000-01-17', 1, N'0946219377', N'PS03410@fpt.edu.vn', N'0946219377 - TRẦN  NHẠT', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03411', N'TRƯƠNG THÀNH ĐẠT', '2000-01-17', 1, N'0991509408', N'PS03411@fpt.edu.vn', N'0991509408 - TRƯƠNG THÀNH ĐẠT', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03425', N'TÔ VĂN NĂNG', '2000-01-17', 1, N'0915134551', N'PS03425@fpt.edu.vn', N'0915134551 - TÔ VĂN NĂNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03454', N'NGUYỄN NHẬT VĨNH', '2000-01-17', 1, N'0917886371', N'PS03454@fpt.edu.vn', N'0917886371 - NGUYỄN NHẬT VĨNH', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03472', N'NGUYỄN VĂN HUY', '2000-01-17', 1, N'0960620997', N'PS03472@fpt.edu.vn', N'0960620997 - NGUYỄN VĂN HUY', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03488', N'NGUYỄN NHƯ NGỌC', '2000-01-17', 0, N'0912880267', N'PS03488@fpt.edu.vn', N'0912880267 - NGUYỄN NHƯ NGỌC', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03530', N'PHẠM THÀNH TÂM', '2000-01-17', 1, N'0918161783', N'PS03530@fpt.edu.vn', N'0918161783 - PHẠM THÀNH TÂM', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03553', N'ĐINH TẤN CÔNG', '2000-01-17', 1, N'0918182551', N'PS03553@fpt.edu.vn', N'0918182551 - ĐINH TẤN CÔNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03561', N'LÊ MINH ĐIỀN', '2000-01-17', 1, N'0948199564', N'PS03561@fpt.edu.vn', N'0948199564 - LÊ MINH ĐIỀN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03596', N'NGUYỄN THANH HIỀN', '2000-01-17', 1, N'0910545901', N'PS03596@fpt.edu.vn', N'0910545901 - NGUYỄN THANH HIỀN', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03603', N'LÊ PHẠM KIM THANH', '2000-01-17', 0, N'0924696779', N'PS03603@fpt.edu.vn', N'0924696779 - LÊ PHẠM KIM THANH', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03610', N'TRẦN ĐÌNH TRƯỜNG', '2000-01-17', 1, N'0941528106', N'PS03610@fpt.edu.vn', N'0941528106 - TRẦN ĐÌNH TRƯỜNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03614', N'NGUYỄN VĂN SÁU', '2000-01-17', 1, N'0940711328', N'PS03614@fpt.edu.vn', N'0940711328 - NGUYỄN VĂN SÁU', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03618', N'PHÍ ĐÌNH VIỆT HÙNG', '2000-01-17', 1, N'0939020097', N'PS03618@fpt.edu.vn', N'0939020097 - PHÍ ĐÌNH VIỆT HÙNG', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03638', N'PHẠM NHẬT MINH', '2000-01-17', 1, N'0927771672', N'PS03638@fpt.edu.vn', N'0927771672 - PHẠM NHẬT MINH', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03640', N'LƯU THANH NGỌC', '2000-01-17', 0, N'0918358164', N'PS03640@fpt.edu.vn', N'0918358164 - LƯU THANH NGỌC', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03662', N'NGUYỄN CAO NGỌC LỢI', '2000-01-17', 1, N'0930260679', N'PS03662@fpt.edu.vn', N'0930260679 - NGUYỄN CAO NGỌC LỢI', N'DungNM', '2021-07-08')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK]) VALUES (N'PS03674', N'TRẦN TUẤN ANH', '2000-01-17', 1, N'0914082094', N'PS03674@fpt.edu.vn', N'0914082094 - TRẦN TUẤN ANH', N'DungNM', '2021-07-08')

SET IDENTITY_INSERT [dbo].[HocVien] ON 
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (1, 1, N'PS01638', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (2, 2, N'PS02037', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (3, 2, N'PS02771', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (5, 2, N'PS02930', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (7, 2, N'PS02983', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (8, 2, N'PS02988', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (11, 3, N'PS01638', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (12, 3, N'PS02037', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (13, 3, N'PS02771', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (14, 3, N'PS02867', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (15, 3, N'PS02930', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (16, 1, N'PS01638', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (17, 1, N'PS02037', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (19, 1, N'PS02867', 3)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (20, 1, N'PS02930', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (33, 1, N'PS02771', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (34, 1, N'PS02979', 4)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (35, 1, N'PS02983', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (36, 1, N'PS02988', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (37, 1, N'PS03031', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (38, 1, N'PS03046', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (39, 1, N'PS03080', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (40, 1, N'PS03088', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (41, 1, N'PS03096', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (42, 1, N'PS03104', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (43, 1, N'PS03120', -1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (44, 1, N'PS03130', -1)
SET IDENTITY_INSERT [dbo].[HocVien] OFF
--Report: MANH - HOTEN - DIEM
GO
CREATE PROC sp_BangDiem(@MaKH INT)
AS BEGIN
	SELECT 
		nh.MaNH,
		nh.HoTen,
		hv.Diem
	FROM HocVien hv 
		JOIN NguoiHoc nh ON nh.MaNH = hv.MaNH 
	WHERE hv.MaKH = @MaKH
	ORDER BY hv.Diem DESC
END
GO
--Report: TEN_CD - SO_HV - THAPNHAT - CAONHAT - TRUNGBINH
CREATE PROC sp_ThongKeDiem
AS BEGIN 
	SELECT 
		TenCD ChuyenDe,
		COUNT(MaHV) SoHV,
		MIN(Diem) ThapNhat,
		MAX(Diem) CaoNhat,
		AVG(Diem) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	GROUP BY TenCD
END 
GO
--Report: Ten_CD - SO_KH - SO_HV - DOANHTHU - THAPNHAT - CAONHAT - TRUNGBINH
CREATE PROC sp_ThongKeDoanhThu(@Year INT)
AS BEGIN
	SELECT 
		TenCD ChuyenDe,
		COUNT(DISTINCT kh.MaKH) SoKH,
		COUNT(hv.MaHV)  SoHV,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH=hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD=kh.MaCD
	WHERE YEAR(NgayKG) = @Year
	GROUP BY TenCD
END
GO
--Report: NAM - SO_LUONG - DAU_TIEN - CUOI_CUNG
CREATE PROC sp_ThongKeNguoiHoc
AS BEGIN 
	SELECT 
		YEAR(NgayDK) Nam,
		COUNT(*) SoLuong,
		MIN(NgayDK) DauTien,
		MAX(NgayDK) CuoiCung
	FROM NguoiHoc
	GROUP BY YEAR(NgayDK)
END
GO
select * from NhanVien
select * from NguoiHoc
select * from ChuyenDe
select * from HocVien
select * from KhoaHoc
