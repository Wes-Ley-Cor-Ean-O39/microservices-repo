package br.com.hubfintech.atendimento99.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Atendimento99Model {

	@Id
	private Long id;

}
