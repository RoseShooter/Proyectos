-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-03-2025 a las 10:59:49
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `herriendba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `herriak`
--

CREATE TABLE `herriak` (
  `Herria` varchar(50) NOT NULL,
  `Probintzia` varchar(20) NOT NULL,
  `Hondartza1` varchar(100) DEFAULT NULL,
  `Hondartza2` varchar(100) DEFAULT NULL,
  `Hondartza3` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `herriak`
--

INSERT INTO `herriak` (`Herria`, `Probintzia`, `Hondartza1`, `Hondartza2`, `Hondartza3`) VALUES
('Bilbao', 'Bizkaia', NULL, NULL, NULL),
('Casalarreina', 'La Rioja', NULL, NULL, NULL),
('Donosti', 'Gipuzkoa', 'Ondarreta', 'Kontxa', 'Zurriola'),
('Eibar', 'Gipuzkoa', NULL, NULL, NULL),
('Ermua', 'Bizkaia', NULL, NULL, NULL),
('Zaldibar', 'Bizkaia', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE `log` (
  `id` int(11) NOT NULL,
  `ekintza` varchar(255) DEFAULT NULL,
  `data` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`id`, `ekintza`, `data`) VALUES
(1, 'Herria txertatu: Bilbo (Bizkaia)', '2025-03-13 07:27:48'),
(2, 'Txertatze huts egina (duplikatuta): Ermua', '2025-03-13 07:27:57'),
(3, 'Herria txertatu: Zarautz (Gipuzkoa)', '2025-03-13 07:28:24'),
(4, 'Txertatze huts egina (duplikatuta): Eibar', '2025-03-13 07:29:57'),
(5, 'Errorea izena aldatzerakoan: -1', '2025-03-13 08:15:40'),
(6, 'Errorea izena aldatzerakoan: 1142', '2025-03-13 08:16:30'),
(7, 'Herriaren izena aldatu da: Ermua -> Hermua(Bizkaia)', '2025-03-13 08:29:02'),
(8, 'Herriaren izena aldatu da: Hermua -> Ermua(Bizkaia)', '2025-03-13 08:30:00'),
(9, 'Herrial taulako erregistro kopurua zenbatu da', '2025-03-13 08:32:27'),
(10, 'Herrien zerrenda atera da.', '2025-03-13 08:32:27'),
(11, 'Herriaren izena aldatu da: Eibar -> Heibar(Gipuzkoa)', '2025-03-13 08:32:57'),
(12, 'Herriaren izena aldatu da: Heibar -> Eibar(Gipuzkoa)', '2025-03-13 08:33:12'),
(13, 'Errorea herria eguneratzerakoan: 1292', '2025-03-13 08:50:54'),
(14, 'Errorea herria eguneratzerakoan: 1292', '2025-03-13 08:51:50'),
(15, 'Herria eguneratu da: Ermua (Bizkaia) -> Hermua (Vizkaia)', '2025-03-13 08:53:59'),
(16, 'Herrial taulako erregistro kopurua zenbatu da', '2025-03-19 07:53:20'),
(17, 'Herrien zerrenda atera da.', '2025-03-19 07:53:20'),
(18, 'Herria txertatu: Casalarreina (La Rioja)', '2025-03-19 08:04:56'),
(19, 'Herrien zerrenda atera da.', '2025-03-19 08:32:13'),
(20, 'Herria ezabatuta: Elgoibar', '2025-03-19 08:32:20'),
(21, 'Probintzien zerrenda atera da.', '2025-03-20 08:10:30'),
(22, 'Probintziako herriak atera dira.', '2025-03-20 08:10:30'),
(23, 'Probintzien zerrenda atera da.', '2025-03-20 08:44:44'),
(24, 'Probintziako herriak atera dira.', '2025-03-20 08:44:54'),
(25, 'Probintziako herriak atera dira.', '2025-03-20 08:44:57'),
(26, 'Probintziako herriak atera dira.', '2025-03-20 08:45:01'),
(27, 'Probintzien zerrenda atera da.', '2025-03-20 08:45:56'),
(28, 'Probintzien zerrenda atera da.', '2025-03-20 08:46:17'),
(29, 'Probintzien zerrenda atera da.', '2025-03-20 08:50:27'),
(30, 'Probintziako herriak atera dira.', '2025-03-20 08:50:38'),
(31, 'Probintziako herriak atera dira.', '2025-03-20 08:50:39'),
(32, 'Probintziako herriak atera dira.', '2025-03-20 08:50:40'),
(33, 'Probintziako herriak atera dira.', '2025-03-20 08:50:43'),
(34, 'Herria eguneratu da: Bilbo (Bizkaia) -> Bilbao (Bizkaia)', '2025-03-20 08:50:58'),
(35, 'Probintzien zerrenda atera da.', '2025-03-20 08:51:31'),
(36, 'Probintzien zerrenda atera da.', '2025-03-20 08:54:20'),
(37, 'Probintzien zerrenda atera da.', '2025-03-20 08:55:59'),
(38, 'Probintzien zerrenda atera da.', '2025-03-20 08:58:00'),
(39, 'Probintzien zerrenda atera da.', '2025-03-20 08:58:35'),
(40, 'Probintzien zerrenda atera da.', '2025-03-20 08:59:48'),
(41, 'Probintzien zerrenda atera da.', '2025-03-20 09:00:49'),
(42, 'Probintzien zerrenda atera da.', '2025-03-20 09:01:44'),
(43, 'Probintzien zerrenda atera da.', '2025-03-20 09:02:07'),
(44, 'Probintzien zerrenda atera da.', '2025-03-20 09:02:31'),
(45, 'Probintzien zerrenda atera da.', '2025-03-20 09:02:58'),
(46, 'Probintzien zerrenda atera da.', '2025-03-20 09:03:40'),
(47, 'Probintzien zerrenda atera da.', '2025-03-20 09:05:17'),
(48, 'Probintziako herriak atera dira.', '2025-03-20 09:05:30'),
(49, 'Probintziako herriak atera dira.', '2025-03-20 09:05:33'),
(50, 'Probintziako herriak atera dira.', '2025-03-20 09:05:35');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `herriak`
--
ALTER TABLE `herriak`
  ADD PRIMARY KEY (`Herria`);

--
-- Indices de la tabla `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
