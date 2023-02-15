/**
 * Clasa pentru stocarea informatiilor despre comenzi
 */
package Model;

public class Comanda {
    private int id;
    private int id_client;
    private int id_product;
    private int pieces;

    /**
     *  Constructor
     * @param id_client
     * @param id_product
     * @param pieces
     */
    public Comanda( int id_client, int id_product, int pieces) {
        this.id_client = id_client;
        this.id_product = id_product;
        this.pieces = pieces;
    }

    /**
     * getter id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter id client
     * @return
     */
    public int getId_client() {
        return id_client;
    }

    /**
     * setter id client
     * @param id_client
     */
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    public int getId_product() {
        return id_product;
    }
    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
    public int getPieces() {
        return pieces;
    }
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
}
