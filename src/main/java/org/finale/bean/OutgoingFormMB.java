/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finale.bean;

import com.github.adminfaces.persistence.bean.CrudMB;
import org.finale.model.Outgoing;
import org.finale.service.OutgoingService;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import javax.inject.Named;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import static com.github.adminfaces.persistence.util.Messages.addDetailMessage;
import static com.github.adminfaces.persistence.util.Messages.getMessage;

import java.util.*;


@Named
@ViewScoped
public class OutgoingFormMB extends CrudMB<Outgoing> implements Serializable {

    @Inject
    OutgoingService outgoingService;

    @Inject
    public void initService() {
        setCrudService(outgoingService);
    }

    @Override
    public void afterRemove() {
        try {
            super.afterRemove();//adds remove message
            Faces.redirect("outgoing/outgoing-list.xhtml");
            clear(); 
            sessionFilter.clear(OutgoingFormMB.class.getName());//removes filter saved in session for this bean.
        } catch (Exception e) {
            log.log(Level.WARNING, "",e);
        }
    }

 

}
