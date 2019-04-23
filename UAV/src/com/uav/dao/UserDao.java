package com.uav.dao;

import java.util.Date;
import java.util.List;

import com.uav.entity.Post;
import com.uav.entity.Punch;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.entity.Discuss;
import com.uav.entity.Message;

public interface UserDao {
	public boolean login(String name,String password);//登陆
	public boolean register(User user);//注册
	public boolean modify(int uid,String pass);//修改密码
	public String logins(String name,String password);//权限登陆

	/*发帖*/
	public boolean dopost(Post post);//发帖

	
	/*输入关键词查询
	 *
	 */
/*	List<Post> getPost(String arg, int a,int b);//获取要查询帖子的范围
    Object getValue(Object arg);//获取查询关键词的记录数
*/	public List<Post> querypost(String key);//查询帖子
    public List<User> queryuser(String key);//查询用户
	
    /* 修改信息*/
	public boolean alertinfo(User user);
	
	/*打卡*/
	public Punch record(int uid);
	public boolean punch(int uid,int sum);
	
	/*上传头像名*/
	public boolean uphead(String headimg,int uid);
	/*获取头像名*/
	public String gethead(int uid);
	/*查看我发的所有帖子信息*/
	public List<Post> mypost(int uid);
	/*查看我发的帖子的详细内容*/
	public List<Post> detailpost(String pid);
	/*上传帖图片子名*/
	public boolean uppost(String imgname,int pid);
    /*用户添加评论*/
	public boolean adddiscuss(int uid,String discuss,int pid ,String time);
	/*查询帖子的评论*/
	public List<Discuss> seediscuss(String pid);
	//修改帖子信息
	public boolean alterpost(Post post,String pid);
	//用户点击收藏
	public void collect(String pid,String uid);
	//查询收藏表
	public List<Post> mycoll(String uid);
	//用户点赞
	public boolean like(String pid);
	//获取其他用户主页
	public List<User> otherpage(String uid);
	//获取其他用户所发的帖子
	public List<Post> otherpost(String uid);
	//添加好友
	public boolean addfri(String fid,int uid);
	//查询好友
	public List<User> myfri(int uid);
	//留言功能
	public boolean mess(Message mess);
	//查询最新留言
	public List<Message> newmess(int toid);
	//更改为已读记录
	public boolean readed(int toid);
	//查询邮件账号
	public String myemail(int uid);
	//绑定邮箱
	public boolean bandmail(int uid,String mail);
	//解除邮箱绑定
	public boolean cancelmail(int uid);
	//取消收藏
	public boolean cancelcoll(String pid,int uid);
	//删除好友
	public boolean delfri(String fid,int uid);
	
	//根据UID获取用户信息
	public User getuser(String uid);
	//上传作品
	public boolean upwork(Work work);
	//查看作品
	public List seework(String id);
	//举报评论
	public boolean report(String did);
	//查看教程
	public Post seeteach(int tid);
	//找回密码
	public User findpass(String username);
}
