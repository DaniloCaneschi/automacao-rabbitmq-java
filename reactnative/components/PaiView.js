import React from 'react';
import {Dimensions, View} from 'react-native';

import AwesomeAlert from 'react-native-awesome-alerts';
import {Card} from "react-native-elements";
import estilos from "../styles/main";
import config from "../styles/config";

export default class PaiView extends React.Component {

  constructor(props) {
    super(props);
  };

  render() {
    var width = config.deviceWidth
    if (width > 600)
      width = 600

    return (
        <View style={[estilos.container]}>

          <Card containerStyle={[estilos.card, {"maxWidth": width}]}>
            <Card.Title>{this.props.titulo}</Card.Title>
            {this.props.children}
          </Card>

          <AwesomeAlert
              show={this.props.exibirErro}
              showProgress={false}
              title="Erro"


              titleStyle={{color: "red"}}
              message={this.props.mensagemErro}
              closeOnTouchOutside={true}
              closeOnHardwareBackPress={true}
              showCancelButton={false}
              showConfirmButton={false}
              confirmText="Ok"
          />
        </View>
    );
  };
};