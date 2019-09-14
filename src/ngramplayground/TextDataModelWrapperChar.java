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
 * Обёртка для символьной модели данных
 * @author Admin
 */
public class TextDataModelWrapperChar extends TextDataModelWrapper {
    TextDataModelWrapperChar() {
        super();
    }
    @Override
    public void buildModel(String source, int chainLength){
        HashSet<String> charSet = new HashSet<>();
        ArrayList<Integer> sourceConverted = new ArrayList<>();
        int srcLen = source.length();
        this.forwardAssignment = new HashMap<>();
        this.reverseAssignment = new HashMap<>();
        int iter = 0;
        while (iter < srcLen) {
            charSet.add(source.substring(iter, iter + 1));
            iter += 1;
        }
        iter = 0;
        for (String srcChar : charSet) {
            forwardAssignment.put(srcChar, iter);
            reverseAssignment.put(iter, srcChar);
            iter++;
        }
        iter = 0;
        while (iter < srcLen) {
            sourceConverted.add(
                    forwardAssignment.get(source.substring(iter, iter + 1))
            );
            iter += 1;
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
