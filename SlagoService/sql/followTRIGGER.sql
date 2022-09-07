USE SLAGODATABASE;
-- 被点关注后用户粉丝数量+1 自身的关注数量+1
DROP TRIGGER followautoincre;
DELIMITER $$
CREATE TRIGGER followautoincre AFTER INSERT ON follow for each row
BEGIN
    UPDATE user
    SET fansNum=user.fansNum+1
    WHERE user.id=NEW.followed;
    UPDATE user
    SET AboutNum=user.AboutNum+1
    WHERE user.id=NEW.userid;
END$$
DELIMITER ;

-- 被取消关注后用户粉丝数量-1 自身的关注数量+1
DROP TRIGGER followautodecre;
DELIMITER $$
CREATE TRIGGER followautodecre AFTER delete ON follow for each row
BEGIN
    UPDATE user
    SET fansNum=user.fansNum-1
    WHERE user.id=OLD.followed;
	UPDATE user
    SET AboutNum=user.AboutNum-1
    WHERE user.id=OLD.userid;
END$$
DELIMITER ;