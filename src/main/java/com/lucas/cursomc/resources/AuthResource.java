package com.lucas.cursomc.resources;

import com.lucas.cursomc.Security.JwtUtil;
import com.lucas.cursomc.Security.UserSS;
import com.lucas.cursomc.dto.EmailDTO;
import com.lucas.cursomc.services.AuthService;
import com.lucas.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@RequestMapping(value="/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/forgot", method=RequestMethod.POST)
	public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO objDTO) {
		authService.sendNewPassword(objDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}