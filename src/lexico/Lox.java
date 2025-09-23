package lexico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

	static boolean hadError = false;

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			System.out.println("Usage: jlox [script]");
			System.exit(64);
		} else if (args.length == 1) {
			runFile(args[0]);
		} else {
			runPrompt();
		}
	}

	private static void runFile(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		run(new String(bytes, Charset.defaultCharset()));
		if (hadError)
			System.exit(65);
	}

	private static void runPrompt() throws IOException {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		StringBuilder code = new StringBuilder();

		System.out.println("-- Escriba su codigo fuente Kind++ -- Termine de escribir poniendo un '#' en una linea sola");

		for (;;) {
			String line = reader.readLine();

			if (line == null) {
				break;
			}

			if (line.trim().equals("#")) {
				if (code.length() > 0) {
					System.out.println("=========================================");
					System.out.println("             Token             | Lexema");
					System.out.println("=========================================");
					run(code.toString());
				} else {
					System.out.println("No hay codigo que analizar");
				}
				break;
			}

			// Agregar la línea al código acumulado
			code.append(line).append("\n");
		}
	}

	private static void run(String source) {
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scanTokens();

		for (Token token : tokens) {
			System.out.println(token);
		}
	}
	
	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error" + where + ": " + message);
		hadError = true;
	}
}
