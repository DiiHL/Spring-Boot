package br.com.diih.service;

import br.com.diih.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person create(Person person) {
        logger.info("Creating one Person!");

        return person;
    }

    public Person update(Person person) {
        logger.info("Updating one Person!");
        return person;
    }

    public void delete(String id) {
        logger.info("Deleting one Person!");
    }

    public List<Person> findByAll() {
        logger.info("Finding all people!");
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);

        }
        return persons;
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName " + i);
        person.setLastName("LastName " + i);
        person.setAddress("Some Address brazil");
        if (i % 2 == 0) {
            person.setGender("Masc");
        } else {
            person.setGender("Fem");
        }
        return person;
    }

    public Person findById(String id) {
        logger.info("Finding person by Id!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Diego");
        person.setLastName("Lopes");
        person.setGender("Masc");
        person.setAddress("Bahia - Salvador - Brazil");

        return person;
    }
}
