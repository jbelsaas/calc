import kotlin.test.*

internal class TestTokenizer {
    private val scanner:  Tokenizer = Tokenizer()

    @Test
    fun testEmptyExpression() {
        val tokenList = scanner.scan("")
        assertNotNull(tokenList)
        assertEquals(0, tokenList.size, "Empty Expression should return empty list")
    }

    @Test
    fun testInvalidExpression() {
        val tokenList = scanner.scan("a")
        assertNull(tokenList)
        assertEquals("Unexpected char in expression: a", scanner.lastError)
    }

    @Test
    fun testInvalidExpression2() {
        val tokenList = scanner.scan("=")
        assertNull(tokenList)
        assertEquals("Unexpected char in expression: =", scanner.lastError)
    }

    @Test
    fun testWhiteSpaceOnly() {
        val tokenList = scanner.scan("          ")
        assertNotNull(tokenList)
        assertEquals(0, tokenList.size, "Empty Expression should return empty list")
    }

    @Test
    fun testPluss() {
        val tokenList = scanner.scan("+")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals( TokenOperatorType.Plus, (tokenList[0] as OperatorToken).type,"PlusToken Expected")
    }

    @Test
    fun testMinus() {
        val tokenList = scanner.scan("-")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals(TokenOperatorType.Minus,(tokenList[0] as OperatorToken).type,"MinusToken Expected")
    }

    @Test
    fun testMultiply() {
        val tokenList = scanner.scan("*")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals( TokenOperatorType.Multiply, (tokenList[0] as OperatorToken).type,"MultiplyToken Expected")
    }

    @Test
    fun testDivide() {
        val tokenList = scanner.scan("/")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals(TokenOperatorType.Divide, (tokenList[0] as OperatorToken).type,"DivideToken Expected")
    }

    @Test
    fun testParanthesisStart() {
        val tokenList = scanner.scan("(")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals( TokenParenthesisType.Open, (tokenList[0] as ParenthesisToken).type,"OpenParanthesis Expected")
    }

    @Test
    fun testParanthesisEnd() {
        val tokenList = scanner.scan(")")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals( TokenParenthesisType.Close, (tokenList[0] as ParenthesisToken).type, "CloseParanthesis Expected")
    }

    @Test
    fun testInteger() {
        val tokenList = scanner.scan("3")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals(3.0, (tokenList[0] as NumberToken).value)
    }

    @Test
    fun testIntegerMultipleDigits() {
        val tokenList = scanner.scan("30")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals(30.0, (tokenList[0] as NumberToken).value)
    }

    @Test
    fun testDouble() {
        val tokenList = scanner.scan("30.34")
        assertNotNull(tokenList)
        assertEquals(1, tokenList.size, "Expected one token")
        assertEquals(30.34, (tokenList[0] as NumberToken).value)
    }

    @Test
    fun testDoubleWithMultipleDots() {
        val tokenList = scanner.scan("30.3.4")
        assertNull(tokenList)
        assertEquals("Can not have multiple dots in number", scanner.lastError)
    }

    @Test
    fun testExpression() {
        val tokenList = scanner.scan("3+3*(6+8)")
        assertNotNull(tokenList)
        assertEquals(9, tokenList.size)
        val testCond =
                    (tokenList[0] as NumberToken).value == 3.0 &&
                    (tokenList[1] as OperatorToken).type == TokenOperatorType.Plus &&
                    (tokenList[2] as NumberToken).value == 3.0 &&
                    (tokenList[3] as OperatorToken).type == TokenOperatorType.Multiply &&
                    (tokenList[4] as ParenthesisToken).type == TokenParenthesisType.Open &&
                    (tokenList[5] as NumberToken).value == 6.0 &&
                    (tokenList[6] as OperatorToken).type == TokenOperatorType.Plus &&
                    (tokenList[7] as NumberToken).value == 8.0 &&
                    (tokenList[8] as ParenthesisToken).type == TokenParenthesisType.Close

        assertTrue(testCond)
    }

    @Test
    fun testExpressionWithErr() {
        val tokenList = scanner.scan("3g+3*(6+8)")
        assertNull(tokenList)
        assertEquals("Unexpected char in expression: g", scanner.lastError)
    }
}