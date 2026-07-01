package com.hemreozalp.event_ticket_reservation_system.event.repository;

import com.hemreozalp.event_ticket_reservation_system.event.dto.EventFilter;
import com.hemreozalp.event_ticket_reservation_system.event.entity.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification {


    public static Specification<Event> filter(EventFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.title() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + filter.title().toLowerCase() + "%"
                        )
                );
            }

            if (filter.location() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("location")),
                                "%" + filter.location().toLowerCase() + "%"
                        )
                );
            }

            if (filter.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice())
                );
            }

            if (filter.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice())
                );
            }

            if (filter.status() != null) {
                predicates.add(
                        cb.equal(root.get("status"), filter.status())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
