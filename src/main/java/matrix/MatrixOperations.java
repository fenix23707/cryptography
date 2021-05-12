package matrix;

public class MatrixOperations {

    public static void printMatrix(int [][]matrix){
        for(int i=0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.printf("%5d ",matrix[i][j]);
            System.out.println();
        }
    }

    public static int determinant(int[][] matrix){
        int det=0;
        if(matrix.length==2)
        {
            return matrix[0][0]*matrix[1][1]- matrix[0][1]*matrix[1][0];//нахождение определителя двумерной матрицы
        }
        else
            for(int i=0;i<matrix.length;i++)//разложение по 0-ой строке
            {
                if(i%2==0)//если i - четное число то -1 в степени i будет поллож
                    det+= matrix[0][i]*determinant(subMatrix(i,matrix));
                else//и наоборот
                    det-= matrix[0][i]*determinant(subMatrix(i,matrix));
            }
        return det;
    }

    private static int[][] subMatrix(int column,int[][]matrix){//метод, который возващает подматрицу убирая первую строку и выбранную диагональ
        int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
        for(int i=0;i<newMatrix.length;i++)
        {
            int k = 0;
            for (int j = 0; j < newMatrix.length;j++ ) {
                if (j == column)
                    k++;
                newMatrix[i][j] = matrix[i+1][k++];
            }
        }
        return newMatrix;
    }

    public static int[][] getAlgebraicAddition(int [][]matrix)  {
        int[][] inverse = new int[matrix.length][matrix.length];
        int det = determinant(matrix);
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++) {
                int temp = determinant(getMinor(matrix,i,j));
                if ((i + j) % 2 == 0) {
                    //temp = (temp/ det) % module;
                } else {
                    temp = -temp;// (temp/ det) % module;
                }

                inverse[j][i] = temp;
            }
        }
        return inverse;
    }

    private static int[][] getMinor(int[][] matrix, int row, int col) {
        int[][] minor = new int[matrix.length - 1][matrix.length -1];
        int k = 0;
        for(int i = 0; i < matrix.length; i++) {
            int t = 0;
            if (i == row) {
                continue;
            }
            for(int j = 0; j < matrix.length; j++) {
                if  (j == col) {
                    continue;
                }
                minor[k][t++] = matrix[i][j];
            }
            k++;
        }
        return minor;
    }

    private static int[][] addUnitMatrix(int [][]matrix) {//метод, который дописывае еденичную матрицу
        if(matrix.length!=matrix[0].length)//если матрица не квадратная, то дальше выполнение метода бесползено
            throw new IllegalArgumentException("the matrix is not square");

        int [][]matrixWithOneMatrix = new int[matrix.length][2*matrix.length];
        copyMatrix(matrix,matrixWithOneMatrix,0);//копирует первую матрицу во вторую начиная с 0 столбца
        //заполнение главной диагонали оставшейся матрицы 1:
        int row = 0;
        for(int i=matrix.length;i<matrixWithOneMatrix[0].length;i++)//цикл, который заполняет главную диагональ оставшейся матрицы еденицами
            matrixWithOneMatrix[row++][i]=1;
        return matrixWithOneMatrix;
    }

    private static void copyMatrix(int[][] firstMatrix, int[][]secondMatrix,int column){//метод, который копирует первую матрицу во вторую начиная со столбца column
        for(int i=0;i<firstMatrix.length;i++)
            for(int j=0;j<firstMatrix.length;j++)
                secondMatrix[i][j]=firstMatrix[i][j+column];
    }

    public static void makeUnitMatrix(int[][]matrix) {
        for(int i=0;i<matrix.length;i++)
        {
            if(matrix[i][i]==0)//если нужный элемент равен 0,
                makeElementNonZero(matrix,i);//то нужно поменять строчки местами так чтобы новый элемент на этой позиции перестал быть 0

            divRowByElement(matrix,i,matrix[i][i]);
            subRowFromMatrix(matrix,i);
        }
    }

    //метод, который меняет местами срочки матрицы, чтобы нужный элемент стал не 0
    private static void makeElementNonZero(int[][]matrix,int index) {
        System.out.println();
        for(int i=0;i<matrix.length;i++)
        {
            if (matrix[i][index] != 0)//если нашли строку, в которой нужный элемент не равен 0
            {
                int[] temp = matrix[index];//то меняем местами
                matrix[index] = matrix[i];
                matrix[i] = temp;
                return;//и завершаем метод
            }
        }
        throw new IllegalArgumentException("it is not possible to reduce the matrix to a triangular form");
    }

    //метод, который делит всю строку на переданный элемент
    private static void divRowByElement(int[][] matrix,int indexRow,double element){
        for(int i=0;i<matrix[0].length;i++)
            matrix[indexRow][i]/=element;
    }

    //метод, который отнимает нужную строку от всех строк в матрицы
    private static void subRowFromMatrix(int[][]matrix,int indexMainDiag){
        for(int i=0;i<matrix.length;i++)
        {
            if(i!=indexMainDiag)
            {
                double firstNumOfRow = matrix[i][indexMainDiag]/matrix[indexMainDiag][indexMainDiag];
                for(int j=0;j<matrix[0].length;j++)//цикл, который отнимает от i-ой строки 0-ую строку домноженную на первый элемент строки
                    matrix[i][j]-=matrix[indexMainDiag][j]*firstNumOfRow;
            }
        }
    }

    //произведение матриц:
    public static int[][]mull(int[][] m1, int[][] m2) {
        int[][] mull = new int[m1.length][m1.length];
        for(int i= 0; i < m1.length; i++) {
            for(int j = 0; j < m1[0].length; j++) {
                mull[i][j] = findElementForMull(m1,m2,i,j);
            }
        }
        return mull;
    }

    private static int findElementForMull(int[][] m1, int[][] m2,int row, int col) {
        int sum = 0;
        for(int i = 0; i < m1.length; i++) {
            sum += m1[row][i]*m2[i][col];
        }
        return sum;
    }

    public static int[] mull(int[] v2, int vectorBeg, int[][] m1, int module) {
        int[] mull = new int[m1.length];
        for( int i = 0; i < m1.length; i++) {
            int sum = 0;
            for(int j = 0; j < m1.length; j++) {
               sum += v2[vectorBeg+ j] * m1[j][i];
            }
            mull[i] = sum % module;
        }
        return mull;
    }

    public static void divModule(int[][] matrix, int module) {
        for(int i = 0; i < matrix.length; i++ ) {
            for(int j = 0; j < matrix.length; j++) {
                matrix[i][j] %= module;
            }
        }
    }

    public static void mullOnNumber(int[][] matrix, int num) {
        for(int i = 0; i < matrix.length; i++ ) {
            for(int j = 0; j < matrix.length; j++) {
                matrix[i][j] *= num;
            }
        }
    }

    public static void addModuleToNegativeElements(int[][] matrix, int module) {
        for(int i = 0; i < matrix.length; i++ ) {
            for(int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] < 0) {
                    matrix[i][j] += module;
                }
            }
        }
    }


}
