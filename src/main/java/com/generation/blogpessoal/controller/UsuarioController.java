package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios") //endereço do recurso controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService; //onde está o método cadastrar
    
    @Autowired
	private UsuarioRepository usuarioRepository;
    
    @GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(usuarioRepository.findAll());

	}
    
    @PostMapping("/cadastrar") //endpoint para cadastro - libera o uso sem autenticação
    public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario usuario){
        return usuarioService.cadastrarUsuario(usuario)
        		.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta)) //como estamos trabalhando com Optional em cadastrarUsuario, é melhor usar o Map
        		.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    
    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> put(@Valid @RequestBody Usuario usuario) {
    	return usuarioService.atualizarUsuario(usuario)
        		.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
        		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticar(@Valid @RequestBody Optional<UsuarioLogin> usuarioLogin) {
    	return usuarioService.autenticarUsuario(usuarioLogin)
        		.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
        		.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}