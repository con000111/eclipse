package com.uav.entity;

import java.util.List;

public class Page<T> {
	private Integer pno;//当前页码
	private int tpage;//总页数（总记录数/每页大小）
	private int trec;//数据中的总记录数
	public static final Integer psize=2;//每页大小
	private List<T> beanList;//当前页的记录对象（保存查询结果）
	private String url;//记录请求url/项目名/servlet地址
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
		int tpages=tpage/psize;   //页数
		if(trec%psize!=0){  //页数不整除
			tpages++;         //页数加一
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
