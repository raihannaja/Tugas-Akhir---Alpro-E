import java.util.*;

public class Djikstra {

    // Class untuk merepresentasikan sisi / jalur antar simpul
    static class Sisi {
        int tujuan;
        int bobot;

        Sisi(int tujuan, int bobot) {
            this.tujuan = tujuan;
            this.bobot = bobot;
        }
    }

    // ================== ALGORITMA DIJKSTRA ==================
    public static void dijkstra(List<List<Sisi>> graf, int simpulAwal) {

        int jumlahSimpul = graf.size();

        // Array untuk menyimpan jarak terpendek
        int[] jarak = new int[jumlahSimpul];
        Arrays.fill(jarak, Integer.MAX_VALUE);
        jarak[simpulAwal] = 0;

        // Priority Queue berdasarkan jarak terkecil
        PriorityQueue<int[]> antrianPrioritas =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        antrianPrioritas.add(new int[]{simpulAwal, 0});

        while (!antrianPrioritas.isEmpty()) {

            int[] sekarang = antrianPrioritas.poll();
            int u = sekarang[0];   // simpul saat ini
            int jarakSaatIni = sekarang[1];

            // Jika jarak lebih besar dari yang sudah tercatat, abaikan
            if (jarakSaatIni > jarak[u]) continue;

            // Cek semua tetangga
            for (Sisi sisi : graf.get(u)) {

                int v = sisi.tujuan;
                int bobot = sisi.bobot;

                if (jarak[u] + bobot < jarak[v]) {
                    jarak[v] = jarak[u] + bobot;
                    antrianPrioritas.add(new int[]{v, jarak[v]});
                }
            }
        }

        // ================== CETAK HASIL ==================
        System.out.println("Jarak terpendek dari simpul " + simpulAwal + ":");
        for (int i = 0; i < jumlahSimpul; i++) {
            if (jarak[i] == Integer.MAX_VALUE) {
                System.out.println("Ke simpul " + i + " : Tidak terjangkau");
            } else {
                System.out.println("Ke simpul " + i + " : " + jarak[i]);
            }
        }
    }

    // ================== PROGRAM UTAMA ==================
    // Studi kasus: Graf dengan 5 simpul (0=A, 1=B, 2=C, 3=D, 4=E)
    public static void main(String[] args) {

        int jumlahSimpul = 7;

        List<List<Sisi>> graf = new ArrayList<>();
        for (int i = 0; i < jumlahSimpul; i++) {
            graf.add(new ArrayList<>());
        }

        // Tambah jalur (arah searah)
        // A(0)-B(1):4, A(0)-C(2):1
        // B(1)-C(2):2, B(1)-D(3):5
        // C(2)-D(3):8, C(2)-E(4):10
        // D(3)-E(4):2

        graf.get(0).add(new Sisi(1, 4));
        graf.get(0).add(new Sisi(2, 1));

        graf.get(1).add(new Sisi(2, 2));
        graf.get(1).add(new Sisi(3, 5));

        graf.get(2).add(new Sisi(3, 8));
        graf.get(2).add(new Sisi(4, 10));

        graf.get(3).add(new Sisi(4, 2));

        // Jalankan Dijkstra dari simpul A (0)
        dijkstra(graf, 0);
    }
}
