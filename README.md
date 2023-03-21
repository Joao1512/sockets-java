# Descobrindo Sockets: O Mundo Oculto da Comunicação na Internet

# O que são sockets

Socket é uma abstração do protocolo TCP/IP que permite processos em diferentes computadores de uma rede se comunicarem de maneira bidimensional e contínua. Isso permite a criação de um “túnel” constante entre dois processos, tornando-se desnecessária a criação de uma nova requisição toda vez que um dos lados deseja enviar algo, como acontece no protocolo HTTP.

# Definição Cliente-Servidor

Neste estudo focarei no desenvolvimento cliente-servidor, o qual possui um provedor de informações central e clientes que se conectam a ele simultaneamente. Veja a imagem: 

![foto de [omnisci](https://www.omnisci.com/technical-glossary/client-server)](readme_files/Untitled.png)

foto de [omnisci](https://www.omnisci.com/technical-glossary/client-server)

# O Diagrama de uma API Socket

A imagem a seguir mostra uma visão geral do sistema de chamadas envolvido na criação de um socket TCP:

![Untitled](readme_files/Untitled%201.png)

1. O processo responsável por ser o servidor inicia um socket. No caso do Java, isso é implementado pela classe `ServerSocket` do pacote java.io. Através dela, você irá conseguir esperar por requisições vindas por meio da rede. O conteúdo dessas requisições será direcionado para a sua aplicação Java.
2. Em seguida, o `ServerSocket`fará um *bind* da aplicação em alguma porta. A porta é um número inteiro escolhido, como 8080. Isso significa que vamos “pendurar” a aplicação na porta 8080, então toda vez que algum processo enviar dados para esta porta, eles serão direcionados para a aplicação do servidor Java.
3. O servidor entra em modo de escuta, permanecendo congelado até que algum processo tente estabelecer uma conexão.
4. Para que um processo consiga fazer uma requisição ao servidor, é preciso também iniciar um socket no lado deste processo. Para isso, o Java fornece a classe `Socket`. No construtor da classe, iremos inserir o ip da máquina a qual tem o processo servidor rodando seguido da porta usada por este servidor.
5. Antes que o client envie dados efetivamente, ele fará uma *connection request*,  que precisará ser aceita pelo servidor através do *accept()*.
6. Eles entrarão em um loop que se consiste em um fluxo constante de escrita e leitura de informações nos dois lados da conexão.
7. O fluxo de informações se encerra quando o client terminar a conexão.

# Iniciando um Servidor

 

```java
public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    System.out.println("Listening on port " + port + "...");
    int count = 0;
    while (true) {
        clientSocket = serverSocket.accept();
        count++;
        System.out.println("Total clients connected: " + count);
        ClientThread clientThread = new ClientThread(clientSocket, count);
        clientThread.start();
    }
}
```

Este código representa a estrutura básica de um servidor e que implementa boa parte do que foi descrito anteriormente: 

1.  A aplicação é pendurada em uma porta em *serverSocket = new ServerSocket(port).*
2. *While (true)* serve para que o servidor fique eternamente aguardando novas requisições.
3. O código é congelado na linha *clientSocket = serverSocket.accept(),* e só continua quando um client tenta se conectar.
4. Sempre que um client se conecta, é criada uma thread para ele. Desta maneira, as conexões com cada um dos clients conectados são mantidas rodando assincronamente e separadamente.  Em outras palavras, depois de criada a thread para o client, ele permanecerá conectado enquanto o servidor pode voltar a “ouvir” novas conexões.
5. No final, teremos estabelecido a conexão bidirecional. Ela usará um `InputStream` e um `OutputStream` para estabelecer um fluxo, uma “corrente” que servirá de caminho para que e os dados trafeguem. 

Você pode verificar aqui no repositório os detalhes de implementação da thread do client e de como fazer a leitura destes streams de dados usando `InputStreamReader`, `PrintWriter` e `BufferedReader`.

Ao compreender a arquitetura Cliente-Servidor, é viável criar e operar seu próprio servidor, o que permite uma compreensão mais aprofundada das engrenagens internas das aplicações que você utiliza cotidianamente.