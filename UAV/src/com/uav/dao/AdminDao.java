package com.uav.dao;

import java.util.List;

import com.uav.entity.Apply;
import com.uav.entity.Discuss;
import com.uav.entity.Evaluate;
import com.uav.entity.Post;
import com.uav.entity.Section;
import com.uav.entity.User;

public interface AdminDao {
	/*public List<User> queryalluser();//查询用户
	public List<Post> queryallpost();//查询帖子
*/	public List<Discuss> queryalldiscuss();//查询所有评论
	
	public boolean deluser(String uid);//删除用户
	public boolean upduser(User user); //更新用户信息
	public boolean insuser(User user);//插入用户
	
	public List<User> queryuser(int page,int limit);//分页查询用户
	public int getValue(String table);//获取记录数
	public List<Post> querypost(int page,int limit);//分页查询帖子
	
	public boolean delpost(String pid);//根据pid删除帖子
	
	public boolean adduser(String uname,String pass);//增加用户
    public boolean updpost(Post post);//更新帖子
    public boolean deldiscuss(String did);//删除评论
    public boolean upddiscuss(Discuss discuss);//更新评论
    public List<User> ukeyquery(String key,int page,int limit);//关键字查询用户
    public List<Post> pkeyquery(String key,int page,int limit);//关键字查询帖子
    public List<Apply> dealapply(int page,int limit);//处理申请单
    public List<Section> getsection(int page,int limit);//获取板块
    public int spcount(String forum);//获取某个板块的帖子数量
    public boolean addsec(String vendor,String section);//添加板块
    public boolean editsec(String vendor,String section,String uid,String id);//修改板块
    public boolean delsec(String uid,String section);//删除板块
    public List<Evaluate> getevaluate(int page,int limit);//获取用户评价表
    public boolean uauth(String uid,String auth);//用户权限设置
    public List<Post> dealpost(String status,int page,int limit);//管理员查看所有未审批的帖子
    public List<Discuss> dealreport(int page,int limit);//管理员查看所有的举报情况
    
} 
