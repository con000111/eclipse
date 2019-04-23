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
	public boolean login(String name,String password);//��½
	public boolean register(User user);//ע��
	public boolean modify(int uid,String pass);//�޸�����
	public String logins(String name,String password);//Ȩ�޵�½

	/*����*/
	public boolean dopost(Post post);//����

	
	/*����ؼ��ʲ�ѯ
	 *
	 */
/*	List<Post> getPost(String arg, int a,int b);//��ȡҪ��ѯ���ӵķ�Χ
    Object getValue(Object arg);//��ȡ��ѯ�ؼ��ʵļ�¼��
*/	public List<Post> querypost(String key);//��ѯ����
    public List<User> queryuser(String key);//��ѯ�û�
	
    /* �޸���Ϣ*/
	public boolean alertinfo(User user);
	
	/*��*/
	public Punch record(int uid);
	public boolean punch(int uid,int sum);
	
	/*�ϴ�ͷ����*/
	public boolean uphead(String headimg,int uid);
	/*��ȡͷ����*/
	public String gethead(int uid);
	/*�鿴�ҷ�������������Ϣ*/
	public List<Post> mypost(int uid);
	/*�鿴�ҷ������ӵ���ϸ����*/
	public List<Post> detailpost(String pid);
	/*�ϴ���ͼƬ����*/
	public boolean uppost(String imgname,int pid);
    /*�û��������*/
	public boolean adddiscuss(int uid,String discuss,int pid ,String time);
	/*��ѯ���ӵ�����*/
	public List<Discuss> seediscuss(String pid);
	//�޸�������Ϣ
	public boolean alterpost(Post post,String pid);
	//�û�����ղ�
	public void collect(String pid,String uid);
	//��ѯ�ղر�
	public List<Post> mycoll(String uid);
	//�û�����
	public boolean like(String pid);
	//��ȡ�����û���ҳ
	public List<User> otherpage(String uid);
	//��ȡ�����û�����������
	public List<Post> otherpost(String uid);
	//��Ӻ���
	public boolean addfri(String fid,int uid);
	//��ѯ����
	public List<User> myfri(int uid);
	//���Թ���
	public boolean mess(Message mess);
	//��ѯ��������
	public List<Message> newmess(int toid);
	//����Ϊ�Ѷ���¼
	public boolean readed(int toid);
	//��ѯ�ʼ��˺�
	public String myemail(int uid);
	//������
	public boolean bandmail(int uid,String mail);
	//��������
	public boolean cancelmail(int uid);
	//ȡ���ղ�
	public boolean cancelcoll(String pid,int uid);
	//ɾ������
	public boolean delfri(String fid,int uid);
	
	//����UID��ȡ�û���Ϣ
	public User getuser(String uid);
	//�ϴ���Ʒ
	public boolean upwork(Work work);
	//�鿴��Ʒ
	public List seework(String id);
	//�ٱ�����
	public boolean report(String did);
	//�鿴�̳�
	public Post seeteach(int tid);
	//�һ�����
	public User findpass(String username);
}
