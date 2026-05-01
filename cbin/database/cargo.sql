/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - cargo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cargo` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `cargo`;

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `booking_date` varchar(20) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `Weight` varchar(200) DEFAULT NULL,
  `height` varchar(200) DEFAULT NULL,
  `width` varchar(200) DEFAULT NULL,
  `from_location` varchar(100) DEFAULT NULL,
  `to_location` varchar(100) DEFAULT NULL,
  `amount` varchar(300) DEFAULT NULL,
  `booking_status` varchar(100) DEFAULT NULL,
  `boy_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `bookings` */

insert  into `bookings`(`booking_id`,`customer_id`,`booking_date`,`branch_id`,`Weight`,`height`,`width`,`from_location`,`to_location`,`amount`,`booking_status`,`boy_id`) values (1,1,'2020-02-14',1,'100','2','1','Kochi','Kozhikode','5000','assign',1),(2,3,'2020-02-14',1,'10','1','1','Kottayam','ernakulam','6000','assign',1);

/*Table structure for table `branchemngr` */

DROP TABLE IF EXISTS `branchemngr`;

CREATE TABLE `branchemngr` (
  `branch_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`branch_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `branchemngr` */

insert  into `branchemngr`(`branch_id`,`login_id`,`name`,`latitude`,`longitude`,`phone`,`email`) values (1,2,'Branch1','9.97220606289992','76.290413330719','9879684566','br@gmail.com'),(2,3,'Branch2','9.97533380394697','76.28775257937623','9879689875','br2@gmail.com');

/*Table structure for table `cargostatus` */

DROP TABLE IF EXISTS `cargostatus`;

CREATE TABLE `cargostatus` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `datetime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `cargostatus` */

insert  into `cargostatus`(`status_id`,`booking_id`,`place`,`datetime`) values (1,1,'kochi','2020-02-13 11:44:09'),(2,1,'kochi hub','2020-02-13 11:47:24'),(3,2,'updates','2020-02-14 14:49:33');

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(20) DEFAULT NULL,
  `l_name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `customers` */

insert  into `customers`(`customer_id`,`login_id`,`f_name`,`l_name`,`phone`,`email`,`latitude`,`longitude`) values (1,7,'Achu','S','909898786','achu@gmail.com','2','3'),(3,11,'Anila','Rajan','9498563256','anila@gmail.com','9.9763484','76.2862919');

/*Table structure for table `deliveryboys` */

DROP TABLE IF EXISTS `deliveryboys`;

CREATE TABLE `deliveryboys` (
  `boy_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(20) DEFAULT NULL,
  `l_name` varchar(20) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`boy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `deliveryboys` */

insert  into `deliveryboys`(`boy_id`,`login_id`,`f_name`,`l_name`,`branch_id`,`phone`,`email`) values (1,6,'Dinu','K.P',1,'9879684566','dinu@gmail.com');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `fed_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `reply_description` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`fed_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`fed_id`,`customer_id`,`description`,`reply_description`,`date`) values (1,3,'Good','okk','2020-02-14'),(2,3,'Very good service','Pending','2020-02-14');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin','admin'),(2,'br','br','branchemngr'),(3,'br2','br2','branchemngr'),(8,'\" uname \"','\" pass','customer'),(5,'st','st','staff'),(6,'de','de','boy'),(7,'cu','cu','customer'),(11,'an','an','customer');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `amount_paid` varchar(200) DEFAULT NULL,
  `payment_date` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`booking_id`,`amount_paid`,`payment_date`) values (2,1,'5000','13-02-2020'),(4,4,'5000','2020-02-14'),(5,2,'6000','2020-02-14');

/*Table structure for table `prices` */

DROP TABLE IF EXISTS `prices`;

CREATE TABLE `prices` (
  `price_id` int(11) NOT NULL AUTO_INCREMENT,
  `branch_id` int(11) DEFAULT NULL,
  `max_weight` varchar(200) DEFAULT NULL,
  `max_height` varchar(200) DEFAULT NULL,
  `max_width` varchar(200) DEFAULT NULL,
  `max_distance` varchar(200) DEFAULT NULL,
  `min_price` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`price_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `prices` */

insert  into `prices`(`price_id`,`branch_id`,`max_weight`,`max_height`,`max_width`,`max_distance`,`min_price`) values (1,1,'100','4','4','100','6000');

/*Table structure for table `review_rating` */

DROP TABLE IF EXISTS `review_rating`;

CREATE TABLE `review_rating` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `review_coment` varchar(200) DEFAULT NULL,
  `rating_point` varchar(100) DEFAULT NULL,
  `review_date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `review_rating` */

insert  into `review_rating`(`review_id`,`customer_id`,`branch_id`,`review_coment`,`rating_point`,`review_date`) values (1,1,1,'good','5','2020-02-13'),(2,3,2,'review','4.0','2020-02-14');

/*Table structure for table `staffs` */

DROP TABLE IF EXISTS `staffs`;

CREATE TABLE `staffs` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `f_name` varchar(20) DEFAULT NULL,
  `l_name` varchar(20) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `staffs` */

insert  into `staffs`(`staff_id`,`login_id`,`f_name`,`l_name`,`branch_id`,`phone`,`email`) values (1,5,'Anitha','Sasi',1,'anitha@gmail.com','8787878787');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
