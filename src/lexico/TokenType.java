package lexico;

public enum TokenType {
    // --- Operadores y símbolos ---
    DOS_PUNTOS(50),           // :
    SONRISA(51),           // :)
    PUNTO(52),             // .
    ASTERISCO(53),            // *
    SUMA(54),            // +
    MENOS(55),           // -
    MENOR(56),            // <
    MAYOR(57),         // >
    SLASH(58),           // /
    MODULO(59),        // %
    AND_AND(60),        // &&
    OR_OR(61),          // ||
    COMA(62),          // ,
    LEFT_PAREN(63),     // (
    RIGHT_PAREN(64),    // )
    LEFT_BRACE(65),     // {
    RIGHT_BRACE(66),    // }
    EQUAL(67),          // =
    EQUAL_EQUAL(68),    // ==
    BANG_EQUAL(69),     // !=
    MENOR_QUE(70),     // <=
    MAYOR_QUE(71),  // >=
    LEFT_BRACKET(72),   // [
    RIGHT_BRACKET(73),  // ]

    // --- Literales ---
    IDENTIFICADOR(1000),
    IDENTIFICADOR_MAYUSCULA(1010),
    STRING(4000),
    CHAR(3000),
    ENTERO(2010),
    REAL(2020),
    BOOLEAN(2030),

    // --- Palabras reservadas ---
    PRINCIPALSITO(10),
    PORFAVOR(11),
    FAVOR(12),
    PODRIASCREAR(13),
    METODILLO(14),
    PODRIASIMPRIMIR(15),
    PODRIASLEER(16),
    ADIOS_TRISTE(17),
    ACLAMA(18),
    SICUMPLE(19),
    PEROSICUMPLE(20),
    CASOCONTRARIO(21),
    SIPERSISTE(22),
    SALTEAR(23),
    PARAR(24),
    ENCASOSEA(25),
    OSINO(26),
    RETORNA(27),
    CLONA(28),
    ENTERITO(29),
    REALITO(30),
    BOOLEANITO(31),
    CHARSITO(32),
    CADENITA(33),
    NULITO(34),
    VACIO(35),

   
    ERROR(666),
    
    EOF(999);

    private final int code;

    TokenType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}