package services;

import classes.Cargo;
import classesDAO.CargoDAO;
import java.util.List;

public class CargoService {

    private final CargoDAO cargoDAO;

    public CargoService() {
        this.cargoDAO = new CargoDAO();
    }

    public void salvarCargo(Cargo cargo) {
        validarCargo(cargo);
        cargoDAO.salvar(cargo);
    }

    public void atualizarCargo(Cargo cargo) {
        if (cargo == null || cargo.getId() == null) {
            throw new IllegalArgumentException("Cargo inválido para atualização!");
        }
        validarCargo(cargo);
        cargoDAO.atualizar(cargo);
    }

    public List<Cargo> listarCargos() {
        return cargoDAO.listarCargos();
    }

    public Cargo buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID inválido para busca de cargo!");
        }
        return cargoDAO.buscarPorId(id);
    }

    public void excluirCargo(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID inválido para exclusão de cargo!");
        }
        cargoDAO.excluir(id);
    }

    private void validarCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("Cargo não pode ser nulo!");
        }

        if (cargo.getFuncao() == null || cargo.getFuncao().isBlank()) {
            throw new IllegalArgumentException("A função do cargo é obrigatória!");
        }

        if (cargo.getSalario() == null || cargo.getSalario() <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero!");
        }
    }
}
