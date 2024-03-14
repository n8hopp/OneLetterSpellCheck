package visitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SpellCheck {
    HashMap<String, String> seenTypos;

    Set<String> validNames;

    public SpellCheck(Set<String> varNames)
    {
        validNames = varNames;
        seenTypos = new HashMap<>();
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

}
