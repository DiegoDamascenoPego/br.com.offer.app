package br.com.offer.infra.web;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Slice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonComponent
public class SliceJsonSerializer extends StdSerializer<Slice<?>> {

    protected SliceJsonSerializer() {
        super(Slice.class, false);
    }

    @Override
    public void serialize(final Slice<?> objects, final JsonGenerator jsonGenerator,
        final SerializerProvider serializerProvider)
        throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("items");
        jsonGenerator.writeStartArray();

        for (Object item : objects) {
            jsonGenerator.writeObject(item);
        }

        jsonGenerator.writeEndArray();
        jsonGenerator.writeBooleanField("hasNext", objects.hasNext());
        jsonGenerator.writeEndObject();
    }
}
