package com.uav.dao;

import java.util.List;

import com.uav.entity.Discuss;
import com.uav.entity.Post;
import com.uav.entity.User;
import com.uav.entity.Work;

public interface ModerDao {
	/*public List<User> queryuser();//��ѯ�û�
*/  public List<Post> querypost(String forum,int page,int limit);//��ѯ���� 
    public List<Discuss> querydiscuss(String forum,int page,int limit);//��ѯ����
    public List<Post> kquerypost(String key,String forum,int page,int limit);//��ѯ����
    public List<Post> unreadpost(String forum,int page,int limit);//�鿴δ��˵�����
    public boolean judge(String status,String pid);//����Ƿ�ͨ��
    public List<Work> unreadvideo(int page,int limit);//�鿴δ��˵���Ƶ
    public boolean judvideo(String status,String id);//����Ƿ�ͨ��
    public List<Discuss> dealreport(String section,int page,int limit);//������ѯ�ٱ��ظ�
    /*public boolean deldiscuss(String did);//ɾ�����۹���Ա������
     * */
    public boolean tozero(String did);//�ٱ�ֵ����
    public String myforum(int uid);//����UID��ѯ���������İ��
    public boolean totop(String pid,String operate);//�ö�����
    public boolean addteach(Post ps);
}
