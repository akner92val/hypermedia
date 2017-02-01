package com.mlavrenko.api.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.mlavrenko.api.client.PersonApi;
import com.mlavrenko.api.dto.PersonDto;
import com.mlavrenko.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/hypermedia"})
public class PersonController implements PersonApi {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public Resource<PersonDto> getPersonById(@PathVariable("personId") Long personId) {
        return new Resource<>(personService.getById(personId), linkTo(methodOn(getClass()).getPersonById(personId)).withSelfRel());
    }
}
