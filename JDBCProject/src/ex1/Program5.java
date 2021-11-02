package ex1;

import java.sql.SQLException;

import com.newlecture.app.console.NoticeConsole;

public class Program5 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		NoticeConsole console = new NoticeConsole();
		// int page = 1;
		EXIT:
		while(true) {
		console.printNoticeList();
		int menu = console.inputNoticeMenu();
		
		switch(menu) {
		case 1: // 상세조회
			break;
		case 2: // 이전
			// page--;
			console.movePrevList(); // 콘솔에서 페이지를 제어하면 프로그램 보기가 편함
			break;
		case 3: // 다음
			// page++;
			console.moveNextList();
			break;
		case 4: // 글쓰기
			break;
		case 5: // 검색
			console.inputSerchWord();
			break ;
		case 6: // 종료
			System.out.println("Bye~~");
			break EXIT;
		default:
			System.out.println("1~4까지만 입력할 수 있습니다.");
			break;
		}
		}

	}

}
