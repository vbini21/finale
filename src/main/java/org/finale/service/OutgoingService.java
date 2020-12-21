package org.finale.service;

import com.github.adminfaces.persistence.model.Filter;
import com.github.adminfaces.persistence.service.CrudService;
import com.github.adminfaces.template.exception.BusinessException;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.finale.model.Outgoing;
import org.finale.model.Outgoing_;
import org.finale.bean.AppLists;
import org.finale.repository.OutgoingRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import static com.github.adminfaces.template.util.Assert.has;


 
@Stateless
public class OutgoingService extends CrudService<Outgoing, Long> implements Serializable {

    @Inject
    protected OutgoingRepository outgoingRepository;// you can use repositories to extract complex queries from your service

    @Inject
    protected AppLists appLists;

    @Override
    public void afterAll(Outgoing outgoing) {
        appLists.clearOutgoings();//invalidate Outgoing list cache
    }

    /** 
     * This method is used for (real) pagination and is called by lazy 
     * PrimeFaces datatable on list page
     *
     * @param filter holds restrictions populated on the list page
     * @return a criteria populated with given restrictions 
     */ 
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected Criteria<Outgoing, Outgoing> configRestrictions(Filter<Outgoing> filter) {
        Criteria<Outgoing, Outgoing> criteria = criteria();
        
        if (filter.hasParam(Outgoing_.id.getName())) {
            criteria.eq(Outgoing_.id, filter.getLongParam(Outgoing_.id.getName()));   
        }  
        if (filter.hasParam(Outgoing_.data.getName())) {
            criteria.eq(Outgoing_.data, (String)filter.getParam(Outgoing_.data.getName()));   
        }  

        //create restrictions based on filter entity
        if (has(filter.getEntity())) {
            Outgoing filterEntity = filter.getEntity();
            
	        if (has(filterEntity.getId())) {
                 criteria.eq(Outgoing_.id, filterEntity.getId());   
	        }  
	        if (has(filterEntity.getData())) {
                 criteria.eq(Outgoing_.data, filterEntity.getData());   
	        }  
        }
        return criteria;
    }
    
    public void beforeInsert(Outgoing outgoing) {
        validate(outgoing);
    }

    public void beforeUpdate(Outgoing outgoing) {
        validate(outgoing);
    }


    public void validate(Outgoing outgoing) {
        BusinessException be = new BusinessException();

        /** just an example of validation
        if (!car.hasModel()) {
            be.addException(new BusinessException("Car model cannot be empty"));
        }
        if (!car.hasName()) {
            be.addException(new BusinessException("Car name cannot be empty"));
        }

        if (!has(car.getPrice())) {
            be.addException(new BusinessException("Car price cannot be empty"));
        } 

        if (count(criteria()
                .eqIgnoreCase(Car_.name, car.getName())
                .notEq(Car_.id, car.getId())) > 0) {

            be.addException(new BusinessException("Car name must be unique"));
        }
        **/

        //if there is exceptions enqueued then throw them. Each business exception will be transformed into a JSF error message and displayed on the page
        if (has(be.getExceptionList())) {
            throw be;
        }
    }


 
}
