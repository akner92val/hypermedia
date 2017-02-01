package com.mlavrenko.api.client;

import com.mlavrenko.api.dto.PersonDto;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/hypermedia")
public interface PersonApi {
    @RequestMapping(method = RequestMethod.GET, value = "/person/{personId}", produces = MediaTypes.HAL_JSON_VALUE)
    Resource<PersonDto> getPersonById(@PathVariable("personId") Long personId);
}
