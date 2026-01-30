package br.com.diih.service;

import br.com.diih.controllers.PersonController;
import br.com.diih.data.dto.v1.PersonDTO;
import br.com.diih.exceptions.RequiredObjectNullException;
import br.com.diih.exceptions.ResourceNotFoundException;
import br.com.diih.model.Person;
import br.com.diih.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.diih.mapper.ObjectMapper.parseListObjects;
import static br.com.diih.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public List<PersonDTO> findByAll() {
        List<PersonDTO> personDTOS = parseListObjects(personRepository.findAll(), PersonDTO.class);
        personDTOS.forEach(this::addHateoasLink);

        return personDTOS;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by Id!");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        PersonDTO dto = parseObject(person, PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) throw new RequiredObjectNullException();

        logger.info("Creating one Person!");
        Person entity = parseObject(person, Person.class);
        PersonDTO dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) throw new RequiredObjectNullException();

        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        PersonDTO dto = parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLink(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        personRepository.delete(person);
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Disabling one Person!");
        personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        personRepository.disablePerson(id);

        Person entity = personRepository.findById(id).get();
        PersonDTO personDTO = parseObject(entity, PersonDTO.class);
        addHateoasLink(personDTO);
        return personDTO;
    }

    private void addHateoasLink( PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).findByAll()).withRel("FindAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("Create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("Update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("Disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("Delete").withType("DELETE"));
    }

    // v2
/*    public PersonDTOV2 create(PersonDTOV2 person) {
//        logger.info("Creating one Person V2!");
//        Person entity = converter.convertDTOToEntity(person);
//        return converter.convertEntityToDTO(personRepository.save(entity));
}*/
}
