package org.finale.bean;

import com.github.adminfaces.persistence.bean.CrudMB;
import com.github.adminfaces.persistence.util.Messages;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import org.finale.model.Outgoing;
import org.finale.service.OutgoingService;

import static com.github.adminfaces.persistence.util.Messages.addDetailMessage;
import static com.github.adminfaces.persistence.util.Messages.getMessage;
import static com.github.adminfaces.template.util.Assert.has;
import java.util.*;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class OutgoingListMB extends CrudMB<Outgoing> implements Serializable {

    @Inject
    OutgoingService outgoingService;

    @Inject
    public void initService() {
        setCrudService(outgoingService);
    }

    public void delete() {
        int deletedEntities = 0;
        for (Outgoing selected : selectionList) {
        	deletedEntities++;
        	outgoingService.remove(selected);
        }
        selectionList.clear();
        addDetailMessage(deletedEntities + " outgoing(s) deleted successfully!");
        clear();
    }

    /**
     * Used in datatable footer to display current search criteria
     */
    public String getSearchCriteria() {
        StringBuilder sb = new StringBuilder(21);
        Outgoing outgoingFilter = filter.getEntity();
 
        Long id = null;
        if (filter.hasParam("id")) {
            id = filter.getLongParam("id");
        } else if (has(outgoingFilter.getId())) {
            id = outgoingFilter.getId();
        }
        if (has(id)) {
  
	        sb.append("<b>id</b>: ").append(id).append(", ");
        }
 
        String data = null;
        if (filter.hasParam("data")) {
            data = (String)filter.getParam("data");
        } else if (has(outgoingFilter.getData())) {
            data = outgoingFilter.getData();
        }
        if (has(data)) {
  
	        sb.append("<b>data</b>: ").append(data).append(", ");
        }
        int commaIndex = sb.lastIndexOf(",");
        if (commaIndex != -1) {
            sb.deleteCharAt(commaIndex);
        }
        if (sb.toString().trim().isEmpty()) {
            PrimeFaces.current().executeScript("jQuery('div[id=footer] .fa-filter').addClass('ui-state-disabled')");
            return getMessage("empty-search-criteria");
        } else {
            PrimeFaces.current().executeScript("jQuery('div[id=footer] .fa-filter').removeClass('ui-state-disabled')");
        }
        return sb.toString();
    }

}