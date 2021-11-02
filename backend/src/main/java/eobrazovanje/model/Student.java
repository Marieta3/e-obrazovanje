package eobrazovanje.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("student")
public class Student extends User{

    private static final long serialVersionUID = 1L;

    public Student() {
        super();
    }
}
