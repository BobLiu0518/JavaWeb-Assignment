# Assignment 06

## 概述

基于 Jakarta Servlet 的简单二手商品信息发布与浏览系统，采用 MVC 分层设计。

## 系统结构设计

-   `controller`（Servlets）
    -   负责接收 HTTP 请求，调用对应的 Service，并将结果转发到 JSP。
-   `service`（Services）
    -   封装业务规则：`AuthService`（注册、登录、密码哈希校验）、`UserService`（获取用户信息）、`GoodsService`（商品分页查询、保存、删除）、`ImageService`（图片元信息持久化）。
    -   构造函数中会调用需要的 DAO 的表初始化方法（确保表存在）。
-   `dao`（DAOs）
    -   使用 JNDI `DataSource`（`java:comp/env/jdbc/pgsql`）获取数据库连接（见 `Dao.getConnection()`）。
    -   包含 SQL 建表与 CRUD 实现（使用 PostgreSQL 驱动）。
-   `model`
    -   简单 POJO，使用 Lombok 生成 getter/setter/构造器。

## 数据库结构说明

该项目使用 PostgreSQL，JNDI 名称为 `jdbc/pgsql`。下面是数据库表结构：

1. `users` 表

```sql
CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password_hash CHAR(60) NOT NULL,
  UNIQUE(username)
);
```

-   说明：`password_hash` 使用 BCrypt 存储，以确保不可逆推、无法查表。

2. `images` 表

```sql
CREATE TABLE IF NOT EXISTS images (
  hash CHAR(32) PRIMARY KEY,
  mime VARCHAR(50) NOT NULL
);
```

-   说明：图片文件以哈希命名存储在服务器 `uploads/` 目录中，数据库仅保存 `hash` 与 `mime` 类型。

3. `goods` 表

```sql
CREATE TABLE IF NOT EXISTS goods (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10, 2) NOT NULL,
  is_sold BOOLEAN DEFAULT FALSE,
  publisher_id INT NOT NULL,
  image_hash VARCHAR(255) DEFAULT NULL,
  FOREIGN KEY (publisher_id) REFERENCES users(id),
  FOREIGN KEY (image_hash) REFERENCES images(hash)
);
```

-   说明：`publisher_id` 关联 `users(id)`，`image_hash` 关联 `images(hash)`（可为空）。

数据库初始化：

-   应用启动时（调用对应 Service 的构造函数）会执行 `CREATE TABLE IF NOT EXISTS ...`，无须手工创建表；但仍需事先创建数据库和数据库用户，并在容器中配置 JNDI DataSource。

## 配置 JNDI

将下面配置添加到 Tomcat 的 `conf/context.xml`（或部署的应用 `META-INF/context.xml`）：

```xml
<Resource name="jdbc/pgsql" type="javax.sql.DataSource"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://127.0.0.1:5432/assignment06"
          username="assignment06_user" password="your_password_here"
          maxTotal="20" maxIdle="10" maxWaitMillis="-1"/>
<Environment name="imageStoragePath"
             value="/path/to/image/storage"
             type="java.lang.String"
             override="false"/>
```

## 测试与账号说明

-   项目支持用户注册功能；测试时请通过注册功能自行注册测试账号。
-   密码使用 BCrypt 哈希保存；请使用长度 >= 8 的密码和用户名长度 >= 4 的用户名。
-   推荐测试流程：
    -   注册用户 → 登录 → 发布商品（可上传图片） → 在商品列表/详情查看 → 尝试编辑/删除（登录用户为发布者时可进行）
