package app.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Funcionario;
import app.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	public List<Funcionario> listarTodos() {
		return repository.findAll();
	}

	public void removerFuncionarioPorNome(String nome) {
		List<Funcionario> funcionarios = repository.findAll();
		funcionarios.stream().filter(funcionario -> funcionario.getNome().equals(nome)).findFirst()
				.ifPresent(repository::delete);
	}

	public void aumentarSalario(BigDecimal percentual) {
		List<Funcionario> funcionarios = repository.findAll();
		funcionarios.forEach(funcionario -> {
			BigDecimal novoSalario = funcionario.getSalario().multiply(BigDecimal.ONE.add(percentual));
			funcionario.setSalario(novoSalario);
		});
		repository.saveAll(funcionarios);
	}

	public Map<String, List<Funcionario>> agruparPorFuncao() {
		return repository.findAll().stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
	}

	public List<Funcionario> aniversariantes(int[] meses) {
		return repository.findAll().stream().filter(funcionario -> {
			int mes = funcionario.getDataNascimento().getMonthValue();
			for (int m : meses) {
				if (mes == m)
					return true;
			}
			return false;
		}).collect(Collectors.toList());
	}

	public Funcionario funcionarioMaisVelho() {
		return repository.findAll().stream().min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
				.orElse(null);
	}

	public List<Funcionario> ordenarAlfabeticamente() {
		return repository.findAll().stream().sorted((f1, f2) -> f1.getNome().compareTo(f2.getNome()))
				.collect(Collectors.toList());
	}

	public BigDecimal totalSalarios() {
		return repository.findAll().stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Map<Funcionario, BigDecimal> salariosMinimos(BigDecimal salarioMinimo) {
		return repository.findAll().stream().collect(Collectors.toMap(funcionario -> funcionario,
				funcionario -> funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP)));
	}

}