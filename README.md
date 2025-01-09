MYSQL
Nome do banco de dados: suporte
Senha: admin


json de criar chamado ou atualizar cascade:
{
  "cliente": {
    "nome": "Maria Oliveira",
    "telefone": "(11) 98765-4321"
  },
  "equipamento": {
    "nome": "Computador",
    "descricao": "Computador de mesa",
    "tombamento": "TOMB223345",
    "tipo": "COMPUTADOR"
  },
  "local": {
    "departamento": "TI",
    "descricao": "Departamento de Tecnologia"
  }
}







Tipos Computadores:							Situações:
								
    COMPUTADOR							ABERTO	    
    NOTEBOOK							EM_ANDAMENTO
    MONITOR							CONCLUIDO
    NOBREAK_ESTABILIZADOR					FECHADO
    PROJETOR
    ROTEADOR
    SWITCH
    OUTRO





para get de descricoes:
http://localhost:8080/situacoes/id/descricoes




para atualizar a situacao via put e modificar a descricao:

{
  "descricao": "Nova descrição para a situação",
  "situacao": {
    "situacao": "EM_ANDAMENTO"
  }
}



lembre de criar um usuario no banco de dados antes.
Criar um usuario: ROLE_ADMIN, ROLE_GESTOR, ROLE_USER.
{
  "login": "Cleberson Luiz",
  "senha": "Gustavo$23",
  "papel": "ROLE_USER"
}

Tem o cadastrar Também não necessario colocar o papel:
{  
  "login": "Cleberson Luiz",
  "senha": "Gustavo$23"
}
Se quiser atualizar o usuario precisa já tem um  admin e usar o put nesse formato:

{  
   "login":"Cleberson Luiz",
   "papel":"ROLE_ADMIN"

}


