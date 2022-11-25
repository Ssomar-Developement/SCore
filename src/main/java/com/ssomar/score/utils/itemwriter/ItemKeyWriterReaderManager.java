package com.ssomar.score.utils.itemwriter;

import lombok.Getter;

public class ItemKeyWriterReaderManager {

    private static ItemKeyWriterReaderManager instance;

    @Getter
    private ItemKeyWriterReader itemKeyWriterReader;


    public ItemKeyWriterReaderManager() {
        itemKeyWriterReader = ItemKeyWriterReader.init();
    }

    public static ItemKeyWriterReaderManager getInstance() {
        if (instance == null) instance = new ItemKeyWriterReaderManager();
        return instance;
    }
}
