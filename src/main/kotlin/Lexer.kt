fun lex(sourceCode: String): List<Token> = Lexer(sourceCode).tokens().toList()

private class Lexer(sourceCode: String) {
	private val code: String
	private var pointer = 0
	private val tokens = mutableListOf<Token>()
	private var value: String? = null
	private var type = TokenType.UNDEFINED

	init {
		code = sourceCode.replace("\n", "")

		while (pointer < code.length) {
			value = null
			type = TokenType.UNDEFINED

			if (char() == ' ' || char() == '\t') {
				inc()
				continue
			}

			if (char()?.isLetter() == true) {
				value = ""
				while (char()?.isLetter() == true) {
					value += char()
					inc()
				}
				appendToken(tokenType = TokenType.KEYWORD)
				continue
			}

			if (char() == '>') {
				if (proChar() == '=') {
					appendToken(tokenType = TokenType.GREATER_EQUAL_THAN, incAmount = 2)
					continue
				} else {
					type = if (preChar() == ';'|| preChar() == '{' || preChar() == null) {
						TokenType.VARIABLE_MUTATOR
					} else {
						TokenType.GREATER_THAN
					}
				}
			}

			if (char()?.isDigit() == true) {
				value = ""
				var decimal = false
				while ((char()?.isDigit() == true) || (!decimal && char() == '.')) {
					if (char() == '.') {
						if (proChar()?.isDigit() == true) {
							decimal = true
						} else {
							break
						}
					}
					value += char()
					inc()
				}
				appendToken(tokenType = TokenType.NUMBER)
				continue
			}

			if (char() == '<') {
				if (proChar() == '=') {
					appendToken(tokenType = TokenType.LESS_EQUAL_THAN, incAmount = 2)
					continue
				} else {
					type = TokenType.LESS_THAN
				}
			}

			if (char() == '"') {
				value = ""
				inc()
				while (char() != '"' && char() != null) {
					if (char() == '\\') {
						if (proChar()?.let { "\\\"".contains(it) } == true) {
							inc()
						} else if (proChar() == 'n') {
							value += '\n'
							inc(2)
							continue
						} else if (proChar() == 't') {
							value += '\t'
							inc(2)
							continue
						}
					}
					value += char()
					inc()
				}
				if (char() == null) {
					value = null
				} else {
					appendToken(tokenType = TokenType.STRING_LITERAL, incAmount = 1)
					continue
				}
			}

			if (char() == '.' && proChar() == '.') {
				val isInclusive = proChar(2) == '.'
				val isExclusive = proChar(2) == '/'
				if (isInclusive) {
					type = TokenType.INCLUSIVE_INTERVAL
				} else if (isExclusive) {
					type = TokenType.EXCLUSIVE_INTERVAL
				}
				if (isInclusive || isExclusive) {
					appendToken(incAmount = 3)
					continue
				}
			}

			if (char() == '#') {
				value = ""
				inc()
				while (char() != ';' && char() != null) {
					value += char()
					inc()
				}
				appendToken(tokenType = TokenType.COMMENT)
				continue
			}

			for (tokenType in TokenType.values()) {
				if (!tokenType.complex && char() == tokenType.char) {
					type = tokenType
				}
			}

			appendToken(incAmount = 1)
		}
	}

	fun tokens(): List<Token> = tokens.toList()

	private fun appendToken(tokenType: TokenType = type, tokenValue: String? = value, incAmount: Int = 0) {
		inc(incAmount)
		tokens.add(Token(tokenType, tokenValue))
	}

	private fun char(index: Int = pointer): Char? = if (index < code.length) code[index] else null

	// TODO make preChar() ignore whitespace
	private fun preChar(index: Int = 1): Char? {
//		return if (pointer == index - 1) null else code[pointer - index]
		var prevCount = 0
		var backPointer = 0
		while (prevCount < index) {
			backPointer++
			if (pointer - backPointer < 0) {
				return null
			}
			if (!code[pointer - backPointer].isWhitespace()) {
				prevCount++
			}
		}
		return code[pointer - backPointer]
	}

	private fun proChar(index: Int = 1): Char? = if (pointer + index < code.length) code[pointer + index] else null

	private fun inc(x: Int = 1) {
		pointer += x
	}
}

data class Token(val type: TokenType, val value: String? = null) {
	override fun toString(): String =
		"[${type.name.replace('_', ' ')}${if (value != null) ", '$value'" else ""}]"
}

enum class TokenType(val char: Char?, val complex: Boolean) {
	UNDEFINED(null, true),
	KEYWORD(null, true),
	VARIABLE_MUTATOR('>', true),
	COMMA(',', false),
	SEMICOLON(';', false),
	SNIPPET_START('{', false),
	SNIPPET_END('}', false),
	NUMBER(null, true),
	PLUS('+', false),
	MINUS('-', false),
	DIVIDE('/', false),
	MULTIPLY('*', false),
	MODULO('%', false),
	POWER('^', false),
	EQUALS('=', false),
	LESS_THAN('<', true),
	GREATER_THAN('>', true),
	LESS_EQUAL_THAN(null, true),
	GREATER_EQUAL_THAN(null, true),
	NOT('!', false),
	OR('|', false),
	AND('&', false),
	OPEN_BRACKET('(', false),
	CLOSE_BRACKET(')', false),
	OPEN_INDEX('[', false),
	CLOSE_INDEX(']', false),
	TYPE_INDICATOR(':', false),
	STRING_LITERAL('"', true),
	INCLUSIVE_INTERVAL(null, true),
	EXCLUSIVE_INTERVAL(null, true),
	COMMENT('#', true)
}
