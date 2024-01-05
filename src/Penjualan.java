// Kelas Penjualan merupakan turunan dari kelas Transaksi dan mengimplementasikan interface Pembayaran
public class Penjualan extends Transaksi implements Pembayaran {

    // Konstruktor untuk membuat objek Penjualan dengan parameter yang diberikan
    public Penjualan(String namaPelanggan, String noHp, String alamat, String kodeBarang, String namaBarang,
            double hargaBarang, int jumlahBeli) {
        // Memanggil konstruktor kelas induk (Transaksi) dengan menggunakan keyword super
        super(namaPelanggan, noHp, alamat, kodeBarang, namaBarang, hargaBarang, jumlahBeli);
    }

    // Implementasi metode hitungTotalBayar() dari interface Pembayaran
    @Override
    public double hitungTotalBayar() {
        return hargaBarang * getJumlahBeli();
    }

    // Override metode setJumlahBeli() dari kelas dasar (Transaksi)
    @Override
    public void setJumlahBeli(int jumlahBeli) {
        super.setJumlahBeli(jumlahBeli);
    }

    // Mendapatkan nilai jumlahBeli dari kelas dasar (Transaksi)
    public int getJumlahBeli() {
        return super.jumlahBeli;
    }

    // Override metode tampilkanDetailTransaksi() dari kelas dasar (Transaksi)
    @Override
    public void tampilkanDetailTransaksi() {
        // Memanggil metode tampilkanDetailTransaksi() dari kelas dasar (Transaksi)
        super.tampilkanDetailTransaksi();

        // Menampilkan informasi tambahan untuk transaksi penjualan
        System.out.println("Total Bayar\t: " + hitungTotalBayar());
        System.out.println("+++++++++++++++++++++++++++");
        System.out.println("Kasir\t\t: " + kasir);
    }

    //Getter untuk mendapatkan nilai transactionID
    public int getTransactionID() {
        return transactionID;
    }
}
