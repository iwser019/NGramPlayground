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
public class TextDataModelWrapperWord extends TextDataModelWrapper {
    public TextDataModelWrapperWord() {
        super();
    }
    @Override
    public void buildModel(String source, int chainLength){
        HashSet<String> charSet = new HashSet<>();
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(" ");
        ArrayList<Integer> sourceConverted = new ArrayList<>();
        ArrayList<String> sourceSplitted = Util.splitWithTokens(source, tokens);
        ArrayList<String> sourceSplittedPrepared = (ArrayList<String>)sourceSplitted.clone();
        sourceSplittedPrepared.removeIf((s) -> s.equals(" "));
        int srcLen = sourceSplitted.size();
        this.forwardAssignment = new HashMap<>();
        this.reverseAssignment = new HashMap<>();
        int iter = 0;
        for (String srcWord : sourceSplittedPrepared) {
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
        result += reverseAssignment.get(resultRaw.get(0));
        resultRaw.remove(0);
        while (!resultRaw.isEmpty()) {
            result += " ";
            result += reverseAssignment.get(resultRaw.get(0));
            resultRaw.remove(0);
        }
        return result;
    }
}
