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
