package br.com.hubfintech.atendimento99.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.hubfintech.atendimento99.model.Atendimento99Model;
import br.com.hubfintech.atendimento99.repository.Atendimento99Repository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class Atendimento99Service {

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${endereco.final}")
	private String enderecoFinal;

	@Autowired(required = true)
	private Atendimento99Repository atendimentoRepository;

	@PostConstruct
	public void inicializador() throws ClassNotFoundException, SQLException, IOException {
		insertDadosPEC();
	}

	public void insertDadosPEC() throws SQLException, ClassNotFoundException, IOException {

		Class.forName(driverClassName);
		Connection con = DriverManager.getConnection(url, userName, password);

		Statement stm = con.createStatement();

		log.info("Conexao com Banco de Dados Sucesso");

		File arquivos[];
		File diretorio = new File(enderecoFinal);

		log.info("Diretório do arquivo: " + enderecoFinal);

		arquivos = diretorio.listFiles();

		for (File fileprocessing : arquivos) {

			log.info("Arquivo sendo processado: " + fileprocessing.getName());

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(fileprocessing));

			String dados[] = new String[5];
			String linha = br.readLine();

			int flag = 0;

			while (linha != null) {

				flag = 0;

				if (linha.contains("PAYPAXX")) {
					flag = 1;
					linha = br.readLine();
					continue;
				}

				if (linha.contains("CPF")) {
					flag = 1;
					linha = br.readLine();
					continue;
				}

				if (flag == 0) {

					StringTokenizer st = new StringTokenizer(linha, ";");

					dados[0] = st.nextToken();

					List<Atendimento99Model> list99 = atendimentoRepository.findDocument(dados[0]);

					dados[1] = st.nextToken();
					dados[2] = st.nextToken();
					dados[3] = st.nextToken();
					dados[4] = String.valueOf(list99.get(0).getId());

					log.info("Conteúdo a ser alterado para o CPF: " + dados[0] + "{}", linha);

					stm.executeUpdate("update Processadora.accounts.Contact set email = '" + dados[2]
							+ "' , mobile_phone = '" + dados[3] + "' where id = " + dados[4]);

					log.info("Update realizado com Sucesso para o idContact (accounts.Contact): " + dados[4]);

					linha = br.readLine();
				}

			}
		}

	}

}
