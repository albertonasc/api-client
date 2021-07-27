package com.alberto.clientapi.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alberto.clientapi.dto.ClientDTO;
import com.alberto.clientapi.entities.Client;
import com.alberto.clientapi.repositories.ClientRepository;
import com.alberto.clientapi.services.exceptions.DataBaseException;
import com.alberto.clientapi.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> list = repository.findAll(pageable);
		
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> object = repository.findById(id);
		
		Client entity = object.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);

			return new ClientDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client entity) {		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
			
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

}
