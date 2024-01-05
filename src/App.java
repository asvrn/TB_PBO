import java.util.*; //Mengimport paket-paket yang diperlukan
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//Kelas utama program
public class App {
    private static String namaSuperMarket = "asvrn market"; //Deklarasi variabel-variabel yang digunakan dalam program,
    private static String inputUsername;                    //termasuk nama supermarket,
    private static String username = "Admin";               
    private static String inputPw;
    private static String password = "Admin1";              //informasi autentikasi (username, password, captcha),
    private static String captcha = "12345A";
    private static String inputCapt;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/asvrn_market";
    private static final String DB_USER = "postgres";       //informasi koneksi database (URL, username, password)
    private static final String DB_PASSWORD = "12345678";

    private static Map<Integer, Transaksi> transaksiMap = new HashMap<>(); //deklarasi map
    
    static Date date = new Date(); //Membuat objek Date dan objek SimpleDateFormat untuk menampilkan tanggal dan jam.
    static SimpleDateFormat tanggal = new SimpleDateFormat("E dd/MM/yyyy"); // membuat objek untuk menampilkan tanggal
    static SimpleDateFormat jam = new SimpleDateFormat("hh:mm:ss zzzz"); // membuat objek untuk menampilkan jam

    // Metode utama program
    public static void main(String[] args) throws Exception {
        Transaksi tampil = new Transaksi(); //Instansiasi Objek Transaksi untuk Greeting
        tampil.greeting();
        try {
            try (Scanner scan = new Scanner(System.in)) {
                // Loop untuk autentikasi pengguna
                while (inputUsername != username || inputPw != password) { //Memulai loop untuk autentikasi pengguna. Loop ini berlanjut selama username atau password 
                    System.out.print("\nUsername\t: ");                  //yang dimasukkan tidak sesuai dengan nilai yang telah ditetapkan
                    inputUsername = scan.nextLine();                       //Autentikasi Pengguna
                    System.out.print("Password\t: ");
                    inputPw = scan.nextLine();

                    // Memeriksa username dan password
                    if (inputUsername.equals(username) && inputPw.equals(password)) { //Jika username dan password sesuai, masuk ke dalam blok ini.
                        System.out.println("\nKode Captcha " + captcha);
                        // Loop untuk memasukkan kode captcha
                        while (inputCapt != captcha) {                    //Loop ini berlanjut selama kode captcha yang dimasukkan tidak sesuai dengan nilai captcha yang ditetapkan.
                            System.out.print("Entry Captcha\t:");
                            inputCapt = scan.nextLine();

                            // Memeriksa kevalidan kode captcha
                            if (inputCapt.equals(captcha)) { //jika kode captcha sesuai, masuk ke dalam blok ini.
                                boolean exitProgram = false;
                                // Loop utama program. Program akan berjalan selama pengguna tidak memilih untuk menutup program.
                                while (!exitProgram) {  
                                    System.out.println("\nPilih Menu:");
                                    System.out.println("1. Create Data");
                                    System.out.println("2. Read Data");
                                    System.out.println("3. Read Data by ID");
                                    System.out.println("4. Update Data");
                                    System.out.println("5. Delete Data");
                                    System.out.println("6. Menutup program\n");

                                    // Memilih opsi menu
                                    int choice = scan.nextInt();
                                    scan.nextLine();

                                    switch (choice) { //Switch statement untuk memproses pilihan menu yang dimasukkan oleh pengguna.
                                        case 1:
                                            // Create Data
                                            System.out.print("Masukkan Nama Pelanggan\t\t: ");
                                            String namaPelanggan = scan.nextLine();

                                            System.out.print("Masukkan No HP\t\t\t: ");
                                            String noHp = scan.nextLine();

                                            System.out.print("Masukkan Alamat\t\t\t: ");
                                            String alamat = scan.nextLine();

                                            System.out.print("Masukkan Kode Barang\t\t: ");
                                            String kodeBarang = scan.nextLine();

                                            System.out.print("Masukkan Nama Barang\t\t: ");
                                            String namaBarang = scan.nextLine();

                                            // Menggunakan inputValid untuk memastikan hargaBarang dan jumlahBeli valid
                                            double hargaBarang = 0;
                                            boolean inputValid = false; //penanda untuk menentukan apakah input yang dimasukkan sudah valid atau belum.
                                            // Loop untuk memasukkan hargaBarang yang valid
                                            while (!inputValid) { //Memulai loop yang akan berlanjut selama inputValid bernilai false
                                                try {
                                                    System.out.print("Masukkan Harga Barang\t\t: ");
                                                    hargaBarang = scan.nextDouble();
                                                    scan.nextLine();
                                                    // Membuat objek Transaksi untuk memastikan hargaBarang valid
                                                    new Transaksi("", "", "", namaBarang, namaBarang, hargaBarang, 0); 
                                                    inputValid = true; //Jika berhasil membuat objek tanpa exception, maka inputValid diubah menjadi true, dan loop akan berakhir.
                                                } catch (InputMismatchException | IllegalArgumentException e) { //Jika terjadi exception, program akan menangkap InputMismatchException 
                                                    System.out.println( //(ketika pengguna memasukkan input yang bukan angka) atau IllegalArgumentException
                                                            "Masukan tidak valid. Harga barang harus berupa angka."); //(ketika harga barang tidak valid, misalnya kurang dari nol). 
                                                    scan.nextLine();
                                                }
                                            }

                                            int jumlahBeli;
                                            // Loop untuk memasukkan jumlahBeli yang valid
                                            do {
                                                try {
                                                    System.out.print("Masukkan Jumlah Beli (max 1000)\t: ");
                                                    jumlahBeli = scan.nextInt();
                                                    
                                                    // Validasi jumlahBeli tidak boleh 0 atau negatif
                                                    if (jumlahBeli <= 0) {
                                                        System.out.println("Jumlah beli harus lebih besar dari 0.");
                                                        jumlahBeli = -1;; // Mengatur jumlahBeli menjadi -1 agar loop terus berlanjut
                                                    }
                                                    // Membuat objek Transaksi untuk memastikan jumlahBeli valid
                                                    new Transaksi("", "", "", namaBarang, namaBarang, 0, jumlahBeli); 
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Masukan tidak valid. Jumlah beli harus berupa angka.");
                                                    scan.nextLine(); // Membersihkan buffer input
                                                    jumlahBeli = -1; // Mengatur jumlahBeli menjadi -1 agar loop terus berlanjut
                                                } catch (IllegalArgumentException e) {
                                                    System.out.println(e.getMessage());
                                                    jumlahBeli = -1; // Mengatur jumlahBeli menjadi -1 agar loop terus berlanjut
                                                }
                                            } while (jumlahBeli == -1);

                                            // Membuat objek Penjualan
                                            Penjualan penjualan = new Penjualan(namaPelanggan, noHp, alamat, kodeBarang, namaBarang, hargaBarang, jumlahBeli);
            
                                            // Menyimpan data ke database
                                            saveToDatabase(penjualan);

                                            // Update data di database
                                            updateInDatabase(penjualan);

                                            // Menampilkan detail transaksi
                                            System.out.println("\n" + namaSuperMarket.toUpperCase());
                                            System.out.println("Tanggal\t\t: " + tanggal.format(date));
                                            System.out.println("Waktu\t\t: " + jam.format(date));
                                            System.out.println("===========================\n");
                                            penjualan.tampilkanDetailTransaksi();
                                            break;

                                        case 2:
                                            // Retrieve Data
                                            retrieveFromDatabase();
                                            break;

                                        case 3:
                                            // Retrieve Data by ID
                                            System.out.print("Masukkan ID transaksi yang ingin dilihat: ");
                                            int retrieveTransactionId = scan.nextInt();
                                            scan.nextLine();

                                            // Memeriksa kevalidan ID transaksi
                                            if (isTransactionIdValid(retrieveTransactionId)) { //Memanggil metode untuk memeriksa apakah ID transaksi yang dimasukkan valid
                                                Transaksi retrievedTransaction = getTransactionByIdFromDatabase(retrieveTransactionId); //mengambil objek Transaksi dari database berdasarkan ID transaksi yang dimasukkan.
                                                if (retrievedTransaction != null) { //Jika transaksi berhasil diambil  
                                                    transaksiMap.put(retrieveTransactionId, retrievedTransaction); //objek transaksi tersebut disimpan dalam transaksiMap
                                                    retrieveTransactionById(retrieveTransactionId); //menampilkan detail transaksi dari objek Transaksi yang telah diambil dari database.
                                                } else {
                                                    System.out.println("Transaksi dengan ID " + retrieveTransactionId
                                                            + " tidak ditemukan di database.");
                                                }
                                            } else {
                                                System.out.println("Transaction ID tidak valid atau tidak ditemukan.");
                                            }
                                            break;

                                        case 4:
                                            // // Update Data
                                            System.out.print("Masukkan ID transaksi yang ingin diupdate: ");
                                            int updateTransactionId = scan.nextInt();
                                            scan.nextLine();

                                            // Memeriksa kevalidan ID transaksi
                                            if (isTransactionIdValid(updateTransactionId)) {

                                                System.out.print("Masukkan Nama Pelanggan\t: ");
                                                String updateNamaPelanggan = scan.nextLine();

                                                System.out.print("Masukkan No HP\t\t: ");
                                                String updateNoHp = scan.nextLine();

                                                System.out.print("Masukkan Alamat\t\t: ");
                                                String updateAlamat = scan.nextLine();

                                                System.out.print("Masukkan Kode Barang\t: ");
                                                String updateKodeBarang = scan.nextLine();

                                                System.out.print("Masukkan Nama Barang\t: ");
                                                String updateNamaBarang = scan.nextLine();

                                                System.out.print("Masukkan Harga Barang\t: ");
                                                double updateHargaBarang = scan.nextDouble();

                                                System.out.print("Masukkan Jumlah Beli\t: ");
                                                int updateJumlahBeli = scan.nextInt();

                                                // Membuat objek Penjualan dan update data di database
                                                Penjualan updatePenjualan = new Penjualan(updateNamaPelanggan, updateNoHp, updateAlamat,
                                                        updateKodeBarang, updateNamaBarang, updateHargaBarang, updateJumlahBeli);
                                                updatePenjualan.setTransactionID(updateTransactionId);
                                                updateInDatabase(updatePenjualan);

                                                System.out.println("Data berhasil diupdate di database.");
                                            } else {
                                                System.out.println("Transaction ID tidak valid atau tidak ditemukan.");
                                            }
                                            break;

                                        case 5:
                                            // Delete Data
                                            System.out.print("Masukkan ID transaksi yang ingin dihapus: ");
                                            int deleteTransactionId = scan.nextInt();
                                            scan.nextLine();
                                            // Memeriksa kevalidan ID transaksi
                                            if (isTransactionIdValid(deleteTransactionId)) { //memeriksa apakah ID transaksi yang dimasukkan valid
                                                deleteFromDatabase(deleteTransactionId); //menghapus data transaksi dari database berdasarkan ID transaksi yang dimasukkan.
                                                System.out.println("Data berhasil dihapus dari database.");
                                            } else {
                                                System.out.println("Transaction ID tidak valid atau tidak ditemukan.");
                                            }
                                            break;

                                        case 6:
                                            // Menutup program
                                            System.out.println("\nHave a nice day!\n");
                                            exitProgram = true;
                                            break;

                                        default:
                                            System.out.println("Pilihan tidak valid.");
                                            break;
                                    }
                                }
                                break;
                            } else {
                                System.out.println("\nKode captcha salah");
                            }
                        }
                        break;
                    } else {
                        System.out.println("Username atau Password Anda salah");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    // Metode untuk menyimpan transaksi ke database
    private static void saveToDatabase(Penjualan penjualan) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) { // untuk mendapatkan koneksi ke database PostgreSQL
            //Membuat string query SQL menggunakan prepared statement untuk memasukkan data transaksi ke dalam tabel transaksi. 
            // Query ini menggunakan placeholder (?) untuk parameter yang akan diisi nanti.
            String query = "INSERT INTO transaksi (nama_pelanggan, no_hp, alamat, kode_barang, nama_barang, harga_barang, jumlah_beli) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, penjualan.getNamaPelanggan()); //untuk mengisi nilai parameter pada prepared statement 
                preparedStatement.setString(2, penjualan.getNoHp());          //dengan nilai-nilai yang sesuai dari objek Penjualan 
                preparedStatement.setString(3, penjualan.getAlamat());        
                preparedStatement.setString(4, penjualan.getKodeBarang());
                preparedStatement.setString(5, penjualan.getNamaBarang());
                preparedStatement.setDouble(6, penjualan.getHargaBarang());
                preparedStatement.setInt(7, penjualan.getJumlahBeli());
                
                preparedStatement.executeUpdate(); //untuk menjalankan query dan menyimpan data transaksi ke dalam tabel transaksi di database.
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) { // untuk mendapatkan kunci yang dihasilkan
                    if (generatedKeys.next()) {                                        //yang dalam konteks ini adalah ID transaksi yang baru saja dimasukkan ke database
                        int transactionID = generatedKeys.getInt(1);
                        penjualan.setTransactionID(transactionID); // untuk mendapatkan nilai ID transaksi dan mengatur ID tersebut ke objek Penjualan

                        // Menambahkan transaksi ke transaksiMap
                        transaksiMap.put(transactionID, penjualan); //Menambahkan objek Penjualan ke dalam transaksiMap dengan menggunakan ID transaksi sebagai kunci
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat menyimpan ke database: " + e.getMessage());
        }
    }

    // Metode untuk mengambil transaksi dari database
    private static void retrieveFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) { //mendapatkan koneksi ke database PostgreSQL
            String query = "SELECT * FROM transaksi"; //Membuat string query SQL 
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) { //menjalankan query 
                    while (resultSet.next()) { //untuk iterasi melalui setiap baris 
                        // Mengambil data dan menampilkan
                        System.out.println("\nTransaction ID\t: " + resultSet.getInt("id"));
                        System.out.println("Nama Pelanggan\t: " + resultSet.getString("nama_pelanggan"));
                        System.out.println("No HP\t\t: " + resultSet.getString("no_hp"));
                        System.out.println("Alamat\t\t: " + resultSet.getString("alamat"));
                        System.out.println("Kode Barang\t: " + resultSet.getString("kode_barang"));
                        System.out.println("Nama Barang\t: " + resultSet.getString("nama_barang"));
                        System.out.println("Harga Barang\t: " + resultSet.getDouble("harga_barang"));
                        System.out.println("Jumlah Beli\t: " + resultSet.getInt("jumlah_beli"));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil dari database: " + e.getMessage());
        }
    }

    // Metode untuk mengupdate transaksi di database
    private static void updateInDatabase(Penjualan penjualan) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE transaksi SET nama_pelanggan=?, no_hp=?, alamat=?, kode_barang=?, nama_barang=?, harga_barang=?, jumlah_beli=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(8, penjualan.getTransactionID());
                preparedStatement.setString(1, penjualan.getNamaPelanggan());
                preparedStatement.setString(2, penjualan.getNoHp());
                preparedStatement.setString(3, penjualan.getAlamat());
                preparedStatement.setString(4, penjualan.getKodeBarang());
                preparedStatement.setString(5, penjualan.getNamaBarang());
                preparedStatement.setDouble(6, penjualan.getHargaBarang());
                preparedStatement.setInt(7, penjualan.getJumlahBeli());

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error saat mengupdate di database: " + e.getMessage());
        }
    }

    // Metode untuk memeriksa kevalidan ID transaksi
    private static boolean isTransactionIdValid(int transactionID) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM transaksi WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { //membuat objek PreparedStatement untuk menjalankan pernyataan SQL. 
                //Parameter diatur dengan menggunakan preparedStatement.setInt(1, transactionID) untuk mengisi nilai ID transaksi pada pernyataan SQL.
                preparedStatement.setInt(1, transactionID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); 
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat memeriksa transaction ID: " + e.getMessage());
            return false;
        }
    }

