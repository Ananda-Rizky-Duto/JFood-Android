package AnandaRizkyDutoPamungkas.jfood_android;

public class Food {
    private int id;
    private String name;
    private int price;
    private String category;
    private Seller seller;

    public Food(int id, String name, Seller seller, int price, String category)
    {
        this.id = id;
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.category = category;
    }

    /*
     * Metode untuk mendapatkan id makanan
     *
     * @return id dari makanan
     */

    public int getId()
    {
        return id;
    }

    /*
     * Metode untuk mendapatkan nama makanan
     *
     * @return name dari makanan
     */

    public String getName()
    {
        return name;
    }

    /*
     * Metode untuk mendapatkan penjual dari makanan
     *
     * @return seller dari penjual
     */

    public Seller getSeller()
    {
        return seller;
    }

    /*
     * Metode untuk mendapatkan harga dari makanan
     *
     * @return price dari makanan
     */

    public int getPrice()
    {
        return price;
    }

    /*
     * Metode untuk mendapatkan kategori dari makanan
     *
     * @return category dari makanan
     */

    public String getCategory()
    {
        return category;
    }

    /*
     * Metode untuk merubah id makanan
     *
     * @return id dari makanan
     */

    public void setId(int id)
    {
        this.id = id;
    }

    /*
     * Metode untuk merubah nama makanan
     *
     * @return name dari makanan
     */

    public void setName(String name)
    {
        this.name = name;
    }

    /*
     * Metode untuk merubah penjual makanan
     *
     * @return seller dari makanan
     */

    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }

    /*
     * Metode untuk merubah harga makanan
     *
     * @return price dari makanan
     */

    public void setPrice(int price)
    {
        this.price = price;
    }

    /*
     * Metode untuk merubah kategori makanan
     *
     * @return category dari makanan
     */

    public void setCategory(String category)
    {
        this.category = category;
    }

    /*
     * Metode untuk menampilkan data
     *
     * @return name dari makanan
     */
}
