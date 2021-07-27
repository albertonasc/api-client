package com.alberto.clientapi.services;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alberto.clientapi.dto.ClientDTO;
import com.alberto.clientapi.entities.Client;
import com.alberto.clientapi.repositories.ClientRepository;

@ExtendWith(SpringExtension.class)
public class ClientServiceTests {
	
	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	private Client client;
	private PageImpl<Client> page;
	
	@BeforeEach
	void setUp() {
		client = createClient();
		page = new PageImpl<>(List.of(client));
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 6);
		
		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		
		Page<ClientDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}
	
	private Client createClient() {
		return new Client(1L, "Maria", "12345678910", 5.000, Instant.parse("2018-07-05T03:00:00Z"), 2);
	}
}
