package com.uav.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.uav.dao.BaseDao;
import com.uav.entity.Apply;
import com.uav.entity.Discuss;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.util.DBconn;

public class BaseDaoImpl implements BaseDao {

	@Override
	public User setSession(String user) {
		int uid;
		String nick;
		String sex;
		String address;
		String sign;
		User list=new User();
		String sql=" select * from user where username='"+user+"'";
		
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				nick=rs.getString("nick");
				uid=rs.getInt("uid");
				sex=rs.getString("sex");
				address=rs.getString("address");
				sign=rs.getString("sign");
				list.setUid(uid);
				list.setNick(nick);
				list.setUsername(user);
			    list.setAddress(address);
			    list.setSex(sex);
			    list.setSign(sign);
				return list;
				
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查询存储用户信息异常");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override//用户上传作品图片更新数据库
	public boolean upwork(String headimg,int uid) {
		boolean flag=false;
		DBconn.init();
		String sql="replace into works set uid='"+uid+"',workname='"+headimg+"'";
		System.out.println(sql)	;
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return flag;
	}

	@Override
	public List<String> workname(int uid) {
		
	    List<String> list=new ArrayList<String>();
		DBconn.init();
		String sql="select workname from works where uid='"+uid+"'";
		System.out.println(sql)	;
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				list.add(rs.getString("workname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库查询错误");
		}
		for(String s:list){
			System.out.println("list数组："+s);
		}
		DBconn.closeConn();
		return list;
	}

	@Override
	public List<User> ranking() {
		List<User> list=new ArrayList<User>();
		String sql="SELECT nick,uid,fraction,imgname FROM user ORDER BY fraction DESC LIMIT 0,9";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User us=new User();
				us.setNick(rs.getString("nick"));
				us.setUid(rs.getInt("uid"));
				us.setFraction(rs.getInt("fraction"));
				us.setImgname(rs.getString("imgname"));
				list.add(us);
			}
		} catch (SQLException e) {
			System.out.println("数据库排行榜查询异常");
			e.printStackTrace();
			
		}
		DBconn.closeConn();
		return list;
	}

	@Override
	public List<Post> post(int first,int length) {
		List<Post> list=new ArrayList<Post>();
		String sql="select nick,pid,subjects,mydate,p.uid,forum,`like`,postimg from post p,user u where p.uid=u.uid and status='pass' ORDER BY mydate desc LIMIT "+first+","+length+"";
	    System.out.println(sql);
		DBconn.init();
	    ResultSet rs=DBconn.selectSql(sql);
	    try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setDate(rs.getString("mydate"));
				ps.setUid(rs.getInt("uid"));
				ps.setForum(rs.getString("forum"));
				ps.setLike(rs.getInt("like"));
				ps.setPostimg(rs.getString("postimg"));
				ps.setNick(rs.getString("nick"));
				list.add(ps);
			}
		} catch (SQLException e) {
			System.out.println("查询首页帖子信息错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int getvalue(String table,String clas) {
		int value=0;

		String sql="select count(*) from "+table+" where forum like '"+clas+"'";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				value =((Number)rs.getObject("count(*)")).intValue();
			}
		} catch (SQLException e) {
			System.out.println("Base查询记录数有误");
		}
		
		return value;
	}

	@Override
	public List<Post> forpost(String forum,int first) {
		List<Post> list=new ArrayList<Post>();
		String sql="select nick,pid,subjects,mydate,p.uid,`like` from post p,user u where forum='"+forum+"' and p.uid=u.uid and status='pass'  ORDER BY mydate desc limit "+first+",6";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setDate(rs.getString("mydate"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setUid(rs.getInt("uid"));
				ps.setLike(rs.getInt("like"));
				ps.setNick(rs.getString("nick"));
				ps.setForum(forum);
				list.add(ps);
			}return list;
		} catch (SQLException e) {
			System.out.println("根据板块查询帖子错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}

	@Override
	public List<Post> toppost(String forum, String grade) {
		List list=new ArrayList<Post>();
		DBconn.init();
		String sql="SELECT p.uid,subjects,pid,mydate,`like`,nick from post p,user u where p.uid=u.uid and forum='"+forum+"' and grade="+grade+" ORDER BY mydate desc LIMIT 0,3";
		System.out.println(sql);
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setUid(rs.getInt("uid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setPid(rs.getInt("pid"));
				ps.setDate(rs.getString("mydate"));
				ps.setLike(rs.getInt("like"));
				ps.setNick(rs.getString("nick"));
				list.add(ps);
				
			}return list;
		} catch (SQLException e) {
			System.out.println("查询置顶帖失败");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
		
	}

	@Override
	public int getcount() {
		int count=0;
		Date date =new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sql="select count(*) from post where mydate like '"+format.format(date)+"%'";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				count=(((Number)rs.getObject("count(*)")).intValue());
			}
		} catch (SQLException e) {
			System.out.println("查询今日发帖人数错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return count;
	}

	@Override
	public int disscount(String pid) {
		int count=0;
		String sql="select count(*) from discuss where pid="+pid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				count =((Number)rs.getObject("count(*)")).intValue();
			}
		} catch (SQLException e) {
			System.out.println("查询帖子的回复数有误");
			e.printStackTrace();
		}DBconn.closeConn();
		return count;
	}

	@Override
	public int dissvalue(String pid) {
		int dissvalue=0;
		String sql="select count(*) from discuss where pid="+pid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				dissvalue=(((Number)rs.getObject("count(*)")).intValue());	
			}
		} catch (SQLException e) {
			System.out.println("BDao查询回帖错误");
			e.printStackTrace();
		}
		return dissvalue;
	}

	@Override
	public List<Post> timedesc(int page,int limit) {
		List list =new ArrayList<Post>();
		String sql="select nick,pid,subjects,mydate,p.uid,forum,`like` from post p,user u where  p.uid=u.uid and status='pass' ORDER BY mydate desc limit "+page+","+limit+";";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setDate(rs.getString("mydate"));
				ps.setForum(rs.getString("forum"));
				ps.setLike(rs.getInt("like"));
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setUid(rs.getInt("uid"));
				ps.setNick(rs.getString("nick"));
			    list.add(ps);		
			}DBconn.closeConn();
			return list;
		} catch (SQLException e) {
			System.out.println("按时间查询Post有错");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Post> likedesc(int page,int limit) {
		List list =new ArrayList<Post>();
		String sql="select nick,pid,subjects,mydate,p.uid,forum,`like` from post p,user u where  p.uid=u.uid and status='pass' ORDER BY `like` desc limit "+page+","+limit+";";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setDate(rs.getString("mydate"));
				ps.setForum(rs.getString("forum"));
				ps.setLike(rs.getInt("like"));
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setUid(rs.getInt("uid"));
				ps.setNick(rs.getString("nick"));
			    list.add(ps);		
			}DBconn.closeConn();
			return list;
		} catch (SQLException e) {
			System.out.println("按喜欢人数查询Post有错");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void apply(Apply apply) {
		String sql="insert apply(uid,name,reason,date,forum) values("+apply.getUid()+",'"+apply.getName()+"','"+apply.getReason()+"','"+apply.getDate()+"','"+apply.getForum()+"')";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		System.out.println(sql);
		if(i>0){
			System.out.println("申请表已更新");
		}else{
			System.out.println("申请失败");
		}
	}

	@Override
	public List<String> getsection(String forum) {
		List list=new ArrayList<String>();
		String sql="select section from forum where vendor='"+forum+"' group by section;";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				list.add(rs.getString("section"));
			}
		} catch (SQLException e) {
			System.out.println("通过专栏查询板块错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return list;
	}

	@Override
	public List<Work> getwork() {
		List list =new ArrayList<Work>();
		String sql="select id,u.uid,title,date,w.`like`,nick,workpath from works w,`user` u where w.uid=u.uid";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Work wk=new Work();
				wk.setDate(rs.getString("date"));
				wk.setNick(rs.getString("nick"));
				wk.setTitle(rs.getString("title"));
				wk.setUid(rs.getShort("uid"));
				wk.setWorkpath(rs.getString("workpath"));
				wk.setLike(rs.getInt("like"));
				wk.setId(rs.getInt("id"));
				list.add(wk);
			}
		} catch (SQLException e) {
			System.out.println("版面查询作品错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int whomuch(int uid) {
		int count=0;
		String sql="select count(*) from message where toid="+uid+" and look=0 ";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				count=((Number)rs.getObject("count(*)")).intValue();
			}
		} catch (SQLException e) {
			System.out.println("查询用户留言数量有错");
			e.printStackTrace();
		}DBconn.closeConn();
		return count;
	}

	@Override
	public List<Post> postbyhot(String forum, int first) {
		List<Post> list=new ArrayList<Post>();
		String sql="select nick,pid,subjects,mydate,p.uid,`like` from post p,user u where forum='"+forum+"' and p.uid=u.uid and status='pass'  ORDER BY `like` desc limit "+first+",6";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setDate(rs.getString("mydate"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setUid(rs.getInt("uid"));
				ps.setLike(rs.getInt("like"));
				ps.setNick(rs.getString("nick"));
				ps.setForum(forum);
				list.add(ps);
			}return list;
		} catch (SQLException e) {
			System.out.println("根据板块查询帖子错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}

	@Override
	public List<Post> carousel() {
		List list =new ArrayList<Post>();
		String sql="select pid,postimg,subjects from post where uid=1 order by mydate desc limit 0,3";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setPostimg(rs.getString("postimg"));
				list.add(ps);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查询轮播帖错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Post> teach() {
		List list =new ArrayList<Post>();
		String sql="select tid,title,t.uid,date,nick from teach t,user u where u.uid=t.uid order by date desc";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("tid"));
				ps.setSubjects(rs.getString("title"));
				ps.setUid(rs.getInt("uid"));
				ps.setDate(rs.getString("date"));
				ps.setNick(rs.getString("nick"));
				list.add(ps);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查询教程有错");
			e.printStackTrace();
		}return list;
	}

	@Override
	public List<Post> toppost() {
		List list=new ArrayList();
		String sql="select pid,forum,subjects from post where grade=1 ORDER BY mydate desc limit 0,6";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setForum(rs.getString("forum"));
				list.add(ps);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("首页置顶帖错误");
			e.printStackTrace();
		}
		return list;
	}

	

	
	

}
