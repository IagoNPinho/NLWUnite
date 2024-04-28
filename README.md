# Pass.in - Gerencimento de Participantes em Eventos

O **Pass.in** é uma aplicação voltada a gerencia de participantes em eventos. Ele permite que organizadores criem eventos, inscrevam participantes e facilitem o check-in no dia do evento.

## Tecnologias Utilizadas
- **Backend**:
    - **Java** com:
        - **Spring Boot**
        - **Maven**
        - **JPA**
        - **Lombok**
- **Banco de Dados**:
    - **HSQLDB** (local):
        - **Flayway**

## Funcionalidades Atuais
1. **Criação de evento**:
    - O organizador pode criar um evento.

2. **Inscrição de Participantes**:
    - O organizador pode inscrever novos participantes.

3. **Visualizar Dados do Evento**:
    - O organizador pode ver detalhes do evento, como:
        - ID do evento;
        - Título do evento;
        - Detalhe/Descrição do evento;
        - Slug do evento (Identificação com base no título);
        - Quantidade máxima de participantes;
        - Quantidade atual de participantes.

4. **Visualizar Participantes do Evento**:
    - O organizador tem acesso à lista completa de participantes inscritos com as seguintes informações:
        - ID do participante;
        - Nome do participante;
        - E-mail do participante;
        - Data de inscrição;
        - Data de check-in.

5. **Visualizar Crachá de Inscrição do Participante**:
    - Os participantes inscritos podem emitir um crachá de inscrição com as seguintes informações:
        - Nome do participante;
        - E-mail do participante;
        - Link para checkin no evento;
        - ID do evento.

6. **Realizar Check-in no Dia do Evento**:
    - O sistema permite o check-in dos participantes no dia do evento.

7. **Remover Participante do Evento**
    - O sistema permite remover o participante do evento antes de realizar o check-in.
    - Ao remover o participante, retornará informações do participante em caso de erro, assim poderá entrar em contato.

## Regras de Negócio

- Os participantes só podem se inscrever uma vez em um evento.
- A inscrição só é permitida em eventos com vagas disponíveis.
- O check-in só pode ser feito uma única vez por participante.
- Os participantes só podem ser removidos antes de realizar o check-in.


## Como Executar o Projeto

1. Clone este repositório.
2. Abra o projeto em um editor de código de sua preferência. (Utilizei o Intellij por ter mais familiaridade nele com o Java)
3. Instale as dependências necessárias.
4. Execute o servidor backend.
5. Acesse a aplicação pelo insomnia.

## Como Realizar as Requisições HTTP do Projeto com Insomnia

