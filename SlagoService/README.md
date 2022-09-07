# Slago-Web

#### 介绍
Slago社交平台后端源代码  
UI Project:  
https://www.github.com/gaowanlu/Slago  
Service Project:  
https://gitee.com/gaowanlu/slago-web  
https://github.com/gaowanlu/slago-web  
我们的域名  
http://www.linkway.site:5555  
#### 工程技术
UI (Android\React\JavaScript)  
Java （Tomcat Server）
druid (数据库连接池)  
Mysql DataBase  

#### 数据库设计  
...

#### 贡献  
Wanlu | 欢迎Fork \ Issue \ Pull Request

#### 应用特点

原生JavaScript移动端发开解决方案  
安卓原生应用  
React.js Web开发

## API开发文档
### 用户身份验证模块 UserDataInit.Login
##### 身份验证 Filter
* 采用账号id与客户端Session解决方案   
cookie:{`id`:账号,`SlagoSession`:125位随机由数字与字母组成的序列}  
* 拦截器,当身份验证失败后返回数据格式  
{status:404}

##### 账号登录
* 请求地址`/SlagoService_Login`
* 请求格式:?id=$ & password=$ 
* 返回格式:  
    * 成功:  {"status":"200","result":"true"}
    * 失败:  {"status":"300"}
    
##### 身份验证
* 请求地址`/SlagoService_Authentication`  
* 返回格式:
    * 失败: {"status":"404","result":"true"}
    * 成功: {"status":"200","result":"false"}
    
### 用户个人信息模块 UserData
##### 用户头像
* 获取
  * 请求地址`/apis/getUserHeadImg`
  * 请求格式:?id=$
* 上传
  * 请求地址`/apis/setHeadImg`
  * 请求格式:`enctype:multipart/form-data   "headImg":图片数据 `
  * 返回格式
    * 成功: {"status":"200","result":"true"}
    * 失败: {"status":"300","result":"false"}
    
##### 获取用户点赞关注粉丝的数量
* 请求地址`/apis/getLikeAboutFans`
* 返回格式:?id=$
    * 成功: {"status":200,"likeNum":"0","aboutNum":"0","fansNum":"0"}
    * 失败: {"status":300}
