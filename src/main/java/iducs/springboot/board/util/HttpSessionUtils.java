package iducs.springboot.board.util;

import iducs.springboot.board.domain.User;

public class HttpSessionUtils {
	public static boolean isLogined(User user) { // isnull이라고 생각하면 쉬움
		// session에 user객체가 null이면 ==> 로그인 필요
		if(user == null) {
			return true;
		}
		else { return false; }
	}
}
/*
public class HttpSessionUtils {

	// session 의 key 값을 상수로 변경
	public static final String USER_SESSION_KEY = "user";
	
	// session 에 로그인된 유저의 존재 여부 판별하는 메서드
	public static boolean isLoginUser(HttpSession session) {
		// session 에서 값을 꺼내면 Object 타입으로 리턴하게 되므로 User 타입이 아닌 Object 타입으로 변수선언
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		// session 값이 null 이면 false 리턴, 즉 로그인 안되어 있음
		if ( sessionUser == null ) {
			return false;
		}	
		return true;
	}
	
	// session 에 저장된 값을 가져오는 메서드
	public static User getUserFromSession(HttpSession session) {
		if ( !isLoginUser(session) ) {
			return null;
		}	
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
 */