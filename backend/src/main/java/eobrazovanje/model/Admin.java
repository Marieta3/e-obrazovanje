package eobrazovanje.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{

    private static final long serialVersionUID = 1L;

    public Admin() {
        super();
    }
}
