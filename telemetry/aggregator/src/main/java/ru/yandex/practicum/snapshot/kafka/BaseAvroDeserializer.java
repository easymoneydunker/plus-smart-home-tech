package ru.yandex.practicum.snapshot.kafka;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    static final Logger log = LoggerFactory.getLogger(BaseAvroDeserializer.class);

    final DatumReader<T> reader;
    final DecoderFactory decoder;

    public BaseAvroDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    public BaseAvroDeserializer(DecoderFactory decoderFactory, Schema schema) {
        log.debug("Initializing BaseAvroDeserializer with schema: {}", schema.getFullName());
        reader = new SpecificDatumReader<>(schema);
        decoder = decoderFactory;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            log.warn("Received null data for topic: {}", topic);
            return null;
        }

        log.debug("Deserializing message from topic: {}, data length: {}", topic, data.length);
        try {
            BinaryDecoder d = decoder.binaryDecoder(data, null);
            T record = reader.read(null, d);
            if (record instanceof SensorsSnapshotAvro) {
                Instant timestamp = ((SensorsSnapshotAvro) record).getTimestamp();
                log.debug("Deserialized event with timestamp: {}", timestamp);
            }
            log.debug("Successfully deserialized message from topic: {}", topic);
            return record;
        } catch (Exception e) {
            log.error("Failed to deserialize message from topic: {}", topic, e);
            throw new RuntimeException("Failed to deserialize Avro message", e);
        }
    }
}
