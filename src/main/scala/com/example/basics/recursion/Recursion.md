# Recursion

## Tail Recursion

This list of contact points exists to show you who can answer questions/concerns you might have about the Datastore Service.
Tail recursive is when a recursive call is the last thing executed by the function

### Benefits:
  - Tail recursion can be optimized by the compiler
      - Since the recursive call is the last statement, there is nothing left to do in the current function, so
        saving the current function's stack frame is pointless
  - Stack depth is essential 1, as opposed to n number of recursive calls
