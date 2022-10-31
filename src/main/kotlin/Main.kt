fun main() {
    val sourceCode = """
        Hello world,,, this is a test for the while loop;
        > varName;
        greater than next > >= > > ;>=;;;
        {{}
        snippet
        };}
        1234567890987651;
        123 8478 123 1.32 331231.123 231
        23;;-+-+*///*** %% ^^^ 12^3^ =;=;< < < <="==<=*<;";>=;;>=>+ ;<<< !||||(&&;!>)[[]]][]:::;:::!
        
        <"=">
        
        "hell ow orld"aa
        
        "1112345""qweqweqwe"asd
        
        ""1... ../.../a
        
    """.trimIndent()
    lex(sourceCode).also { println(it.size) }.forEach(::println)
}
