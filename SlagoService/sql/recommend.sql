USE SLAGODATABASE;
# 实现  
-- * 请求地址 `/apis/getAboutPosts`  
-- * 请求格式:  ?size=6[0<size<=10]  
-- * 返回格式:    
--   * 成功:{"status":200,"result":["12","43","65","3"]}  
--   * 失败:{"status":300,"result":[]}  

# 获取指定用户所关注的用户
SELECT followed FROM follow where userid="1901420313";
# 获取指定的多个用户所发的帖子  
SELECT id from post where userid IN (SELECT followed FROM follow where userid="1901420313") order by rand() limit 10;


# 实现  
-- * 请求地址 `/apis/getFindPosts`  
-- * 请求格式:  ?size=6[0<size<=10]  
-- * 返回格式:  
--   * 成功:{"status":200,"result":["12","43","65","3"]}  
--   * 失败:{"status":300,"result":[]}  
# 获取所有帖子内随机取100条 之后在在100条内取最新发布的10个帖子
SELECT posts.id from (SELECT * from post order by rand() limit 15) as posts order by posts.postdate limit 10;