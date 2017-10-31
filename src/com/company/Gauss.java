package com.company;

public class Gauss {
    private  double[][] matrix;
    private double[][] sourceMatrix;
    private double[] vectorX;
    private double[] vectorNevyazki;
    private byte numberOfPermutation;
    private double determinant;
    private double vectorNevyazkiNorm;
    private double[][] reverseMatrix;
    private double[][] vectorNevyazkiMatrix = new double[5][5];
    private double normofvectorNevyazkiMatrix;
    private double nu;


    public Gauss() {
        matrix = new double[][]{
                {0.6444, 0.0000, -0.1683, 0.1184, 0.1973, 1.2677},
                {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263, 1.6819},
                {0.0132, -0.1184, 0.7627, 0.0145, 0.0460, -2.3657},
                {0.0395, 0.0000, -0.0960, 0.7627, 0.0000, -6.5369},
                {0.0263, -0.0395, 0.1907, -0.0158, 0.5523, 2.8351}
        };
        sourceMatrix = new double[][]{
                {0.6444, 0.0000, -0.1683, 0.1184, 0.1973, 1.2677},
                {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263, 1.6819},
                {0.0132, -0.1184, 0.7627, 0.0145, 0.0460, -2.3657},
                {0.0395, 0.0000, -0.0960, 0.7627, 0.0000, -6.5369},
                {0.0263, -0.0395, 0.1907, -0.0158, 0.5523, 2.8351}
        };
        vectorX = new double[5];
        vectorNevyazki = new double[5];
        numberOfPermutation = (byte) 0;
        determinant = 0;
        normofvectorNevyazkiMatrix=0;
        vectorNevyazkiNorm = 0;
        reverseMatrix = new double[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
    }

    public void printMatrix(){
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++ ){
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }

    ///// Поиск максимального элемента в столбце
    public void findMaxInColumn(int column) {
        double[] swap_ = new double[6];
        for (int r = column + 1; r < 5; r++) {
            if (Math.abs(matrix[column][column]) < Math.abs(matrix[r][column])) {
                for (int c = 0; c < 6; c++) {
                    swap_[c] = matrix[column][c];
                    matrix[column][c] = matrix[r][c];
                    matrix[r][c] = swap_[c];
                }
                numberOfPermutation++;
            }
        }
    }

    ///////////Прямой ход
    public void directCourse() {
        double ev;
        for (int i = 0; i < 4; i++) {
            for (int j = i; j < 4; j++) {
                ev = matrix[j + 1][i] / matrix[i][i];
                for (int c = 0; c < 6; c++) {
                    matrix[j + 1][c] -= matrix[i][c] * ev;
                }
            }
        }
        System.out.println("Матрица полученная в результате прямого хода:");
        for (int m = 0; m < 5; m++)
        {
            for (int n = 0; n < 5; n++)
            {
                System.out.print(matrix[m][n]+" ");
            }
            System.out.println();
        }
    }

    //////////////Обратный ход
    public void reverseCourse() {
        double sum;
        vectorX[4] = matrix[4][5] / matrix[4][4];
        for (int i = 3; i >= 0; i--) {
            sum = 0;
            for (int j = 4; j > i; j--) {
                sum += matrix[i][j] * vectorX[j];
            }
            vectorX[i] = (matrix[i][5] - sum) / matrix[i][i];
        }
        for(int k=0;k<5;k++){
            System.out.println(vectorX[k]);
        }
        System.out.println();
    }

    /////////////Вычисление детерминанта матрицы A
    public void calculateDeterminant() {
        determinant = Math.pow(-1, numberOfPermutation);
        for (int i = 0; i < 5; i++) {
            determinant *= matrix[i][i];
        }
        System.out.println(determinant);
        System.out.println();
    }


    //////////////////////Вычисление вектора невязки
    public void calculateVectorNevyazki() {
        for (int i = 0; i < 5; i++) {
            vectorNevyazki[i] = -sourceMatrix[i][5];
            for (int j = 0; j < 5; j++) {
                vectorNevyazki[i] += sourceMatrix[i][j] * vectorX[j];
            }
            System.out.println(vectorNevyazki[i]);
        }
        System.out.println();
    }

    //////////////Нахождение нормы вектора невязки
    public void calculateNormOfVectorNevyazki() {
        double min=0;
        for (int i = 0; i < 5; i++) {
            vectorNevyazkiNorm = Math.abs(vectorNevyazki[i]);
            if(vectorNevyazkiNorm>min){
                min =vectorNevyazkiNorm;
            }
        }
        vectorNevyazkiNorm=min;
        System.out.println("Октаэдрическая норма вектора невязки= ");
        System.out.println(vectorNevyazkiNorm);
        System.out.println();
    }

    /////////Нахождение обратной матрицы
    public void inversion()
    {double[][] ae = new double[][]{
            {0.6444, 0.0000, -0.1683, 0.1184, 0.1973},
            {-0.0395, 0.4208, 0.0000, -0.0802, 0.0263},
            {0.0132, -0.1184, 0.7627, 0.0145, 0.0460},
            {0.0395, 0.0000, -0.0960, 0.7627, 0.0000},
            {0.0263, -0.0395, 0.1907, -0.0158, 0.5523}
    };
        double temp;

        for (int k = 0; k < 5; k++)
        {
            temp = ae[k][k];

            for (int j = 0; j < 5; j++)
            {
                ae[k][j] /= temp;
                reverseMatrix[k][j] /= temp;
            }

            for (int i = k + 1; i < 5; i++)
            {
                temp = ae[i][k];

                for (int j = 0; j < 5; j++)
                {
                    ae[i][j] -= ae[k][j] * temp;
                    reverseMatrix[i][j] -= reverseMatrix[k][j] * temp;
                }
            }
        }

        for (int k = 5 - 1; k > 0; k--)
        {
            for (int i = k - 1; i >= 0; i--)
            {
                temp = ae[i][k];

                for (int j = 0; j < 5; j++)
                {
                    ae[i][j] -= ae[k][j] * temp;
                    reverseMatrix[i][j] -= reverseMatrix[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                ae[i][j] = reverseMatrix[i][j];
            }
        }

        for (int m = 0; m < 5; m++)
        {
            for (int n = 0; n < 5; n++)
            {
                System.out.print(ae[m][n]+" ");
            }
            System.out.println();
        }


    }
    //////////////Нахождение матрицы невязки
    public void calculateNevyazkiMatrix() {
        double[][] A_Areversed = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                A_Areversed[i][j] = 0;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    A_Areversed[i][j] += sourceMatrix[i][k] * reverseMatrix[k][j];
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                vectorNevyazkiMatrix[i][j] = A_Areversed[i][j];
            }
        vectorNevyazkiMatrix[i][i] -= 1;
        }


        for (int m = 0; m < 5; m++)
        {
            for (int n = 0; n < 5; n++)
            {
                System.out.print(vectorNevyazkiMatrix[m][n]+" ");
            }
            System.out.println();
        }
    }
    public void NormOfVectorNevyazkiMatrix(){
              for(int j=0;j<5;j++)
              {
                  double sum=0;
                  for(int i=0;i<5;i++){
                      sum +=Math.abs(vectorNevyazkiMatrix[i][j]);
                  }
                  if(sum>normofvectorNevyazkiMatrix){
                      normofvectorNevyazkiMatrix=sum;
                  }
              }
        System.out.println(normofvectorNevyazkiMatrix);
    }
////////////////////////////Нахождение числа обусловленности
    public void calculateVnumber(){
        double nuA=0;
        double nuAreverse=0;
        for(int i=0;i<5;i++){
            nuA +=Math.abs(sourceMatrix[0][i]);
            nuAreverse += Math.abs(reverseMatrix[0][i]);
        }
        nu=nuA*nuAreverse;
        System.out.println(nu);
        System.out.println();
    }
public static void main(String[] args){
        Gauss call=new Gauss();
    System.out.println("Исходная матрица имеет вид:");
        call.printMatrix();
    call.directCourse();
    System.out.println("Определитель матрицы:");
    call.calculateDeterminant();
    System.out.println("Столбец решений x:");
    call.reverseCourse();
    System.out.println("Вектор невязки: ");
    call.calculateVectorNevyazki();
    call.calculateNormOfVectorNevyazki();
    System.out.println("Обратная матрица имеет вид : ");
    call.inversion();
    System.out.println("Матрица невязки: ");
    call.calculateNevyazkiMatrix();
    System.out.println("Норма матрицы невязки: ");
    call.NormOfVectorNevyazkiMatrix();
    System.out.println("Число обусловленности: ");
    call.calculateVnumber();
}

}
