package lexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lexico.TokenType.*;

class Scanner {
	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	private int start = 0;
	private int current = 0;
	private int line = 1;

	Scanner(String source) {
		this.source = source;
	}

	List<Token> scanTokens() {
		while (!isAtEnd()) {
			start = current;
			scanToken();
		}

		tokens.add(new Token(EOF, "", null, line));
		return tokens;
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private char advance() {
		return source.charAt(current++);
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, literal, line));
	}

	private void scanToken() {
		char c = advance();
		switch (c) {
		case '(':
			addToken(LEFT_PAREN);
			break;
		case ')':
			addToken(RIGHT_PAREN);
			break;
		case '{':
			addToken(LEFT_BRACE);
			break;
		case '}':
			addToken(RIGHT_BRACE);
			break;
		case '[':
			addToken(LEFT_BRACKET);
			break;
		case ']':
			addToken(RIGHT_BRACKET);
			break;
		case ',':
			addToken(COMA);
			break;
		case '.':
			addToken(PUNTO);
			break;
		case '-':
			addToken(MENOS);
			break;
		case '+':
			addToken(SUMA);
			break;
		case '*':
			addToken(ASTERISCO);
			break;
		case '%':
			addToken(MODULO);
			break;
		case '\'':
			character();
			break;

		case ':':
			if (match(')')) {
				addToken(SONRISA); // :)
			} else {
				addToken(DOS_PUNTOS); // :
			}
			break;

		case '!':
			if (match('=')) {
				addToken(BANG_EQUAL); // !=
			} else {
			    addToken(TokenType.ERROR, "Unexpected '" + c + "'");
			}
			break;

		case '=':
			if (match('=')) {
				addToken(EQUAL_EQUAL); // ==
			} else {
				addToken(EQUAL); // =
			}
			break;

		case '<':
			addToken(match('=') ? MENOR_QUE : MENOR);
			break;

		case '>':
			addToken(match('=') ? MAYOR_QUE : MAYOR);
			break;

		case '&':
		    if (match('&')) {
		        addToken(AND_AND);
		    } else {
		    	addToken(TokenType.ERROR, "Unexpected '" + c + "'");
		    }
		    break;

		case '|':
			if (match('|')) {
				addToken(OR_OR); // ||
			} else {
				addToken(TokenType.ERROR, "Unexpected '" + c + "'");
			}
			break;

		case '/':
			if (match('/')) {
				
				while (peek() != '\n' && !isAtEnd())
					advance();
			} else {
				addToken(SLASH);
			}
			break;

		case ' ':
		case '\r':
		case '\t':
	
			break;

		case '\n':
			line++;
			break;

		case '"':
			string();
			break;

		default:
			if (c == 'a' && checkKeyword("adios:(")) {
				addToken(TokenType.ADIOS_TRISTE);
			} else if (isDigit(c)) {
				number();
			} else if (isAlpha(c)) {
				identifier();
			} else {
				 addToken(TokenType.ERROR, "Unexpected '" + c + "'");
			}
			break;
		}
	}

	private boolean checkKeyword(String expected) {
		int length = expected.length();
		if (current - 1 + length > source.length())
			return false;
		String text = source.substring(current - 1, current - 1 + length);
		if (text.equals(expected)) {
			current = current - 1 + length; // avanzamos hasta el final de la palabra
			return true;
		}
		return false;
	}

	private void character() {
		// Si llegamos al final sin cerrar la comilla
		if (isAtEnd()) {
			Lox.error(line, "Unterminated character literal.");
			return;
		}

		char value = advance(); // el caracter dentro de las comillas

		// El carácter debe cerrarse con otra comilla simple
		if (peek() != '\'') {
			Lox.error(line, "Carecter debe contener solo un carecter.");
			return;
		}
		advance(); // consumir la comilla de cierre

		addToken(TokenType.CHAR, value);
	}

	private void identifier() {
		while (isAlphaNumeric(peek()))
			advance();

		String text = source.substring(start, current);

		// Verificar si es palabra reservada
		TokenType type = keywords.get(text);
		if (type != null) {
			addToken(type);
			return;
		}

		// Si no es reservada, distinguir mayúsculas
		if (text.equals(text.toUpperCase())) {
			addToken(TokenType.IDENTIFICADOR_MAYUSCULA);
		} else {
			addToken(TokenType.IDENTIFICADOR);
		}
	}

	private void number() {
		boolean isReal = false;

		while (isDigit(peek()))
			advance();

		// Parte fraccionaria
		if (peek() == '.' && isDigit(peekNext())) {
			isReal = true;
			advance(); // consumir '.'

			while (isDigit(peek()))
				advance();
		}

		String text = source.substring(start, current);

		if (isReal) {
			addToken(REAL, Double.parseDouble(text));
		} else {
			addToken(ENTERO, Integer.parseInt(text));
		}
	}

	private void string() {
		while (peek() != '"' && !isAtEnd()) {
			if (peek() == '\n')
				line++;
			advance();
		}

		if (isAtEnd()) {
		    addToken(TokenType.ERROR, "Unterminated string");
		    return;
		}

		// The closing ".
		advance();

		// Trim the surrounding quotes.
		String value = source.substring(start + 1, current - 1);
		addToken(STRING, value);
	}

	private boolean match(char expected) {
		if (isAtEnd())
			return false;
		if (source.charAt(current) != expected)
			return false;

		current++;
		return true;
	}

	private char peek() {
		if (isAtEnd())
			return '\0';
		return source.charAt(current);
	}

	private char peekNext() {
		if (current + 1 >= source.length())
			return '\0';
		return source.charAt(current + 1);
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private static final Map<String, TokenType> keywords;

	static {
		keywords = new HashMap<>();
		keywords.put("principalsito", TokenType.PRINCIPALSITO);
		keywords.put("porfavor", TokenType.PORFAVOR);
		keywords.put("favor", TokenType.FAVOR);
		keywords.put("podriasCrear", TokenType.PODRIASCREAR);
		keywords.put("metodillo", TokenType.METODILLO);
		keywords.put("podriasImprimir", TokenType.PODRIASIMPRIMIR);
		keywords.put("podriasLeer", TokenType.PODRIASLEER);
		keywords.put("aclama", TokenType.ACLAMA);
		keywords.put("siCumple", TokenType.SICUMPLE);
		keywords.put("peroSiCumple", TokenType.PEROSICUMPLE);
		keywords.put("casoContrario", TokenType.CASOCONTRARIO);
		keywords.put("siPersiste", TokenType.SIPERSISTE);
		keywords.put("saltear", TokenType.SALTEAR);
		keywords.put("parar", TokenType.PARAR);
		keywords.put("enCasoSea", TokenType.ENCASOSEA);
		keywords.put("oSino", TokenType.OSINO);
		keywords.put("retorna", TokenType.RETORNA);
		keywords.put("clona", TokenType.CLONA);
		keywords.put("enterito", TokenType.ENTERITO);
		keywords.put("realito", TokenType.REALITO);
		keywords.put("booleanito", TokenType.BOOLEANITO);
		keywords.put("charsito", TokenType.CHARSITO);
		keywords.put("cadenita", TokenType.CADENITA);
		keywords.put("nulito", TokenType.NULITO);
		keywords.put("vacio", TokenType.VACIO);
		keywords.put("true", BOOLEAN);
		keywords.put("false", BOOLEAN);
	}

}
