# Host: localhost  (Version: 5.7.23)
# Date: 2020-02-24 19:42:05
# Generator: MySQL-Front 5.3  (Build 4.191)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "t_tag"
#

CREATE TABLE `t_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Structure for table "t_type"
#

CREATE TABLE `t_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Structure for table "t_user"
#

CREATE TABLE `t_user` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nick_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `search` (`username`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Structure for table "t_blog"
#

CREATE TABLE `t_blog` (
  `id` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8_unicode_ci,
  `first_picture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `flag` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `views` int(11) NOT NULL DEFAULT '0',
  `appreciation` bit(1) NOT NULL,
  `share_statement` bit(1) DEFAULT NULL COMMENT '转载声明',
  `commentabled` bit(1) NOT NULL,
  `published` bit(1) DEFAULT NULL,
  `recommend` bit(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tagIds` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `user_id` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `FK_type_id` (`type_id`),
  KEY `search` (`title`,`type_id`,`recommend`),
  CONSTRAINT `FK_type_id` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Structure for table "t_comment"
#

CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(40) COLLATE utf8_unicode_ci DEFAULT 'https://unsplash.it/100/100?image=10',
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isower` bit(1) DEFAULT b'0',
  `create_time` datetime DEFAULT NULL,
  `email` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nick_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `blog_id` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_blog_id` (`blog_id`),
  KEY `Fk_pid` (`pid`),
  CONSTRAINT `FK_blog_id` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`),
  CONSTRAINT `Fk_pid` FOREIGN KEY (`pid`) REFERENCES `t_comment` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

#
# Structure for table "t_blog_tags"
#

CREATE TABLE `t_blog_tags` (
  `blogs_id` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `tags_id` bigint(20) NOT NULL,
  KEY `FK_blogs_id` (`blogs_id`),
  KEY `FK_tags_id` (`tags_id`),
  CONSTRAINT `FK_blogs_id` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`),
  CONSTRAINT `FK_tags_id` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
