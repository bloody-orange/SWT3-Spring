package swt6.spring.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// map 1:1 with one table
@Entity
public class Address implements BaseEntity<AddressId> {
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "zipCode")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "street", column = @Column(name = "street")),
    })
    @EmbeddedId
    private AddressId id;


    @OneToMany(mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Employee> inhabitants = new HashSet<>();

    public Set<Employee> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Set<Employee> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public void addInhabitant(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee was null");
        }
        this.inhabitants.add(employee);
    }

    public void removeInhabitant(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee was null");
        }
        this.inhabitants.remove(employee);
    }

    public AddressId getId() {
        return id;
    }

    @Override
    public void removeDependencies() {
        while (this.getInhabitants().size() > 0) {
            this.removeInhabitant(this.getInhabitants().iterator().next());
        }
    }

    public void setId(AddressId addressId) {
        this.id = addressId;
    }

    public String getZipCode() {
        return id.getZipCode();
    }

    public void setCity(String city) {
        id.setCity(city);
    }

    public String getCity() {
        return id.getCity();
    }

    public void setStreet(String street) {
        id.setStreet(street);
    }

    public String getStreet() {
        return id.getStreet();
    }

    public void setZipCode(String zipCode) {
        id.setZipCode(zipCode);
    }

    public Address() {
    }

    public Address(AddressId id) {
        this.id = id;
    }

    public Address(String zipCode, String city, String street) {
        this.id = new AddressId(zipCode, city, street);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object other) {
        return this == other ||
                other instanceof Address && this.getId().equals(((Address) other).getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
