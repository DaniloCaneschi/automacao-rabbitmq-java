import React from 'react';
import {StyleSheet, View} from 'react-native';
import {Card, Switch} from "react-native-elements";
import Sessao from "../Sessao";
import RequisicaoHttp from "../services/RequisicaoHttp";
import PaiView from "../components/PaiView";

export default class ComponenteFuncao extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dados: [],
      exibirErro: false,
      mensagemErro: ''
    }

    this.getComponentes = this.getComponentes.bind(this)

    let getPeriodicos = null

    this.props.navigation.addListener(
        'focus',
        () => {
          this.getComponentes()
          getPeriodicos = setInterval(this.getComponentes, 5000)
        }
    )

    this.props.navigation.addListener(
        'blur',
        () => clearInterval(getPeriodicos)
    )
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

  executar = (i, valor) => {
    let dadosTemp = this.state.dados
    dadosTemp[i].ativo = valor
    this.setState({dados: dadosTemp})

    this.salvar(i)
  }

  salvar = (i) => {
    let dadosTemp = this.state.dados
    let componente = dadosTemp[i]
    let erros = ''

    RequisicaoHttp.post(Sessao.endpointAtivarComponente, componente)
        .then(function (response) {
        })
        .catch(function (erro) {
          erros = erro.mensagem
        });

    this.setState({exibirErro: erros !== '', mensagemErro: erros})
  }

  render() {
    return (
        <PaiView titulo={"Funções"} exibirErro={this.state.exibirErro} mensagemErro={this.state.mensagemErro}>

          <View style={estilo.lista}>
            {
              this.state.dados.map((l, i) => (
                  <Card key={i}>
                    <Card.Title>
                      {l.nome}
                    </Card.Title>
                    <Card.Divider/>
                    <Switch value={l.ativo}
                            onValueChange={value => this.executar(i, value)}></Switch>
                  </Card>
              ))
            }
          </View>
        </PaiView>
    );
  }
}

const estilo = StyleSheet.create({
  titulo: {
    justifyContent: "center",
    paddingBottom: "15px"
  },

  lista: {
    flexDirection: "row",
    flexWrap: "wrap"
  }
});