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
    DIVISION(58),           // /
    MOD(59),        // %
    AND(60),        // &&
    OR(61),          // ||
    COMA(62),          // ,
    PAREN_IZQ(63),     // (
    PAREN_DER(64),    // )
    LLAVE_IZQ(65),     // {
    LLAVE_DER(66),    // }
    IGUAL(67),          // =
    EQUIVALE(68),    // ==
    DIFERENTE(69),     // !=
    MENOR_QUE(70),     // <=
    MAYOR_QUE(71),  // >=
    CORCHETE_IZQ(72),   // [
    CORCHETE_DER(73),  // ]

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
    CONSTANTITO(36),
    CLASESITA(37),

   
    ERROR(666);

    private final int code;

    TokenType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}