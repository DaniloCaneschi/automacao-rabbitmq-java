import React from 'react';
import {StyleSheet, View} from 'react-native';
import {Button, Input, ListItem, Switch} from "react-native-elements";
import Sessao from "../Sessao";
import Icon from "react-native-vector-icons/FontAwesome";
import RequisicaoHttp from "../services/RequisicaoHttp";
import PaiView from "../components/PaiView";
import config from "../styles/config";

class CompartilhaComponente extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dados: [],
      email: '',
      exibirErro: false,
      mensagemErro: ''
    }

    this.props.navigation.addListener(
        'focus',
        () => this.getComponentes()
    );
  }

  async getComponentes() {
    this.setState({exibirErro: false})

    let dados = []
    let erros = ''
    await RequisicaoHttp.getAll(Sessao.endpointComponente)
        .then(function (response) {
          dados = response.data
        })
        .catch(function (erro) {
          erros = erro.mensagem
        });

    this.setState({dados: dados, exibirErro: erros !== '', mensagemErro: erros})
  }

  atualizarCompartilhamento = (i, valor) => {
    let dadosTemp = this.state.dados
    dadosTemp[i].compartilhar = valor
    this.setState({dados: dadosTemp})
  }

  salvar = () => {
    let listaComponentes = this.state.dados
        .filter(value => value.compartilhar)
        .map(value => value.identificador)

    let compartilhamento = {
      email: this.state.email,
      componentes: listaComponentes
    }

    let erros = '';
    RequisicaoHttp.post(Sessao.endpointCompartilharComponente, compartilhamento)
        .then(function (response) {
        })
        .catch(function (erro) {
          erros = erro.mensagem
        });

    this.setState({exibirErro: erros !== '', mensagemErro: erros})
  }

  remover = () => {
    let listaComponentes = this.state.dados
        .filter(value => value.compartilhar)
        .map(value => value.identificador)

    let compartilhamento = {
      email: this.state.email,
      componentes: listaComponentes
    }

    let erros = '';
    RequisicaoHttp.post(Sessao.endpointRemoverCompartilharComponente, compartilhamento)
        .then(function (response) {
        })
        .catch(function (erro) {
          erros = erro.mensagem
        });

    this.setState({exibirErro: erros !== '', mensagemErro: erros})
  }

  render() {
    return (
        <PaiView titulo={"Compartilhamento de Componente"} exibirErro={this.state.exibirErro} mensagemErro={this.state.mensagemErro}>

          <Input placeholder="Email"
                 onChangeText={value => this.setState({email: value})}
                 value={this.state.email}>
          </Input>

          {
            this.state.dados.map((l, i) => (
                <ListItem key={i} bottomDivider>
                  <ListItem.Content>
                    <ListItem.Title>{l.identificador}</ListItem.Title>
                    <ListItem.Title>{l.nome}</ListItem.Title>
                  </ListItem.Content>
                  <Switch value={l.compartilhar}
                          onValueChange={value => this.atualizarCompartilhamento(i, value)}>
                  </Switch>
                </ListItem>
            ))
          }

          <View style={{display: 'flex', flexDirection: 'row', paddingTop: config.deviceWidth * 0.1}}>
            <Button
                buttonStyle={{backgroundColor: "green", marginRight: config.deviceWidth * 0.1}}
                icon={
                  <Icon
                      name="share"
                      size={15}
                      color="white"
                  />
                }
                title=" Salvar"
                onPress={() => this.salvar()}
            />

            <Button
                buttonStyle={{backgroundColor: "red"}}
                icon={
                  <Icon
                      name="share-square"
                      size={15}
                      color="white"
                  />
                }
                title=" Remover"
                onPress={() => this.remover()}
            />
          </View>
        </PaiView>
    );
  }
}

export default CompartilhaComponente;