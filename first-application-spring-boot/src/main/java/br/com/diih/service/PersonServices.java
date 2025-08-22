package br.com.diih.service;

import br.com.diih.data.dto.v1.PersonDTO;
import br.com.diih.data.dto.v2.PersonDTOV2;
import br.com.diih.exceptions.ResourceNotFoundException;
import br.com.diih.mapper.custom.PersonMapper;
import br.com.diih.model.Person;
import br.com.diih.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.diih.mapper.ObjectMapper.parseListObjects;
import static br.com.diih.mapper.ObjectMapper.parseObject;

@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public List<PersonDTO> findByAll() {
        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding person by Id!");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        return parseObject(person, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person!");
        Person entity = parseObject(person, Person.class);
        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
        personRepository.delete(person);
    }

    // v2
//    public PersonDTOV2 create(PersonDTOV2 person) {
//        logger.info("Creating one Person V2!");
//        Person entity = converter.convertDTOToEntity(person);
//        return converter.convertEntityToDTO(personRepository.save(entity));
//    }
}
