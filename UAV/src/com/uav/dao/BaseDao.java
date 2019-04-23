package com.uav.dao;

import java.util.List;

import com.uav.entity.Apply;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;

public interface BaseDao {
	/*存储用户的信息*/
	User setSession(String user);//存储用户信息
	public boolean upwork(String headimg,int uid);//上传work作品图片名字
	public List<String> workname(int uid);//获取workname
	
	//大神榜
	public List<User> ranking();
   //首页的帖子
	public List<Post> post(int first,int length);
	//查询某个表的记录数
	public int getvalue(String table,String clas);
	//根据板块获取板块信息
	public List<Post> forpost(String forum,int first);
	//搜索置顶帖
	public List<Post> toppost(String table,String forum);
	//查询今日发帖人数
	public int getcount();
	//搜索帖子的评论数
	public int disscount(String pid);
	//查询某个帖子的回帖数量
	public int dissvalue(String pid);
	//论坛按时间降序查询
	public List<Post> timedesc(int page,int limit);
	//论坛按喜欢数降序查询
	public List<Post> likedesc(int page,int limit);
    //用户申请为版主
	public void apply(Apply apply);
	//根据专栏返回板块
	public List<String> getsection(String forum);
	//获得面板获得作品,所有作品
	public List<Work> getwork();
   //搜索有几条留言
	public int whomuch(int uid);
	//板块按热度排行
	public List<Post> postbyhot(String forum,int first);
	//轮播内容
	public List<Post> carousel();
	//加载教程内容
	public List<Post> teach();
	//加载置顶帖
	public List<Post> toppost();

}
