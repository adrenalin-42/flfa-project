# LFAF Laboratory Work #4

### Course: Formal Languages & Finite Automata
### Author: Dumitru Moraru

----

“It is easy to love your friend, but sometimes the hardest lesson to learn is to love
your enemy”. - _Sun Tzu’s "The Art of War"_

## Theory

### Step 1: Taming the CFG Beast
In this first step, we face the beastly CFG. It's like a wild animal that needs to be
tamed! We take those unruly grammar rules and examine them closely, trying to make sense
of their madness. It's like decoding a secret language, but with a twist of humor.

### Step 2: The Power of Unit Productions
In step two, we discover the power of unit productions. They're like those "shortcuts"
in life that make everything easier. We identify those pesky unit productions - those
rules with only one variable on the right-hand side - and give them a makeover. We
replace them with their expanded versions, adding more variables into the mix. It's
like going from a plain sandwich to a gourmet feast!

### Step 3: Binary Rules to the Rescue
Step three is all about embracing the binary rules. They're like the dynamic duos
of grammar! We look for those grammar rules with more than two variables on the
right-hand side and break them down. It's like dividing a big group project into
smaller teams, each working on its own part. We create new variables and pair them
up, making the grammar structure more manageable and organized.

### Step 4: Superhero Terminators
In the final step, we tackle the terminal symbols, giving them a superhero transformation. Terminals are like the witty one-liners in a conversation - they add flavor! We replace those terminals with shiny new variables, creating a whole new grammar rule. It's like putting on a disguise and joining the grammar superhero team.

## Objectives:

* Learn about **Chomsky Normal Form**.
* Get familiar with the approaches of normalizing a grammar.
* Cure procrastination.
* Implement a method for normalizing an input grammar by the rules of CNF.
* Make so the functions accepts any type of grammar (which it does).


## Implementation description

* By initializing a new class named `ChomskyNormalForm` by initializing we start a chain
  of fun, which has under the hood the stuff below.

* Say sayonara to empty string with `removeEpsilonProductions`: We're dealing with
  ε-productions here, those sneaky ones that create empty strings. But fear not! We
  replace the nullable variables in other productions, updating the grammar
  accordingly. It's like telling those productions, "Hey, you can't just disappear
  into thin air!"

* Let's evict the solo acts with `removeUnitProductions`: We're talking about those
  productions that only have a single variable on the right side. It's like having a
  concert with just one musician - no fun at all! We swap them out with non-unit
  productions to make sure our grammar is all CNF-compliant. It's all about giving our
  grammar some friends.

* `removeInaccessibleSymbols`: This bad boy kicks out variables and productions that
  can't be reached from the start symbol. We're cleaning house, getting rid of unused
  stuff, and making our grammar simpler than a toddler's ABC book.

* `removeNonProductiveSymbols`: Time to bid adieu to nonproductive variables and their
  productions. These variables are like those friends who never show up to the party.
  We're optimizing our grammar by getting rid of them. We only want variables that can
  generate some cool terminal strings.

* `toCNF` is where the real magic happens: We're converting the remaining productions
  to the magnificent Chomsky Normal Form. How? By replacing terminals in mixed
  productions with shiny new variables and breaking down long productions into bite-sized
  binary productions. It's like giving our grammar a makeover worthy of a reality
  TV show - new faces, new rules, new life!

Finally, we have use `printGrammar`: This method is the icing on the cake.
It shows off our dazzling CNF grammar, displaying variables, terminals, and
productions in a format even our grandmas can understand. We're all about making
it user-friendly (Thanks, Ms Veronica Bagrin)!

## Conclusions / Screenshots / Results

### Opinion:

It was a big laboratory work. Had fun doing it, even though it was not os easy to work
on. I delayed the lab pretty hard, so hope I pass at least. Thank you for checking my
work if you are reading this <3.

### Results:

#### CNF Transformation in action

```jshelllanguage
HashMap<List<Character>, List<String>> productions = QualityOfLife.initialize_productions();

productions.put(List.of('S'), List.of("aA", "AC"));
productions.put(List.of('A'), List.of("a", "ASC", "BC", "aD"));
productions.put(List.of('B'), List.of("b", "bA"));
productions.put(List.of('C'), List.of("$", "BA"));
productions.put(List.of('D'), List.of("abC"));
productions.put(List.of('E'), List.of("aB"));

List<Character> Vt = QualityOfLife.generate_vt_from_productions(productions);
List<Character> Vn = QualityOfLife.generate_vn_from_productions(productions);

Grammar grammar = new Grammar(Vn, Vt, productions, 'S');

ChomskyNormalForm newGrammar = new ChomskyNormalForm(grammar);

newGrammar.printGrammar();
```

#### Output:
```
Variables: [S, A, C, B, D, 1, 2, 3, 4, 5, 6]
Terminals: [a, b]
Productions: 
[1] -> [a]
[A] -> [1, A2, AS, BC, 1D, 4, 4A]
[2] -> [S3]
[B] -> [4, 4A]
[3] -> [C, C]
[S] -> [1A, AC, 1, A2, AS, BC, 1D]
[C] -> [BA]
[4] -> [b]
[D] -> [a5, 14]
[5] -> [b6]
[6] -> [C]
```


## References

_**Books:**_
* _Władysław Homenda, Witold Pedrycz_ \
**Automata Theory and Formal Languages**

**Websites:**

* Art of War quotes **[here](https://www.amardeep.co/blog/10-quotes-from-the-art-of-war-that-will-transform-your-life)**.
* Wikipedia page about Chomsky Normal Form **[here](https://en.wikipedia.org/wiki/Chomsky_normal_form)**