/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngramplayground;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Random;
import static java.util.Collections.unmodifiableList;

/**
 * Класс модели данных
 * @author Admin
 */
public class DataModel {
    /**
     * Длина входной цепочки
     */
    protected int chainLength;
    protected HashMap<List<Integer>, ArrayList<Integer>> data;
    /**
     * Рандомизатор
     */
    protected Random randomizer;
    public DataModel() {
        chainLength = 0;
        data = null;
        randomizer = new Random();
        randomizer.setSeed(java.time.LocalTime.now().getNano());
    }
    /**
     * Конструктор модели данных
     * @param sourceSeq Исходная последовательность
     * @param chainLength Длина входной цепочки
     */
    public DataModel(ArrayList<Integer> sourceSeq, int chainLength) {
        this();
        this.chainLength = chainLength;
        data = new HashMap<>();
        int srcLength = sourceSeq.size();
        List<Integer> key = new ArrayList<>();
        for (int i = 0; i < chainLength; i++)
            key.add(sourceSeq.get(Util.rotateIndex(i, srcLength)));
        for (int i = 0; i < srcLength; i++){
            Integer newLink = sourceSeq.get(Util.rotateIndex(i + chainLength, srcLength));
            if (!data.containsKey(key)) {
                data.put(unmodifiableList(new ArrayList<>(key)), new ArrayList<>());
            }
            data.get(key).add(newLink);
            key.remove(0);
            key.add(newLink);
        }
    }
    /**
     * Генерация последовательности на основе имеющихся данных
     * @param unitLength Максимальное количество элементов последовательности
     * @return Сгенерированная последовательность
     */
    public ArrayList<Integer> generate(int unitLength) {
        int currLength = chainLength;
        ArrayList<Integer> result = new ArrayList<>();
        List<List<Integer>> dataKeys = new ArrayList<>(data.keySet());
        int randIdx = randomizer.nextInt(dataKeys.size());
        List<Integer> key = new ArrayList(dataKeys.get(randIdx));
        key.forEach((unit) -> {
            result.add(unit);
        });
        while (currLength < unitLength) {
            ArrayList<Integer> nextLinkVariants = data.get(key);
            if (nextLinkVariants == null)
                break;
            int variantsCount = nextLinkVariants.size();
            
            Integer nextLink = nextLinkVariants.get(
                    (variantsCount == 1) ? 0 :
                    randomizer.nextInt(
                            nextLinkVariants.size()
                    )
            );
            result.add(nextLink);
            currLength++;
            key.remove(0);
            key.add(nextLink);
        }
        return result;
    }
}
