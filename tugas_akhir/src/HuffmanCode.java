import java.util.*;

// Node Pohon Huffman
class NodeHuffman implements Comparable<NodeHuffman> {
    char karakter;
    int frekuensi;
    NodeHuffman kiri, kanan;

    NodeHuffman(char karakter, int frekuensi) {
        this.karakter = karakter;
        this.frekuensi = frekuensi;
    }

    @Override
    public int compareTo(NodeHuffman lain) {
        return this.frekuensi - lain.frekuensi;
    }
}

public class HuffmanCode {

    private static Map<Character, String> kodeHuffman = new HashMap<>();
    private static NodeHuffman akar;

    public static void bangunPohon(String teks) {

        Map<Character, Integer> petaFrekuensi = new HashMap<>();
        for (char c : teks.toCharArray()) {
            petaFrekuensi.put(c, petaFrekuensi.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<NodeHuffman> antrian = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> data : petaFrekuensi.entrySet()) {
            antrian.add(new NodeHuffman(data.getKey(), data.getValue()));
        }

        while (antrian.size() > 1) {
            NodeHuffman kiri = antrian.poll();
            NodeHuffman kanan = antrian.poll();

            NodeHuffman baru = new NodeHuffman('\0', kiri.frekuensi + kanan.frekuensi);
            baru.kiri = kiri;
            baru.kanan = kanan;

            antrian.add(baru);
        }

        akar = antrian.poll();
        buatKode(akar, "");
    }

    private static void buatKode(NodeHuffman node, String kode) {
        if (node == null) return;

        if (node.kiri == null && node.kanan == null) {
            kodeHuffman.put(node.karakter, kode);
        }

        buatKode(node.kiri, kode + "0");
        buatKode(node.kanan, kode + "1");
    }

    public static String kompres(String teks) {
        StringBuilder hasil = new StringBuilder();
        for (char c : teks.toCharArray()) {
            hasil.append(kodeHuffman.get(c));
        }
        return hasil.toString();
    }

    public static String dekompres(String data) {
        StringBuilder hasil = new StringBuilder();
        NodeHuffman sekarang = akar;

        for (char bit : data.toCharArray()) {
            if (bit == '0') sekarang = sekarang.kiri;
            else sekarang = sekarang.kanan;

            if (sekarang.kiri == null && sekarang.kanan == null) {
                hasil.append(sekarang.karakter);
                sekarang = akar;
            }
        }
        return hasil.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan teks yang ingin di olah: ");
        String teks = input.nextLine();

        bangunPohon(teks);

        String hasilKompresi = kompres(teks);
        String hasilDekompresi = dekompres(hasilKompresi);

        System.out.println("\nKode Huffman:");
        for (Map.Entry<Character, String> data : kodeHuffman.entrySet()) {
            System.out.println(data.getKey() + " : " + data.getValue());
        }

        System.out.println("\nHasil Kompresi  : " + hasilKompresi);
        System.out.println("Hasil Dekompresi: " + hasilDekompresi);

        double rasio = (double) hasilKompresi.length() / (teks.length() * 8);
        System.out.println("Rasio Kompresi  : " + rasio);
    }
}
