/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngramplayground;

import java.util.HashMap;

/**
 * Обёртка для текстовой модели данных
 * @author Admin
 */
public class TextDataModelWrapper extends DataModelWrapper {
    /**
     * Прямое соответствие элементов и их индексов
     */
    protected HashMap<String, Integer> forwardAssignment;
    /**
     * Обратное соответствие элементов и их индексов
     */
    protected HashMap<Integer, String> reverseAssignment;
    /**
     * Основной конструктор
     */
    public TextDataModelWrapper() {
        super();
    }
    /**
     * Построение модели данных на основе строки
     * @param source Исходная строка
     * @param chainLength Длина цепочки
     */
    public void buildModel(String source, int chainLength){
        
    }
    /**
     * Генерация текста на основе построенной модели
     * @param unitLength Количество элементов
     * @return Сгенерированный текст
     */
    public String generate(int unitLength) {
        return "";
    }
}
