package app.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Funcionario;
import app.services.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService service;

	@GetMapping
	public List<Funcionario> listarTodos() {
		return service.listarTodos();
	}

	@DeleteMapping("/remover/{nome}")
	public void removerFuncionario(@PathVariable String nome) {
		service.removerFuncionarioPorNome(nome);
	}

	@PutMapping("/aumentarSalario")
	public void aumentarSalario() {
		service.aumentarSalario(BigDecimal.valueOf(0.10));
	}

	@GetMapping("/agruparPorFuncao")
	public Map<String, List<Funcionario>> agruparPorFuncao() {
		return service.agruparPorFuncao();
	}

	@GetMapping("/aniversariantes")
	public List<Funcionario> aniversariantes(@RequestParam List<Integer> meses) {
		return service.aniversariantes(meses.stream().mapToInt(i -> i).toArray());
	}

	@GetMapping("/maisVelho")
	public Funcionario funcionarioMaisVelho() {
		return service.funcionarioMaisVelho();
	}

	@GetMapping("/ordenarAlfabeticamente")
	public List<Funcionario> ordenarAlfabeticamente() {
		return service.ordenarAlfabeticamente();
	}

	@GetMapping("/totalSalarios")
	public BigDecimal totalSalarios() {
		return service.totalSalarios();
	}

	@GetMapping("/salariosMinimos")
	public Map<Funcionario, BigDecimal> salariosMinimos(@RequestParam BigDecimal salarioMinimo) {
		return service.salariosMinimos(salarioMinimo);
	}
}