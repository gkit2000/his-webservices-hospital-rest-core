package org.openmrs.module.hospitalrestcore.inventory.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalrestcore.OpenmrsCustomConstants;
import org.openmrs.module.hospitalrestcore.ResourceNotFoundException;
import org.openmrs.module.hospitalrestcore.api.db.hibernate.HibernateSingleClassDAO;
import org.openmrs.module.hospitalrestcore.inventory.api.db.PersonNameDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * @author Mujuzi Moses
 *
 */

public class HibernatePersonNameDAO extends HibernateSingleClassDAO implements PersonNameDAO {

    public HibernatePersonNameDAO() {
        super(PersonName.class);
    }

    @Override
    public List<PersonName> listAllPersonName() throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("voided", false)); //TODO: remove restriction
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonName getPersonNameByPersonId(Integer personId) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass, "personName")
                .createAlias("personName.person", "person");
        criteria.add(Restrictions.eq("person.personId", personId))
                .add(Restrictions.eq("voided", false));
        return (PersonName) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true)
    public PersonName getPersonNameByNameString(String name) throws DAOException {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(mappedClass);
        Criterion isGivenName = Restrictions.eq("givenName", name).ignoreCase();
        Criterion isFamilyName = Restrictions.eq("familyName", name).ignoreCase();
        criteria.add(Restrictions.or(isGivenName, isFamilyName))
                .add(Restrictions.eq("voided", false)).setMaxResults(1);

        PersonName personName = (PersonName) criteria.uniqueResult();

        if (personName == null) {
            Criteria criteria2 = sessionFactory.getCurrentSession().createCriteria(mappedClass);
            criteria2.add(Restrictions.like("voided", false));

            ProjectionList proList = Projections.projectionList();
            proList.add(Projections.property("givenName"))
                    .add(Projections.property("middleName"))
                    .add(Projections.property("familyName"))
                    .add(Projections.property("person"));

            criteria2.setProjection(proList);
            List<Object> list = criteria2.list();
            if (list == null || list.size() == 0)
                return null;

            for (Object o : list) {
                Object[] row = (Object[]) o;
                String givenName = (String) row[0];
                String middleName = (String) row[1];
                String familyName = (String) row[2];

                String fullName = givenName + (middleName != null ? " " + middleName + " " : " ") + familyName;
                String givenFamilyName = givenName + " " + familyName;

                if (fullName.toLowerCase().contains(name.toLowerCase()) ||
                        givenFamilyName.toLowerCase().contains(name.toLowerCase())) {
                    Person person = (Person) row[3];
                    return getPersonNameByPersonId(person.getPersonId());
                }
            }
            throw new ResourceNotFoundException(String.format(
                    OpenmrsCustomConstants.VALIDATION_ERROR_NOT_VALID_PERSON_NAME, name));
        }
        else {
            return personName;
        }
    }
}
