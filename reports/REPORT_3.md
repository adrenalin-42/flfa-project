# LFAF Laboratory Work #3

### Course: Formal Languages & Finite Automata
### Author: Dumitru Moraru

----

“The greatest victory is that which requires no battle”. - _Sun Tzu’s "The Art of War"_

## Theory

Basically, Lexical Analysis which is also called **tokenization** breaks up a long
string of characters into small tokens, that can be understood by your program. Such
examples of tokens include:

* keywords: var, if, else, int
* identifiers: test, foo, bar
* separators: (, ), {, }, ;
* operators: +, -, /, *
* literal: "Please give me a 10", 69.420, true
* etc.

They all add up and make up a meanigful list of tokens, that should obey the rules you
described when designing the tokenizer/scanner/lexer.

## Objectives:

* Understand what **lexical analysis** is.
* Get familiar with the inner workings of a lexer/scanner/tokenizer.
* Take a course, so I can get myself ready for an internship soon, and not die poor.
* Implement a sample lexer and show how it works.


## Implementation description

We used this code  in our _ELSD project_, so it is the contribution of the whole
team, not only me. I have just ported it to **Java** and tried to understand
it at maximum capacity.

The code above is an implementation of a tokenizer class in Java. This
class has a method called `tokenize(String input)`.The method uses regular
expressions to identify tokens in the input string, and returns a list of
**Token** objects.

The **Tokenizer class** contains a constant array TOKENS, which represents all
possible token names that can be identified by the tokenizer. It also contains several
_constant regular expressions_, one for each type of token that can be identified.
These regular expressions are used to match tokens in the input string.

The tokenize method creates a new **ArrayList** of _Token objects_ to hold the tokens
found in the input string. It then creates a _Matcher object using the Pattern
constant created from the regular expressions_. The method then iterates over the
input string, using the Matcher object to find matches for the regular
expressions. For each match found, the method creates a new **Token** object and adds
it to the list of tokens.

Finally, the tokenize method returns the list of Token objects.

## Conclusions / Screenshots / Results

### Opinion:

It was a weird laboratory work to work on. I didn't expect it to be so short
and meaningless. I would have enjoyed a lot more, if you provided us with an
input, and our goal was to make a lexer for that specific input. That would have
been more fun, **imho**. Worst one yet. 6/10.

### Results:

#### Tokenizer.tokenize() method in action

```
String code = "Description {\n" +
              "    name=\"John\"\n" +
              "    type=\"NPC\"\n" +
              "    mbti=\"intj\"\n" +
              "    role=\"protagonist\"\n" +
              "    background=\"John is a witch hunter\"\n" +
              "}\n" +
              "Setting {\n" +
              "    type=\"game\"\n" +
              "    category=\"adventure, magic, open-world\"\n" +
              "    background=\"A world similar to Mars, where witches are living in forests and fight with humans\"\n" +
              "}\n" +
              "Response {\n" +
              "    length=300\n" +
              "    prompt=\"I need the background story of the character. Add some info about family. Give 3 possible stories in which John is side character\"\n" +
              "}";

List<Token> tokens = Tokenizer.tokenize(code);
System.out.println(tokens);
```

#### Output:
```
[(DESCRIPTION keyword), (LEFT_BRACE separator), (NAME identifier), (EQUALS operator), (STRING_LITERAL literal), (TYPE identifier), (EQUALS operator), (STRING_LITERAL literal), (MBTI identifier), (EQUALS operator), (STRING_LITERAL literal), (ROLE identifier), (EQUALS operator), (STRING_LITERAL literal), (BACKGROUND identifier), (EQUALS operator), (STRING_LITERAL literal), (RIGHT_BRACE separator), (SETTING keyword), (LEFT_BRACE separator), (TYPE identifier), (EQUALS operator), (STRING_LITERAL literal), (CATEGORY identifier), (EQUALS operator), (STRING_LITERAL literal), (BACKGROUND identifier), (EQUALS operator), (STRING_LITERAL literal), (RIGHT_BRACE separator), (RESPONSE keyword), (LEFT_BRACE separator), (LENGTH identifier), (EQUALS operator), (NUMBER_LITERAL literal), (PROMPT identifier), (EQUALS operator), (STRING_LITERAL literal), (RIGHT_BRACE separator)]
```


## References

_**Books:**_
* _Władysław Homenda, Witold Pedrycz_ \
**Automata Theory and Formal Languages**

**Websites:**

* Art of War quotes **[here](https://www.amardeep.co/blog/10-quotes-from-the-art-of-war-that-will-transform-your-life)**.
* Lexer information and explanation **[here](https://llvm.org/docs/tutorial/MyFirstLanguageFrontend/LangImpl01.html)**
* Wikipedia page that explains Lexical Analysis really well **[here](https://en.wikipedia.org/wiki/Lexical_analysis)**