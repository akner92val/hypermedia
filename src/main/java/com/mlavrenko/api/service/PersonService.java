package com.mlavrenko.api.service;

import com.mlavrenko.api.dto.PersonDto;
import com.mlavrenko.api.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDto getById(Long personId) {
        final PersonDto person = new PersonDto();
        BeanUtils.copyProperties(personRepository.findOne(personId), person);
        return person;
    }
}
