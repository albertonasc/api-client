package com.alberto.clientapi.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alberto.clientapi.entities.Client;
import com.alberto.clientapi.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
	}

}
