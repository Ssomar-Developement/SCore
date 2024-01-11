package com.ssomar.score.utils.writerreader.entitywriter;

import com.ssomar.score.utils.writerreader.WriterReaderPersistentDataContainer;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;

public class EntityKeyWriterReader extends WriterReaderPersistentDataContainer {
    PersistentDataContainer persistentDataContainer;

    public EntityKeyWriterReader(Entity entity) {
        this.persistentDataContainer = entity.getPersistentDataContainer();
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return persistentDataContainer;
    }
}
