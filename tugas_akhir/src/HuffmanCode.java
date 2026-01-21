import java.util.*;

// Class untuk merepresentasikan Node pada Pohon Huffman
class NodeHuffman implements Comparable<NodeHuffman> {
    char karakter;
    int frekuensi;
    NodeHuffman kiri, kanan;

    NodeHuffman(char karakter, int frekuensi) {
        this.karakter = karakter;
        this.frekuensi = frekuensi;
    }

    // Untuk mengurutkan berdasarkan frekuensi (priority queue)
    @Override
    public int compareTo(NodeHuffman nodeLain) {
        return this.frekuensi - nodeLain.frekuensi;
    }
}

public class HuffmanCode{

    private static Map<Character, String> kodeHuffman = new HashMap<>();
    private static NodeHuffman akar;

    // ================== MEMBANGUN POHON HUFFMAN ==================
    public static void bangunPohonHuffman(String teks) {

        // Hitung frekuensi setiap karakter
        Map<Character, Integer> petaFrekuensi = new HashMap<>();
        for (char c : teks.toCharArray()) {
            petaFrekuensi.put(c, petaFrekuensi.getOrDefault(c, 0) + 1);
        }

        // Masukkan semua node ke Priority Queue
        PriorityQueue<NodeHuffman> antrianPrioritas = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> data : petaFrekuensi.entrySet()) {
            antrianPrioritas.add(new NodeHuffman(data.getKey(), data.getValue()));
        }

        // Gabungkan node sampai tersisa satu akar
        while (antrianPrioritas.size() > 1) {
            NodeHuffman kiri = antrianPrioritas.poll();
            NodeHuffman kanan = antrianPrioritas.poll();

            NodeHuffman nodeBaru =
                    new NodeHuffman('\0', kiri.frekuensi + kanan.frekuensi);
            nodeBaru.kiri = kiri;
            nodeBaru.kanan = kanan;

            antrianPrioritas.add(nodeBaru);
        }

        akar = antrianPrioritas.poll();

        // Buat kode Huffman dari pohon
        buatKodeHuffman(akar, "");
    }

    // ================== MEMBUAT KODE HUFFMAN ==================
    private static void buatKodeHuffman(NodeHuffman node, String kode) {
        if (node == null) return;

        // Jika node daun, simpan kodenya
        if (node.kiri == null && node.kanan == null) {
            kodeHuffman.put(node.karakter, kode);
        }

        buatKodeHuffman(node.kiri, kode + "0");
        buatKodeHuffman(node.kanan, kode + "1");
    }

    // ================== KOMPRESI ==================
    public static String kompres(String teks) {
        StringBuilder hasil = new StringBuilder();
        for (char c : teks.toCharArray()) {
            hasil.append(kodeHuffman.get(c));
        }
        return hasil.toString();
    }

    // ================== DEKOMPRESI ==================
    public static String dekompres(String dataTerkompresi) {
        StringBuilder hasil = new StringBuilder();
        NodeHuffman sekarang = akar;

        for (char bit : dataTerkompresi.toCharArray()) {
            if (bit == '0') {
                sekarang = sekarang.kiri;
            } else {
                sekarang = sekarang.kanan;
            }

            // Jika sampai di daun
            if (sekarang.kiri == null && sekarang.kanan == null) {
                hasil.append(sekarang.karakter);
                sekarang = akar;
            }
        }
        return hasil.toString();
    }

    // ================== PROGRAM UTAMA ==================
    public static void main(String[] args) {

        String teks = "hello";

        bangunPohonHuffman(teks);

        String hasilKompresi = kompres(teks);
        String hasilDekompresi = dekompres(hasilKompresi);

        System.out.println("Teks Asli       : " + teks);
        System.out.println("Kode Huffman    : " + kodeHuffman);
        System.out.println("Hasil Kompresi  : " + hasilKompresi);
        System.out.println("Hasil Dekompresi: " + hasilDekompresi);

        double rasioKompresi =
                (double) hasilKompresi.length() / (teks.length() * 8);

        System.out.println("Rasio Kompresi  : " + rasioKompresi);
    }
}
