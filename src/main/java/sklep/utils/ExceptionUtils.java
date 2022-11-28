package sklep.utils;

import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils {
	
	public static List<String> allMessages(Throwable e) {
		List<String> messages = new ArrayList<>();
		messages.add(oneMessage(e));
		Throwable cause = e.getCause();
		while(cause != null) {
			messages.add(oneMessage(cause));
			cause = cause.getCause();
		}
		return messages;
	}

	private static String oneMessage(Throwable e) {
		return e.getClass().getSimpleName() + ": " + e.getMessage();
	}
}
