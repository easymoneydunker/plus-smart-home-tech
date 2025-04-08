package ru.yandex.practicum.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.ProductNotFoundException;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();
    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.resolve(response.status());
        log.error("Feign error: method {}, status {}, cause {}", methodKey, response.status(), response.reason());

        assert httpStatus != null;
        return switch (httpStatus) {
            case NOT_FOUND -> new ProductNotFoundException("product not found");
            case BAD_REQUEST -> new BadRequestException("Bad Request");
            case INTERNAL_SERVER_ERROR -> new InternalServerErrorException("Internal Server Error");
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
}