package br.com.diih.service;

import br.com.diih.exceptions.ResourceNotFoundException;
import br.com.diih.model.Person;
import br.com.diih.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public List<Person> findByAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding person by Id!");
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));
    }

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Record found for this ID"));

        personRepository.delete(entity);
    }
}
