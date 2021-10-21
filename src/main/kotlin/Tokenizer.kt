import java.lang.Exception
class Tokenizer {

    private var position = -1
    var lastError: String = ""
        private set


    fun scan(expression: String) : List<Token>? {
        val tokens = mutableListOf<Token>()

        while(position + 1 < expression.length) {
            val c = expression[position + 1]
            when {
                c.isWhitespace() -> {
                    position++
                }
                c.isDigit() -> {
                    try {
                        var number = parseNumberAsDouble(expression)
                        tokens.add(NumberToken(number))
                    }
                    catch (e: Exception) {
                        lastError = e.message.toString()
                        return null
                    }
                }
                c == '+' -> {
                    tokens.add(OperatorToken(TokenOperatorType.Plus))
                    position++
                }
                c == '-' -> {
                    tokens.add(OperatorToken(TokenOperatorType.Minus))
                    position++
                }
                c == '*' -> {
                    tokens.add(OperatorToken(TokenOperatorType.Multiply))
                    position++
                }
                c == '/' -> {
                    tokens.add(OperatorToken(TokenOperatorType.Divide))
                    position++
                }
                c == '(' -> {
                    tokens.add(ParenthesisToken(TokenParenthesisType.Open))
                    position++
                }
                c == ')' -> {
                    tokens.add(ParenthesisToken(TokenParenthesisType.Close))
                    position++
                }
                else -> {
                    lastError = "Unexpected char in expression: $c"
                    return null
                }
            }
        }


        return tokens;
    }

    private fun parseNumberAsDouble(expression: String): Double {
        var character = expression[position + 1]
        val sbNumber: StringBuilder = StringBuilder()
        var foundSeparator = false

        while(character.isDigit() || character == '.') {
            if(character == '.') {
                if(foundSeparator) throw Exception("Can not have multiple dots in number")
                foundSeparator = true
            }
            sbNumber.append(character)
            position++
            character = if(position + 1 < expression.length) expression[position + 1] else ' '
        }
        return sbNumber.toString().toDouble()
    }
}