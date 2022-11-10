CREATE TABLE IF NOT EXISTS `book` (
  `ISBN` bigint NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `author` varchar(40) DEFAULT NULL,
  `category` varchar(40) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `purchase` int DEFAULT NULL,
  `sales` int DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
);