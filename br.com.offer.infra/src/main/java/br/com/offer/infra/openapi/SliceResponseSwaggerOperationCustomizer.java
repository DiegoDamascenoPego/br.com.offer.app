package br.com.offer.infra.openapi;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Slice;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

@Configuration
@ConditionalOnClass({ SpringDocUtils.class })
public class SliceResponseSwaggerOperationCustomizer implements GlobalOperationCustomizer {
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        if (handlerMethod.getMethod().getReturnType().isAssignableFrom(Slice.class)) {
            Schema<?> schema = this.getSchema(handlerMethod.getMethod());
            Content content = (Content) Objects.requireNonNullElse(
                ((ApiResponse) operation.getResponses().getOrDefault("200", new ApiResponse())).getContent(), new Content());
            content.keySet().forEach((mediaTypeKey) -> {
                MediaType mediaType = (MediaType) content.get(mediaTypeKey);
                mediaType.schema(this.customizeSchema(schema));
            });
        }

        return operation;
    }

    private Schema<?> getSchema(Method method) {
        Type typeArgument = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
            .resolveAsResolvedSchema((new AnnotatedType(typeArgument)).resolveAsRef(false));
        return ((ResolvedSchema) Objects.requireNonNullElse(resolvedSchema, new ResolvedSchema())).schema;
    }

    private Schema<?> customizeSchema(Schema<?> objectSchema) {
        Schema<?> wrapperSchema = new Schema();
        wrapperSchema.addProperty("hasNext", (new BooleanSchema())._default(true));
        wrapperSchema.addProperty("items",
            (new ArraySchema()).items((Schema) Objects.requireNonNullElse(objectSchema, new ObjectSchema())));
        return wrapperSchema;
    }
}
