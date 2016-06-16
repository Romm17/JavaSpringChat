package model.hibernateDAO;

import java.util.List;

/**
 * @author Roman Usik
 */
public interface Dao<E> {

    Integer create(E object);

    E read(String name);

    List getAll();

}
