package br.com.hubfintech.atendimento99;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.hubfintech.atendimento99.service.Atendimento99Service;

@SpringBootApplication
public class Atendimento99Application {

	public static void main(String[] args) {
		SpringApplication.run(Atendimento99Application.class, args);
	}
	
	@Autowired
	Atendimento99Service service;

	public void run(String... args) throws IOException, ClassNotFoundException, SQLException {
		service.insertDadosPEC();
	}

}
