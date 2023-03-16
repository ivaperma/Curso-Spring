package com.bolsaideas.springboot.error.app.services;

import com.bolsaideas.springboot.error.app.models.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private List<Usuario> lista;

    public UsuarioServiceImpl() {
        this.lista = new ArrayList<>();
        this.lista.add(new Usuario(1, "Ivan", "Perez"));
        this.lista.add(new Usuario(2, "Pipo", "Sanchez"));
        this.lista.add(new Usuario(3, "Brody", "Rodriguez"));
        this.lista.add(new Usuario(4, "Sara", "Blanco"));
        this.lista.add(new Usuario(5, "Laia", "Gutierrez"));
        this.lista.add(new Usuario(6, "Tommy", "Lee"));

    }

    @Override
    public List<Usuario> listar() {
        return this.lista;
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        Usuario resultado = null;
        for (Usuario u : this.lista) {
            if (u.getId().equals(id)) {
                resultado = u;
                break;
            }
        }
        return resultado;
    }

    @Override
    public Optional<Usuario> obtenerPorIdOptional(Integer id) {
        Usuario usuario = this.obtenerPorId(id);
        return Optional.ofNullable(usuario);
    }
}