##### 用户昵称
* 获取
    * 请求地址`/apis/getUserName`
    * 请求格式:?id=$  
      * 成功: {\"status\":200,\"name\":\"\"}
      * 失败: {\"status\":300,\"name\":\"\"}
* 更新
    * 请求地址`/apis/updateUserName`
    * 请求格式:?newname=$
    * 返回格式:  
      * 成功: {"status":200,"result":true}
      * 失败: {"status":300,"result":false}
      
##### 用户个性签名 
* 获取
  * 请求地址`/apis/getUserProfile`
  * 请求格式:?id=$
    * 成功: {\"status\":200,\"result\":\"\"}
    * 失败: {\"status\":300,\"result\":\"\"}
* 更新
  * 请求地址`/apis/setUserProfile`
  * 请求格式:?newProfile=$
  * 返回格式:
    * 成功: {"status":200,"result":true}
    * 失败: {"status":300,"result":false}
##### 用户性别
* 获取
  * 请求地址`/apis/getUserSex`
  * 请求格式:?id=$
    * 成功: {\"status\":200,\"result\":\"\"}
    * 失败: {\"status\":300,\"result\":\"\"}
* 更新
  * 请求地址`/apis/setUserSex`
  * 请求格式:?newSex=$
  * 返回格式:
    * 成功: {"status":200,"result":true}
    * 失败: {"status":300,"result":false}


### 帖子模块
##### 获取帖子信息
* 请求地址`/apis/getPostData`
* 请求格式:?postid=$
* 返回格式:
  * 成功:  {"status":200,"userid":"1901420313","posttext":"🤬🦊","postdate":"2021-07-19 20:49:55","imgs":["4"]}
  * 失败: {"status":300}
  
##### 获取帖子图片
* 请求地址`/apis/getPostImg` 
* 请求格式:?id=$  
* 返回格式 :图片

##### 获取指定用户的帖子
* 请求地址`/apis/getUserAllPost`
* 请求格式:?num=12&page=2&userid=1901420313
* 返回格式
  * 成功: {"status":200,"list":["22","21","20","19","18","17","16","15","14","13","12","11"],"countPostNum":7}
  * 失败: {"status":300,"list":[]}

##### 用户提交新的帖子
* 请求地址`/apis/uploadpost`
* 请求格式:  
  enctype:multipart/form-data  
  "img1":图片  
  "img2":图片  
  "img3":图片  
  "img4":图片  
  "img5":图片  
  "img6":图片  
  "content":文字
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":300,"result":false}

### 关注模块
###### 用户关注某个用户
* 请求地址 `/apis/following`
* 请求格式: ?otherId=$  
* 返回格式:
  * 成功:{"status":200,"result":true}  
  * 失败:{"status":200,"result":false}
###### 用户取消关注某个用户
* 请求地址 `/apis/unfollowing`
* 请求格式: ?otherId=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 获取粉丝列表
* 请求地址 `/apis/fanslist`
* 请求格式 ?page=$&pagesize=$
* 返回格式:
  {"status":200,"list":["123456"]}
  
###### 获取用户关注的用户列表
* 请求地址 `/apis/aboutlist`
* 请求格式 ?page=$&pagesize=$
* 返回格式:
  {"status":200,"list":["123456"]}


### 帖子点赞模块
###### 用户为某个帖子点赞
* 请求地址 `/apis/likePost`
* 请求格式: ?postId=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 用户为某个帖子取消点赞
* 请求地址 `/apis/unlikePost`
* 请求格式: ?postId=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
  
###### 获取用户点过赞的列表(本人只能获取自己的)
* 请求地址 `/apis/likelist`
* 请求格式 ?page=$&pagesize=$
* 返回格式:
  {"status":200,"list":["2","30","32","34"]}


### 帖子收藏模块
###### 用户收藏某个帖子
* 请求地址 `/apis/collectionPost`
* 请求格式: ?postId=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 用户取消对某个帖子的收藏
* 请求地址 `/apis/unCollectionPost`
* 请求格式: ?postId=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 获取收藏列表
* 请求地址 `/apis/collectionlist`
* 请求格式 ?page=$&pagesize=$
* 返回格式:
  {"status":200,"list":["3"]}


### 账号安全管理模块
###### 发送邮箱验证码
* 请求地址 `/SendVerificationCode`  
* 请求格式 ?email=$  
* 返回格式: 
  * 成功:{"status":200,"result":true,"id":"注册的账号"}  
###### 注册新账号
* 请求地址 `/RegisterNewCount`
* 请求格式: ?email=$&sex=$&name=$&password=$&checkCode=$    
* 返回格式:  
  * 成功:{"status":200,"result":true,"id":"注册的账号"}  
  * 失败:{"status":200,"result":false,"id":"出错的原因"}  
###### 修改密码  
* 请求地址 `/ChangePwd`  
* 请求格式: ?email=$&new=$&check=$  
* 返回格式:  
      * 成功:{"status":200,"result":"true"}  
      * 失败:{"status":200,"result":"原因"}  
  
###### 验证邮箱是否已经注册
* 请求地址 `/CheckUser`  
* 请求格式: ?email=$  |  name=$  |  id=$  任意组合  
* 返回格式:  
      * 成功:{"status":200,"result":"true|false"}  

###### 注销账号
* 暂不支持、用户所需可进行向开发者邮箱发送邮件

### 评论系统模块
###### 对某个帖子发起父评论[none]
* 请求地址 `/apis/addFComment`
* 请求格式: ?content=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 对某个父评论发起子评论[none]
* 请求地址 `/apis/addSComment`
* 请求格式: ?content=$ & fid=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 删除父评论[none]
* 请求地址 `/apis/DelFComment`
* 请求格式: ?fid=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
###### 删除子评论[none]
* 请求地址 `/apis/DelSComment`
* 请求格式: ?fid=$ & sid=$
* 返回格式:
  * 成功:{"status":200,"result":true}
  * 失败:{"status":200,"result":false}
  
###### 获取某帖子的评论信息[none]


### 系统推荐
###### 关注推荐
* 请求地址 `/apis/getAboutPosts`  
* 请求格式:  ?size=6[0<size<=10]  
* 返回格式:    
  * 成功:{"status":200,"result":["12","43","65","3"]}  
  * 失败:{"status":300,"result":[]}  
###### 发现推荐  
* 请求地址 `/apis/getFindPosts`  
* 请求格式:  ?size=6[0<size<=10]  
* 返回格式:  
  * 成功:{"status":200,"result":["12","43","65","3"]}  
  * 失败:{"status":300,"result":[]}  


