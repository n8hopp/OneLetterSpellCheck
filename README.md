A super simple (and likely very inefficient) one-letter spell checker implemented in Java.

Constructor takes in a `Set<String>` as 'valid' words. The `correctTypo` function takes in a string as input, and if that string is within 1 letter of a valid word, it will return that valid word.

Uses a hashmap so that you can programmatically build a dictionary of suggestions the longer the class runs. You could likely easily implement this with file i/o but I just implemented this as extra credit for a Compilers assignment so I didn't have time to mess around too much.
