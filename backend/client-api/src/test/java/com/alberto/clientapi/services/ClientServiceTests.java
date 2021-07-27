package com.alberto.clientapi.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alberto.clientapi.dto.ClientDTO;
import com.alberto.clientapi.entities.Client;
import com.alberto.clientapi.repositories.ClientRepository;
import com.alberto.clientapi.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ClientServiceTests {
	
	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private Client client;
	private PageImpl<Client> page;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistingId = 2L;
		client = createClient();
		page = new PageImpl<>(List.of(client));
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 6);
		
		when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		
		Page<ClientDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdShouldReturnClientDTOWhenIdExist() {
		
		when(repository.findById(existingId)).thenReturn(Optional.of(client));
		
		ClientDTO clientDTO = service.findById(existingId);
		
		Assertions.assertNotNull(clientDTO);
		verify(repository, times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldReturnThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		
		verify(repository, times(1)).findById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExist() {
		
		doNothing().when(repository).deleteById(existingId);
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		verify(repository, times(1)).deleteById(existingId);
	}
	
	private Client createClient() {
		return new Client(1L, "Maria", "12345678910", 5.000, Instant.parse("2018-07-05T03:00:00Z"), 2);
	}
}
