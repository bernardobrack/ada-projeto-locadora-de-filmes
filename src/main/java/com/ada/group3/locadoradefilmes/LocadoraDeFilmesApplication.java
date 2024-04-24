package com.ada.group3.locadoradefilmes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;

@SpringBootApplication
public class LocadoraDeFilmesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocadoraDeFilmesApplication.class, args);
		menu();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void menu() {
		Scanner scanner = new Scanner(System.in);
		int opcao;
		do {
			System.out.println("=== ALUGUEL FILMES ===");
			System.out.println("1. Cadastrar usuário");
			System.out.println("2. Alterar usuário cadastrado");
			System.out.println("3. Cadastrar filme");
			System.out.println("4. Listar filmes");
			System.out.println("5. Alugar filme");
			System.out.println("6. Devolver filme");
			System.out.println("7. Sair do programa");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
				case 1:
					// Incluir cadastro usuário;
					break;
				case 2:
					System.out.println("Informe o CPF do usuário que deseja alterar:");
					String cpf = scanner.nextLine();
					// Incluir método alterar usuário;
					break;
				case 3:
					// Incluir cadastro filme;
					break;
				case 4:
					// Incluir listar filme;
					break;
				case 5:
					// Incluir alugar filme;
					break;
				case 6:
					// Incluir devolver filme;
					break;
				case 7:
					System.out.println("Encerrando o programa...");
					break;
				default:
					System.out.println("Opção inválida. Escolha novamente.");
			}
		} while (opcao != 7);

		scanner.close();
	}
}
