/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : wk_open_km

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 19/10/2023 09:32:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wk_km_action_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_action_record`;
CREATE TABLE `wk_km_action_record`  (
  `record_id` bigint NOT NULL,
  `status` int NULL DEFAULT NULL COMMENT '1 浏览 2 删除',
  `type` int NOT NULL COMMENT '1 知识库 2 文件夹 3 文档 4 文件 ',
  `type_id` bigint NOT NULL,
  `create_user_id` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `company_id`(`type_id` ASC, `type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库操作记录（最近使用）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_action_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_auth
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_auth`;
CREATE TABLE `wk_km_auth`  (
  `auth_id` bigint NOT NULL,
  `is_open` int NULL DEFAULT NULL COMMENT '是否公开 0 私有 1 公开',
  `open_auth` int NULL DEFAULT NULL COMMENT '公开权限 2 均可编辑 3 均可见，不可编辑',
  `is_mobile` tinyint NULL DEFAULT 1 COMMENT '是否手机端仅查看 0 否 1是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文档文件夹权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_auth
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_auth_user`;
CREATE TABLE `wk_km_auth_user`  (
  `r_id` bigint NOT NULL,
  `auth_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门id',
  `auth` int NULL DEFAULT NULL COMMENT '私有权限 1 所有权限 2 编辑权限 3只读权限',
  `is_mobile` tinyint NULL DEFAULT 1 COMMENT '是否支持手机端查看 0 否 1是',
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`r_id`) USING BTREE,
  INDEX `auth_id`(`auth_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_auth_user
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_collect
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_collect`;
CREATE TABLE `wk_km_collect`  (
  `collect_id` bigint NOT NULL,
  `type` int NOT NULL COMMENT '1 知识库 2 文件夹 3 文件',
  `type_id` bigint NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_collect
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document`;
CREATE TABLE `wk_km_document`  (
  `document_id` bigint NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文档标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `type` int NOT NULL COMMENT '3 富文本 4 文件',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '文件类型',
  `parent_id` bigint NULL DEFAULT 0,
  `status` int NULL DEFAULT 1 COMMENT '-1 删除 0 草稿 1 正常 2 模板',
  `library_id` bigint NULL DEFAULT NULL,
  `folder_id` bigint NOT NULL COMMENT '文件夹id',
  `auth_id` bigint NULL DEFAULT NULL,
  `label_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标签id',
  `create_time` datetime NOT NULL,
  `create_user_id` bigint NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `delete_user_id` bigint NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `ai_sync_status` tinyint NULL DEFAULT 0 COMMENT 'AI解析状态（0.未解析 1.解析中，2.解析成功，3.解析失败，默认 0',
  PRIMARY KEY (`document_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库文档表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_document
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document_favor
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_favor`;
CREATE TABLE `wk_km_document_favor`  (
  `favor_id` bigint NOT NULL,
  `document_id` bigint NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`favor_id`) USING BTREE,
  UNIQUE INDEX `wk_km_document_favor_document_id_create_user_id_uindex`(`document_id` ASC, `create_user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文档点赞表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_document_favor
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document_label
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_label`;
CREATE TABLE `wk_km_document_label`  (
  `label_id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文档标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_document_label
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document_share
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_share`;
CREATE TABLE `wk_km_document_share`  (
  `share_id` bigint NOT NULL,
  `document_id` bigint NULL DEFAULT NULL COMMENT '文档id',
  `share_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分享内部成员id',
  `share_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '外部分享链接',
  `token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '外部查看文档的唯一标识',
  `status` int NULL DEFAULT NULL COMMENT '1 启用 0 关闭分享',
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `close_user_id` bigint NULL DEFAULT NULL,
  `close_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`share_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '文档分享' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_document_share
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_folder
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_folder`;
CREATE TABLE `wk_km_folder`  (
  `folder_id` bigint NOT NULL,
  `library_id` bigint NULL DEFAULT NULL COMMENT '知识库id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` int NULL DEFAULT 1 COMMENT '-1 删除 1 正常',
  `create_user_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `auth_id` bigint NULL DEFAULT NULL,
  `delete_user_id` bigint NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库文件夹' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_folder
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_group
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_group`;
CREATE TABLE `wk_km_group`  (
  `group_id` bigint NOT NULL COMMENT '项目分组ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分组名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序字段',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '新建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `type` tinyint(1) NULL DEFAULT 3 COMMENT '分组类型(1:全部分组，2:未分组，3:自定义分组)',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库分组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_group
-- ----------------------------
INSERT INTO `wk_km_group` VALUES (1714547428749279232, '全部项目', 0, 1711560060463276032, '2023-10-18 15:42:15', '2023-10-18 15:42:15', NULL, 1);
INSERT INTO `wk_km_group` VALUES (1714547428749279233, '未分组', 1, 1711560060463276032, '2023-10-18 15:42:15', '2023-10-18 15:42:15', NULL, 2);

-- ----------------------------
-- Table structure for wk_km_group_management
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_group_management`;
CREATE TABLE `wk_km_group_management`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_id` bigint NOT NULL COMMENT '知识库组ID',
  `library_id` bigint NOT NULL COMMENT '知识库ID',
  `sort` int NULL DEFAULT NULL COMMENT '排序字段',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对应主键batchId',
  `create_user_id` bigint NOT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id`(`group_id` ASC) USING BTREE,
  INDEX `project_id`(`library_id` ASC) USING BTREE,
  INDEX `batch_id`(`batch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分组管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_group_management
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_knowledge_library
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_knowledge_library`;
CREATE TABLE `wk_km_knowledge_library`  (
  `library_id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '知识库名称',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '简介',
  `is_open` int NOT NULL COMMENT '是否公开 1 公开 2 私有',
  `status` int NULL DEFAULT 1 COMMENT '-1 删除 1 正常 2 模板',
  `is_system_cover` int NULL DEFAULT NULL COMMENT '0 否 1 是',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '知识库封面',
  `create_user_id` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `delete_user_id` bigint NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `ai_service_switch` tinyint NULL DEFAULT 1 COMMENT '是否开启AI服务（1 关 2 开 默认1）',
  PRIMARY KEY (`library_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_knowledge_library
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_knowledge_library_dept
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_knowledge_library_dept`;
CREATE TABLE `wk_km_knowledge_library_dept`  (
  `r_id` bigint NOT NULL,
  `library_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库部门关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_knowledge_library_dept
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_knowledge_library_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_knowledge_library_user`;
CREATE TABLE `wk_km_knowledge_library_user`  (
  `r_id` bigint NOT NULL,
  `library_id` bigint NOT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门id',
  `type` tinyint NULL DEFAULT 0 COMMENT '0 用户 1 部门',
  `role` int NULL DEFAULT NULL COMMENT '1 创建人 2 管理员 3 成员',
  `is_mobile` tinyint NULL DEFAULT 0 COMMENT '是否支持手机端查看',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '知识库成员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wk_km_knowledge_library_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
