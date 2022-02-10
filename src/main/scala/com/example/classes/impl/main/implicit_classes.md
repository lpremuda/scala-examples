# Implicit Class
Give us the ability to add additional methods to existing classes.

### Limitations
1. Implicit classes cannot be top-level objects (they must be encapsulated in traits, classes, or objects)
2. Implicit classes cannot take multiple non-implicit arguments in their constructor.
3. We cannot use implicit classes in case classes.
4. There cannot be any member or object in scope with the same name as the implicit class.
