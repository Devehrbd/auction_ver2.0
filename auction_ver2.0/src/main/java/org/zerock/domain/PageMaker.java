package org.zerock.domain;


//pageMaker -> setcri, settotalcount 세팅 해야 이후 로직돌아감
// 총게시글 수와 , cri (한페이지당 게시글 수, 현재페이지 수)

public class PageMaker {
	private Criteria cri;
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	// 한화면 페이지 개수
	private int displayPageNum = 5;

	// cri g,s
	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	// totalCount
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	private void calcData() {

		// endpage 계산
		endPage = (int) (Math.ceil(cri.getCurrentPage() / (double) displayPageNum) * displayPageNum);

		startPage = (endPage - displayPageNum) + 1;

		if (startPage <= 0) {
			startPage = 1;
		}
		// 총 개수를 페이지당 게시글수 나눈것 = 페이지의 수
		int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getOnePageNum()));
		// 총 페이지의 수가 계산한 endPage의 수보다 적다면, endPage는 총페이지수.
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}

		// 이전,다음버튼 로직
		prev = startPage == 1 ? false : true;
		next = (endPage * cri.getOnePageNum()) < totalCount ? true : false;

	}
	
	//StartPage
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	//EndPage
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	//prev
	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	//next
	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	//displayPageNum
	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

}
