package com.uav.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uav.dao.ModerDao;
import com.uav.entity.Discuss;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.util.DBconn;

public class ModerDaoImpl implements ModerDao{
	@Override
	public List<Post> querypost(String forum, int page, int limit) {
		List list=new ArrayList<Post>();
		String sql="select * from post where forum='"+forum+"' limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post post =new Post();
				post.setPid(rs.getInt("pid"));
				post.setUid(rs.getInt("uid"));
				post.setContent(rs.getString("content"));
				post.setDate(rs.getString("mydate"));
				post.setForum(rs.getString("forum"));
				post.setSubjects(rs.getString("subjects"));
				post.setPostimg(rs.getString("postimg"));
				post.setGrade(rs.getInt("grade"));
				post.setLike(rs.getInt("like"));
				list.add(post);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("版主查询帖子错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Discuss> querydiscuss(String forum, int page, int limit) {
		List<Discuss> list =new ArrayList<Discuss>();
		DBconn.init();
		String sql="select d.did,discuss,unlike,p.pid,time,d.uid,forum from discuss d,post p where d.pid=p.pid and forum='"+forum+"' limit "+page+","+limit+"";
		System.out.println(sql);
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Discuss discuss=new Discuss();
				discuss.setDid(rs.getInt("did"));
				discuss.setDiscuss(rs.getString("discuss"));
				discuss.setLikenum(rs.getInt("unlike"));
				discuss.setPid(rs.getInt("pid"));
				discuss.setTime(rs.getString("time"));
				discuss.setUid(rs.getInt("uid"));
				list.add(discuss);
			}
			DBconn.closeConn();
			return list;
		} 
		catch (SQLException e) {
			System.out.println("版主查询评论错误");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Post> kquerypost(String key, String forum, int page, int limit) {
		List list=new ArrayList<Post>();
		String sql="select * from post where subjects like '%"+key+"%' or uid like '%"+key+"%' and forum='"+forum+"' limit "+page+","+limit+"";
		System.out.println("moder查询关键字查询语句:"+sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post post =new Post();
				post.setPid(rs.getInt("pid"));
				post.setUid(rs.getInt("uid"));
				post.setContent(rs.getString("content"));
				post.setDate(rs.getString("mydate"));
				post.setForum(rs.getString("forum"));
				post.setSubjects(rs.getString("subjects"));
				post.setPostimg(rs.getString("postimg"));
				post.setGrade(rs.getInt("grade"));
				post.setLike(rs.getInt("like"));
				list.add(post);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("版主查询帖子错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Post> unreadpost(String forum,int page,int limit) {
		List list=new ArrayList<Post>();
		String sql="select pid,subjects,content,mydate from post where forum like '%"+forum+"%' and status='unread' limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setSubjects(rs.getString("subjects"));
				ps.setDate(rs.getString("mydate"));
				ps.setForum(forum);
				ps.setContent(rs.getString("content"));
				ps.setPid(rs.getInt("pid"));
				list.add(ps);
			}DBconn.closeConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean judge(String status,String pid) {
		boolean flag=false;
		String sql="update post set status='"+status+"' where pid="+pid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}

	@Override
	public List<Work> unreadvideo(int page, int limit) {
		List list=new ArrayList<Work>();
		String sql="select uid,id,title,content,date from works where status='unread' order by date desc limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Work wk=new Work();
				wk.setContent(rs.getString("content"));
				wk.setDate(rs.getString("date"));
				wk.setId(rs.getInt("id"));
				wk.setTitle(rs.getString("title"));
				wk.setUid(rs.getInt("uid"));
				list.add(wk);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查询未通过的作品错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean judvideo(String status, String id) {
		boolean flag=false;
		String sql="update works set status='"+status+"' where id="+id+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public List<Discuss> dealreport(String section,int page,int limit) {
		List list =new ArrayList<Discuss>();
		String sql="select d.pid,time,did,forum,discuss,d.uid,unlike from discuss d,post p where d.pid=p.pid and unlike>=10 and forum='"+section+"' limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Discuss ds=new Discuss();
				ds.setPid(rs.getInt("pid"));
				ds.setDid(rs.getInt("did"));
				ds.setDiscuss(rs.getString("discuss"));
				ds.setLikenum(rs.getInt("unlike"));
				ds.setUid(rs.getInt("uid"));
				ds.setForum(rs.getString("forum"));
				ds.setTime(rs.getString("time"));
				list.add(ds);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("版主查询举报评论有错");
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean tozero(String did) {
		boolean flag=false;
		String sql="update discuss set unlike=0 where did="+did+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public String myforum(int uid) {
		String mysection="您不是版主啊";
		String sql="select section from forum where uid="+uid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				mysection=rs.getString("section");
				
			}
		} catch (SQLException e) {
			System.out.println("版主认证自己的板块错误");
			e.printStackTrace();
		}
				
		return mysection;
	}

	@Override
	public boolean totop(String pid,String operate) {
		boolean flag=false;
		String sql="update post set grade="+operate+" where pid="+pid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean addteach(Post ps) {
		boolean flag=false;
		String sql="insert teach(title,content,uid,date,videopath) values('"+ps.getSubjects()+"','"+ps.getContent()+"',"+ps.getUid()+",'"+ps.getDate()+"','"+ps.getPostimg()+"')";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		
		return flag;
	}

/*	@Override
	public boolean deldiscuss(String did) {
		boolean flag=false;
		String sql="DELETE from discuss where did="+did+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){管理员中已有
			flag=true;
		}DBconn.closeConn();
		return flag;
	}*/
	
}
