package com.newsmanager.web.dto;

public final class TopTenLabelDto {

    private final String labelName;
    private final int occurrences;

    public TopTenLabelDto(String labelName, int occurrences) {
        this.labelName = labelName;
        this.occurrences = occurrences;
    }

    public String getLabelName() {
        return labelName;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
