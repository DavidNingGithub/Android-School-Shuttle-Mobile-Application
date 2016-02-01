-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2015-04-17 19:21:50
-- 服务器版本： 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `subusdb`
--

-- --------------------------------------------------------

--
-- 表的结构 `cptojameslodi_weekend`
--

CREATE TABLE IF NOT EXISTS `cptojameslodi_weekend` (
  `CP` time DEFAULT NULL,
  `EGenUni` time DEFAULT NULL,
  `JamesLodi` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `cptojameslodi_weekend`
--

INSERT INTO `cptojameslodi_weekend` (`CP`, `EGenUni`, `JamesLodi`) VALUES
('05:30:00', '05:37:00', '05:45:00'),
('06:10:00', '06:17:00', '06:25:00'),
('07:00:00', '07:07:00', '07:15:00'),
('07:35:00', '07:42:00', '07:50:00'),
('08:10:00', '08:17:00', '08:25:00'),
('08:45:00', '08:52:00', '09:00:00');

-- --------------------------------------------------------

--
-- 表的结构 `cptojameslodi_weekend_temp`
--

CREATE TABLE IF NOT EXISTS `cptojameslodi_weekend_temp` (
  `CP` time DEFAULT NULL,
  `EGenUni` time DEFAULT NULL,
  `JamesLodi` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `cptojameslodi_weekend_temp`
--

INSERT INTO `cptojameslodi_weekend_temp` (`CP`, `EGenUni`, `JamesLodi`) VALUES
('05:30:00', '05:37:00', '05:45:00'),
('06:10:00', '06:17:00', '06:25:00'),
('07:00:00', '07:07:00', '07:15:00'),
('07:37:10', '07:42:00', '07:37:16'),
('08:10:00', '08:17:00', '07:37:51'),
('08:45:00', '08:52:00', '09:00:00');

-- --------------------------------------------------------

--
-- 表的结构 `jamesloditocp_weekend`
--

CREATE TABLE IF NOT EXISTS `jamesloditocp_weekend` (
  `JamesLodi` time DEFAULT NULL COMMENT 'James and Lodi',
  `EGenIrv` time DEFAULT NULL COMMENT 'E.Genesee and Irving',
  `EGenUni` time DEFAULT NULL COMMENT 'E.genesee and Univ.Ave.',
  `EGenWes` time DEFAULT NULL COMMENT 'E.genesee and Westcott',
  `WesEuc` time DEFAULT NULL COMMENT 'Westcott and Euclid',
  `CP` time DEFAULT NULL COMMENT 'College Place'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `jamesloditocp_weekend`
--

INSERT INTO `jamesloditocp_weekend` (`JamesLodi`, `EGenIrv`, `EGenUni`, `EGenWes`, `WesEuc`, `CP`) VALUES
('05:45:00', '05:50:00', '05:51:00', '05:55:00', '06:00:00', '06:05:00'),
('06:25:00', '06:30:00', '06:31:00', '06:35:00', '06:40:00', '06:45:00'),
('07:10:00', '07:20:00', '07:21:00', '07:25:00', '07:30:00', '07:35:00'),
('07:50:00', '07:55:00', '07:56:00', '08:00:00', '08:05:00', '08:10:00'),
('08:25:00', '08:30:00', '08:31:00', '08:35:00', '08:40:00', '08:45:00');

-- --------------------------------------------------------

--
-- 表的结构 `jamesloditocp_weekend_temp`
--

CREATE TABLE IF NOT EXISTS `jamesloditocp_weekend_temp` (
  `JamesLodi` time DEFAULT NULL COMMENT 'James and Lodi',
  `EGenIrv` time DEFAULT NULL COMMENT 'E.Genesee and Irving',
  `EGenUni` time DEFAULT NULL COMMENT 'E.genesee and Univ.Ave.',
  `EGenWes` time DEFAULT NULL COMMENT 'E.genesee and Westcott',
  `WesEuc` time DEFAULT NULL COMMENT 'Westcott and Euclid',
  `CP` time DEFAULT NULL COMMENT 'College Place'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `jamesloditocp_weekend_temp`
--

INSERT INTO `jamesloditocp_weekend_temp` (`JamesLodi`, `EGenIrv`, `EGenUni`, `EGenWes`, `WesEuc`, `CP`) VALUES
('05:45:00', '05:50:00', '05:51:00', '05:55:00', '06:00:00', '06:05:00'),
('06:25:00', '06:30:00', '06:31:00', '06:35:00', '06:40:00', '06:45:00'),
('07:10:00', '07:20:00', '07:21:00', '07:25:00', '07:30:00', '07:35:00'),
('07:50:00', '07:55:00', '07:56:00', '08:00:00', '08:05:00', '08:10:00'),
('08:25:00', '08:30:00', '08:31:00', '08:35:00', '08:40:00', '08:45:00');

-- --------------------------------------------------------

--
-- 表的结构 `realtimeinfo`
--

CREATE TABLE IF NOT EXISTS `realtimeinfo` (
  `busname` varchar(100) NOT NULL,
  `stopname` varchar(100) NOT NULL,
  `gonetime` time NOT NULL,
  `reporter` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `realtimeinfo`
--

INSERT INTO `realtimeinfo` (`busname`, `stopname`, `gonetime`, `reporter`) VALUES
('cptojameslodi_weekend', 'CP', '07:37:10', 'shishi'),
('cptojameslodi_weekend', 'CP', '18:02:39', 'shishi'),
('cptojameslodi_weekend', 'JamesLodi', '07:35:54', 'shishi'),
('cptojameslodi_weekend', 'JamesLodi', '07:36:51', 'shishi'),
('cptojameslodi_weekend', 'JamesLodi', '07:37:16', 'shishi'),
('cptojameslodi_weekend', 'JamesLodi', '07:37:51', 'shishi');

-- --------------------------------------------------------

--
-- 表的结构 `update`
--

CREATE TABLE IF NOT EXISTS `update` (
  `busname` int(11) NOT NULL,
  `stopname` int(11) NOT NULL,
  `time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `realtimeinfo`
--
ALTER TABLE `realtimeinfo`
 ADD PRIMARY KEY (`busname`,`stopname`,`gonetime`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
