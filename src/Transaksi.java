// Kelas Transaksi untuk merepresentasikan informasi transaksi pembelian barang
public class Transaksi {
    private String namaPelanggan;
    private String noHp;
    private String namaBarang;
    protected double hargaBarang;
    protected int jumlahBeli;
    private String namaSuperMarket = "asvrn market";
    private String alamat;
    private String kodeBarang;
    protected String kasir = "Anies Baswedan";
    protected int transactionID;

    // Konstruktor untuk membuat objek Transaksi dengan informasi pembelian barang
    public Transaksi(String namaPelanggan, String noHp, String alamat, String kodeBarang, String namaBarang,
            double hargaBarang, int jumlahBeli) {
        this.namaPelanggan = namaPelanggan; //Menginisialisasi atribut namaPelanggan dari objek Transaksi dengan nilai yang diterima sebagai parameter namaPelanggan
        this.noHp = noHp;
        this.alamat = alamat;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.setJumlahBeli(jumlahBeli); //Memanggil metode setJumlahBeli(int jumlahBeli) untuk mengatur nilai atribut jumlahBeli dari objek Transaksi. 
        //Metode ini juga melakukan validasi apakah jumlah beli tidak lebih dari 1000, dan jika iya, akan melempar IllegalArgumentException.
    }

    // Konstruktor default
    public Transaksi() {
    }

    // Setter untuk mengatur jumlah beli dengan validasi
    public void setJumlahBeli(int jumlahBeli) {
        if (jumlahBeli > 1000) {
            throw new IllegalArgumentException("Jumlah beli tidak boleh lebih dari 1000");
        }
        this.jumlahBeli = jumlahBeli;
    }

    // Metode untuk menampilkan sambutan
    public void greeting() {
        System.out.println("SELAMAT DATANG DI " + namaSuperMarket.toUpperCase());
        System.out.println("=========================");
        System.out.println("Log in");
    }

    // Getter untuk mendapatkan nama pelanggan
    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    // Getter untuk mendapatkan nomor HP
    public String getNoHp() {
        return noHp;
    }

    // Getter untuk mendapatkan alamat
    public String getAlamat() {
        return alamat;
    }

    // Getter untuk mendapatkan kode barang
    public String getKodeBarang() {
        return kodeBarang;
    }

    // Getter untuk mendapatkan nama barang
    public String getNamaBarang() {
        return namaBarang;
    }

    // Getter untuk mendapatkan harga barang
    public double getHargaBarang() {
        return hargaBarang;
    }

    // Setter untuk mengatur ID transaksi
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    // Metode untuk menampilkan detail transaksi
    public void tampilkanDetailTransaksi() {
        System.out.println("DATA PELANGGAN");
        System.out.println("---------------------------");
        System.out.println("Nama Pelanggan\t: " + namaPelanggan);
        System.out.println("No HP\t\t: " + noHp);
        System.out.println("Alamat\t\t: " + alamat);
        System.out.println("+++++++++++++++++++++++++++");
        System.out.println("DATA PEMBELIAN BARANG");
        System.out.println("---------------------------");
        System.out.println("Kode barang\t: " + kodeBarang);
        System.out.println("Nama Barang\t: " + namaBarang);
        System.out.println("Harga Barang\t: " + hargaBarang);
        System.out.println("Jumlah Beli\t: " + jumlahBeli);
    }
}
