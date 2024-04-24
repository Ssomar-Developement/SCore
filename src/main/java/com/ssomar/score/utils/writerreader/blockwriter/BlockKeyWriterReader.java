package com.ssomar.score.utils.writerreader.blockwriter;

import com.ssomar.score.splugin.SPlugin;
import com.ssomar.score.utils.writerreader.WriterReaderPersistentDataContainer;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;

public class BlockKeyWriterReader extends WriterReaderPersistentDataContainer {
    PersistentDataContainer persistentDataContainer;

    public BlockKeyWriterReader(SPlugin sPlugin, Block block) {
        //this.persistentDataContainer = new CustomBlockData(block, sPlugin.getPlugin());
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return persistentDataContainer;
    }
}
