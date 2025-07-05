package com.example.demo.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.modelos.Usuario;

@Controller
public class UsuariosController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/mesas")
	public String mesas() {
		return "mesas";
	}
	@GetMapping("/administrador")
	public String administrador() {
		return "administrador";
	}
	

	@GetMapping("/registro")
	public String showRegister(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}
	
	@PostMapping("/registro")
	public String validarDatos(@ModelAttribute Usuario usuario, Model model) {
	    String nombre = usuario.getNombre();
	    String correo = usuario.getEmail();
	    String password = usuario.getPassword();

	    if ("admin".equals(nombre) && "admin@correo.com".equals(correo) && "12345".equals(password)) {
	        model.addAttribute("nombre", nombre);
	        model.addAttribute("correo", correo);
	        return "ventas";
	        
	    } else if ("mozo".equals(nombre) && "mozo@loloyta.com".equals(correo) && "10".equals(password)) {
	        model.addAttribute("nombre", nombre);
	        return "redirect:/mesas";
	        
	    } else if ("jefe".equals(nombre) && "cocina@loloyta.com".equals(correo) && "11".equals(password)) {
	        model.addAttribute("nombre", nombre);
	        return "redirect:/cocina";
	        
	    } else if ("administrador".equals(nombre) && "admin@admin.com".equals(correo) && "adminpass".equals(password)) {
	        model.addAttribute("nombre", nombre);
	        return "redirect:/administrador"; 
	        
	    } else {
	        model.addAttribute("mensaje", "Correo y contraseña incorrecta");
	        return "registro";
	    }
	}

	
}
