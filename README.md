# Assignment 08

## 概要

-   这是一个使用 Spring Boot + Kotlin 开发的在线问答留言交互平台，采用 MVC 分层架构，带验证码、用户认证与帖子回复功能。
-   Java 版本位于 [Assignment08-java](https://github.com/BobLiu0518/JavaWeb-Assignment/tree/Assignment08-java) 分支中。

## 技术栈

-   语言与平台：Kotlin，运行于 Java 17
-   框架：Spring Boot (WebMVC + Security + Data JPA + Thymeleaf)
-   持久层：Spring Data JPA + PostgreSQL
-   构建：Maven（打包为 WAR）

## 模块结构

-   `controller`：处理 HTTP 请求和路由（如认证、验证码、帖子列表与详情）
-   `service`：封装业务逻辑（如用户注册/登录、帖子创建/回复、验证码轮换）
-   `repository`：基于 JPA 的数据访问层（User/Thread/Post 仓库接口）
-   `model`：JPA 实体定义（`User`、`Thread`、`Post`）
-   `config`：应用配置（安全、验证码属性绑定等）
-   `resources`：静态资源、Thymeleaf 模板、应用配置文件（`application.properties`、`datasource.properties`、`captcha.properties`）

## 应用架构

-   架构：MVC（Controller → Service → Repository），Service 层为核心业务逻辑入口。
-   认证：使用 `HttpSession` 存储登录用户，Spring Security 提供密码加密（BCrypt）。
-   验证码：静态图片列表（`captcha/`），由 `CaptchaService` 随机轮换并将答案保存在 Session 中以供校验。
-   持久化：使用 JPA 映射实体，数据库为 PostgreSQL。

## 数据模型

-   `User`：主键 `id`，唯一用户名 `username`，密码哈希 `passwordHash`。
-   `Thread`：讨论主题（`title`、作者 `sender`、创建时间、关联 `posts` 列表）。
-   `Post`：帖子的内容、作者、时间、可选的回复父帖（`parent`）、所属 `thread`。

## 主要路由

-   认证：`/auth/login`、`/auth/register`、`/auth/logout`
-   验证码：`/captcha`（返回图片并在 Session 中保存答案）
-   帖子：`/threads`（列表、创建）、`/threads/{id}`（查看）、`/threads/{id}/reply`（回复）
