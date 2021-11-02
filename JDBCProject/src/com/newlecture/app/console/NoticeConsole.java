package com.newlecture.app.console;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.newlecture.app.entity.Notice;
import com.newlecture.app.service.NoticeService;

public class NoticeConsole {

	private NoticeService service;
	private int page;
	private String searchField;
	private String searchWord;
	
	public NoticeConsole() {
		service = new NoticeService();
		page = 1;
		searchField = "title";
		searchWord = "";
		
	}
	
	public void printNoticeList() throws ClassNotFoundException, SQLException {
		
		List<Notice> list = service.getList(page, searchField, searchWord);
		int count = service.getCount(); // 리셋할때마다 페이지를 구해야 하므로
		int lastPage = count/10;
		lastPage = count%10>0 ? lastPage+1:lastPage;
		
		System.out.println("--------------------------------------");
		System.out.printf("<공지사항> 총 %d게시글\n", count);
		System.out.println("--------------------------------------");
		
		for(Notice n : list) {
			System.out.printf("%d. %s / %s / %s\n",
					n.getId(),
					n.getTitle(),
					n.getWriterid(),
					n.getRegDate());
		}
		System.out.println("--------------------------------------");
		System.out.printf("             %d/%d pages\n", page, lastPage);
		
	}

	public int inputNoticeMenu() {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("1. 상세조회/ 2. 이전/ 3. 다음/ 4. 글쓰기/ 5. 검색/ 6. 종료 >");
		String menu_ = scan.nextLine();
		int menu = Integer.parseInt(menu_);
		
		scan.getClass();
		
		return menu;
	}

	public void movePrevList() {
		if(page == 1) {
			System.out.println("이전 페이지가 없습니다.");
			return;
		}
		
		page--;
		
	}

	public void moveNextList() throws ClassNotFoundException, SQLException {
		// 지역으로 쓰는 이유는 항상 최신 상태를 사용하기 위해
		// 멤버변수로 사용하면 이전에 사용했던 값을 사용하기 때문에 
		// 다음 페이지를 누르기 직전에 누군가 게시물을 올렸다면
		// 그것을 반영하지 않고 마지막 페이지를 구한다.
		int count = service.getCount(); 
		int lastPage = count/10;
		lastPage = count%10>0 ? lastPage+1:lastPage;
		
		if(page == lastPage) {
			System.out.println("다음 페이지가 없습니다.");
			return;
		}
		page++;
		
	}

	public void inputSerchWord() {
		Scanner scan = new Scanner(System.in);
		System.out.println("검색 범주(title/content/writer_id 중에 하나를 입력하세요.");
		// 게시물 리스트를 가져올 때 써야하므로 멤버변수
		System.out.println(" > ");
		searchField = scan.nextLine(); 
		
		System.out.println("검색어 > ");
		searchWord = scan.nextLine();
		
		
	}

}
