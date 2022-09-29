package io.github.martnsbreno.exception.PedidoNaoEncontradoException;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}
