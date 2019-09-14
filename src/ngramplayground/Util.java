/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngramplayground;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Admin
 */
public class Util {
    public static ArrayList<String> splitWithTokens(
            String source, 
            ArrayList<String> tokens){
        ArrayList<String> result = new ArrayList<>();
        HashSet<String> tokenPrefixes = new HashSet<>();
        String tempString = "";
        int maxTokenLength = 0;
        for (String token : tokens) {
            maxTokenLength = Math.max(maxTokenLength, token.length());
            if (token.length() > 0)
                tokenPrefixes.add(token.substring(0, 1));
        }
        int maxIter = source.length();
        int iter = 0;
        while (iter < maxIter) {
            if (tokenPrefixes.contains(source.substring(iter, iter + 1))) {
                if (!tempString.isEmpty()) {
                    result.add(tempString);
                    tempString = "";
                }
                int maxJ = Math.min(maxIter - iter, maxTokenLength);
                String tokenCheckStr = source.substring(iter, Math.min(iter + maxJ + 1, maxIter));
                ArrayList<String> tokensQuery = (ArrayList<String>)tokens.clone();
                tokensQuery.removeIf((s) -> s.length() > maxJ);
                tokensQuery.removeIf((s) -> !tokenCheckStr.startsWith(s));
                int goodTokenLength = 0;
                for (String token : tokensQuery)
                    goodTokenLength = Math.max(goodTokenLength, token.length());
                tempString = source.substring(iter, iter + goodTokenLength);
                result.add(tempString);
                tempString = "";
                iter += goodTokenLength;
            } else {
                tempString += source.substring(iter, iter + 1);
                iter++;
            }
        }
        if (!tempString.isEmpty()) {
            result.add(tempString);
        }
        return result;
    }
    public static int rotateIndex(int idx, int size) {
        int result = idx;
        while (result < 0)
            result += size;
        while (result >= size)
            result -= size;
        return result;
    }
}
