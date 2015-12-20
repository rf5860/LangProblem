package com.rjf;

import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.apache.commons.collections4.SetUtils.intersection;
import static org.apache.commons.io.FileUtils.lineIterator;
import static org.apache.commons.io.LineIterator.closeQuietly;
import static org.apache.commons.lang3.StringUtils.*;

public class LanguageProblem {
    private Map<String, Set<String>> languages = new HashMap<String, Set<String>>();

    public Set<String> getLanguageWords(String language) {
        return languages.get(language);
    }

    public void addLanguage(String fileName) {
        String[] paths = split(fileName, '/');
        String language = split(paths[paths.length - 1], ".")[0];
        Set<String> existingWords = languages.get(language);
        if (existingWords == null) {
            existingWords = new HashSet<String>();
            languages.put(language, existingWords);
        }
        existingWords.addAll(getWords(fileName));
    }

    public void addLanguages(String... fileNames) {
        for (String fileName : fileNames) addLanguage(fileName);
    }

    private HashSet<String> getWords(String fileName) {
        HashSet<String> newWords = new HashSet<String>();
        LineIterator it = null;
        try {
            it = lineIterator(new File(fileName));
            while (it.hasNext()) {
                newWords.addAll(asList(split(upperCase(removePattern(it.nextLine(), "[.:;,]")), ' ')));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(it);
        }
        return newWords;
    }

    public String determineLanguage(String file) {
        HashSet<String> words = getWords(file);
        int highestMathces = 0;
        String matchingLanguage = null;
        for (Map.Entry<String, Set<String>> entry : languages.entrySet()) {
            int currentMatches = intersection(words, entry.getValue()).size();
            if (currentMatches > highestMathces) {
                highestMathces = currentMatches;
                matchingLanguage = entry.getKey();
            }
        }
        return matchingLanguage;
    }
}
