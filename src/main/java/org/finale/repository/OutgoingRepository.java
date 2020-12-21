package org.finale.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.finale.model.Outgoing;

@Repository
public interface OutgoingRepository extends EntityRepository<Outgoing,Long> {

}