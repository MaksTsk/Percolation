/**
 * Created by Максим on 29.03.2015.
 */
public class Percolation {

    private WeightedQuickUnionUF _uf;

    private int _size;

    private Boolean[][] _openCloseMas;

    private int _elementsCount;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {

        _size = N;

        if(N<=0) throw new IllegalArgumentException();

        _elementsCount = N * N;
        _uf = new WeightedQuickUnionUF(_elementsCount);

        _openCloseMas = new Boolean[N][N];
        for(int i = 0; i<N; i++){
            for(int j = 0; j< N; j++){
                _openCloseMas[i][j] = false;
            }
        }
    }

    private int GetNumber(int x, int y){
        return _size * x + y;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        ValidateNumbers(i,j);

        int x = i-1;
        int y = j-1;

        _openCloseMas[x][y] = true;

        int numb = GetNumber(x,y);

        ConnectWithLeftNeighbour(x,y,numb);
        ConnectWithTopNeighbour(x,y,numb);
        ConnectWithBottomNeighbour(x,y,numb);
        ConnectWithRightNeighbour(x, y, numb);
    }

    private void ConnectWithLeftNeighbour(int x, int y, int sourceNumber) {
        int left = x-1;

        if(left>0 && _openCloseMas[left][y]){
            int neighNum = GetNumber(left,y);
            _uf.union(sourceNumber,neighNum);
        }
    }

    private void ConnectWithTopNeighbour(int x, int y, int sourceNumber){
        int top = y-1;
        if(top > 0 && _openCloseMas[x][top]){
            int neighNum = GetNumber(x,top);
            _uf.union(sourceNumber,neighNum);
        }
    }

    private void ConnectWithRightNeighbour(int x, int y, int sourceNumber) {
        int right = x+1;
        if(right<_size && _openCloseMas[right][y]){
            int neighNum = GetNumber(right,y);
            _uf.union(sourceNumber,neighNum);
        }
    }

    private void ConnectWithBottomNeighbour(int x, int y, int sourceNumber){
        int bottom = y+1;
        if(bottom < _size && _openCloseMas[x][bottom]){
            int neighNum = GetNumber(x,bottom);
            _uf.union(sourceNumber,neighNum);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j){
        ValidateNumbers(i,j);

        return _openCloseMas[i-1][j-1];
    }

    //todo one-based index
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        ValidateNumbers(i,j);

        int x = i-1;
        int y = j -1;

        if(!isOpen(i,j)) return false;

        int num = GetNumber(x,y);

        for(int n = 0; n<_size; n++){
            if(_uf.connected(num,n)) {
                return true;
            }
        }

        return false;
    }

    private void ValidateNumbers(int i, int j){
        if (i <= 0 || i > _size) throw new IndexOutOfBoundsException("row index i out of bounds");

        if (j <= 0 || j > _size) throw new IndexOutOfBoundsException("column index j out of bounds");
    }


    // does the system percolate?
    public boolean percolates(){
        for(int i = 0; i<_size; i++){
            if(isFull(i+1,_size)) return true;
        }

        return false;
    }
}
