package com.uav.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uav.dao.AdminDao;
import com.uav.dao.UserDao;
import com.uav.entity.Apply;
import com.uav.entity.Discuss;
import com.uav.entity.Evaluate;
import com.uav.entity.Post;
import com.uav.entity.Section;
import com.uav.entity.User;
import com.uav.util.DBconn;

public class AdminDaoImpl implements AdminDao {

	/*@Override
	public List<User> queryalluser() {
		List list =new ArrayList();
		
		DBconn.init();
		String sql="select * from user";
		System.out.println(sql);
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User user=new User();
				user.setAddress(rs.getString("address"));
				user.setNick(rs.getString("nick"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setSign(rs.getString("sign"));
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setNick(rs.getString("nick"));
				user.setUauth(rs.getString("uauth"));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return list;
	}*/

	/*@Override
	public List<Post> queryallpost() {
		List<Post> list =new ArrayList();
		
		DBconn.init();
		String sql="select * from post";
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
				list.add(post);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return list;
		
	}
*/
	@Override
	public List<Discuss> queryalldiscuss() {
		List<Discuss> list =new ArrayList<Discuss>();
		
		DBconn.init();
		String sql="select * from discuss";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean deluser(String uid) {
		boolean flag=false;
		String sql="delete from user where uid="+uid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
			System.out.println("删除用户成功");
		}DBconn.closeConn();
		return flag;
	}

	@Override
	public boolean upduser(User user) {
		boolean flag=false;
		System.out.println("kashfosafho");
		String sql="update user set username='"+user.getUsername()+"',password='"+user.getPassword()+"',nick='"+user.getNick()+"',sex='"+user.getSex()+"',address='"+user.getAddress()+"',sign='"+user.getSign()+"',uauth='"+user.getUauth()+"',fraction="+user.getFraction()+" where uid="+user.getUid()+"";
	    System.out.println(sql);
		DBconn.init();
	    int i=DBconn.addupdel(sql);
	    if(i>0){
	    	flag=true;
	    }else{
	    	System.out.println("Adao更新用户数据库错误");
	    }
	    DBconn.closeConn();
	    return flag;
	}

