package com.arrkhange1.keywordsmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrioritizedKeySkillsService {
    private static final Logger logger = LoggerFactory.getLogger(PrioritizedKeySkillsService.class);
    private final Integer KEY_SKILLS_AMOUNT = 50;

    PriorityQueue<Map.Entry<String, Integer>> prioritizedKeySkills =
            new PriorityQueue<>(KEY_SKILLS_AMOUNT + 1, Comparator.comparingInt(Map.Entry::getValue));

    private void fillPrioritizedSkills(Map<String, Integer> keywordsCounter) {
        for (var entry : keywordsCounter.entrySet()) {
            prioritizedKeySkills.add(entry);
            if (prioritizedKeySkills.size() > KEY_SKILLS_AMOUNT) {
                prioritizedKeySkills.poll();
            }
        }
    }

    private List<String> convertQueueToList() {
        List<String> skills = new ArrayList<>(KEY_SKILLS_AMOUNT);
        while (!prioritizedKeySkills.isEmpty()) {
            var keyword = prioritizedKeySkills.poll();
            logger.info(keyword.toString());
            skills.add(keyword.getKey());
        }
        Collections.reverse(skills);
        return skills;
    }

    public List<String> getKeySkills(Map<String, Integer> keywordsCounter) {
        fillPrioritizedSkills(keywordsCounter);
        return convertQueueToList();
    }


}