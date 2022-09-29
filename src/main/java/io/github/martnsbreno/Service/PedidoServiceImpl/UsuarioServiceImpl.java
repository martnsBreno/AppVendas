package io.github.martnsbreno.Service.PedidoServiceImpl;

import io.github.martnsbreno.domain.entity.Usuario;
import io.github.martnsbreno.domain.entity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl  implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public Usuario salvar (Usuario usuario) {
        return repository.save(usuario);
    }

    public UserDetails autenticar( Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean matches = encoder.matches(usuario.getSenha(), user.getPassword());
        if (matches) {
            return user;
        }
        throw new SenhaInvalidaException();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado na base de dados"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
