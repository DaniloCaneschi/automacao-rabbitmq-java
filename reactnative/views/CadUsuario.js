import React from 'react';
import {Button, Input} from "react-native-elements";
import Icon from "react-native-vector-icons/FontAwesome";
import Sessao from "../Sessao";
import RequisicaoHttp from "../services/RequisicaoHttp";
import PaiView from "../components/PaiView";
import config from "../styles/config";


export default class CadUsuario extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      senha: "",
      perfil: "ROLE_USER",
      exibirErro: false,
      mensagemErro: ''
    }

    this.salvar = this.salvar.bind(this)
  }

  salvar = () => {
    this.setState({exibirErro: false})

    let erros = ''
    RequisicaoHttp.post(Sessao.endpointUsuario, this.state)
        .catch((erro) => {
          erros = erro.mensagem
        });

    this.setState({exibirErro: erros !== '', mensagemErro: erros})
  }

  render() {
    return (
        <PaiView titulo={"Cadastro de usuários"} exibirErro={this.state.exibirErro} mensagemErro={this.state.mensagemErro}>
          <Input
              placeholder="E-mail"
              leftIcon={{type: 'font-awesome', name: 'envelope'}}
              onChangeText={value => this.setState({email: value})}
              value={this.state.email}
              keyboardType={"email-address"}
          />

          <Input
              placeholder="Senha"
              leftIcon={{type: 'font-awesome', name: 'lock'}}
              onChangeText={value => this.setState({senha: value})}
              value={this.state.senha}
              secureTextEntry={true}
          />

          <Button
              buttonStyle={{backgroundColor: "green", width: config.deviceWidth * 0.9}}
              icon={
                <Icon
                    name="check"
                    size={15}
                    color="white"
                />
              }
              title=" Salvar"
              onPress={() => this.salvar()}
          />
        </PaiView>
    );
  }
}