    // Metode untuk menghapus transaksi dari database
    private static void deleteFromDatabase(int transactionID) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM transaksi WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, transactionID);

                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error saat menghapus dari database: " + e.getMessage());
        }
    }

    private static Transaksi getTransactionByIdFromDatabase(int transactionID) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) { //menghubungkan ke database
            String query = "SELECT * FROM transaksi WHERE id = ?"; //string SQL untuk mengambil data transaksi dari database berdasarkan ID.
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { 
                preparedStatement.setInt(1, transactionID); //objek PreparedStatement dibuat untuk menjalankan pernyataan SQL. Parameter di set menggunakan preparedStatement. 
                //setInt(1, transactionID) untuk mengisi nilai ID transaksi pada pernyataan SQL.
                try (ResultSet resultSet = preparedStatement.executeQuery()) { //mengeksekusi pernyataan SQL dan menghasilkan objek ResultSet yang berisi hasil query.
                    if (resultSet.next()) { //Jika hasil query mengembalikan setidaknya satu baris, maka blok ini akan dieksekusi
                        String namaPelanggan = resultSet.getString("nama_pelanggan");
                        String noHp = resultSet.getString("no_hp");
                        String alamat = resultSet.getString("alamat");
                        String kodeBarang = resultSet.getString("kode_barang");
                        String namaBarang = resultSet.getString("nama_barang");
                        double hargaBarang = resultSet.getDouble("harga_barang");
                        int jumlahBeli = resultSet.getInt("jumlah_beli");

                        // Membuat objek Penjualan untuk menampung data transaksi
                        Penjualan penjualan = new Penjualan(namaPelanggan, noHp, alamat, //data tersebut digunakan untuk membuat objek Penjualan
                                kodeBarang, namaBarang, hargaBarang, jumlahBeli);
                        penjualan.setTransactionID(transactionID); //yang kemudian diatur ID transaksinya 

                        return penjualan; //dan dikembalikan.
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat mengambil ID Transaksi dari database: " + e.getMessage());
        }
        return null; //jika tidak ada hasil dari query (resultSet.next() bernilai false), maka metode ini mengembalikan null.
    }

    // Metode untuk menampilkan transaksi berdasarkan ID dari map
    private static void retrieveTransactionById(int transactionID) {
        Transaksi transaksi = transaksiMap.get(transactionID); //Metode get dari Map digunakan untuk mendapatkan nilai yang terkait dengan kunci. Hasilnya disimpan dalam variabel transaksi.
        if (transaksi != null) { //memeriksa apakah transaksi ditemukan di dalam transaksiMap
            transaksi.tampilkanDetailTransaksi(); //menampilkan detail transaksi.
        } else {
            System.out.println("Transaksi dengan ID " + transactionID + " tidak ditemukan di dalam map.");
        }
    }

}
