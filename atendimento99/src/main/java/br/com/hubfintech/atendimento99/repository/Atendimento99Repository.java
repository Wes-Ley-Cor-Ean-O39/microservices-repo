package br.com.hubfintech.atendimento99.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hubfintech.atendimento99.model.Atendimento99Model;

@Repository
public interface Atendimento99Repository extends CrudRepository<Atendimento99Model, Long>{

	@Query(value = "select distinct ac.id, ad.document, ac.email, ac.mobile_phone from Processadora.accounts.Person ap \r\n" + 
			" inner join Processadora.accounts.Contact ac on ac.id = ap.contact_id \r\n" + 
			" inner join Processadora.accounts.Document ad on ad.id = ap.identifier_document_id \r\n" + 
			" inner join Processadora.ledger.accounts la on la.person_id = ap.id\r\n" + 
			"where ad.document = :document\r\n" + 
			"order by ad.document desc\r\n" + 
			"", nativeQuery = true)
	public List<Atendimento99Model> findDocument(@Param("document") String document);
	
}
