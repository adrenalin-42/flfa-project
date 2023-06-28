public class Statement {
    Token name;
    Token operand;
    Token value;


    public Statement(Token value, Token operand, Token name) {
        this.name = name;
        this.operand = operand;
        this.value = value;
    }

    public String getVarType() {
        return (value.getType());
    }

    public String getVarName() {
        return (name.getValue());
    }

    public String getValue() {
        return (value.getValue());
    }
}
