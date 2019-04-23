package com.uav.dao;

import java.util.List;

import com.uav.entity.Apply;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;

public interface BaseDao {
	/*�洢�û�����Ϣ*/
	User setSession(String user);//�洢�û���Ϣ
	public boolean upwork(String headimg,int uid);//�ϴ�work��ƷͼƬ����
	public List<String> workname(int uid);//��ȡworkname
	
	//�����
	public List<User> ranking();
   //��ҳ������
	public List<Post> post(int first,int length);
	//��ѯĳ����ļ�¼��
	public int getvalue(String table,String clas);
	//���ݰ���ȡ�����Ϣ
	public List<Post> forpost(String forum,int first);
	//�����ö���
	public List<Post> toppost(String table,String forum);
	//��ѯ���շ�������
	public int getcount();
	//�������ӵ�������
	public int disscount(String pid);
	//��ѯĳ�����ӵĻ�������
	public int dissvalue(String pid);
	//��̳��ʱ�併���ѯ
	public List<Post> timedesc(int page,int limit);
	//��̳��ϲ���������ѯ
	public List<Post> likedesc(int page,int limit);
    //�û�����Ϊ����
	public void apply(Apply apply);
	//����ר�����ذ��
	public List<String> getsection(String forum);
	//����������Ʒ,������Ʒ
	public List<Work> getwork();
   //�����м�������
	public int whomuch(int uid);
	//��鰴�ȶ�����
	public List<Post> postbyhot(String forum,int first);
	//�ֲ�����
	public List<Post> carousel();
	//���ؽ̳�����
	public List<Post> teach();
	//�����ö���
	public List<Post> toppost();

}
