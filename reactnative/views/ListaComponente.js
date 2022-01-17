import React from 'react';
import {FAB, Input, ListItem, Switch, Text} from "react-native-elements";
import Sessao from "../Sessao";
import Icon from "react-native-vector-icons/FontAwesome";
import RequisicaoHttp from "../services/RequisicaoHttp";
import PaiView from "../components/PaiView";


export default class ListaComponente extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      dados: [],
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

  atualizarNome = (i, valor) => {
    let dadosTemp = this.state.dados
    dadosTemp[i].nome = valor
    this.setState({dados: dadosTemp})
  }

  atualizarModoPulso = (i, valor) => {
    let dadosTemp = this.state.dados
    dadosTemp[i].modopulso = valor
    this.setState({dados: dadosTemp})
  }

  salvar = (i) => {
    let dadosTemp = this.state.dados
    let componente = dadosTemp[i]

    let erros = ''
    RequisicaoHttp.put(Sessao.endpointComponente, componente.codigo, componente)
        .then(function (response) {
        })
        .catch(function (erro) {
          erros = erro.mensagem
        });

    this.setState({exibirErro: erros !== '', mensagemErro: erros})
  }

  render() {
    return (
        <PaiView titulo={"Componentes"} exibirErro={this.state.exibirErro} mensagemErro={this.state.mensagemErro}>
          {
            this.state.dados.map((l, i) => (
                <ListItem key={i} bottomDivider>
                  <ListItem.Content>
                    <ListItem.Title>{l.identificador}</ListItem.Title>
                    <Input placeholder="Nome"
                           onChangeText={value => this.atualizarNome(i, value)}
                           value={l.nome}>
                    </Input>
                    <Text>Modo pulso</Text>
                    <Switch value={l.modopulso}
                            onValueChange={value => this.atualizarModoPulso(i, value)}>
                    </Switch>
                  </ListItem.Content>
                  <FAB
                      size="small"
                      color="green"
                      icon={
                        <Icon
                            name="check"
                            size={15}
                            color="white"
                        />
                      }
                      onPress={() => this.salvar(i)}
                  />
                </ListItem>
            ))
          }
        </PaiView>
    )
  }
}