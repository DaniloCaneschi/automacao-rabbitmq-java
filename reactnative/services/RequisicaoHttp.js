import Sessao from "../Sessao";

export default class RequisicaoHttp {

  static getAll(path) {
    return fetch(Sessao.url + path,
        {
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          })
        })
        .then(response => {
          return this._tratarRetono(response)
        })
  };

  static getAll_FiltroDinamico(path) {
    return fetch(Sessao.url + path,
        {
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          })
        })
        .then(response => {
          return this._tratarRetono(response)
        })
  };

  static getId(path, id) {
    return fetch(Sessao.url + path + id.toString(),
        {
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          })
        })
        .then(response => {
          return this._tratarRetono(response)
        })
  };

  static post(path, body) {
    return fetch(Sessao.url + path,
        {
          method: "POST",
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          }),
          body: JSON.stringify(body)
        }
    ).then(response => {
      return this._tratarRetono(response)
    })
  };

  static put(path, id, body) {
    return fetch(Sessao.url + path + id.toString(),
        {
          method: "PUT",
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          }),
          body: JSON.stringify(body)
        }
    ).then(response => {
      return this._tratarRetono(response)
    })
  };

  static delete(path, id) {
    return fetch(Sessao.url + path + id.toString(),
        {
          method: "DELETE",
          headers: new Headers({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + Sessao.base64
          })
        }
    ).then(response => {
      return this._tratarRetono(response)
    })
  };

  static _tratarRetono(response) {
    console.log(response.status)
    if (response.ok) {
      return response.json();
    } else if (response.status === 401) {
      throw {mensagem: "Usuário ou senha estão incorretos"}
    } else if (response.status === 500) {
      return response.json().then(erro => {
        throw {mensagem: erro.message}
      })
    } else if (response.status === 400) {
      return response.json().then(erro => {
        throw {mensagem: erro.erro.toArray().toString()}
      })
    } else {
      throw {mensagem: 'falha ao conectar no servidor'}
    }
  }
}