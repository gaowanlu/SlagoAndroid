USE SLAGODATABASE;

-- 必须字段 password name sex email


-- CREATE TABLE user
-- (
-- 	id BIGINT NOT NULL auto_increment primary KEY,
--     password VARCHAR(20) NOT NULL CHECK(NOT(password="")),
--     cookie CHAR(125),
--     name varchar(20) NOT NULL CHECK(NOT(name=""))
-- );
-- insert INTO user(id,password,cookie,name)
-- VALUES
-- (1901420313,220912,NULL,"高万禄");

# 添加性别字段
-- ALTER TABLE user
-- ADD sex VARCHAR(2) NOT NULL default '保密';
# 性别字段添加约束
-- ALTER TABLE user
-- ADD CONSTRAINT
-- sexArea CHECK(sex IN ('男','女','保密'));

# 添加个性签名字段
-- ALTER TABLE user
-- ADD profile VARCHAR(20) NOT NULL default '';

# 添加用户头像字段
-- ALTER TABLE user
-- ADD headImg MEDIUMBLOB default NULL;

# 添加用户头像文件类型
-- ALTER TABLE user
-- ADD headImgType varchar(10) default NULL;
-- ALTER TABLE user
-- ADD headImgSize  long default NULL;
-- SELECT * FROM SLAGODATABASE.user;

# 添加喜欢 关注 粉丝 数量字段
#为表添加新的列
-- ALTER TABLE user
-- ADD likeNum BIGINT NOT NULL DEFAULT 0;
-- ALTER TABLE user
-- ADD fansNum BIGINT NOT NULL DEFAULT 0;
-- ALTER TABLE user
-- ADD AboutNum BIGINT NOT NULL DEFAULT 0;


# 添加邮箱字段
-- ALTER TABLE user
-- ADD email VARCHAR(125) NOT NULL;

# 为邮箱与昵称添加唯一约束
-- ALTER TABLE user
-- ADD CONSTRAINT
-- unique_name UNIQUE(name);
-- ALTER TABLE user
-- ADD CONSTRAINT
-- unique_email UNIQUE(email);


