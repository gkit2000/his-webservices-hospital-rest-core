package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.api.db.PersonDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernatePersonDAO extends HibernateSingleClassDAO implements PersonDAO {

    public HibernatePersonDAO() {
        super(Person.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Person getPersonByPersonId(Integer personId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("personId", personId))
                .add(Restrictions.eq("voided", false));

        return (Person) criteria.uniqueResult();
    }

}
