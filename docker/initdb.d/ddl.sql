-- Create syntax for TABLE 'column'
CREATE TABLE `column` (
  `id` varchar(36) NOT NULL,
  `project_id` varchar(36) NOT NULL,
  `name` varchar(256) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'event'
CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event` text NOT NULL,
  `event_name` varchar(256) NOT NULL,
  `stored_at` datetime NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'consumed_event'
CREATE TABLE `consumed_event` (
  `id` bigint(20) NOT NULL,
  `receiver` varchar(100) NOT NULL,
  `received_at` datetime NOT NULL,
  PRIMARY KEY (`id`, `receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'note'
CREATE TABLE `note` (
  `id` varchar(36) NOT NULL,
  `project_id` varchar(36) NOT NULL,
  `column_id` varchar(36) NOT NULL,
  `description` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'project'
CREATE TABLE `project` (
  `id` varchar(36) NOT NULL,
  `name` varchar(256) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
