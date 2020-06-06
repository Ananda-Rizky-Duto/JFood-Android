package AnandaRizkyDutoPamungkas.jfood_android;

/**
 * Kelas ini digunakan untuk data penjual pada JFood.
 *
 * @author Ananda Rizky Duto Pamungkas
 * @version 27 Februari 2020
 *
 */

public class Seller {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    /**
     * Constructor untuk objek seller
     * @param id
     * @param name
     * @param email
     * @param phoneNumber
     * @param location
     */

    public Seller(int id, String name, String email, String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Metode untuk mendapatkan id penjual
     * @return id dari penjual
     */

    public int getId()
    {
        return id;
    }

    /**
     * Metode untuk mendapatkan nama penjual
     * @return name dari penjual
     */

    public String getName()
    {
        return name;
    }

    /**
     * Metode untuk mendapatkan email penjual
     * @return email dari penjual
     */

    public String getEmail()
    {
        return email;
    }

    /**
     * Metode untuk mendapatkan nomor telfon penjual
     * @return phoneNumber dari penjual
     */

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Metode untuk mendapatkan lokasi penjual
     * @return location dari lokasi
     */

    public Location getLocation()
    {
        return location;
    }

    /**
     * Metode untuk merubah id penjual
     * @param id dari penjual
     */

    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Metode untuk merubah nama penjual
     * @param name dari penjual
     */

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Metode untuk merubah email penjual
     * @param email dari penjual
     */

    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Metode untuk merubah nomor telfon penjual
     * @param phoneNumber  dari penjual
     */

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Metode untuk merubah lokasi penjual
     * @param location dari lokasi
     */

    public void setLocation(Location location)
    {
        this.location = location;
    }
}
