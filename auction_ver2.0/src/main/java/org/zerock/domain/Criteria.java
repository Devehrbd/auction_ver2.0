package org.zerock.domain;


import lombok.Data;

public class Criteria {
	//현재 페이지
	private int currentPage;
	//페이지당 개수
	private int onePageNum;
	
	private String keyword;	// 키워드
	
	//페이지 시작 게시글 번호 얻기
	public int getPageStart() {
		
		return (this.currentPage-1)*onePageNum;
		
	}
	
	//기본 디폴트
	public Criteria() {
		this.currentPage = 1;
		this.onePageNum = 5;
	}
	
	//getter,setter 현재페이지
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		
		if(currentPage <= 0) {
			this.currentPage = 1;
		}else {
			this.currentPage = currentPage;
		}
	
	}
	
	//getter setter 페이지당 게시글수
	public int getOnePageNum() {
		return onePageNum;
	}
	
	public void setOnePageNum(int pageCount) {
		
		// 복잡하게 되었는데, 원래값이랑 다르면 저장하고 아니면 말고 식 추후에 수정
		int cnt = this.onePageNum;
		if(pageCount != cnt) {
			this.onePageNum = cnt;
		}else {
			this.onePageNum = pageCount;
		}
		
	}
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}

