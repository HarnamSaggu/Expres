fun main() {
    val sourceCode = """
        > str, "hello world";
        if(1, {println(str)}, 0, {> str, "help, world"});
        
        
        # this is a comment;
        
        
        a(b(c(d, e, f), g, h), i(j));
    """.trimIndent()
    println(sourceCode + "\n")
    val tokens = lex(sourceCode)
    tokens.forEach(::println)
    println(tokens.size)
    println()
    val parser = Parser(tokens)

//    val str = "[VARIABLE MUTATOR] [KEYWORD] [COMMENT] [LESS EQUAL THAN] [GREATER EQUAL THAN] [CLOSE BRACKET] [OPEN INDEX] [CLOSE INDEX] [TYPE INDICATOR] [STRING LITERAL] [INCLUSIVE INTERVAL] [EXCLUSIVE INTERVAL] [VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX] [TYPE INDICATOR] [KEYWORD]"
//    val ttr = parser.tokensToRegex(str)
//    val a = parser.a(str)
//    println(ttr)
//    println(a)
//    println(a.toString() == ttr.toString())
}
