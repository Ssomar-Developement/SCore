package com.ssomar.score.config;

public enum Locale {

    //locale.equals("FR")
    //                || locale.equals("EN")
    //                || locale.equals("ES")
    //                || locale.equals("HU")
    //                || locale.equals("ptBR")
    //                || locale.equals("DE")
    //                || locale.equals("UK")
    //                || locale.equals("RU")
    //                || locale.equals("ZH"

    EN("English"), // English
    FR("Français"), // French
    DE("Deutsch"), // German
    ES("Español"), // Spanish
    //HU,
    //ptBR,
    //UK,
    RU("Pусский язык"), // Russian;
    ZH("中文"), // Chinese;
    ID("Bahasa Indonesia"), // Indonesian;
    AR("العربية"), // Arabic
    ;

    private String name;

    Locale(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
