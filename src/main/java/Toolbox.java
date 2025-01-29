
import com.mysql.cj.PreparedQuery;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.*;

public interface Toolbox {
	Random RANDOM = new Random();
	Scanner SCANNER = new Scanner(System.in);
	String ANSI_BLACK = "\u001B[30m";
	String ANSI_RED = "\u001B[31m";
	String ANSI_GREEN = "\u001B[32m";
	String ANSI_YELLOW = "\u001B[33m";
	String ANSI_BLUE = "\u001B[34m";
	String ANSI_PURPLE = "\u001B[35m";
	String ANSI_CYAN = "\u001B[36m";
	String ANSI_WHITE = "\u001B[37m";
	String ANSI_BLACK_BG = "\u001B[40m";
	String ANSI_RED_BG = "\u001B[41m";
	String ANSI_GREEN_BG = "\u001B[42m";
	String ANSI_YELLOW_BG = "\u001B[43m";
	String ANSI_BLUE_BG = "\u001B[44m";
	String ANSI_PURPLE_BG = "\u001B[45m";
	String ANSI_CYAN_BG = "\u001B[46m";
	String ANSI_WHITE_BG = "\u001B[47m";
	String ANSI_RESET = "\u001B[0m";


	static Set<Field> getFieldHierarchy(Class<?> clazz) {
		Set<Field> fieldSet = new LinkedHashSet<>();

		while (clazz != null) {
			// Add fields declared in the current class
			Field[] declaredFields = clazz.getDeclaredFields();
			Collections.addAll(fieldSet, declaredFields);

			// Move to the superclass
			clazz = clazz.getSuperclass();
		}

		String declaringClass = "";
		for (Field field : fieldSet) {
			String tmp = field.getDeclaringClass().getSimpleName();
			System.out.print(tmp.equals(declaringClass)?
					"" : "# " + (declaringClass = tmp) + "\n");

			System.out.printf("%-25s | %-20s | %-10s\n",
					Modifier.toString(field.getModifiers()),
					field.getType().getSimpleName(),
					field.getName());
		}

		return fieldSet;
	}

	static void hl() {
		System.out.println("=".repeat(70));
	}
	static void hl(int count) {
		System.out.println("=".repeat(count));
	}
	static void hl(boolean newline){
		System.out.println("\n" + "=".repeat(70));
	}

	static void nl() {
		System.out.println();
	}

	static void dl() {
		System.out.println(green(">>> DEBUGGING ( ^)o(^ )<<<"));
	}

	static IntPredicate primeOrNot() {
		return n -> {
			for (int i = 2; i <= sqrt(n); i++)
				if (n % i == 0)
					return false;
			return true;
		};
	}
	static Predicate<Integer> primeOrNotBoxed() {
		return n -> {
			for (int i = 2; i <= sqrt(n); i++)
				if (n % i == 0)
					return false;
			return true;
		};

	}

	/** 1 = matches(), 2 = find(), 3 = lookingAt()*/
	static void matchingDetail(String textString, String patternString, int mode) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(textString);

		switch (mode) {
			case 1:
				System.out.println("### matches() ###");
				System.out.println(ANSI_PURPLE + "Sentence: " + textString + ANSI_RESET);
				System.out.println(ANSI_CYAN + "Pattern: " + patternString + ANSI_RESET);
				System.out.println(matcher.matches() + " : " + textString);
				System.out.println("sentence length: " + textString.length());
				// end(): if matched, return the index of last matching character
				System.out.println("Matched ending index: " + matcher.end());
				System.out.println("Matched text: " +
								   textString.substring(matcher.start(), matcher.end()));
				break;
			case 2:
				System.out.println("### find() ###");
				System.out.println(ANSI_PURPLE + "Sentence: " + textString + ANSI_RESET);
				System.out.println(ANSI_CYAN + "Pattern: " + patternString + ANSI_RESET);
				System.out.println(matcher.find() + " : " + textString);
				System.out.println("sentence length: " + textString.length());
				System.out.println("Matched ending index: " + matcher.end());
				System.out.println("Matched text: " +
								   textString.substring(matcher.start(), matcher.end()));
				break;
			case 3:
				System.out.println("### lookingAt()");
				System.out.println(ANSI_PURPLE + "Sentence: " + textString + ANSI_RESET);
				System.out.println(ANSI_CYAN + "Pattern: " + patternString + ANSI_RESET);
				System.out.println(matcher.lookingAt() + " : " + textString);
				System.out.println("sentence length: " + textString.length());
				System.out.println("Matched ending index: " + matcher.end());
				System.out.println("Matched text: " +
								   textString.substring(matcher.start(), matcher.end()));
				break;
			default:

		}
	}

	static String psQueryString(PreparedStatement ps) {
		return (ps instanceof com.mysql.cj.jdbc.ClientPreparedStatement psImpl)?
				((PreparedQuery) psImpl.getQuery()).asSql() : "^_^?";
	}

	static boolean printRecords(ResultSet resultSet) throws SQLException {
		boolean foundData = false;
		ResultSetMetaData meta = resultSet.getMetaData();

		for (int i = 1; i <= meta.getColumnCount(); i++) {
			System.out.printf("%-15s ", meta.getColumnName(i));
		}
		hl(true);

		if (resultSet.isBeforeFirst()) {
			for (;resultSet.next(); foundData = true) {
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					System.out.printf("%-15s ", resultSet.getString(i));
				}
				nl();
			}
		} else {
			System.out.print("<No matching records found!>");
		}
		nl();

		return foundData;
	}


	static String black(String text) {
		return ANSI_BLACK + text + ANSI_RESET;
	}

	static String red(String text) {
		return ANSI_RED + text + ANSI_RESET;
	}

	static String green(String text) {
		return ANSI_GREEN + text + ANSI_RESET;
	}

	static String yellow(String text) {
		return ANSI_YELLOW + text + ANSI_RESET;
	}

	static String blue(String text) {
		return ANSI_BLUE + text + ANSI_RESET;
	}

	static String purple(String text) {
		return ANSI_PURPLE + text + ANSI_RESET;
	}

	static String cyan(String text) {
		return ANSI_CYAN + text + ANSI_RESET;
	}

	static String white(String text) {
		return ANSI_WHITE + text + ANSI_RESET;
	}

	static String blackBg(String text) {
		return ANSI_BLACK_BG + text + ANSI_RESET;
	}

	static String redBg(String text) {
		return ANSI_RED_BG + text + ANSI_RESET;
	}

	static String greenBg(String text) {
		return ANSI_GREEN_BG + text + ANSI_RESET;
	}

	static String yellowBg(String text) {
		return ANSI_YELLOW_BG + text + ANSI_RESET;
	}

	static String blueBg(String text) {
		return ANSI_BLUE_BG + text + ANSI_RESET;
	}

	static String purpleBg(String text) {
		return ANSI_PURPLE_BG + text + ANSI_RESET;
	}

	static String cyanBg(String text) {
		return ANSI_CYAN_BG + text + ANSI_RESET;
	}

	static String whiteBg(String text) {
		return ANSI_WHITE_BG + text + ANSI_RESET;
	}

	/** This should print foo*/
	static void foo() {
		System.out.println("foo");
	}



}
