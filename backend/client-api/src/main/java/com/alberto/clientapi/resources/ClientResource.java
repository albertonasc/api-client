package com.alberto.clientapi.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alberto.clientapi.entities.Client;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list = new ArrayList<>();
		list.add(new Client(1L, "Ana", "789724389", 3500.00, Instant.now(), 2));
		list.add(new Client(2L, "Maria", "867878687", 10100.50, Instant.now(), 1));
		
		return ResponseEntity.ok().body(list);
	}

}
