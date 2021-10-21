class Parser(private val tokens: List<Token>) {

    private var position = -1

    //PARSE TOKES
    fun parseExpression(): Double {
        var result = parseTerm()

        var next = peek()
        while(next != null && next is OperatorToken && isAdditionOrSubtraction(next)) {
            position++
            val termValue = parseTerm()
            result = when(next.type) {
                TokenOperatorType.Plus -> result + termValue
                TokenOperatorType.Minus -> result - termValue
                else -> throw Exception("Unexpected + or -")  //AssertTrue(should not happen)
            }
            next = peek()
        }

        return result
    }

    private fun isAdditionOrSubtraction(next: OperatorToken) =
        (next.type == TokenOperatorType.Plus || next.type == TokenOperatorType.Minus)

    private fun parseTerm(): Double {
        var result = parseFactor()

        var next = peek()
        while(next != null && next is OperatorToken && isDivisionOrMultiplication(next)) {
                position++
                val factorValue = parseFactor()
                when(next.type) {
                    TokenOperatorType.Multiply -> result *= factorValue
                    TokenOperatorType.Divide -> result /= factorValue
                    else -> throw Exception("Unexpected * or /") //AssertTrue(should not happen)
                }
            next = peek()
        }

        return result
    }

    private fun parseFactor(): Double {
        var next = peek()
        if(next is NumberToken) {
            position++
            return next.value
        }

        next = peek()
        if( !(next is ParenthesisToken && next.type == TokenParenthesisType.Open) ) throw Exception("Expected: Number or (")
        position++

        val result = parseExpression()

        next = peek()
        if( !(next is ParenthesisToken && next.type == TokenParenthesisType.Close) ) throw Exception("Expected: Number or )")
        position++

        return result
    }

    private fun peek(): Token? {
        return if(!hasNext()) null else tokens[position + 1]
    }

    private fun hasNext(): Boolean = (position + 1) < tokens.size

    private fun isDivisionOrMultiplication(next: OperatorToken) =
        (next.type == TokenOperatorType.Multiply || next.type == TokenOperatorType.Divide)
}