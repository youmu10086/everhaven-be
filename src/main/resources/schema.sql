DROP TABLE IF EXISTS carousel_slides;
CREATE TABLE carousel_slides (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    image VARCHAR(500) NOT NULL,
    image_dark VARCHAR(500),
    type VARCHAR(50) DEFAULT 'hero',
    title VARCHAR(200) NOT NULL,
    subtitle VARCHAR(500),
    link VARCHAR(500),
    label VARCHAR(100),
    description VARCHAR(1000),
    sort_order INT DEFAULT 0,
    status BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS nav_items;
DROP TABLE IF EXISTS nav_categories;
SET FOREIGN_KEY_CHECKS = 0; DROP TABLE IF EXISTS users; SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'USER' COMMENT '角色: USER-普通用户, HOST-房东, ADMIN-管理员',
    user_pic VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE nav_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE nav_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    link VARCHAR(500),
    image VARCHAR(500),
    FOREIGN KEY (category_id) REFERENCES nav_categories(id) ON DELETE CASCADE
);


-- 房屋公寓表
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS apartments;
SET FOREIGN_KEY_CHECKS = 1;
CREATE TABLE apartments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    location VARCHAR(500),
    price DECIMAL(10, 2),
    price_unit VARCHAR(50),
    image VARCHAR(500),
    tag VARCHAR(500),
    category_group VARCHAR(50),
    category_type VARCHAR(50),
    status TINYINT DEFAULT 1 COMMENT '1-正常, 0-下架',
    rating DECIMAL(2, 1) DEFAULT 4.5,
    review_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



-- Apartment Label Management
DROP TABLE IF EXISTS apartment_labels;
DROP TABLE IF EXISTS labels;

CREATE TABLE labels (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    type VARCHAR(20) DEFAULT 'default' COMMENT '标签类型: default, facility, service',
    color VARCHAR(20) DEFAULT '#409EFF',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE apartment_labels (
    apartment_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    PRIMARY KEY (apartment_id, label_id)
);
CREATE TABLE IF NOT EXISTS category_meta (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  category_group VARCHAR(50) NOT NULL,
  category_type VARCHAR(50) NOT NULL,
  title VARCHAR(200),
  description VARCHAR(500),
  badge VARCHAR(50),
  image VARCHAR(500),
  UNIQUE KEY uk_group_type (category_group, category_type)
);

-- 活动表
DROP TABLE IF EXISTS activities;
CREATE TABLE activities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    image VARCHAR(500),
    tag VARCHAR(100),
    type VARCHAR(50),
    valid_until DATETIME,
    status BOOLEAN DEFAULT TRUE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
