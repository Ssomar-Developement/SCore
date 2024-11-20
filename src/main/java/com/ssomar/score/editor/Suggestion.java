package com.ssomar.score.editor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Suggestion {

    private String suggestion;
    private String suggestionDisplay;
    private String suggestionHover;
    private String wikiLink;

    public Suggestion(String suggestion, String suggestionDisplay, String suggestionHover) {
        this.suggestion = suggestion;
        this.suggestionDisplay = suggestionDisplay;
        this.suggestionHover = suggestionHover;
    }

    public Suggestion(String suggestion, String suggestionDisplay, String suggestionHover, String wikiLink) {
        this.suggestion = suggestion;
        this.suggestionDisplay = suggestionDisplay;
        this.suggestionHover = suggestionHover;
        this.wikiLink = wikiLink;
    }
}
