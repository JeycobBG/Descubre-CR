package cr.ac.una.DescubreCR.service;

import cr.ac.una.DescubreCR.domain.ColaboradorEmpresarial;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IColaboradorEmpresarialServices {
    public void guardar(ColaboradorEmpresarial colab);
    public List getColaboradorEmpresarial();
    boolean eliminar(String codigo);
    Page<ColaboradorEmpresarial> listar(Pageable pageable);
    public ColaboradorEmpresarial getColaboradorEmpresarialPorCode(String ide);
    public ColaboradorEmpresarial buscar(String ide);
}
