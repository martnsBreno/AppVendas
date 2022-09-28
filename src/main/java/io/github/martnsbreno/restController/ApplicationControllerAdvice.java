package io.github.martnsbreno.restController;

import io.github.martnsbreno.exception.PedidoNaoEncontradoException.PedidoNaoEncontradoException;
import io.github.martnsbreno.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrors handleRegraNegocioException(RegraNegocioException ex) {
        String mensagemErro = ex.getMessage();
        return new APIErrors(mensagemErro);
    }
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIErrors pedidoNaoEncontradoException(PedidoNaoEncontradoException pedidoNaoEncontradoException) {
        return new APIErrors(pedidoNaoEncontradoException.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> error = ex.getBindingResult().getAllErrors()
                .stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        return new APIErrors(error);
    }
}
