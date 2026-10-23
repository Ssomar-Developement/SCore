package com.ssomar.score.utils.itemwriter;

import com.ssomar.score.SCore;
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

    /**
     * Re-initializes the writer/reader with the chosen storage backend.
     *
     * <p>Call this after the plugin configuration is loaded so the chosen
     * backend takes effect before any item is read or written.
     *
     * @param forceNBT when {@code true}, raw NBT tags are used for storage
     *                 even on 1.13+ servers; when {@code false} (default) the
     *                 Persistent Data Container is used on 1.13+.
     */
    public void reinit(boolean forceNBT) {
        if (SCore.is1v13Less()) {
            // Pre-1.13 can only use the legacy NBT path regardless of the setting.
            itemKeyWriterReader = new NBTWriterReader();
        } else {
            itemKeyWriterReader = forceNBT ? new NBTWriterReader() : new NameSpaceKeyWriterReader();
        }
    }
}
