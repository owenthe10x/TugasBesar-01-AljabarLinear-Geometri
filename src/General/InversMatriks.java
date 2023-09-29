package General;
import java.util.Scanner;


public class InversMatriks {

    // Melakukan operasi inverse terhadap matrix dengan menggunakan metode Gauss Jordan
    public static double[][] inverseGaussJordan(double[][] matrix) {
        // KAMUS LOKAL
        int n = matrix.length;
        double pengkalian;
        
        // Membuat sebuah matrix identitas
        double[][] inverse = OperasiMatriks.CreateMatrixIdentitas(matrix);
        
        // Menggunakan Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Cari baris dengan elemen terbesar di kolom i sebagai pivot
            int maxRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = j;
                }
            }
            
            // Tukar baris i dengan baris maxRow jika perlu
            if (maxRow != i) {
                // Tukar baris matrix
                double[] temp = matrix[i];
                matrix[i] = matrix[maxRow];
                matrix[maxRow] = temp;
                
                // Tukar baris matriks identitas
                temp = inverse[i];
                inverse[i] = inverse[maxRow];
                inverse[maxRow] = temp;
            }
            
            pengkalian = matrix[i][i];
            
            // Cek apakah matriks singular atau tidak
            if (pengkalian == 0.0) {
                System.out.println("Matrix tersebut singular, tidak bisa diinverse.");
                return null;
            }
            
            // Mengalikan baris dengan elemen seterusnya
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= pengkalian;
                inverse[i][j] /= pengkalian;
            }
            
            // Eliminasi elemen lain
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    pengkalian = matrix[k][i];
                    for (int j = 0; j < n; j++) {
                        matrix[k][j] -= pengkalian * matrix[i][j];
                        inverse[k][j] -= pengkalian * inverse[i][j];
                    }
                }
            }
        }
        
        return inverse;
    }
    
    // Melakukan operasi inverse terhadap matrix dengan menggunakan metode Adjoint
    public static double[][] inverseAdjoint(double[][] matrix) {
        double determinant = OperasiMatriks.determinanKofaktor(matrix);
    
        if (Math.abs(determinant) < 1e-10) {
            System.out.println("Matrix tersebut singular, tidak bisa diinverse.");
            return null;
        }
    
        double pengkalian = 1 / determinant;
        double[][] Adjoin = OperasiMatriks.matrixAdjoin(matrix);
        double[][] inverse = new double[matrix.length][matrix.length];
        int i, j;
    
        
        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix[i].length; j++) {
                // Membuat agar tidak terdapat negative zero (-0.0)
                if (Math.abs(Adjoin[i][j]) < 1e-10) {
                    inverse[i][j] = 0.0;
                } else {
                    inverse[i][j] = pengkalian * Adjoin[i][j];
                }
            }
        }
        
        return inverse;
    }
    
    // Print rumus inverse Gauss-Jordan
    public static void printGauss(double[][] matrix) {
        //KAMUS LOKAL
        int i, j;

        // ALGORITMA
        System.out.println();
        double[][] verse = inverseGaussJordan(OperasiMatriks.copyMatrix(matrix));
        double[][] identitas = OperasiMatriks.CreateMatrixIdentitas(matrix);
        for (i = 0; i < matrix.length; i++) {
            System.out.print("|");
            for (j = 0; j < matrix[i].length;j++) {
                System.out.print(matrix[i][j]);
                if(j != matrix[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            for (j = 0; j < identitas[i].length;j++) {
                System.out.print(identitas[i][j]);
                if(j != identitas[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
               System.out.println();
        for (i = 0; i < identitas.length; i++) {
            System.out.print("|");
            for (j = 0; j < identitas[i].length;j++) {
                System.out.print(identitas[i][j]);
                if(j != identitas[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            for (j = 0; j < verse[i].length;j++) {
                System.out.print(verse[i][j]);
                if(j != verse[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
    }
    
    // sub menu inverse
    public static void main(String[] args) {
        // ALGORITMA
        double[][] matrix = null;
        double[][] inverse = null;
        Scanner input = new Scanner(System.in);
        String metode = null;
        while (true) {
            System.out.println("Metode Inverse:\n1. Metode Gauss Jordan\n2. Metode Adjoin");
            System.out.print("Metode: ");
            String via = input.nextLine();
            if (via.equals("1")){
                metode = "1";
                break;
            } else if (via.equals("2")) {
                metode = "2";
                break;
            } else {
                System.out.println("Input salah harap untuk memasukkan pilihan yang tersedia.");
            }
            }
        while (true) {
            System.out.println("Input matrix via:\n1. File\n2. User");
            System.out.print("Via: ");
            String via = input.nextLine();
            if (via.equals("1") ){
                matrix = Fungsi.inputFromTxt();
                break;
            } else if (via.equals("2")) {
                matrix = Fungsi.inputFromUser();
                break;
            } else {
                System.out.println("Input salah harap untuk input hanya \"file\" atau \"user\".");
            }
        }
        input.close();

        if (metode == "1") {
            printGauss(matrix);
            inverse = inverseGaussJordan(matrix);
        } else {
            System.out.println();
            System.out.println("Inverse matrix = 1 / det(matrix) x Adjoin(matrix)");
            inverse = inverseAdjoint(matrix);
        }
        
        System.out.println();
        System.out.println("Inverse Matriks: ");
        Fungsi.displayMatrix(inverse);
    }   

}