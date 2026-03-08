/***********************************************
*
* Pedro Bertoncelo Figuieredo
* Trabalho de Estrutura de Dados
* Professor: Diego Padilha Rubert
*
*/

    #include <cstdio>
    #include <cstdint>
    #include <map>
    #include <vector>
    #include <list>
    #include <string>

    using std::string;

    // Buffer de 1 byte para leitura ou escrita bit-a-bit em arquivo (classe base).
    class BufferBits {
    protected:
      FILE *arquivo;  // Ponteiro para o arquivo sendo lido/escrito
      uint8_t byte;   // Buffer com bits de um byte
      uint8_t n;      // Quantidade de posições ocupadas no vetor acima

    public:
      BufferBits(FILE *arquivo);  // Construtor padrão com o arquivo que será lido ou escrito
      uint8_t ocupados();         // Devolve quantos bits estão ocupados no buffer
      uint8_t livres();           // Devolve quantos bits ainda podem ser adicionados ao buffer do byte
    };
    // Buffer de leitura de bits
    class BufferBitsLeitura : public BufferBits {
    public:
      BufferBitsLeitura(FILE *arquivo); // Construtor padrão com o arquivo que será lido
      uint8_t le_bit();                 // Lê o próximo bit do buffer (lê um byte do arquivo se estiver vazio)
    };

    // Buffer de escrita de bits
    class BufferBitsEscrita : public BufferBits {
    public:
      BufferBitsEscrita(FILE *arquivo);  // Construtor padrão com o arquivo que será escrito
      void escreve_bit(uint8_t bit);     // Escreve um bit 0 ou 1 no buffer (escreve byte no arquivo se completar 8 bits)
      void descarrega();                 // Força a escrita do byte no buffer (completa com 0s, caso necessário)
    };



struct NoHuffman {
    uint8_t caractere; //caractere
    int frequencia; //frequencia dele
    NoHuffman* esq; //ponteiro esq
    NoHuffman* dir; //ponteiro dir

    NoHuffman(uint8_t c, int f, NoHuffman* e = nullptr, NoHuffman* d = nullptr);// pega caractere e frequencia, pega filho esquerda e filho direita
    bool folha() const;// ver se é folha ou não
};

class Huffman {
public:
    Huffman(const std::map<uint8_t, int>& frequencias); //construtor  apatir do mapa das frequencias
    ~Huffman();//destrutor

    const std::map<uint8_t, std::string>& get_codigos() const;//tabela de codificação
    std::vector<uint8_t> get_alfabeto() const;//lista de bytes presentes no arquivo

    void coleta_arvore_bits(std::vector<bool>& bits) const;//transforma a arvore em bits
    static NoHuffman* reconstrua_arvore(BufferBitsLeitura& leitura);//le os bits serializados e reconstroi
    static void libera_arvore(NoHuffman* no);//libera a memoria da arvore
    static void decodifica_arquivo(BufferBitsLeitura& leitura, NoHuffman* raiz, FILE* f_out, size_t bits_uteis);//pela arvore transforma os bits  em texto
    static void descompacta(const char* nome_arquivo_huff, const char* nome_saida_txt);//le o huff e reconstroi
    void compacta(const char* nome_entrada, const char* nome_saida);//compacta e grava no arquivo
    static void compacta_arquivo(const char* nome_entrada, const char* nome_saida);//interface para compactar

private:
    NoHuffman* raiz;//ponteiro para raiz
    std::map<uint8_t, std::string> tabela_codigos;//mapeamento para byte dos caracteres

    void gera_codigos(NoHuffman* no, const std::string& prefixo);//gera a tabela
    void libera(NoHuffman* no);//libera os nos
    void coleta_bits(NoHuffman* no, std::vector<bool>& bits) const;//transforma arvore em sequencia de bits
};

class Heap {
public:
    Heap();//construtor
    ~Heap();//destrutor

    void insere(NoHuffman* p);//insere um novo nó p
    NoHuffman* extrai_minima();//raiz
    int tamanho() const;//quant de elementos da heap

private:
    NoHuffman** S;//ponteiro para ponteiro de Nohuffman
    int n;//quant de elementos
    int capacidade;//tamanho maximo do vetor S
    static const int TAMANHO_INICIAL = 4;//tamanho de incio da heap
    static bool menos_prioridade(NoHuffman* a, NoHuffman* b);//compara as prioridades de cada caractere

