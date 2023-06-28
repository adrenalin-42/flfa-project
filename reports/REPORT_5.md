# LFAF Laboratory Work #5

### Course: Formal Languages & Finite Automata
### Author: Dumitru Moraru

----

“Anger may in time change to gladness… But a kingdom that has once been destroyed
can never come again into being”. - _Sun Tzu’s "The Art of War"_

## Theory

Hey there! So, you know how us programmers write code in fancy languages like _Java_,
_Python_, _or C++?_ Well, before the computer can understand and execute our code, it
goes through a process called **parsing**. Think of it like a _language translator_
for computers!

**Parsing** is like a _superpower_ that breaks down our code into its fundamental parts,
called **tokens**. It's like when you're trying to understand a sentence by separating
it into words. But parsing is way _cooler_ because it not only recognizes the words
but also figures out their **roles** and **relationships**!

Once parsing is done, we get this magical thing called an **Abstract Syntax Tree (AST)**.
It's like the blueprint of our code, showing how all the _tokens_ fit together. It's a
_tree structure_ where each node represents a code construct like a function, loop,
or assignment.

The **AST** is pretty awesome because it captures the _essence_ of our code's structure.
It's like a visual map that the computer can use to navigate and understand what we
meant. Just imagine if humans had **AST**s for everyday life situations—no more confusion
in Ikea furniture assembly!

Now, why do we even bother with parsing and **AST**s? Well, they make our lives easier!
With the AST, we can perform all sorts of cool things like _code analysis_,
_optimization_, and even _transforming code_ into something else entirely. It's like
having a secret decoder ring for programming languages!

So next time you write some code, remember that behind the scenes, **parsing** and
**AST**s are working their _magic_. They're the unsung heroes that help computers understand
our brilliant (and sometimes not-so-brilliant) creations. Embrace the power of **parsing**
and **AST**s, and **_may your code be bug-free_** and your trees be always abstractly awesome!

## Objectives:

* Get familiar with **parsing**, what it is and how it can be programmed.
* GGet familiar with the concept of **AST**.
* Hopefully **_not_** to pay for the tuition fee.
* Have a type **TokenType** that can be used in the lexical analysis 
to categorize the tokens, using **Regular Expressions**.
* Implement the necessary data structures for an AST that could be used for
the text you have processed before.
* Implement a simple parser program that could extract the syntactic
information from the input text.


## Implementation description

I have upgraded my `Tokenizer` class, and now it has more functionality.

* New class `Statement` represents a statement in a code snippet and provides
methods to access information about the statement, such as the variable type,
variable name, and value. It encapsulates the data related to a `Token` and
allows other parts of the program to retrieve specific information from the
statement object.

* I added a method named `buildAST()`, which takes a collection of tokens,
processes them one by one, and builds an **AST** by creating **token nodes**,
connecting them based on **braces**, and forming statements. It provides a
hierarchical representation of the code's structure, which can be used for
further analysis or transformations.

* `printAST(Object node, int level)`: this code provides a way to traverse and
print the contents of an **AST**. It distinguishes between Token and Statement nodes
and prints their values accordingly. By recursively calling the printAST method on
child nodes, it achieves a hierarchical representation of the AST's structure, 
allowing for a visual inspection of the AST's contents.

## Conclusions / Screenshots / Results

### Opinion:

It was fun to practice with AST Data Structure, wanted to make it pretty and make it an
actual data structure instead of just print formatting (which was my initial idea).
Overall, it was a great course and thanks to the laboratory works I actually managed
to learn something useful and practice the skills.

### Results:

#### Printing a AST in action.

```jshelllanguage
String code = """
    Description
    {
        name="John"
        type="NPC"
        mbti="intj"
        role="protagonist"
        background="John is a witch hunter"
    }
    Setting
    {
        type="game"
        category="adventure, magic, open-world"
        background="A world similar to Mars, where witches are living in forests and fight with humans"
    }
    Response
    {
        length=300
        prompt="I need the background story of the character. Add some info about family. Give 3 possible stories in which John is side character"
    }""";

Tokenizer tokenizer = new Tokenizer(code);
Token root = Tokenizer.buildAST();


Tokenizer.printAST(root, 0);
```

#### Output:
```
Description
    name (STRING_LITERAL) "John"
    type (STRING_LITERAL) "NPC"
    mbti (STRING_LITERAL) "intj"
    role (STRING_LITERAL) "protagonist"
    background (STRING_LITERAL) "John is a witch hunter"
Setting
    type (STRING_LITERAL) "game"
    category (STRING_LITERAL) "adventure, magic, open-world"
    background (STRING_LITERAL) "A world similar to Mars, where witches are living in forests and fight with humans"
Response
    length (NUMBER_LITERAL) 300
    prompt (STRING_LITERAL) "I need the background story of the character. Add some info about family. Give 3 possible stories in which John is side character"
```


## References

_**Books:**_
* _Władysław Homenda, Witold Pedrycz_ \
**Automata Theory and Formal Languages**

**Websites:**

* Art of War quotes **[here](https://www.amardeep.co/blog/10-quotes-from-the-art-of-war-that-will-transform-your-life)**.
* Parsing **[here](https://en.wikipedia.org/wiki/Parsing)**
* Abstract syntax tree **[here](https://en.wikipedia.org/wiki/Abstract_syntax_tree)**
