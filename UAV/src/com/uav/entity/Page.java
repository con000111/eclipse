package com.uav.entity;

import java.util.List;

public class Page<T> {
	private Integer pno;//��ǰҳ��
	private int tpage;//��ҳ�����ܼ�¼��/ÿҳ��С��
	private int trec;//�����е��ܼ�¼��
	public static final Integer psize=2;//ÿҳ��С
	private List<T> beanList;//��ǰҳ�ļ�¼���󣨱����ѯ�����
	private String url;//��¼����url/��Ŀ��/servlet��ַ
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getTpage() {
		
		return tpage;
	}
	public void setTpage(int tpage) {
		int tpages=tpage/psize;   //ҳ��
		if(trec%psize!=0){  //ҳ��������
			tpages++;         //ҳ����һ
		}
		this.tpage = tpages;
	}
	public int getTrec() {
		return trec;
	}
	public void setTrec(int trec) {
		this.trec = trec;
	}
	
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}



	@Override
	public String toString() {
		
		return "Page [pno="+pno+",trec="+trec+",psize"+psize+",beanList="+beanList+",url="+url+"]";
	
	}

	
	


}