    int pai(int i);//calculo do indice do pai
    int esquerdo(int i);//calculo do indice do filho esq
    int direito(int i);//calculo do indice do filho dir
    void troca(int i, int j);//troca os elementos
    void desce(int i);//desce com o elementeo
    void sobe(int i);//sobe com elemento
    void redimensiona();//aumenta a capacidade da heap 2x quando chega no max
};

    int DEBUG_BITS = 1;


int main(int argc, char* argv[]) {
    if (argv[1][0] == 'c') {
        Huffman::compacta_arquivo(argv[2], argv[3]);

    } else if (argv[1][0] == 'd') {
        Huffman::descompacta(argv[2], argv[3]);
    }

    return 0;
}



    void escrever_binario(uint8_t numero)
    {
      for (int i = 128; i > 0; i >>= 1)
        printf("%c", numero & i ? '1' : '0');
    }

    BufferBits::BufferBits(FILE *arquivo) :
      arquivo(arquivo),
      byte(0),
      n(0)
    { }

    uint8_t BufferBits::ocupados()
    {
      return n;
    }

    uint8_t BufferBits::livres()
    {
      return 8 - n;
    }

    BufferBitsLeitura::BufferBitsLeitura(FILE *f) :
      BufferBits(f)
    { }

    uint8_t BufferBitsLeitura::le_bit()
    {
      // TODO:
      // Caso n == 0, deve ler 1 byte do arquivo e colocar no buffer
      // Se não houver mais bytes para serem lidos do arquivo, devolver o valor 2
      // Dica: leia sobre a função fread
        if(n == 0) {
            if (fread(&byte, 1, 1, arquivo) == 0)
                return 2;
            n = 8;
        }


        if (DEBUG_BITS) printf("n: %d, byte: %d (", n, byte);
        if (DEBUG_BITS) escrever_binario(byte);

      // TODO: Colocar em bit o próximo bit, e decrementar n
        uint8_t bit = (byte & 0x80) >> 7;
        byte <<= 1;
        n--;


      //uint8_t bit;

      if (DEBUG_BITS) printf(") --> %d(", byte);
      if (DEBUG_BITS) escrever_binario(byte);
      if (DEBUG_BITS) printf("), bit: %d\n", bit);

      return bit;
    }

    BufferBitsEscrita::BufferBitsEscrita(FILE *f) :
      BufferBits(f)
    { }

    void BufferBitsEscrita::escreve_bit(uint8_t bit)
    {
      if (DEBUG_BITS) printf("bit: %d, n: %d, byte: %d (", bit, n, byte);
      if (DEBUG_BITS) escrever_binario(byte);

      // TODO: Adicionar o bit ao byte na posição correta
      byte = (byte << 1) | (bit & 1);
      n++;

      if (DEBUG_BITS) printf(") --> %d(", byte);
      if (DEBUG_BITS) escrever_binario(byte);
      if (DEBUG_BITS) printf(")\n");

      if (n == 8)
        descarrega();
    }

    void BufferBitsEscrita::descarrega()
    {
      // TODO:
      // Escrever no arquivo o byte SE ao menos 1 bit tiver sido adicionao ao byte
      // Dica: não esqueça de atualizar os atributos da classe
      // Dica: leia sobre a função fwrite
        if(n > 0){
            if(n<8) byte <<= (8-n);

            fwrite(&byte, 1, 1, arquivo);
            byte = 0;
            n = 0;
        }
    }

    //Construe
    Heap::Heap() : S(new NoHuffman*[TAMANHO_INICIAL]), n(0), capacidade(TAMANHO_INICIAL) {}

    //Inserindo na heap
    void Heap::insere(NoHuffman* p) {
        if (n == capacidade) {
            redimensiona();
        }
        S[n++] = p;
        sobe(n - 1);
    }

    //Fazendo a heap min
    NoHuffman* Heap::extrai_minima() {
        if (n == 0) return nullptr;
        NoHuffman* min = S[0];
        S[0] = S[--n];
        desce(0);
        return min;
    }

    //Troca para nos
    void Heap::troca(int i, int j) {
        NoHuffman* temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    //Desce os nós
    void Heap::desce(int i) {
        int menor = i;
        int e = esquerdo(i);
        int d = direito(i);

        if (e < n && menos_prioridade(S[e], S[menor]))//confere prioridade
            menor = e;

        if (d < n && menos_prioridade(S[d], S[menor]))//confere prioridade
            menor = d;

        if (menor != i) {//troca de lugar se for diferente do procurado
            troca(i, menor);
            desce(menor);
        }
    }

    //Sobe os nós
    void Heap::sobe(int i) {
        while (i > 0 && menos_prioridade(S[i], S[pai(i)])) {//trocando o sobe
            troca(i, pai(i));
            i = pai(i);
        }
    }

    //Destrutor
    Heap::~Heap() {
        delete[] S;
    }

    //Ver tamanho da heap, quanto de altura
    int Heap::tamanho() const {
        return n;
    }

    //Calculo do pai
    int Heap::pai(int i) {
        return (i - 1) / 2;
    }

    //Calculo do no esquerdo
    int Heap::esquerdo(int i) {
        return 2 * (i + 1) - 1;
    }

    //Calculo do nó direito
    int Heap::direito(int i) {
        return 2 * (i + 1);
    }

    //Redimensiona caso a heap chegou na capacidade max
    void Heap::redimensiona() {
        capacidade *= 2;
        NoHuffman** novo_S = new NoHuffman*[capacidade];
        for (int i = 0; i < n; i++) {
            novo_S[i] = S[i];
        }
        delete[] S;
        S = novo_S;
    }

    //Função para conferir prioridade, para tentar fazer o que mais se repete ser o 0
    bool Heap::menos_prioridade(NoHuffman* a, NoHuffman* b) {
    if (a->frequencia != b->frequencia)
        return a->frequencia < b->frequencia;
    return a->caractere < b->caractere;
}


    //Construtor do nó
    NoHuffman::NoHuffman(uint8_t c, int f, NoHuffman* e, NoHuffman* d) : caractere(c), frequencia(f), esq(e), dir(d) {}

    //Ve se é folha
    bool NoHuffman::folha() const { return esq == nullptr && dir == nullptr; }

    //Construtor da arvore
    Huffman::Huffman(const std::map<uint8_t, int>& freq) : raiz(nullptr) {
        Heap heap;


        for (const auto& p : freq) heap.insere(new NoHuffman(p.first, p.second));

        while (heap.tamanho() > 1) {
            NoHuffman* e = heap.extrai_minima();
            NoHuffman* d = heap.extrai_minima();

            heap.insere(new NoHuffman(0, e->frequencia + d->frequencia, e, d));
        }
        raiz = heap.extrai_minima();
        gera_codigos(raiz, "");
    }

    //Destrutor
    Huffman::~Huffman() { libera(raiz); }

    //Impressão dos códigos
    void Huffman::gera_codigos(NoHuffman* no, const std::string& prefixo) {
        if (no->folha()) {
            tabela_codigos[no->caractere] = prefixo.empty() ? "0" : prefixo;
            return;
        }

        gera_codigos(no->esq, prefixo + "0");

        gera_codigos(no->dir, prefixo + "1");
    }

    const std::map<uint8_t, std::string>& Huffman::get_codigos() const { return tabela_codigos; }

    std::vector<uint8_t> Huffman::get_alfabeto() const {
        std::vector<uint8_t> alfa;
        for (const auto& p : tabela_codigos) alfa.push_back(p.first);
        return alfa;
    }

    //Pega os bits
    void Huffman::coleta_bits(NoHuffman* no, std::vector<bool>& bits) const {
        if (no->folha()) {
            bits.push_back(1);
            for (int i = 7; i >= 0; i--) bits.push_back((no->caractere >> i) & 1);
        } else {
            bits.push_back(0);
            coleta_bits(no->esq, bits);
            coleta_bits(no->dir, bits);
        }
    }

    //Pega a arvore
    void Huffman::coleta_arvore_bits(std::vector<bool>& bits) const {
        coleta_bits(raiz, bits);
    }

    //Destrutor
    void Huffman::libera(NoHuffman* no) {
        if (!no) return;
        libera(no->esq);
        libera(no->dir);
        delete no;
    }

    //Compactador do código
    void Huffman::compacta(const char* in, const char* out) {
        uint8_t byte;
        const auto& codigos = get_codigos();
        const auto& letras = get_alfabeto();
        uint16_t K = letras.size();

        //Montagem do cabeçalho
        FILE* f_out = fopen(out, "wb");
        fputc((K >> 8) & 0xFF, f_out);
        fputc(K & 0xFF, f_out);
        for (uint8_t c : letras) fputc(c, f_out);

        std::vector<bool> bits_arvore;
        coleta_arvore_bits(bits_arvore);
        BufferBitsEscrita escrita(f_out);
        for (bool b : bits_arvore) escrita.escreve_bit(b);

        FILE* f_in = fopen(in, "rb");
        int bits_conteudo = 0;
        while (fread(&byte, 1, 1, f_in)) {
            const std::string& codigo = codigos.at(byte);
            for (char c : codigo) escrita.escreve_bit(c - '0'), bits_conteudo++;
        }

        uint8_t sobra = (8 - (bits_conteudo % 8)) % 8;

        fclose(f_in);
        escrita.descarrega();

        //Forçando a leitura
        fputc((bits_conteudo >> 24) & 0xFF, f_out);
        fputc((bits_conteudo >> 16) & 0xFF, f_out);
        fputc((bits_conteudo >> 8) & 0xFF, f_out);
        fputc(bits_conteudo & 0xFF, f_out);
        fputc(sobra, f_out);
        fclose(f_out);
    }

    //Gera o arquivo compactado huff
    void Huffman::compacta_arquivo(const char* in, const char* out) {
        FILE* f = fopen(in, "rb");
        std::map<uint8_t, int> freq;
        uint8_t byte;
        while (fread(&byte, 1, 1, f)) freq[byte]++;
        fclose(f);
        Huffman huff(freq);
        huff.compacta(in, out);
    }

    //Reconstroe arvore apartir do huff
    NoHuffman* Huffman::reconstrua_arvore(BufferBitsLeitura& leitura) {
        uint8_t bit = leitura.le_bit();

        if (bit == 2) return nullptr;

        if (bit == 1) {
            uint8_t c = 0;

            for (int i = 0; i < 8; ++i) {

                uint8_t b = leitura.le_bit();

                if (b == 2) return nullptr;

                c = (c << 1) | b;
            }

            return new NoHuffman(c, 0);

        } else {
            NoHuffman* e = reconstrua_arvore(leitura);
            NoHuffman* d = reconstrua_arvore(leitura);

            return new NoHuffman(0, 0, e, d);
        }
    }

    //Destrutor da arvore
    void Huffman::libera_arvore(NoHuffman* no) {
        if (!no) return;

        libera_arvore(no->esq);

        libera_arvore(no->dir);

        delete no;
    }

    //Decodificador do huff
    void Huffman::decodifica_arquivo(BufferBitsLeitura& leitura, NoHuffman* raiz, FILE* out, size_t bits_uteis) {
        if (raiz->folha()) {
            for (size_t i = 0; i < bits_uteis; ++i) {

                fputc(raiz->caractere, out);
            }
            return;
        }

        NoHuffman* atual = raiz;

        for (size_t i = 0; i < bits_uteis; ++i) {

            bool bit = leitura.le_bit();
            atual = (bit == 0) ? atual->esq : atual->dir;

            if (atual->folha()) {

                fputc(atual->caractere, out);
                atual = raiz;
            }
        }
    }

    //Montagem da descompactação, faz o cabeçalho de novo e monta o alfabeto
    void Huffman::descompacta(const char* huff_file, const char* out_file) {
        FILE* f_in = fopen(huff_file, "rb");

        uint16_t K = (fgetc(f_in) << 8) | fgetc(f_in);

        std::vector<uint8_t> alfabeto(K);

        fread(alfabeto.data(), 1, K, f_in);

        long pos_arvore = ftell(f_in);

        fseek(f_in, -5, SEEK_END);
        uint32_t bits_conteudo = 0;
        for (int i = 0; i < 4; ++i) {

            bits_conteudo = (bits_conteudo << 8) | fgetc(f_in);
        }
        uint8_t sobra = fgetc(f_in);

        fseek(f_in, pos_arvore, SEEK_SET);

        BufferBitsLeitura leitura(f_in);
        NoHuffman* raiz = reconstrua_arvore(leitura);

        FILE* f_out = fopen(out_file, "wb");

        decodifica_arquivo(leitura, raiz, f_out, bits_conteudo);

        fclose(f_out);
        fclose(f_in);
        libera_arvore(raiz);
    }
