import java.util.Scanner;

class NodeBST {
    int data;
    NodeBST kiri, kanan;

    NodeBST(int data) {
        this.data = data;
        this.kiri = null;
        this.kanan = null;
    }
}

public class BinarySearchTree {

    static NodeBST akar = null;

    static NodeBST tambah(NodeBST node, int data) {
        if (node == null) return new NodeBST(data);

        if (data < node.data)
            node.kiri = tambah(node.kiri, data);
        else if (data > node.data)
            node.kanan = tambah(node.kanan, data);

        return node;
    }

    static void inorder(NodeBST node) {
        if (node != null) {
            inorder(node.kiri);
            System.out.print(node.data + " ");
            inorder(node.kanan);
        }
    }

    static void preorder(NodeBST node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorder(node.kiri);
            preorder(node.kanan);
        }
    }

    static void postorder(NodeBST node) {
        if (node != null) {
            postorder(node.kiri);
            postorder(node.kanan);
            System.out.print(node.data + " ");
        }
    }

    static boolean cari(NodeBST node, int data) {
        if (node == null) return false;
        if (node.data == data) return true;
        if (data < node.data) return cari(node.kiri, data);
        return cari(node.kanan, data);
    }

    static NodeBST hapus(NodeBST node, int data) {
        if (node == null) return null;

        if (data < node.data) {
            node.kiri = hapus(node.kiri, data);
        } else if (data > node.data) {
            node.kanan = hapus(node.kanan, data);
        } else {

            // Jika hanya satu anak atau tidak punya anak
            if (node.kiri == null) return node.kanan;
            if (node.kanan == null) return node.kiri;

            // Jika punya dua anak → ambil nilai terkecil dari kanan
            node.data = nilaiMinimum(node.kanan);
            node.kanan = hapus(node.kanan, node.data);
        }
        return node;
    }

    static int nilaiMinimum(NodeBST node) {
        int min = node.data;
        while (node.kiri != null) {
            min = node.kiri.data;
            node = node.kiri;
        }
        return min;
    }

    static void tampilMenu() {
        System.out.println("\n=== MENU BINARY SEARCH TREE ===");
        System.out.println("(masukan data satu per-satu)");
        System.out.println("1. Tambah Data");
        System.out.println("2. Tampilkan Inorder");
        System.out.println("3. Tampilkan Preorder");
        System.out.println("4. Tampilkan Postorder");
        System.out.println("5. Cari Data");
        System.out.println("6. Hapus Data");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            tampilMenu();
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan angka: ");
                    int data = input.nextInt();
                    akar = tambah(akar, data);
                    System.out.println("Data berhasil ditambahkan.");
                    break;

                case 2:
                    System.out.print("Inorder   : ");
                    inorder(akar);
                    System.out.println();
                    break;

                case 3:
                    System.out.print("Preorder  : ");
                    preorder(akar);
                    System.out.println();
                    break;

                case 4:
                    System.out.print("Postorder : ");
                    postorder(akar);
                    System.out.println();
                    break;

                case 5:
                    System.out.print("Masukkan data yang dicari: ");
                    int cari = input.nextInt();
                    if (cari(akar, cari))
                        System.out.println("Data DITEMUKAN.");
                    else
                        System.out.println("Data TIDAK ditemukan.");
                    break;

                case 6:
                    System.out.print("Masukkan data yang dihapus: ");
                    int hapus = input.nextInt();
                    akar = hapus(akar, hapus);
                    System.out.println("Proses hapus selesai.");
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 0);
    }
}
