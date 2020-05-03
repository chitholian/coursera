
### 1. Social network connectivity

```
Modify the WQUPC algorithm as follows:
1. Take an extra variable "int largestCC" to keep track of the largest size of the connected components.
2. Initialize largestCC := 1 inside the constructor.
3. Inside the "union(int p, int q)" method, in addition to merging the sizes of two connected components, update the instance variable "largestCC" comparing to the size of newly created connected component.
4. for each pair (p, q) and timestamp T in log file: do
5. call union(p, q)
6. if largestCC == N:
7. return T
```

### 2. Union-find with specific canonical element

```
public class UF {
    private int[] root, largest, size;

    public UF(int N) {
        root = new int[N];
        largest = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            root[i] = largest[i] = i;
            size[i] = 1;
        }
    }

    private int getRoot(int p) {
        while (root[p] != p) {
            root[p] = root[root[p]];
            p = root[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    public void union(int p, int q) {
        if (!connected(p, q)) {
            int rp = getRoot(p), rq = getRoot(q);
            int lp = largest[rp], lq = largest[rq];
            if (size[p] < size[q]) {
                root[p] = rq;
                size[q] += size[p];
            }
            else {
                root[q] = rp;
                size[p] += size[q];
            }
            rp = getRoot(p);
            if (lp > lq) {
                largest[rp] = lp;
            }
            else {
                largest[rp] = lq;
            }
        }
    }

    public int find(int p) {
        return largest[getRoot(p)];
    }
}
```


### 3. Successor with delete

```
public class SuccessorDelete {
    private int[] prev, next;

    public SuccessorDelete(int N) {
        prev = new int[N];
        next = new int[N];
        for (int i = 0; i < N; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }
        prev[0] = 0;
        next[N - 1] = N - 1;
    }

    public void deleteX(int x) {
        next[prev[x]] = next[x];
    }

    public int successor(int x) {
        return next[x];
    }
}
```

