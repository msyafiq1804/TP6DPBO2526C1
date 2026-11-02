# JANJI
Saya Muhammad Syafiq A dengan NIM 2500254 mengerjakan TP 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

## Struktur File 
```
│   .gitignore
│   FlappyBird.iml
│
├───.idea
│       .gitignore
│       misc.xml
│       modules.xml
│       vcs.xml
│       workspace.xml
│
└───src
    │   App.java
    │   Logic.java
    │   Pipe.java
    │   Player.java
    │   View.java
    │
    └───assets
            background.png
            bgm.wav
            bird.png
            gameover.wav
            lompat.wav
            lowerPipe.png
            upperPipe.png
```

## Deskripsi Program
Program ini adalah sebuah aplikasi permainan desktop 2D bergaya arcade yang mengkloning mekanik permainan Flappy Bird. Dibangun menggunakan bahasa pemrograman Java dengan library antarmuka pengguna Java Swing dan AWT. Permainan ini mengimplementasikan konsep-konsep Pemrograman Berorientasi Objek (OOP) serta mencakup mekanik fisika dasar (gravitasi dan pergerakan objek), deteksi tabrakan (collision detection), sistem skor, tingkat kesulitan dinamis (perubahan jarak dan kecepatan rintangan), antarmuka multi-layar (Main Menu dan Game), serta integrasi multimedia berupa efek suara dan musik latar (BGM).

## Desain Program
Program dibagi menjadi beberapa class :
- App.java: Bertindak sebagai titik masuk utama (main method) dari program. Kelas ini mengatur JFrame (jendela aplikasi) dan menggunakan CardLayout untuk mengatur pergantian tampilan antara layar Main Menu (berisi tombol Start dan Exit) dan layar utama permainan.

- Logic.java: Merupakan otak atau mesin utama (game engine) dari permainan. Kelas ini menangani seluruh logika, mulai dari penghitungan gravitasi, pergerakan pipa, deteksi tabrakan (menggunakan Rectangle), perhitungan skor, pengaturan tingkat kesulitan dinamis, pemutaran efek suara dan BGM, hingga membaca input dari keyboard pemain.

- View.java: Bertugas sebagai renderer atau antarmuka visual (turunan dari JPanel). Kelas ini bertanggung jawab menggambar (paintComponent) seluruh objek grafis ke layar secara terus-menerus (60 frame per detik), termasuk latar belakang, burung, pipa, teks Game Over, dan memperbarui tampilan angka skor melalui JLabel.

- Player.java: Kelas model yang merepresentasikan karakter burung. Menyimpan data atribut seperti kordinat (X dan Y), ukuran (lebar dan tinggi), kecepatan jatuh vertikal (velocityY), dan aset gambar karakter.

- Pipe.java: Kelas model yang merepresentasikan rintangan pipa. Menyimpan kordinat, ukuran, aset gambar, serta sebuah flag atau penanda boolean (passed) untuk mengecek apakah pipa tersebut sudah dilewati oleh pemain atau belum agar skor tidak dihitung ganda.

## Alur Program / Permainan
- Fase Awal (Main Menu): Saat program dijalankan, jendela memunculkan panel Main Menu dengan gambar latar belakang dan dua tombol. Permainan belum berjalan dan musik belum berbunyi.

- Inisialisasi: Saat pemain mengklik "Start Game", layar beralih ke panel View. Fungsi startGame() dipanggil yang menyalakan timer pergerakan (60 FPS), timer pembuatan rintangan, serta memutar Background Music (BGM).

- Gameplay (Game Loop): Burung secara otomatis akan terus jatuh karena dipengaruhi variabel gravitasi. Setiap beberapa milidetik, objek Pipe baru (atas dan bawah) diciptakan di sisi kanan layar dan bergerak ke arah kiri.

- Tingkat Kesulitan Dinamis: Setiap kali kordinat burung berhasil melewati ujung kanan kordinat pipa, skor bertambah +1. Seiring bertambahnya skor, sistem logika secara otomatis mengurangi jarak celah vertikal antar pipa serta mempercepat waktu kemunculan pipa berikutnya.

- Game Over: Sistem terus memantau apakah kotak kordinat burung bersinggungan dengan kotak pipa atau jatuh ke batas bawah layar. Jika ya, siklus permainan dihentikan, BGM dimatikan, efek suara tabrakan dimainkan, dan layar menampilkan teks "Game Over".

## Kontrol Permainan
- Klik Kiri Mouse: Digunakan di awal untuk menekan tombol "Start Game" (memulai permainan) atau "Exit" (keluar dari aplikasi).

- Tombol Spasi (Spacebar): Memberikan dorongan kecepatan ke atas agar burung melompat melawan gravitasi. Setiap ditekan, akan muncul efek suara melompat.

- Tombol R: Ditekan pada keyboard khusus ketika layar menampilkan status Game Over untuk mereset permainan kembali ke awal (posisi burung di tengah, skor 0, tingkat kesulitan kembali normal, dan musik diputar ulang).

## Dokumentasi Program

### Tampilan awal

<img width="427" height="782" alt="game over" src="https://github.com/user-attachments/assets/b13333f8-0191-4718-bb32-0e4c2fa8641c" />


### Game Over

<img width="427" height="782" alt="game over" src="https://github.com/user-attachments/assets/2e54f554-e2c2-4931-a6ce-0e5596232468" />


### Video Gameplay

https://github.com/user-attachments/assets/cda10767-dd1c-4690-9699-87a120ba641e






