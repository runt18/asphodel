# Contribution guidelines

This document describes a set of conventions to follow to keep our code organised and consistent.

## Java style
Everyone hates style guides but let's try and agree on some things to keep things tidy

- Curly braces on the same line as block definitions
    eg.
    ```java
    void foo(int x) {
        x += 1;
    }
    
    if(something) {
        foo(1);
    }
    else {
        foo(2);
    }
    ```

- Four-space indentation
- Newline at the end of a file
- No trailing whitespace!
- One class per file
- Try and keep to 120 character line limts - not that important
- Classes should have a well-defined purpose - if it's 1000 lines long there's probably unrelated methods in there
- No uncommented methods - at least one line, preferably more

## Git usage
- Do all development in a branch with a name that describes the feature you are working on
- Submit a pull request to merge it into master when it is finished
- Test things before merging - the code in the master branch shoud always pass all unit tests and compile and run correctly
