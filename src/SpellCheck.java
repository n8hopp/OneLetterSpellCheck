import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// idea for program:
// we want:
//      - text file of proper words, dict.txt, to be parsed into a hashset
//          - when we construct spellcheck, check for dict.txt and if it already exists
//      - text file of stored misspellings, typodb.txt, to be parsed into a hashmap
public class SpellCheck {
    HashMap<String, String> seenTypos;

    Set<String> validNames;

    public SpellCheck(Set<String> varNames)
    {
        validNames = InitializeDictionary();
        validNames.addAll(varNames);

        seenTypos = InitializeTypoSet();
    }

    public String correctTypo(String mistake)
    {
        if(seenTypos.containsKey(mistake))
        {
            return seenTypos.get(mistake);
        }
        else
        {
            String correction = newSuggestion(mistake);
            if(known(correction))
            {
                seenTypos.put(mistake, correction);
                return correction;
            }
        }
        return null;
    }
    private String newSuggestion(String mistake)
    {
        // this is so wildly inefficient but it's the easiest way i could think to implement this atm.
        // might try to tryhard later but if I don't then I ran out of time on the assignment
        for(int i = 0; i < mistake.length(); i++)
        {
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder builder = new StringBuilder(mistake);
            for(int j = 0; j < 26; j++)
            {
                builder.setCharAt(i, alphabet.charAt(j));
                String attempt = builder.toString();
                if(known(attempt))
                {
                    return attempt;
                }
                else if(seenTypos.containsKey(attempt))
                {
                    return seenTypos.get(attempt);
                }
            }

            // also checks for if we need to just remove the character entirely
            builder.deleteCharAt(i);
            String removeAttempt = builder.toString();
            if(known(removeAttempt))
            {
                return removeAttempt;
            }
            else if(seenTypos.containsKey(removeAttempt))
            {
                return seenTypos.get(removeAttempt);
            }
        }
        return null;
    }

    private Boolean known(String attempt)
    {
        return validNames.contains(attempt);
    }

    HashSet<String> InitializeDictionary()
    {
        HashSet<String> outDb = new HashSet<>();
        BufferedReader in = null;
        try
        {
            File file = new File("./dict.txt");
            in = new BufferedReader(new FileReader(file));

            String currentLine = null;

            while((currentLine = in.readLine()) != null)
            {
                if(!currentLine.equals(""))
                {
                    outDb.add(currentLine);
                }
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            // Close the BufferedReader
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                };
            }
        }
        return outDb;
    }

    HashMap<String, String> InitializeTypoSet()
    {
        HashMap<String, String> outDb = new HashMap<>();
        BufferedReader in = null;
        try
        {
            File file = new File("./typodb.txt");
            in = new BufferedReader(new FileReader(file));

            String currentLine = null;

            while((currentLine = in.readLine()) != null)
            {
                String[] parts = currentLine.split(":");

                // first part is typo, second is correct spelling
                String typo = parts[0].trim();
                String correction = parts[1].trim();

                if(!typo.equals("") && !correction.equals(""))
                {
                    if(validNames.contains(correction))
                    {
                        outDb.put(typo, correction);
                    }
                    else
                    {
                        throw new RuntimeException("Typo DB contains correction not present in dictionary!");
                    }
                }
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            // Close the BufferedReader
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                };
            }
        }
        return outDb;
    }

}
