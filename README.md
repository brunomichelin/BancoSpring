curl --location 'http://localhost:8080/pessoa/criarPessoaConta' \
--header 'Content-Type: application/json' \
--data '{
    "cpfCnpj": "123.456.789-09",
    "nome": "Bruno Michelin",
    "endereco": "Rua Americana, 300, Baeta Neves",
    "senha": "12345678",
    "agencia": 3,
    "id": 3
}'

curl --location 'http://localhost:8080/pessoa/listarTodasPessoas'

curl --location 'http://localhost:8080/pessoa/criarConta' \
--header 'Content-Type: application/json' \
--data '{
    "cpfCnpj": "123.456.789-09",
    "saldo": 100000,
    "agencia": 1,
    "id": 1
}'

curl --location 'http://localhost:8080/pessoa/listarTodasContas'

curl --location 'http://localhost:8080/banco/transferir' \
--header 'Content-Type: application/json' \
--data '{
    "remetente": {
        "id": 1,
        "agencia": 1
    },
    "destinatario": {
        "id": 3,
        "agencia": 3
    },
    "valor": 5000
}'

curl --location 'http://localhost:8080/banco/depositar' \
--header 'Content-Type: application/json' \
--data '{
    "conta": {
        "id": 1,
        "agencia": 1
    },
    "valor": 1000
}'
