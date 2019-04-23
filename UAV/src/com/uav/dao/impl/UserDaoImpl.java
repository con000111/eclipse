package com.uav.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uav.dao.UserDao;
import com.uav.entity.Discuss;
import com.uav.entity.Message;
import com.uav.entity.Post;
import com.uav.entity.Punch;
import com.uav.entity.User;
import com.uav.entity.Work;
import com.uav.util.DBconn;

public class UserDaoImpl implements UserDao {
 
	@Override
	public String logins(String name, String password) {
		String flag="false";
		String sql="select uauth from user where username='"+name+"' and password='"+password+"'";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				flag=rs.getString("uauth");
			}
		} catch (SQLException e) {
			System.out.println("权限登陆失败");
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	public boolean login(String name, String password) {
		boolean flag = false;
		try {
			    DBconn.init();
				ResultSet rs = DBconn.selectSql("select * from user where username='"+name+"' and password='"+password+"'");
			
				while(rs.next()){
				
					if(rs.getString("username").equals(name) && rs.getString("password").equals(password)){
						flag = true;
					}
				}
				DBconn.closeConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	

	@Override
	public boolean register(User user) {
		boolean flag=false;
		
		DBconn.init();
		String sql="insert into user(username,password) values('"+user.getUsername()+"','"+user.getPassword()+"')";
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return true;
	} 

	@Override
	public boolean dopost(Post post) {
		boolean flag=false;
		/*DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
		/*String date=fmt.format(post.getDate());*/
		DBconn.init();
		String sql="insert into post(subjects,content,mydate,uid,forum,postimg) " +
				"values('"+post.getSubjects()+"','"+post.getContent()+"','"+post.getDate()+"','"+post.getUid()+"','"+post.getForum()+"','"+post.getPostimg()+"')";
		System.out.println(sql);
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}

	@Override
	public List<Post> querypost(String key) {
		List<Post> list=new ArrayList();
		DBconn.init();
		String sql="select * from post where subjects like '%"+key+"%' ";/*or content like '%"+key+"%'*/
		ResultSet rs=DBconn.selectSql(sql);		
		try {
			while(rs.next()){
				Post post=new Post();
				post.setSubjects(rs.getString("subjects"));
				post.setPid(rs.getInt("pid"));
				post.setLike(rs.getInt("like"));
				post.setDate(rs.getString("mydate"));
				post.setUid(rs.getInt("uid"));
				list.add(post);
			}DBconn.closeConn();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询错误");
		}
		return list;
	}



	/*@Override//获取查询范围,arg为用户查询的内容，a\b为查询范围
	public List<Post> getPost(String arg, int a, int b) {
		List<Post> list=new ArrayList();
		DBconn.init();
		a=a-1;
		int c=a*b;
		ResultSet rs=DBconn.selectSql("SELECT * FROM post where subjects like '%"+arg+"%' limit "+c+","+b+" ");
		System.out.println("SELECT * FROM post where subjects like '%"+arg+"%' limit "+c+","+b+" ");
		try {
			while(rs.next()){
				Post post=new Post();
				post.setSubjects(rs.getString("subjects"));
				post.setContent(rs.getString("content"));
				post.setDate(rs.getString("mydate"));
				post.setUid(rs.getInt("uid"));
				list.add(post);
			
			}
			DBconn.closeConn();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("分页查询post内容有错");
		}
		
		return null;
	}*/



	/*@Override//获取记录数
	public Object getValue(Object arg) {
		 int obj = 0;
		  try {
			DBconn.init();
			  String sql = "SELECT COUNT(pid)  FROM post WHERE subjects like '%"+arg+"%' ";
			  ResultSet rs = DBconn.selectSql(sql);
			  while(rs.next()){
			  obj=((Number)rs.getObject("COUNT(pid)")).intValue();
			  System.out.println(obj); 	  
			  }
			  DBconn.closeConn();
			  return obj;
		} 
		  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询总记录数异常");
			return null;
		}
	}
*/


	@Override
	public boolean alertinfo(User user) {
		boolean flag=false;
		
		DBconn.init();
		String sql="update user set nick='"+user.getNick()+"',sex='"+user.getSex()+"',address='"+user.getAddress()+"',sign='"+user.getSign()+"' where uid='"+user.getUid()+"' ";
		System.out.println(sql);
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return flag;
	}



	@Override
	public boolean punch(int uid,int sum) {
		//更新打卡记录
		boolean flag=false;
		Date date=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DBconn.init();
		String sql="replace into punch set uid='"+uid+"',punchtime='"+format.format(date)+"',sum='"+sum+"'";
		System.out.println(sql);
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		//加5积分
		String sql2="update user set fraction=fraction+5 where uid='"+uid+"'";
		int i2=DBconn.addupdel(sql2);
		if(i2<=0){
			flag=false;
		}
		DBconn.closeConn();
		return flag;
	}



	@Override
	public boolean uphead(String headimg,int uid) {
		boolean flag=false;
		DBconn.init();
		String sql="update user set imgname='"+headimg+"' where uid='"+uid+"'";
		System.out.println(sql)	;
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return flag;
		
	}



	@Override
	public String gethead(int uid) {
		String imgname="dhimg.jpg";
		DBconn.init();
		String sql="select imgname from user where uid='"+uid+"'";
		System.out.println(sql)	;
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				imgname=rs.getString("imgname");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库查询错误");
		}
		DBconn.closeConn();
		System.out.println("gethead:"+imgname);
		return imgname;
	}



	@Override
	public Punch record(int uid) {
		Punch punch=new Punch();
		Date date=new Date();
		String StrD ="2000-01-01";		
		SimpleDateFormat sdfd =new SimpleDateFormat("yyy-MM-dd");
		try {
			Date dat =sdfd.parse(StrD);
			punch.setPunchtime(dat);//设置初始打卡时间
			punch.setCount(9999);//设置初始打卡人数
			punch.setSum(0);//设置初始连续打卡次数
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql="select punchtime,sum from punch where uid='"+uid+"' ";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				punch.setPunchtime(rs.getDate("punchtime"));
				punch.setSum(rs.getInt("sum"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库查询打卡日期错误");
		}
		String sql2="select COUNT(uid) from punch where punchtime='"+sdfd.format(date)+"'";
		System.out.println(sql2);
		ResultSet rs2=DBconn.selectSql(sql2);
		try {
			while(rs2.next()){
				punch.setCount(((Number)rs2.getObject("COUNT(uid)")).intValue());
			}
		} catch (SQLException e) {
			System.out.println("数据库查询今日打卡记录错误");
			e.printStackTrace();
		}
		
		DBconn.closeConn();
		return punch;
	}

	@Override
	public List<Post> mypost(int uid) {
		List<Post> list =new ArrayList<Post>();
		String sql="select subjects,pid,mydate from post where uid='"+uid+"'";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post post=new Post();
				post.setSubjects(rs.getString("subjects"));
				post.setPid(rs.getInt("pid"));
				post.setDate(rs.getString("mydate"));
				list.add(post);
			}
		} catch (SQLException e) {
			System.out.println("我的帖子模块查询错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return list;
	}



	@Override
	public List<Post> detailpost(String pid) {
		List<Post> list=new ArrayList<Post>();
		String sql="select pid,subjects,content,mydate,forum,`like`,p.uid,nick from post p,`user` u where p.uid=u.uid and pid="+pid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post pt=new Post();
				pt.setUid(rs.getInt("uid"));
				pt.setPid(rs.getInt("pid"));
			    pt.setSubjects(rs.getString("subjects"));
			    pt.setContent(rs.getString("content"));
			    pt.setDate(rs.getString("mydate"));
			    pt.setForum(rs.getString("forum"));
			    pt.setLike(rs.getInt("like"));
			    pt.setNick(rs.getString("nick"));
			    list.add(pt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}DBconn.closeConn();
		return list;
	}



	@Override
	public List<User> queryuser(String key) {
		List<User> list=new ArrayList<User>();
		String sql="select uid,nick,sex,imgname,fraction,address from user where nick like '%"+key+"%'";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User us=new User();
				us.setUid(rs.getInt("uid"));
				us.setNick(rs.getString("nick"));
				us.setSex(rs.getString("sex"));
				us.setImgname(rs.getString("imgname"));
				us.setFraction(rs.getInt("fraction"));
				us.setAddress(rs.getString("address"));
				list.add(us);
			}
		} catch (SQLException e) {
			System.out.println("数据库查询错误");
			e.printStackTrace();
		}
		DBconn.closeConn();
		return list;
	}



	@Override//上传帖子图片名
	public boolean uppost(String imgname,int pid) {
		boolean flag=false;
		String sql="update post set postimg='"+imgname+"' where pid='"+pid+"'";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}



	@Override
	public boolean adddiscuss(int uid, String discuss, int pid, String time) {
		boolean flag=false;
		String sql="insert into discuss(uid,discuss,pid,time) VALUES("+uid+",'"+discuss+"',"+pid+",'"+time+"')";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}



	@Override
	public List<Discuss> seediscuss(String pid) {
		boolean flag=false;
		String sql="select did,d.uid,nick,discuss,time from discuss d,user u where u.uid=d.uid and pid="+pid+";";
		System.out.println(sql);
		List<Discuss> list=new ArrayList<Discuss>();
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Discuss ds=new Discuss();
				ds.setUid(rs.getInt("uid"));
				ds.setNick(rs.getString("nick"));
				ds.setDiscuss(rs.getString("discuss"));
				ds.setTime(rs.getString("time"));
				ds.setDid(rs.getInt("did"));
				list.add(ds);
			}
		} catch (SQLException e) {
			System.out.println("UDao查询帖子的回复有误");
			e.printStackTrace();
		}
		return list;
	}



	@Override
	public boolean alterpost(Post post,String pid) {
		boolean flag=false;
		String sql="update post set forum='"+post.getForum()+"',content='"+post.getContent()+"', subjects='"+post.getSubjects()+"' where pid='"+pid+"'";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
			System.out.println("UDao修改帖子成功");
		}else{
			System.out.println("UDao修改帖子失败");
		}DBconn.closeConn();
		return flag;
		
	}



	@Override
	public void collect(String pid, String uid) {
		String sql="replace into collect set uid="+uid+",pid="+pid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			System.out.println("收藏成功");
		}else{
			System.out.println("收藏失败");
		}
		
	}



	@Override
	public List<Post> mycoll(String uid) {
		List<Post> list =new ArrayList<Post>();
		String sql="select subjects,mydate,c.pid from (select * from collect where uid="+uid+") c,post p where c.pid=p.pid";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setDate(rs.getString("mydate"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setPid(rs.getInt("pid"));
				list.add(ps);
			}return list;
		} catch (SQLException e) {
			System.out.println("查询收藏表错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}



	@Override
	public boolean like(String pid) {
		boolean flag=false;
		String sql="UPDATE post set `like`=`like`+1 where pid="+pid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			System.out.println("点赞OK！");
			flag=true;
		}
		return flag;
	}



	@Override
	public List<User> otherpage(String uid) {
		List<User> list =new ArrayList<User>();
		String sql="select address,nick,sex,imgname,sign,fraction from user where uid="+uid+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User us=new User();
				us.setAddress(rs.getString("address"));
				us.setNick(rs.getString("nick"));
				us.setSex(rs.getString("sex"));
				us.setImgname(rs.getString("imgname"));
				us.setSign(rs.getString("sign"));
				us.setFraction(rs.getInt("fraction"));
				list.add(us);
			}return list;
		} catch (SQLException e) {
			System.out.println("查询其他用户主页失败");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}



	@Override
	public List<Post> otherpost(String uid) {
		List<Post> list=new ArrayList<Post>();
		String sql="select pid,subjects,mydate from post where uid="+uid+"";
		System.out.println(sql);
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Post ps=new Post();
				ps.setPid(rs.getInt("pid"));
				ps.setSubjects(rs.getString("subjects"));
				ps.setDate(rs.getString("mydate"));
				list.add(ps);
			}return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}



	@Override
	public boolean addfri(String fid, int uid) {
		boolean flag=false;
		String sql="replace into friend set uid="+uid+",fid="+fid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
			System.out.println("添加好友ok");
		}DBconn.closeConn();
		return flag;
	}



	@Override
	public List<User> myfri(int uid) {
		List<User> list=new ArrayList<User>();
		String sql="select u.uid,nick from `user` u,(select * from friend where uid="+uid+") s where u.uid=s.fid";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				User us=new User();
				us.setNick(rs.getString("nick"));
				us.setUid(rs.getInt("uid"));
				list.add(us);
			}return list;
		} catch (SQLException e) {
			System.out.println("查询我的好友错误");
			e.printStackTrace();
		}DBconn.closeConn();
		return null;
	}



	@Override
	public boolean mess(Message mess) {
		boolean flag=false;
		String sql="insert into message(fromid,toid,content,date) values("+mess.getFromid()+","+mess.getToid()+",'"+mess.getContent()+"','"+mess.getDate()+"')";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}



	@Override
	public List<Message> newmess(int toid) {
		List<Message> list=new ArrayList<Message>();
		String sql="select fromid,content,date,nick from message m,`user` u where toid="+toid+" and look=0 and m.fromid=u.uid;";
		DBconn.init();
		System.out.println(sql);
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Message ms=new Message();
				ms.setContent(rs.getString("content"));
				ms.setDate(rs.getString("date"));
				ms.setFromid(rs.getInt("fromid"));
				ms.setNick(rs.getString("nick"));
				list.add(ms);
			}return list;
		} catch (SQLException e) {
			System.out.println("查询未读留言错误");
			e.printStackTrace();
		}
		DBconn.closeConn();
		return null;
	}

	@Override
	public boolean readed(int toid) {
		boolean flag=false;
		DBconn.init();
		String sql="update message set look=1 where toid="+toid+"";
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		DBconn.closeConn();
		return flag;
	}



	@Override
	public String myemail(int uid) {
		String myemail = null;
		String sql="select mail from user where uid="+uid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				myemail=rs.getString("mail");
			}
			DBconn.closeConn();
			return myemail;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}



	@Override
	public boolean modify(int uid, String pass) {
		boolean flag=false;
		String sql="update user set password='"+pass+"' where uid="+uid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}



	@Override
	public boolean bandmail(int uid,String mail) {
		boolean flag=false;
		String sql="update user set mail='"+mail+"' where uid="+uid+"";
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
		
	}



	@Override
	public boolean cancelmail(int uid) {
		boolean flag=false;
		String sql="update user set mail=null where uid="+uid+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
		
	}



	@Override
	public boolean cancelcoll(String pid,int uid) {
		boolean flag=false;
		String sql="DELETE from collect where pid="+pid+" and uid="+uid+"";
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
	public boolean delfri(String fid, int uid) {
		boolean flag=false;
		String sql="delete from friend where fid="+fid+" and uid="+uid+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}DBconn.closeConn();
		return flag;
	}



	@Override
	public User getuser(String uid) {
		User us=new User();
		String sql="select * from user where uid="+uid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				us.setAddress(rs.getString("address"));
				us.setFraction(rs.getInt("fraction"));
				us.setImgname(rs.getString("imgname"));
				us.setNick(rs.getString("nick"));
				us.setSex(rs.getString("sex"));
				us.setSign(rs.getString("sign"));
				
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("通过UID查询用户信息有错");
			e.printStackTrace();
		}
		return us;
	}

	@Override
	public boolean upwork(Work work) {
		boolean flag=false;
		String sql="insert works(workpath,uid,title,content,date) VALUES('"+work.getWorkpath()+"',"+work.getUid()+",'"+work.getTitle()+"','"+work.getContent()+"','"+work.getDate()+"')";
	    System.out.println(sql);
	    DBconn.init();
	    int i=DBconn.addupdel(sql);
	    if(i>0){
	    	flag=true;
	    }DBconn.closeConn();
	    return flag;
	}

	@Override
	public List seework(String id) {
		List list=new ArrayList<Work>();
		String sql="select workpath,u.uid,title,content,date,`like`,nick from works w,`user` u where id="+id+" and u.uid=w.uid";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				Work wk=new Work();
				wk.setContent(rs.getString("content"));
				wk.setDate(rs.getString("date"));
				wk.setTitle(rs.getString("title"));
				wk.setUid(rs.getInt("uid"));
				wk.setWorkpath(rs.getString("workpath"));
				wk.setNick(rs.getString("nick"));
				wk.setLike(rs.getInt("like"));
				list.add(wk);
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("查看作品有错误");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean report(String did) {
		boolean flag=false;
		String sql="update discuss set unlike=unlike+1 where did="+did+"";
		System.out.println(sql);
		DBconn.init();
		int i=DBconn.addupdel(sql);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public Post seeteach(int tid) {
		String sql="select title,nick,content,date,videopath,t.uid from teach t,user u where t.uid=u.uid and tid="+tid+"";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		Post ps=new Post();
		try {
			while(rs.next()){
				
				ps.setContent(rs.getString("content"));
				ps.setSubjects(rs.getString("title"));
				ps.setNick(rs.getString("nick"));
				ps.setDate(rs.getString("date"));
				ps.setPostimg(rs.getString("videopath"));
				ps.setUid(rs.getInt("t.uid"));
			}
		} catch (SQLException e) {
			System.out.println("用户查看教学视频错误");
			e.printStackTrace();
		}
		return ps;
	}

	@Override
	public User findpass(String username) {
		User us=new User();
		String sql="select uid,mail,username from user where username='"+username+"'";
		DBconn.init();
		ResultSet rs=DBconn.selectSql(sql);
		try {
			while(rs.next()){
				us.setUid(rs.getInt("uid"));
				us.setUsername(rs.getString("username"));
				us.setEmail(rs.getString("mail"));
			}DBconn.closeConn();
		} catch (SQLException e) {
			System.out.println("找回密码错误");
			e.printStackTrace();
		}
		return us;
	}




	

}
