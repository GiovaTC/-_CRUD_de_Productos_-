package service;

import model.Producto;
import java.util.List;

public interface ProductoService {

    List<Producto> listar();

    void guardar(Producto producto);

    Producto buscar(Integer id);

    void eliminar(Integer id);
}
