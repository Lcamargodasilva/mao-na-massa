package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
