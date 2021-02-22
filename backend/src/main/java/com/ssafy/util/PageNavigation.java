package com.ssafy.util;

import lombok.*;

@Data
public class PageNavigation {

	private boolean startRange;
	private boolean endRange;
	private int totalCount;
	private int newCount;
	private int totalPageCount;
	private int currentPage;
	private int naviSize;
	private int countPerPage;
	private String navigator;

	public void makeNavigator() {
		int startPage = (currentPage - 1) / naviSize * naviSize + 1;//6 11
		int endPage = startPage + naviSize - 1;//10 15
		if(totalPageCount < endPage)
			endPage = totalPageCount;//11
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("		<ul class=\"pagination\"> \n");
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\"id=\"first-page\" class=\"page-link\" move-pg=\"1\">최신</a> \n");
		buffer.append("			</li> \n");
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" class=\"page-link\" move-pg=\"" + (this.startRange ? 1 : (startPage - 1)) + "\">이전</a> \n");
		buffer.append("			</li> \n");
		for(int i=startPage;i<=endPage;i++) {
			buffer.append("			<li class=\"" + (currentPage == i ? "page-item active" : "page-item") + "\"><a href=\"#\" class=\"page-link\" move-pg=\"" + i + "\">" + i + "</a></li> \n");
		}
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" class=\"page-link\" move-pg=\"" + (this.endRange ? endPage : (endPage + 1)) + "\">다음</a> \n");
		buffer.append("			</li> \n");
		buffer.append("			<li class=\"page-item\"> \n");
		buffer.append("				<a href=\"#\" class=\"page-link\" move-pg=\"" + totalPageCount + "\">마지막</a> \n");
		buffer.append("			</li> \n");
		buffer.append("		</ul> \n");
		this.navigator = buffer.toString();
	}

}
