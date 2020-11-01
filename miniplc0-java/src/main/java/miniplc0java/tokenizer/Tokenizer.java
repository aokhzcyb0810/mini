package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;
public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     * 
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (Character.isAlphabetic(peek)) {
            return lexIdentOrKeyword();
        } else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字为止:
        // -- 前进一个字符，并存储这个字符
        //
        // 解析存储的字符串为无符号整数
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        //
        // Token 的 Value 应填写数字的值
        String str="";
        char ch;
        int len,num=0,i=1;
        Token token=new Token(TokenType.Uint,num,it.currentPos(),it.currentPos());
        while(true){
            ch=it.peekChar();
            if(!(ch>=48 && ch<=57)){
                break;
            }
            str+=it.nextChar();
        }
        len=str.length();
        while(len>0){
            len--;
            num+=((int)(str.charAt(len))-48)*i;
            i*=10;
        }
        token.setValue(num);
        token.setEndPos(it.currentPos());
        return token;
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        //
        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        // -- 否则，返回标识符
        //
        // Token 的 Value 应填写标识符或关键字的字符串
        String str="";
        char ch;
        Token token=new Token(TokenType.Uint,str,it.currentPos(),it.currentPos());
        while(true){
            ch=it.peekChar();
            if(ch<48 || (ch>=58 && ch<=64) || (ch>=91 && ch<=96) || ch>=123){
                break;
            }
            str+=it.nextChar();
        }
        if(str.equals("Begin")){
            token.setTokenType(TokenType.Begin);
            token.setValue(TokenType.Begin.toString());
            token.setEndPos(it.currentPos());
        }
        else if(str.equals("End")){
            token.setTokenType(TokenType.End);
            token.setValue(TokenType.End.toString());
            token.setEndPos(it.currentPos());
        }
        else if(str.equals("Var")){
            token.setTokenType(TokenType.Var);
            token.setValue(TokenType.Var.toString());
            token.setEndPos(it.currentPos());
        }
        else if(str.equals("Const")){
            token.setTokenType(TokenType.Const);
            token.setValue(TokenType.Const.toString());
            token.setEndPos(it.currentPos());
        }
        else if(str.equals("Print")){
            token.setTokenType(TokenType.Print);
            token.setValue(TokenType.Print.toString());
            token.setEndPos(it.currentPos());
        }
        else{
            token.setTokenType(TokenType.Ident);
            token.setValue(TokenType.Ident.toString());
            token.setEndPos(it.currentPos());
        }
        return token;
        //throw new Error("Not implemented");
    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        switch (it.nextChar()) {
            case '+':
                return new Token(TokenType.Plus, '+', it.previousPos(), it.currentPos());

            case '-':
                // 填入返回语句
                return new Token(TokenType.Minus, '-', it.previousPos(), it.currentPos());

            case '*':
                // 填入返回语句
                return new Token(TokenType.Mult, '*', it.previousPos(), it.currentPos());

            case '/':
                // 填入返回语句
                return new Token(TokenType.Div, '/', it.previousPos(), it.currentPos());
            case '=':
                // 填入返回语句
                return new Token(TokenType.Equal, '=', it.previousPos(), it.currentPos());
            case ';':
                // 填入返回语句
                return new Token(TokenType.Semicolon, ';', it.previousPos(), it.currentPos());
            case '(':
                // 填入返回语句
                return new Token(TokenType.LParen, '(', it.previousPos(), it.currentPos());
            case ')':
                // 填入返回语句
                return new Token(TokenType.RParen, ')', it.previousPos(), it.currentPos());
            // 填入更多状态和返回语句

            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }
}
