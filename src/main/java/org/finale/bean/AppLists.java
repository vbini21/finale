/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.finale.bean;

import com.github.adminfaces.persistence.service.CrudService;
import com.github.adminfaces.persistence.service.Service;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.finale.model.Outgoing;
import javax.enterprise.inject.Produces;

/**
 *
 * Cache for lists used across multiple pages
 */
@Named
@ApplicationScoped
public class AppLists implements Serializable {

	@Inject
	@Service
	private CrudService<Outgoing, Long> outgoingService;
	private Set<Outgoing> outgoings;

	@Produces
	@Named("allOutgoings")
	public Set<Outgoing> allOutgoings() {
		if (outgoings == null) {
			outgoings = new HashSet<>(outgoingService.criteria()
					.getResultList());
		}
		return outgoings;
	}

	public void clearOutgoings() {
		outgoings = null;
	}

}
