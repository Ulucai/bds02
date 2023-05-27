package com.devsuperior.bds02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	@Transactional(readOnly=true)
	public List<CityDTO> findAll() {
		List<City> list= repository.findAll(Sort.by("name").ascending());
		return list.stream().map(x-> new CityDTO(x)).toList();
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);
	}
	
	public void delete(Long id) {		
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id "+id+" not Found!");
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation!");
		}				
	}
}
