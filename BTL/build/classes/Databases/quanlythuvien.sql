-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 19, 2025 at 09:35 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlythuvien`
--

-- --------------------------------------------------------

--
-- Table structure for table `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `quyen_han` enum('Admin','User') NOT NULL DEFAULT 'User'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`id`, `username`, `password`, `quyen_han`) VALUES
(1, 'admin', '1', 'Admin'),
(2, 'user1', '$2y$10$abcdefghijklmnopqrstuv', 'User'),
(3, 'user2', '$2y$10$ABCDEFGHIJKLMNOPQRSTUV', 'User'),
(5, 'admin1', '2', 'User'),
(6, '1', '1', 'User');

-- --------------------------------------------------------

--
-- Table structure for table `nha_xuat_ban`
--

CREATE TABLE `nha_xuat_ban` (
  `ma_nxb` varchar(10) NOT NULL,
  `ten_nxb` varchar(255) NOT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `sdt` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nha_xuat_ban`
--

INSERT INTO `nha_xuat_ban` (`ma_nxb`, `ten_nxb`, `dia_chi`, `sdt`) VALUES
('NXB01', 'NXB Giáo dục', 'Hà Nội', '024-1234567'),
('NXB02', 'NXB Trẻ', 'TP. HCM', '028-9876543'),
('NXB03', 'NXB Kim Đồng', 'Hà Nội', '024-6543210');

-- --------------------------------------------------------

--
-- Table structure for table `phieu_muon`
--

CREATE TABLE `phieu_muon` (
  `id` int(11) NOT NULL,
  `ma_sv` varchar(10) NOT NULL,
  `ma_sach` varchar(10) NOT NULL,
  `ngay_muon` date NOT NULL,
  `ngay_tra_du_kien` date NOT NULL,
  `ngay_tra_thuc_te` date DEFAULT NULL,
  `phi_muon` decimal(10,2) DEFAULT 0.00,
  `tinh_trang_muon` enum('Đang mượn','Quá hạn','Đã trả') DEFAULT 'Đang mượn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieu_muon`
--

INSERT INTO `phieu_muon` (`id`, `ma_sv`, `ma_sach`, `ngay_muon`, `ngay_tra_du_kien`, `ngay_tra_thuc_te`, `phi_muon`, `tinh_trang_muon`) VALUES
(1, 'SV001', 'S001', '2025-05-01', '2025-05-10', NULL, 0.00, 'Quá hạn'),
(2, 'SV002', 'S002', '2025-05-05', '2025-05-12', '2025-05-14', 0.00, 'Đã trả'),
(4, 'sv001', 's001', '2025-05-20', '2025-05-21', NULL, 0.00, 'Đang mượn'),
(5, 'sv001', 's001', '2025-05-20', '2025-05-21', NULL, 0.00, 'Đang mượn');

-- --------------------------------------------------------

--
-- Table structure for table `phieu_phat`
--

CREATE TABLE `phieu_phat` (
  `id` int(11) NOT NULL,
  `phieu_muon_id` int(11) NOT NULL,
  `ngay_lap` date NOT NULL DEFAULT curdate(),
  `so_ngay_qua_han` int(11) NOT NULL,
  `tien_phat` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieu_phat`
--

INSERT INTO `phieu_phat` (`id`, `phieu_muon_id`, `ngay_lap`, `so_ngay_qua_han`, `tien_phat`) VALUES
(1, 2, '2025-05-14', 2, 10000.00);

-- --------------------------------------------------------

--
-- Table structure for table `sach`
--

CREATE TABLE `sach` (
  `ma_sach` varchar(10) NOT NULL,
  `tua_de` varchar(255) NOT NULL,
  `tac_gia` varchar(100) NOT NULL,
  `nam_xb` year(4) NOT NULL,
  `gia` decimal(10,2) NOT NULL,
  `so_luong` int(11) NOT NULL DEFAULT 0,
  `ngon_ngu` varchar(50) NOT NULL,
  `tinh_trang` enum('Mới','Cũ') NOT NULL DEFAULT 'Mới',
  `ma_nxb` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sach`
--

INSERT INTO `sach` (`ma_sach`, `tua_de`, `tac_gia`, `nam_xb`, `gia`, `so_luong`, `ngon_ngu`, `tinh_trang`, `ma_nxb`) VALUES
('S001', 'Lập trình C', 'Nguyễn Văn X', '2011', 45000.00, 3, 'Tiếng Việt', 'Mới', 'NXB01'),
('S002', 'Nhật ký Đặng Thùy Trâm', 'Đặng Thùy Trâm', '2005', 120000.00, 2, 'Tiếng Việt', 'Cũ', 'NXB03'),
('S003', 'Nhật ký Đặng Thùy Trâm', 'Đặng Thùy Trâm', '2005', 120000.00, 2, 'Tiếng Việt', 'Cũ', 'NXB01');

-- --------------------------------------------------------

--
-- Table structure for table `sinh_vien`
--

CREATE TABLE `sinh_vien` (
  `ma_sv` varchar(10) NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `gioi_tinh` enum('Nam','Nữ') NOT NULL,
  `tuoi` int(11) NOT NULL,
  `dia_chi` varchar(255) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sinh_vien`
--

INSERT INTO `sinh_vien` (`ma_sv`, `ho_ten`, `gioi_tinh`, `tuoi`, `dia_chi`, `sdt`, `email`) VALUES
('SV001', 'Nguyễn Văn A', 'Nam', 20, 'Hà Nội', '0987000001', 'a@example.com'),
('SV002', 'Trần Thị B', 'Nữ', 21, 'Hải Phòng', '0987000002', 'b@example.com'),
('SV003', 'Lê Văn C', 'Nam', 22, 'Thái Bình', '0987000003', 'c@example.com'),
('SV004', 'Nguyen Thi D', 'Nam', 22, 'Ninh Binh', '0927628887', 'd@example.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `nha_xuat_ban`
--
ALTER TABLE `nha_xuat_ban`
  ADD PRIMARY KEY (`ma_nxb`);

--
-- Indexes for table `phieu_muon`
--
ALTER TABLE `phieu_muon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_phieu_muon_sv` (`ma_sv`),
  ADD KEY `idx_phieu_muon_sach` (`ma_sach`);

--
-- Indexes for table `phieu_phat`
--
ALTER TABLE `phieu_phat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_phieu_phat_muon` (`phieu_muon_id`);

--
-- Indexes for table `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`ma_sach`),
  ADD KEY `fk_sach_nxb` (`ma_nxb`);

--
-- Indexes for table `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD PRIMARY KEY (`ma_sv`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `phieu_muon`
--
ALTER TABLE `phieu_muon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `phieu_phat`
--
ALTER TABLE `phieu_phat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `phieu_muon`
--
ALTER TABLE `phieu_muon`
  ADD CONSTRAINT `fk_phieu_muon_sach` FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_phieu_muon_sv` FOREIGN KEY (`ma_sv`) REFERENCES `sinh_vien` (`ma_sv`) ON UPDATE CASCADE;

--
-- Constraints for table `phieu_phat`
--
ALTER TABLE `phieu_phat`
  ADD CONSTRAINT `fk_phieu_phat_muon` FOREIGN KEY (`phieu_muon_id`) REFERENCES `phieu_muon` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `fk_sach_nxb` FOREIGN KEY (`ma_nxb`) REFERENCES `nha_xuat_ban` (`ma_nxb`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
