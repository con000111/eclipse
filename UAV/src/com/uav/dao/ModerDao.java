package com.uav.dao;

import java.util.List;

import com.uav.entity.Discuss;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;

public interface ModerDao {
	/*public List<User> queryuser();//查询用户
*/  public List<Post> querypost(String forum,int page,int limit);//查询帖子 
    public List<Discuss> querydiscuss(String forum,int page,int limit);//查询评论
    public List<Post> kquerypost(String key,String forum,int page,int limit);//查询帖子
    public List<Post> unreadpost(String forum,int page,int limit);//查看未审核的帖子
    public boolean judge(String status,String pid);//审核是否通过
    public List<Work> unreadvideo(int page,int limit);//查看未审核的视频
    public boolean judvideo(String status,String id);//审核是否通过
    public List<Discuss> dealreport(String section,int page,int limit);//版主查询举报回复
    /*public boolean deldiscuss(String did);//删除评论管理员中已有
     * */
    public boolean tozero(String did);//举报值归零
    public String myforum(int uid);//根据UID查询版主所属的板块
    public boolean totop(String pid,String operate);//置顶帖子
    public boolean addteach(Post ps);
}
