package com.uav.dao;

import java.util.List;

import com.uav.entity.Apply;
import com.uav.entity.Discuss;
import com.uav.entity.Evaluate;
import com.uav.entity.Post;
import com.uav.entity.Section;
import com.uav.entity.User;

public interface AdminDao {
	/*public List<User> queryalluser();//��ѯ�û�
	public List<Post> queryallpost();//��ѯ����
*/	public List<Discuss> queryalldiscuss();//��ѯ��������
	
	public boolean deluser(String uid);//ɾ���û�
	public boolean upduser(User user); //�����û���Ϣ
	public boolean insuser(User user);//�����û�
	
	public List<User> queryuser(int page,int limit);//��ҳ��ѯ�û�
	public int getValue(String table);//��ȡ��¼��
	public List<Post> querypost(int page,int limit);//��ҳ��ѯ����
	
	public boolean delpost(String pid);//����pidɾ������
	
	public boolean adduser(String uname,String pass);//�����û�
    public boolean updpost(Post post);//��������
    public boolean deldiscuss(String did);//ɾ������
    public boolean upddiscuss(Discuss discuss);//��������
    public List<User> ukeyquery(String key,int page,int limit);//�ؼ��ֲ�ѯ�û�
    public List<Post> pkeyquery(String key,int page,int limit);//�ؼ��ֲ�ѯ����
    public List<Apply> dealapply(int page,int limit);//�������뵥
    public List<Section> getsection(int page,int limit);//��ȡ���
    public int spcount(String forum);//��ȡĳ��������������
    public boolean addsec(String vendor,String section);//��Ӱ��
    public boolean editsec(String vendor,String section,String uid,String id);//�޸İ��
    public boolean delsec(String uid,String section);//ɾ�����
    public List<Evaluate> getevaluate(int page,int limit);//��ȡ�û����۱�
    public boolean uauth(String uid,String auth);//�û�Ȩ������
    public List<Post> dealpost(String status,int page,int limit);//����Ա�鿴����δ����������
    public List<Discuss> dealreport(int page,int limit);//����Ա�鿴���еľٱ����
    
} 
