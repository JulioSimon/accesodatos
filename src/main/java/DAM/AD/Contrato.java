package DAM.AD;

import jakarta.persistence.*;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nif;

    private String adjudicatario;

    private String objeto_generico;

    private String objeto;

    private String fecha_adjudicacion;

    private String importe;

    private String proveedores_consultados;

    private String tipo_contrato;

    /*
     * Constructores: protected por defecto para JPA
     * y public para inicializar Contratos a guardar en la bd
     */
    protected Contrato() {
    }

    public Contrato(
            String nif,
            String adjudicatario,
            String objeto_generico,
            String objeto,
            String fecha_adjudicacion,
            String importe,
            String proveedores_consultados,
            String tipo_contrato) {
        this.nif = nif;
        this.adjudicatario = adjudicatario;
        this.objeto_generico = objeto_generico;
        this.objeto = objeto;
        this.fecha_adjudicacion = fecha_adjudicacion;
        this.importe = importe;
        this.proveedores_consultados = proveedores_consultados;
        this.tipo_contrato = tipo_contrato;
    }

    public long getId() {
        return id;
    }

    public String getNif() {
        return nif;
    }

    public String getAdjudicatario() {
        return adjudicatario;
    }

    public String getObjetoGenerico() {
        return objeto_generico;
    }

    public String getObjeto() {
        return objeto;
    }

    public String getFechaAdjudicacion() {
        return fecha_adjudicacion;
    }

    public String getImporte() {
        return importe;
    }

    public String getProveedoresConsultados() {
        return proveedores_consultados;
    }

    public String getTipoContrato() {
        return tipo_contrato;
    }

    @Override
    public String toString() {
        return "Contrato: ID - " + id + " | Nif - " + nif + " | Adjudicatario - " + adjudicatario
                + " | Objeto Generico - " + objeto_generico + " | Objeto - " + objeto + " | Fecha Adjudicacion - "
                + fecha_adjudicacion + " | Importe - " + importe + " | Proveedores Consultados - "
                + proveedores_consultados + " | Adjudicatario - "
                + adjudicatario + " | Tipo Contrato - " + tipo_contrato + ".";
    }

}
