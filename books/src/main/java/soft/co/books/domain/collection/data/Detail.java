package soft.co.books.domain.collection.data;

import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A detail.
 */
public class Detail implements Serializable {

    @NotNull(message = Constants.ERR_NOT_NULL)
    private DocumentSale document;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int cant;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double mount;

    public Detail() {
    }

    public DocumentSale getDocument() {
        return document;
    }

    public void setDocument(DocumentSale document) {
        this.document = document;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }
}
