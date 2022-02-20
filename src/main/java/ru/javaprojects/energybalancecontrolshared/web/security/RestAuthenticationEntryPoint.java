package ru.javaprojects.energybalancecontrolshared.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.javaprojects.energybalancecontrolshared.util.exception.ErrorInfo;
import ru.javaprojects.energybalancecontrolshared.web.json.JacksonObjectMapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.javaprojects.energybalancecontrolshared.util.exception.ErrorType.UNAUTHORIZED_ERROR;
import static ru.javaprojects.energybalancecontrolshared.web.security.SecurityConstants.NOT_AUTHORIZED;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ErrorInfo responseEntity = new ErrorInfo(request.getRequestURL(), UNAUTHORIZED_ERROR,
                UNAUTHORIZED_ERROR.getErrorCode(), NOT_AUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = JacksonObjectMapper.getMapper();
        mapper.writeValue(outputStream, responseEntity);
        outputStream.flush();
    }
}