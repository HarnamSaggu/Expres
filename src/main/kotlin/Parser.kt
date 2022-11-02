class Parser(tokens: List<Token>) {
	private val sentences: MutableList<MutableList<Token>> = mutableListOf(mutableListOf())

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
			if (sentence.first().type == TokenType.VARIABLE_MUTATOR) {
				/*
				[VARIABLE MUTATOR] [KEYWORD] [COMMA] [...value...]
				[VARIABLE MUTATOR] [KEYWORD] [TYPE INDICATOR] [KEYWORD]
				[VARIABLE MUTATOR] [KEYWORD] [TYPE INDICATOR] [KEYWORD] [COMMA] [...value...]
				 */
			}
		}
	}

	private fun tokensToRegex(form: String): Regex =
		form.replace("\\[|]".toRegex(), "").split(' ').map {type ->
			if (type == "...value...") {
				'*'
			} else {
				TokenType.valueOf(type).toRegexSafeCode()
			}
		}.joinToString("").toRegex()

	private fun TokenType.toRegexSafeCode(): Char {
		return Char(192 + TokenType.values().indexOf(this))
	}

	private fun printSentences(lists: MutableList<MutableList<Token>> = sentences) {
		for (sentence in lists) {
			sentence.forEach { print("$it ") }
			println("$${sentence.size}")
		}
	}
}
