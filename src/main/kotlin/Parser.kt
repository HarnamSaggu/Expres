class Parser(tokens: List<Token>) {
	private val sentences: MutableList<MutableList<Token>> = mutableListOf(mutableListOf())
	private var sentence: MutableList<Token> = mutableListOf()

	init {
		for (token in tokens) {
			if (token.type == TokenType.SEMICOLON) {
				sentences.add(mutableListOf())
			} else {
				sentences.last().add(token)
			}
		}
		printSentences()

		for (sentence in sentences) {
			this.sentence = sentence
			if          (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [COMMA] [...value...]")) {
				println("variable assigment with value")
			} else if   (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [TYPE INDICATOR] [KEYWORD] [COMMA] [...value...]")) {
				println("variable assigment with type and value")
			} else if   (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [TYPE INDICATOR] [KEYWORD]")) {
				println("variable assigment with type")
			} else if   (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX] [TYPE INDICATOR] [KEYWORD]")) {
				println("array assigment with type")
			} else if   (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX] [COMMA] [...value...]")) {
				println("array assigment with size")
			} else if   (matchTokensToPattern("[VARIABLE MUTATOR] [KEYWORD] [OPEN INDEX] [CLOSE INDEX]")) {
				println("array assigment")
			} else if   (matchTokensToPattern("[KEYWORD] [OPEN BRACKET] [...value...] [CLOSE BRACKET]")) {
				println("function")
			}
		}
	}

	private fun matchTokensToPattern(form: String, sentence: MutableList<Token> = this.sentence): Boolean =
		sentenceToUTF8(sentence).matches(tokensToRegex(form))

	private fun sentenceToUTF8(sentence: MutableList<Token>): String {
		return sentence.map { token -> token.type.toRegexSafeCode() }.joinToString("")
	}

	private fun tokensToRegex(form: String): Regex =
		("^" + form.substring(1, form.length - 1).split("]\\s*\\[".toRegex()).joinToString(separator = "") {
			val type = it.replace(' ', '_')
			if (type == "...value...") {
				".+"
			} else {
				TokenType.valueOf(type).toRegexSafeCode().toString()
			}
		}).toRegex()

	private fun TokenType.toRegexSafeCode(): Char {
		return Char(192 + TokenType.values().indexOf(this))
	}

	private fun printSentences(lists: MutableList<MutableList<Token>> = sentences) {
		for (sentence in lists) {
			sentence.forEach { print("$it ") }
			println()
//			println("$${sentence.size}")
		}
	}
}
