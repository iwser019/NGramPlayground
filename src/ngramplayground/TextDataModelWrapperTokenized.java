/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngramplayground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Admin
 */
public class TextDataModelWrapperTokenized extends TextDataModelWrapper {
    ArrayList<String> tokens;
    TextDataModelWrapperTokenized() {
        super();
        tokens = new ArrayList<>();
    }
    void setTokens(ArrayList<String> tokens){
        this.tokens = tokens;
    }
    @Override
    public void buildModel(String source, int chainLength){
        HashSet<String> charSet = new HashSet<>();
        ArrayList<Integer> sourceConverted = new ArrayList<>();
        ArrayList<String> sourceSplitted = Util.splitWithTokens(source, tokens);
        int srcLen = sourceSplitted.size();
        this.forwardAssignment = new HashMap<>();
        this.reverseAssignment = new HashMap<>();
        int iter = 0;
        for (String token : tokens){
            forwardAssignment.put(token, iter);
            reverseAssignment.put(iter, token);
            iter++;
        }
        for (String srcWord : sourceSplitted) {
            forwardAssignment.put(srcWord, iter);
            reverseAssignment.put(iter, srcWord);
            iter++;
        }
        iter = 0;
        while (iter < srcLen)
        {
            sourceConverted.add(forwardAssignment.get(sourceSplitted.get(iter)));
            iter++;
        }
        lastModel = new DataModel(sourceConverted, chainLength);
    }
    
    @Override
    public String generate(int unitLength){
        String result = "";
        ArrayList<Integer> resultRaw = lastModel.generate(unitLength);
        for (Integer unit : resultRaw)
            result += reverseAssignment.get(unit);
        return result;
    }
}
