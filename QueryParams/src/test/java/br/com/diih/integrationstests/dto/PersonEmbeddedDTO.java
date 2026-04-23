package br.com.diih.integrationstests.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("peolpe")
    private PersonEmbeddedDTO person;

    List<PersonDTO> persons;

    public PersonEmbeddedDTO() {
    }

    public PersonEmbeddedDTO getPerson() {
        return person;
    }

    public void setPerson(PersonEmbeddedDTO person) {
        this.person = person;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
