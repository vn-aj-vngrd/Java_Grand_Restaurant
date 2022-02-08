-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 17, 2021 at 05:16 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `res_order_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),
(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20),
(21),
(22),
(23),
(24),
(25),
(26),
(27),
(28),
(29),
(30),
(31),
(32),
(33),
(34);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `role` enum('admin','emp','empty','') NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `role`, `username`, `password`, `fname`, `lname`) VALUES
(0, 'empty', 'empty', 'empty', 'empty', 'empty'),
(1, 'admin', 'admin', 'admin', 'Peter', 'Jack'),
(2, 'emp', 'emp1', 'emp1', 'emp1', 'emp1'),
(3, 'emp', 'emp2', 'emp2', 'emp2', 'emp2');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `code` char(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` enum('main','dessert','drinks','') NOT NULL,
  `price` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `code`, `name`, `type`, `price`) VALUES
(1, 'A1', 'Fried Chicken', 'main', 95.00),
(2, 'A2', 'Burger Steak', 'main', 100.00),
(3, 'B1', 'Roasted Vegetables', 'dessert', 200.00),
(4, 'B2', 'Vegetable Salad', 'dessert', 150.00),
(5, 'C1', 'Ice Tea', 'drinks', 20.00),
(6, 'C2', 'Pineapple Juice', 'drinks', 20.00),
(10, 'A3', 'Pork Barbecue', 'main', 120.00),
(11, 'B3', 'Chocolate Cake', 'dessert', 200.00),
(12, 'C3', 'Orange Juice', 'drinks', 30.00),
(13, 'C4', 'Apple Juice', 'drinks', 30.00),
(14, 'A4', 'Fish Fillet', 'main', 150.00),
(15, 'A5', 'Carbonara', 'main', 200.00),
(16, 'B4', 'Leche Flan', 'dessert', 190.00),
(17, 'B5', 'Fruit Salad', 'dessert', 100.00),
(18, 'C5', 'Mango Juice', 'drinks', 20.00);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `menuCode` char(11) NOT NULL,
  `empID` int(11) NOT NULL DEFAULT 0,
  `status` enum('pending','completed','cancelled') NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customerID`, `menuCode`, `empID`, `status`, `date`, `time`) VALUES
(1, 4, 'A3', 0, 'pending', '2021-11-16', '13:23:17'),
(2, 4, 'A3', 0, 'pending', '2021-11-16', '13:23:17'),
(3, 4, 'A2', 3, 'completed', '2021-11-16', '13:23:20'),
(4, 4, 'A2', 3, 'completed', '2021-11-16', '13:23:20'),
(5, 4, 'B1', 0, 'pending', '2021-11-16', '13:23:27'),
(6, 4, 'C2', 3, 'completed', '2021-11-16', '13:23:29'),
(7, 4, 'C2', 3, 'cancelled', '2021-11-16', '13:23:29'),
(8, 5, 'A1', 3, 'cancelled', '2021-11-16', '13:23:36'),
(9, 5, 'A1', 3, 'cancelled', '2021-11-16', '13:23:36'),
(10, 5, 'B1', 0, 'pending', '2021-11-16', '13:23:39'),
(11, 5, 'B1', 3, 'completed', '2021-11-16', '13:23:39'),
(12, 5, 'C2', 3, 'cancelled', '2021-11-16', '13:23:42'),
(13, 5, 'C2', 0, 'pending', '2021-11-16', '13:23:42'),
(14, 10, 'A2', 0, 'pending', '2021-11-16', '15:39:20'),
(15, 10, 'A2', 0, 'pending', '2021-11-16', '15:39:20'),
(16, 10, 'B2', 0, 'pending', '2021-11-16', '15:39:24'),
(17, 10, 'B2', 0, 'pending', '2021-11-16', '15:39:24'),
(18, 10, 'C1', 2, 'completed', '2021-11-16', '15:39:28'),
(19, 10, 'C1', 0, 'pending', '2021-11-16', '15:39:28'),
(20, 10, 'C1', 0, 'pending', '2021-11-16', '15:39:28'),
(21, 26, 'A1', 2, 'cancelled', '2021-11-16', '20:44:19'),
(22, 26, 'A1', 0, 'pending', '2021-11-16', '20:44:19'),
(23, 26, 'C2', 0, 'pending', '2021-11-16', '20:44:27'),
(24, 26, 'C2', 0, 'pending', '2021-11-16', '20:44:27'),
(25, 30, 'A2', 0, 'pending', '2021-11-17', '23:24:54'),
(26, 30, 'A2', 0, 'pending', '2021-11-17', '23:24:54'),
(27, 30, 'B4', 0, 'pending', '2021-11-17', '23:24:56'),
(28, 30, 'B4', 0, 'pending', '2021-11-17', '23:24:56'),
(29, 30, 'B4', 0, 'pending', '2021-11-17', '23:24:56'),
(30, 30, 'C1', 0, 'pending', '2021-11-17', '23:24:59'),
(31, 30, 'C1', 0, 'pending', '2021-11-17', '23:24:59'),
(32, 31, 'A3', 0, 'pending', '2021-11-17', '23:25:05'),
(33, 31, 'A3', 0, 'pending', '2021-11-17', '23:25:05'),
(34, 31, 'B5', 0, 'pending', '2021-11-17', '23:25:08'),
(35, 31, 'B5', 0, 'pending', '2021-11-17', '23:25:08'),
(36, 31, 'C4', 0, 'pending', '2021-11-17', '23:25:11'),
(38, 32, 'A5', 0, 'pending', '2021-11-17', '23:25:21'),
(39, 32, 'B4', 0, 'pending', '2021-11-17', '23:25:32'),
(40, 32, 'B5', 0, 'pending', '2021-11-17', '23:25:34'),
(41, 32, 'C4', 0, 'pending', '2021-11-17', '23:25:39'),
(42, 32, 'C5', 0, 'pending', '2021-11-17', '23:25:41'),
(43, 32, 'C5', 0, 'pending', '2021-11-17', '23:25:41');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_FK1` (`customerID`),
  ADD KEY `order_FK2` (`menuCode`),
  ADD KEY `order_FK3` (`empID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `order_FK1` FOREIGN KEY (`customerID`) REFERENCES `customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `order_FK2` FOREIGN KEY (`empID`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `order_FK3` FOREIGN KEY (`menuCode`) REFERENCES `menu` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