	@Override
	public boolean insuser(User user) {
		boolean flag=false;
		String sql="insert into user(username,password) values('"+user.getUsername()+"','"+user.getPassword()+"')";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			System.out.println("更新成功");
			flag=true;
		}DBconn.closeConn();
		return flag;	
	}

	@Override
	public List<User> queryuser(int page, int limit) {
		String sql="select * from user limit "+page+","+limit+"";
		List<User> list=new ArrayList<User>();
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User user=new User();
				user.setAddress(rs.getString("address"));
				user.setNick(rs.getString("nick"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setSign(rs.getString("sign"));
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setNick(rs.getString("nick"));
				user.setUauth(rs.getString("uauth"));
				list.add(user);
				
			}return list;
		} catch (SQLException e) {
			System.out.println("查询所有用户错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}

	@Override
	public int getValue(String table) {
		int count=0;
		String sql="select count(*) from "+table+"";
		DBconn.init();
		ResultSet i=DBconn.selectSql(sql);
		try {
			while(i.next()){
				count =((Number)i.getObject("count(*)")).intValue();
			}
		} catch (SQLException e) {
			System.out.println("Adao查询数量错误");
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Post> querypost(int page, int limit) {
		List<Post> list =new ArrayList<Post>();
		String sql="select * from post limit "+page+","+limit+" ";
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
			}return list;
		} catch (SQLException e) {
			System.out.println("ADAo查询帖子错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}

	@Override
	public boolean delpost(String pid) {
		boolean flag=false;
		String sql="DELETE from post where pid="+pid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		
		return flag;
	}

	@Override
	public boolean adduser(String uname, String pass) {
		boolean flag=false;
		String sql="insert into user(username,password) values('"+uname+"','"+pass+"')";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return flag;
	}

	@Override
	public boolean updpost(Post post) {
		boolean flag=false;
		String sql="update post set subjects='"+post.getSubjects()+"',content='"+post.getContent()+"',mydate='"+post.getDate()+"',uid="+post.getUid()+",forum='"+post.getForum()+"',`like`="+post.getLike()+",grade="+post.getGrade()+",postimg='"+post.getPostimg()+"' where pid="+post.getPid()+"" ;
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean deldiscuss(String did) {
		boolean flag=false;
		String sql="delete from discuss where did="+did+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean upddiscuss(Discuss discuss) {
		boolean flag=false;
		String sql ="update discuss set uid="+discuss.getUid()+",discuss='"+discuss.getDiscuss()+"',unlike="+discuss.getLikenum()+",pid="+discuss.getPid()+",time='"+discuss.getTime()+"' where did="+discuss.getDid()+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public List<User> ukeyquery(String key,int page, int limit) {
		List list=new ArrayList<User>();
		String sql="select * from user where nick like '%"+key+"%' or username like '%"+key+"%' limit "+page+","+limit+" ";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User user=new User();
				user.setAddress(rs.getString("address"));
				user.setNick(rs.getString("nick"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setSign(rs.getString("sign"));
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setNick(rs.getString("nick"));
				user.setUauth(rs.getString("uauth"));
				list.add(user);
			}
		} catch (SQLException e) {
			System.out.println("条件查询用户错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return list;
	}

	@Override
	public List<Post> pkeyquery(String key, int page, int limit) {
		List list=new ArrayList<Post>();
		String sql="select * from post where subjects like '%"+key+"%' or uid like '%"+key+"%' limit "+page+","+limit+"";
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
			System.out.println("条件查询post错误");
			e.printStackTrace();
		}
		System.out.println("11111111111"+list.size());
		return list;
	}

	@Override
	public List<Apply> dealapply(int page,int limit) {
		List list=new ArrayList<Apply>();
		String sql="select * from apply where status='unread' limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Apply ap=new Apply();
				ap.setDate(rs.getString("date"));
				ap.setForum(rs.getString("forum"));
				ap.setName(rs.getString("name"));
				ap.setReason(rs.getString("reason"));
				ap.setUid(rs.getInt("uid"));
				list.add(ap);
			}DBconn.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Section> getsection(int page,int limit) {
		List list=new ArrayList<Section>();
		String sql="select *from forum group by section limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Section st=new Section();
				st.setVendor(rs.getString("vendor"));
				st.setUid(rs.getInt("uid"));
				st.setSection(rs.getString("section"));
				st.setId(rs.getInt("id"));
				list.add(st);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查询板块厂商有错");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int spcount(String forum) {
		int count=0;
		String sql="select count(*) from post where forum='"+forum+"'";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				count =((Number)rs.getObject("count(*)")).intValue();
			}
		} catch (SQLException e) {
			System.out.println("查询板块的帖子数量有错");
			e.printStackTrace();
		}DBconn.closeConn();
		return count;
	}

	@Override
	public boolean addsec(String vendor, String section) {
		boolean flag=false;
		String sql="insert into forum(vendor,section) value('"+vendor+"','"+section+"')";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}

	@Override
	public boolean editsec(String vendor, String section, String uid,String id) {
		boolean flag=false;
		String sql="update forum set vendor='"+vendor+"',section='"+section+"',uid="+uid+" where id="+id+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}
	public boolean delsec(String uid,String section){
		boolean flag=false;
		String sql="delete from forum where uid="+uid+" and section='"+section+"'" ;
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;	
	}

	@Override
	public List<Evaluate> getevaluate(int page,int limit) {
		List list=new ArrayList<Evaluate>();
		String sql="select a.uid,nolike,yeslike,uauth from "
				+ "(select p.uid,nolike,yeslike from (select uid,SUM(unlike) nolike from discuss "
				+ "group by uid) d right join  (select uid,SUM(`like`)"
				+ " yeslike from post group by uid) p on d.uid=p.uid) a LEFT JOIN user b on a.uid=b.uid limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Evaluate el=new Evaluate();
				el.setLike(rs.getInt("yeslike"));
				el.setUnlike(rs.getInt("nolike"));
				el.setUid(rs.getInt("a.uid"));
				el.setAuth(rs.getString("uauth"));
				list.add(el);
			}
		} catch (SQLException e) {
			System.out.println("查询用户评价表错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean uauth(String uid,String auth) {
		boolean flag=false;
		String sql="update user set uauth='"+auth+"' where uid="+uid+"";	
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public List<Post> dealpost(String status,int page,int limit) {
		List list=new ArrayList<Post>();
		String sql="select * from post where status='"+status+"' limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setContent(rs.getString("content"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setDate(rs.getString("mydate"));
				ps.setForum(rs.getString("forum"));
				ps.setUid(rs.getInt("uid"));
				list.add(ps);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("管理员查询所有帖子状态");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Discuss> dealreport(int page, int limit) {
		List list =new ArrayList<Discuss>();
		String sql="select time,did,forum,discuss,d.uid,unlike from discuss d,post p where d.pid=p.pid and unlike>=10 limit "+page+","+limit+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Discuss ds=new Discuss();
				ds.setDid(rs.getInt("did"));
				ds.setDiscuss(rs.getString("discuss"));
				ds.setLikenum(rs.getInt("unlike"));
				ds.setUid(rs.getInt("uid"));
				ds.setForum(rs.getString("forum"));
				ds.setTime(rs.getString("time"));
				list.add(ds);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("管理员查询举报评论有错");
			e.printStackTrace();
		}
		
		return list;
	}


}
