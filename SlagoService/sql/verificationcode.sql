USE SLAGODATABASE;
-- 验证码临时表
-- CREATE TABLE verificationcode(
-- 	email varchar(125) unique NOT NULL primary key,
--     code char(4) NOT NULL,
--     date DATETIME NOT NULL
-- );

-- 自动清除已经过期的验证码 事件 每30分钟执行一次
-- DELIMITER $$
-- CREATE EVENT SLAGODATABASE.delete_verificationcode
-- ON schedule AT EVERY 1 MINUTE
-- DO BEGIN
-- 	DELETE FROM verificationcode
--     WHERE date<NOW()-INTERVAL 1 HOUR;
-- END $$
