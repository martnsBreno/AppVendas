package io.github.martnsbreno.Service.PedidoServiceImpl;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha Inválida");
    }
}
