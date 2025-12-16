package com.renanresende.bridgetotalk.adapter.out.jpa.spec;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentFilter;
import com.renanresende.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AgentSpecification {


    /**
     * Implementa a Specification para os filtros opcionais.
     * @param filter O record AgentFilter contendo os Optionals.
     * @return Uma Specification que combina os filtros opcionais.
     */
    public static Specification<AgentJpaEntity> withOptionalFiltersByCompany(AgentFilter filter, UUID companyId) {

        // implementa o toPredicate da interface Specification
        return (root, query, criteriaBuilder) -> {

            var predicates = new ArrayList<Predicate>();


            if(filter.findInactiveAgents()){
                predicates.add(criteriaBuilder.isNotNull(root.get("deletedAt")));
            }else{
                predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            }

            //by company
            predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));

            filter.status()
                  .filter(status -> Objects.nonNull(status))
                  .ifPresent(status ->
                          predicates.add(criteriaBuilder.equal(root.get("status"), status))
                  );

            filter.name()
                    .filter(s -> !s.isBlank())
                    .ifPresent(name -> {
                        var namePattern = "%" + name.toLowerCase() + "%";
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                namePattern
                        ));
                    });

            filter.email()
                    .filter(s -> !s.isBlank())
                    .ifPresent(email -> {
                        var emailPattern = "%" + email.toLowerCase() + "%";
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                emailPattern
                        ));
                    });

            // Se não há filtros, retorna TRUE para não impactar o resultado
            if (predicates.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            // Combina todos os predicados opcionais com AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}