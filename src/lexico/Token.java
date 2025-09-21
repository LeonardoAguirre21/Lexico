package lexico;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        // Formato: columna fija (alineado)
        return String.format("%-20s %-15s %-10s",
                type.name() + " (" + type.getCode() + ")",  // Tipo y código
                lexeme,                                    // Lexema
                literal == null ? "null" : literal.toString()); // Literal
    }
}


