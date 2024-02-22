# Microjava compiler
MicroJava is a high-level programming language. As the name suggests, MicroJava is similar to Java (which you are, probably, already familiar with), but it's simpler. Similarly to Java, MicroJava source files are compiled to bytecode, which is then executed by a virtual machine (it's called (as you might have guessed) MicroJava Virtual Machine). MicroJava VM is a simple interpretative emulator (i.e. it does not have any support for some "fancy" techniques such as, e.g., Just-In-Time compilation).

--------------------------------------------------------------------------------------------------------------------------------------------------
A MicoJava program is a single text file (conventionally, the extension .mj is used). It always starts with the keyword program.
The main method of a MicroJava program is always called main. When MicroJava program is executed, this method is called first (i.e. it's the program's entry point).
There are:
Value/primitive types (int, char and bool) and reference/structured types (one-dimensional arrays and user-defined classes)
Integer, character and boolean constants
Global, local and class variables (i.e. fields)
Global (static) and class functions (i.e. methods). There are no constructors
Every class can inherit from some other class, except from itself. MicroJava is a single‐inheritance language.
Inheritance in MicroJava has the same principles as in Java. Object is an instance of its class and, at the same time, all superclasses of its class. The subclass reference can be assigned to any of its superclass references. The act of converting a subclass reference into a superclass reference is called up-casting.
Superclass methods can be overridden in its subclass. Because of that, method binding occurs at run-time, based on the type of the object. This is polymorphism (also called dynamic binding or late binding or run‐time binding) and it is a key principle of OOP.
Within a method, the name of a field refers to the current object's field, assuming that the field isn't hidden by a method parameter. If it is hidden, we can access it through this.<field-name>. In the body of a method, this represents reference to the object whose method has been invoked.
Method overloading is not supported in MicroJava.
There is no Object class, the top‐most class, the class from which all other classes are implicitly derived.
There is no garbage collector (allocated objects are only deallocated when the program ends).
Predeclared static methods are ord (converts char to int), chr (converts int to char) and len (returns array's length)
