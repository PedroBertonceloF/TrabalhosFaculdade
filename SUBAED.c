#include <stdio.h>
#define m 20

int pai(int i);
void suba(int h[], int i);
void troca(int *a, int *b);
int selecionaMaiorPri(int h[]);
void inserir(int h[], int n, int x);
void removerMaiorElemento(int h[]);
int filhoEsq(int i);
int filhoDir(int i);

int main(){
    int h[m], n=0;
    inserir(h, n, 1);
    inserir(h, n, 2);
    inserir(h, n, 3);
    for(int i=0;i < 2;i++){
        printf("%d\n", h[i]);
    }
    }

void troca(int *a, int *b){
    int aux = *a;
    *a = *b;
    *b = aux;
}

int filhoEsq(int i){
    return(2*i) +1;
}

int filhoDir(int i){
    return(2*i)+2;
}

void suba(int h[], int i){
    int j;

    if(i > 0){
        j = pai(i);
    }

    if(h[i] > h[j]){
        troca(&h[i], &h[j]);
        suba(h, j);
    }
}

int selecionaMaiorPri(int h[]){
    return(h[0]);
}

void inserir(int h[], int n, int x){
    if(n < m){
        h[n] = x;
        suba(h, n);
        n = n+1;
    }
}

int pai(int i){
    return(i-1)/2;
}

void removerMairElemento(int h[]){
    h[0] = 0;
    suba(h, filhoDir(h[0]));
}