Deixarei um [tutorial básico](https://youtu.be/YATd7vjQiJM) de como baixar, instalar e criar os tipos de requisições utilizadas. (GET/POST/DELETE)

1. **Criar de evento**
    - **Requisição:**
        - Tipo:
            - POST
        - URL:
            - http://localhost:8080/events
        - Body:
            - JSON
              ```json
              {
                  "title": "{Título-do-evento}",
                  "details": "{Detalhe/Descrição-do-evento}",
                  "maximumAttendees": {Quantidade-máxima-de-participantes}
              }
              ```

      **Exemplo:**
        - Body:
            - JSON
              ```json
              {
                  "title": "Rockseat NLW Unite",
                  "details": "Aprenda várias tecnologias de forma gratuita e online",
                  "maximumAttendees": 1000
              }
              ```
    - **Retorno:**
        - JSON
          ```json
          {
              "eventId": "{ID-do-evento}"
            }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
              "eventId": "ef629727-7ebd-415a-9b39-02bb9de7c368"
            }
          ```
2. **Inscrever Participante**
    - **Requisição:**
        - Tipo:
            - POST
        - URL:
            - http://localhost:8080/events/{ID-do-evento}/attendees
        - Body:
            - JSON
              ```json
              {
                  "name": "{Nome-do-Participante}",
                  "email": "{E-mail-do-Participante}"
              }
              ```

      **Exemplo:**
        - URL:
            - http://localhost:8080/events/4e50fba9-1674-4ba1-8719-1a70370d0749/attendees
        - Body:
            - JSON
              ```json
              {
                  "name": "Iago Nascimento de Pinh",
                  "email": "iaguinho1@gmail.com"
              }
              ```
    - **Retorno:**
        - JSON
          ```json
          {
              "attendeeId": "{ID-do-participante}"
            }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
              "attendeeId": "d5059d0d-5690-4b15-a8ea-b16cd794ff0b"
            }
          ```
3. **Visualizar dados do evento**
    - **Requisição:**
        - Tipo:
            - GET
        - URL:
            - http://localhost:8080/events/{ID-do-Evento}

      **Exemplo:**
        - URL:
            - http://localhost:8080/events/4e50fba9-1674-4ba1-8719-1a70370d0749
    - **Retorno:**
        - JSON
          ```json
          {
              "event": {
                  "id": "{ID-do-evento}",
                  "title": "{Título-do-evento}",
                  "details": "{Detalhe/Descrição-doevento}",
                  "slug": "{Slug-com-base-no-título}",
                  "maximumAttendee": {Quantidade-máxima-de-participantes},
                  "attendeeAmount": {Quantidade-atual-de-participantes}
              }
          }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
              "event": {
                  "id": "4e50fba9-1674-4ba1-8719-1a70370d0749",
                  "title": "Rockseat NLW Unite",
                  "details": "Aprenda várias tecnologias de forma gratuita e online",
                  "slug": "rockseat-nlw-unite",
                  "maximumAttendee": 1000,
                  "attendeeAmount": 2
              }
          }
          ```
4. **Visualizar lista de participantes do evento**
    - **Requisição:**
        - Tipo:
            - GET
        - URL:
            - http://localhost:8080/events/attendees/{ID-do-Evento}

      **Exemplo:**
        - URL:
            - http://localhost:8080/events/attendees/4e50fba9-1674-4ba1-8719-1a70370d0749
    - **Retorno:**
        - JSON
          ```json
          {
              "attendees": [
                  {
                      "id": "{ID-do-participante}",
                      "name": "{Nome-do-Participante}",
                      "email": "{E-mail-do-Participante}",
                      "createdAt": "{Data-de-criação}",
                      "checkInAt": "{Data-de-check-in}"
                  }
            ]
          }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
              "attendees": [
                  {
                      "id": "4e50fba9-1674-4ba1-8719-1a70370d0749",
                      "name": "Iago",
                      "email": "iago.pinho.dev@gmail.com",
                      "createdAt": null,
                      "checkInAt": "2024-04-16T14:30:56.246503"
                  },
                  {
                      "id": "948a669d-0331-4382-9571-bef2cd1a30fd",
                      "name": "Iago Nascimento de Pinho",
                      "email": "iaguinho1450@gmail.com",
                      "createdAt": null,
                      "checkInAt": null
                  },
                  {
                      "id": "d5059d0d-5690-4b15-a8ea-b16cd794ff0b",
                      "name": "Iago Nascimento de Pinh",
                      "email": "iaguinho1@gmail.com",
                      "createdAt": null,
                      "checkInAt": null
                  }      
              ]
          }
          ```
5. **Visualizar Crachá de Inscrição do Participante**
    - **Requisição:**
        - Tipo:
            - GET
        - URL:
            - http://localhost:8080/attendees/{ID-do-Participante}/badge

      **Exemplo:**
        - URL:
            - http://localhost:8080/attendees/948a669d-0331-4382-9571-bef2cd1a30fd/badge
    - **Retorno:**
        - JSON
          ```json
          {
              "badge": {
                  "name": "{Nome do Participante}",
                  "email": "{E-mail do Participante}",
                  "checkInUrl": "{URL para realizar check-in}",
                  "eventId": "{ID do evento}"
              }
          }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
              "badge": {
                  "name": "Iago Nascimento de Pinho",
                  "email": "iaguinho1450@gmail.com",
                  "checkInUrl": "http://localhost:8080/attendees/948a669d-0331-4382-9571-bef2cd1a30fd/check-in",
                  "eventId": "4e50fba9-1674-4ba1-8719-1a70370d0749"
              }
          }
          ```
6. **Realizar Check-in no Dia do Evento**
    - **Requisição:**
        - Tipo:
            - POST
        - URL:
            - http://localhost:8080/attendees/{ID-do-participante}/check-in

      **Exemplo:**
        - URL:
            - http://localhost:8080/attendees/948a669d-0331-4382-9571-bef2cd1a30fd/check-in
    - **Retorno:**
        - Headers:
            - URL para acessar crachá:
                - 	http://localhost:8080/attendees/{ID-do-Participante}/badge

      **Exemplo:**
        - Headers:
            - URL para acessar crachá:
                - 	http://localhost:8080/attendees/948a669d-0331-4382-9571-bef2cd1a30fd/badge
7. **Remover participante que não realizou check-in**
    - **Requisição:**
        - Tipo:
            - DELETE
        - URL:
            - http://localhost:8080/events/{ID-do-evento}/attendee/{ID-do-participante}/delete

      **Exemplo:**
        - URL:
            - http://localhost:8080/events/093bc3c5-f4fb-4d2a-8df5-f6b1a821a1f7/attendee/180425a9-12b5-4781-8fd4-b6548b5eab6c/delete
    - **Retorno:**
        - JSON
          ```json
          {
            "attendeeId": "{ID-do-participante}",
            "name": "{nome-do-participante}",
            "eventID": "{ID-do-evento}"
          }
          ```

      **Exemplo:**
        - JSON
          ```json
          {
            "attendeeId": "180425a9-12b5-4781-8fd4-b6548b5eab6c",
            "name": "Iago Nascimento de Pinho",
            "eventID": "093bc3c5-f4fb-4d2a-8df5-f6b1a821a1f7"
          }
          ```
## Funcionalidades Futuras

1. Atualizar Dados do Evento.
2. Atualizar Dados do Participante.

## Funcionalidades implementadas Extra Projeto

1. Remover o participante do evento.

## Créditos

Este foi um projeto arquitetado e tutorado pela equipe da Rocketseat durante o NLW Unite, em especial pela Fernanda Kipper, que foi crucial para o entendimento do projeto e o absorvimento do conhecimento.
