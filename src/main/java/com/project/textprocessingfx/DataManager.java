package com.project.textprocessingfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataManager {
    private Map<String, TextEntry> entriesMap;
    private Set<String> uniquePatterns;
    private List<TextEntry> entriesList;

    public DataManager() {
        this.entriesMap = new HashMap<>();
        this.uniquePatterns = new HashSet<>();
        this.entriesList = new ArrayList<>();
    }

    // Create operation
    public TextEntry createEntry(String originalText, String processedText, String regexPattern) {
        TextEntry entry = new TextEntry(originalText, processedText, regexPattern);
        entriesMap.put(entry.getId(), entry);
        uniquePatterns.add(regexPattern);
        entriesList.add(entry);
        return entry;
    }

    // Read operations
    public TextEntry getEntryById(String id) {
        return entriesMap.get(id);
    }

    public List<TextEntry> getAllEntries() {
        return new ArrayList<>(entriesList);
    }

    public List<TextEntry> searchByPattern(String pattern) {
        return entriesList.stream()
                .filter(entry -> entry.getRegexPattern().equals(pattern))
                .collect(Collectors.toList());
    }

    // Update operation
    public boolean updateEntry(String id, String processedText) {
        TextEntry entry = entriesMap.get(id);
        if (entry != null) {
            entry.setProcessedText(processedText);
            return true;
        }
        return false;
    }

    // Delete operation
    public boolean deleteEntry(String id) {
        TextEntry entry = entriesMap.remove(id);
        if (entry != null) {
            entriesList.remove(entry);
            // Only remove pattern if no other entries use it
            if (entriesList.stream().noneMatch(e -> e.getRegexPattern().equals(entry.getRegexPattern()))) {
                uniquePatterns.remove(entry.getRegexPattern());
            }
            return true;
        }
        return false;
    }

    // Statistics and utility methods
    public Set<String> getUniquePatterns() {
        return new HashSet<>(uniquePatterns);
    }

    public int getTotalEntries() {
        return entriesList.size();
    }

    public List<TextEntry> getRecentEntries(int limit) {
        return entriesList.stream()
                .sorted((e1, e2) -> e2.getTimestamp().compareTo(e1.getTimestamp()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
