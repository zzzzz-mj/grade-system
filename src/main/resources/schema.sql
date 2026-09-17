-- ============================================================
-- 学生成绩管理系统 数据库脚本（MySQL 8）
-- 由 Spring Boot 启动时自动执行（application.yml: spring.sql.init.mode=always）
-- 已做幂等处理：表使用 IF NOT EXISTS，种子数据使用 INSERT IGNORE
-- 前置：请先手动创建数据库  CREATE DATABASE grade_system CHARACTER SET utf8mb4;
-- ============================================================

CREATE TABLE IF NOT EXISTS users (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(64)  NOT NULL,
    password   VARCHAR(64)  NOT NULL,
    role       VARCHAR(16)  NOT NULL COMMENT 'teacher | student',
    name       VARCHAR(64),
    ref_id     BIGINT COMMENT '关联 students.id（学生账号）',
    UNIQUE KEY uk_users_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS students (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_no VARCHAR(32)  NOT NULL,
    name       VARCHAR(64)  NOT NULL,
    gender     VARCHAR(8)   NOT NULL,
    class_name VARCHAR(64)  NOT NULL,
    UNIQUE KEY uk_students_no (student_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS courses (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_no  VARCHAR(32)  NOT NULL,
    course_name VARCHAR(128) NOT NULL,
    credit     DOUBLE       NOT NULL DEFAULT 3,
    UNIQUE KEY uk_courses_no (course_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS grades (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    score      DOUBLE NOT NULL,
    UNIQUE KEY uk_grades_unique (student_id, course_id),
    CONSTRAINT fk_grades_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_grades_course  FOREIGN KEY (course_id)  REFERENCES courses(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------- 种子数据 ----------------
INSERT IGNORE INTO users(username, password, role, name) VALUES ('teacher', '123456', 'teacher', '王老师');

INSERT IGNORE INTO students(student_no, name, gender, class_name) VALUES
('20230101', '张伟', '男', '2023级软件1班'),
('20230102', '李娜', '女', '2023级软件1班'),
('20230103', '王芳', '女', '2023级软件1班'),
('20230104', '刘洋', '男', '2023级软件1班'),
('20230201', '陈静', '女', '2023级软件2班'),
('20230202', '杨磊', '男', '2023级软件2班'),
('20230203', '赵敏', '女', '2023级软件2班'),
('20230204', '孙强', '男', '2023级软件2班');

INSERT IGNORE INTO courses(course_no, course_name, credit) VALUES
('CS101', 'Java程序设计', 4),
('CS102', '数据库原理', 3),
('CS103', 'Web应用开发', 3),
('CS104', '软件工程', 2);

-- 为学生生成登录账号（username=学号，密码 123456）
INSERT IGNORE INTO users(username, password, role, name, ref_id)
SELECT student_no, '123456', 'student', name, id FROM students
WHERE student_no NOT IN (SELECT username FROM users);

-- 成绩种子（student_id / course_id 通过子查询匹配，保证可重复执行）
INSERT IGNORE INTO grades(student_id, course_id, score)
SELECT s.id, c.id, g.score FROM (
    SELECT 1 AS si, 1 AS ci, 88 AS score UNION ALL
    SELECT 1, 2, 92 UNION ALL SELECT 1, 3, 85 UNION ALL SELECT 1, 4, 90 UNION ALL
    SELECT 2, 1, 76 UNION ALL SELECT 2, 2, 81 UNION ALL SELECT 2, 3, 79 UNION ALL SELECT 2, 4, 88 UNION ALL
    SELECT 3, 1, 95 UNION ALL SELECT 3, 2, 89 UNION ALL SELECT 3, 3, 93 UNION ALL SELECT 3, 4, 91 UNION ALL
    SELECT 4, 1, 68 UNION ALL SELECT 4, 2, 72 UNION ALL SELECT 4, 3, 65 UNION ALL SELECT 4, 4, 70 UNION ALL
    SELECT 5, 1, 82 UNION ALL SELECT 5, 2, 78 UNION ALL SELECT 5, 3, 85 UNION ALL SELECT 5, 4, 80 UNION ALL
    SELECT 6, 1, 90 UNION ALL SELECT 6, 2, 86 UNION ALL SELECT 6, 3, 88 UNION ALL SELECT 6, 4, 84 UNION ALL
    SELECT 7, 1, 73 UNION ALL SELECT 7, 2, 69 UNION ALL SELECT 7, 3, 77 UNION ALL SELECT 7, 4, 71 UNION ALL
    SELECT 8, 1, 60 UNION ALL SELECT 8, 2, 58 UNION ALL SELECT 8, 3, 62 UNION ALL SELECT 8, 4, 55
) g
JOIN students s ON s.id = g.si
JOIN courses  c ON c.id = g.ci;
