package ru.yandex.practicum.snapshot.kafka;

import org.apache.avro.Schema;
import ru.yandex.practicum.hub.kafka.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public class SnapshotAvroDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {
    public SnapshotAvroDeserializer() {
        super(SensorsSnapshotAvro.getClassSchema());
    }

    public SnapshotAvroDeserializer(Schema schema) {
        super(schema);
    }
}