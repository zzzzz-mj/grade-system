# 学生成绩管理系统（Spring Boot + MyBatis + MySQL）

基于简历「学生成绩管理系统（课程设计）」项目经历落地的**真实后端工程**，技术栈与简历一致：
**Spring Boot 3.3 + Java 17 + MyBatis + MySQL 8**，前端为同源托管的单页应用。

## 功能
- 角色登录：**教师（全功能）** / **学生（仅查看自己的成绩与班级排名）**
- 学生管理：增删改查、按姓名/学号/班级搜索（新增学生自动生成登录账号）
- 课程管理：增删
- 成绩管理：按学生+课程录入/修改/删除，**UNIQUE 约束防止成绩重复录入**
- 统计：学生/课程/成绩总数、平均分、及格率、各科与各班平均分
- 班级排名：按平均分排名，并计算班级内名次
- 参数校验与统一异常返回（前端 Toast 提示）

## 目录结构
```
grade-system-springboot/
├── pom.xml
├── src/main/resources/
│   ├── application.yml          # 数据源 / MyBatis / 启动建表配置
│   ├── schema.sql               # 建表 + 种子数据（幂等：IF NOT EXISTS + INSERT IGNORE）
│   └── static/index.html        # 前端单页应用（调用 /api/*）
└── src/main/java/com/example/gradesystem/
    ├── GradeSystemApplication.java
    ├── common/                  # Result 统一返回、BizException、GlobalExceptionHandler
    ├── config/CorsConfig.java
    ├── entity/                  # Student / Course / Grade / User
    ├── dto/                     # UserVO / GradeVO / RankRow / LoginRequest / GradeUpsertRequest
    ├── mapper/                  # Student / Course / Grade / User / Stats Mapper（注解 SQL）
    ├── service/                 # Auth / Student / Course / Grade / Stats Service
    └── controller/              # Auth / Student / Course / Grade / Stats / Classes Controller
```

## 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.x

## 运行步骤
1. 创建数据库（仅需执行一次）：
   ```sql
   CREATE DATABASE grade_system CHARACTER SET utf8mb4;
   ```
2. 修改 `src/main/resources/application.yml` 中的数据库连接账号密码：
   ```yaml
   spring:
     datasource:
       username: root
       password: root
   ```
3. 启动（首次启动会自动建表并写入种子数据）：
   ```bash
   mvn spring-boot:run
   ```
4. 浏览器打开：http://localhost:8080

> 表结构与种子数据由 `spring.sql.init.mode=always` 在启动时自动执行（幂等），
> 无需手动导入 `schema.sql`。

## 演示账号
| 角色 | 账号 | 密码 |
|------|------|------|
| 教师 | `teacher` | `123456` |
| 学生 | `20230101`（学号） | `123456` |

## 主要 REST 接口
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/login` | 登录，返回用户视图 |
| GET | `/api/students?filter=` | 学生列表（支持模糊搜索） |
| POST/PUT/DELETE | `/api/students[/id]` | 学生增改删 |
| GET | `/api/students/{id}/grades` | 某学生成绩（学生端用） |
| GET | `/api/students/{id}/stats` | 某学生统计（平均分/排名） |
| GET/POST/DELETE | `/api/courses[/id]` | 课程增删查 |
| GET/POST/PUT/DELETE | `/api/grades[/id]` | 成绩录入/修改/删除 |
| GET | `/api/stats/dashboard` | 概览统计 |
| GET | `/api/stats/course` `/api/stats/class` | 各科/各班统计 |
| GET | `/api/stats/rankings?className=` | 班级排名 |
| GET | `/api/classes` | 班级列表 |

返回结构统一为 `{ code:0, msg:"ok", data:... }`，`code!=0` 表示业务错误。
