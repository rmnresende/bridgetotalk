package com.renanresende.bridgetotalk.application.mapper;

import com.renanresende.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.renanresende.bridgetotalk.domain.exception.BusinessException;
import com.renanresende.bridgetotalk.domain.Company;
import com.renanresende.bridgetotalk.domain.CompanySettings;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompanyCommandMapper {

    default void updateDomainFromCommand(UpdateCompanyCommand command, Company company) throws BusinessException {
        if ( command == null ) {
            return;
        }

        if ( command.name() != null ) {
            company.setName( command.name() );
        }
        if ( command.slug() != null ) {
            company.setSlug( command.slug() );
        }
        if ( command.email() != null ) {
            company.setEmail( command.email() );
        }
        if ( command.phone() != null ) {
            company.setPhone( command.phone() );
        }
        if ( command.document() != null ) {
            company.setDocument( command.document() );
        }
        if ( command.status() != null ) {
            company.setStatus( command.status() );
        }

        linkBidirectionalReferences( command, company );
    }

    default void linkBidirectionalReferences(UpdateCompanyCommand updateCompanyCommand, @MappingTarget Company existingCompany) throws BusinessException {

        if (updateCompanyCommand.settings() != null) {
            existingCompany.getSettings().applyUpdate(updateCompanyCommand.settings());
        }
    }
}