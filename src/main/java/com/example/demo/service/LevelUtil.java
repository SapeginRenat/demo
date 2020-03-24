package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class LevelUtil {

    @Value("#{${level.init.number-map:{1:100, 10:110, 20:120, 30:150, 40:190, 50:200}}}")
    TreeMap<Integer, Integer> levels = new TreeMap<>();

    public int getLevelByExperience(int exp)  {

        return levels.entrySet()
                .stream()
                .filter(v -> v.getValue() >= exp)
                .map(Map.Entry::getKey).findFirst()
                .orElse(levels.lastKey());
    }

    public int getExpByLevel(int level) {
        if (levels.lowerKey(level) != null) {
            return levels.get(levels.lowerKey(level));
        } else {
            return levels.get(level);
        }
    }

    public  int getMaxLevel() {
        return levels.lastKey();
    }

    public int getFirstLevel() {
        return levels.firstKey();
    }

}
