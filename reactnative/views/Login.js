import React, {useState} from 'react';
import {Image} from 'react-native';
import {Button, Input} from "react-native-elements";
import Icon from "react-native-vector-icons/FontAwesome";
import Sessao from "../Sessao";
import PaiView from "../components/PaiView";
import RequisicaoHttp from "../services/RequisicaoHttp";
import logo from "../assets/logo.png";
import gerarBase64 from "../services/Base64";

export default function Login({navigation}) {
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [mensagemErro, setMensagemErro] = useState(null)
  const [exibirErro, setExibirErro] = useState(null)

  const entrar = () => {
    setExibirErro(false)
    Sessao.base64 = gerarBase64(email + ':' + senha)

    RequisicaoHttp.getAll(Sessao.endpointUsuario + "atual")
        .then(function (response) {
          Sessao.codigoUsuario = response.data.codigo;
          navigation.reset({
            index: 0,
            routes: [{name: "Home"}]
          });
        })
        .catch(function (erro) {
          setExibirErro(true)
          setMensagemErro(erro.mensagem)
        });
  }

  return (
      <PaiView exibirErro={exibirErro} mensagemErro={mensagemErro}>

        <Image source={logo} style={{alignSelf: "center", width: 130, height: 200}}/>

        <Input
            placeholder="E-mail"
            leftIcon={{type: 'font-awesome', name: 'envelope'}}
            onChangeText={value => setEmail(value)}
            keyboardType={"email-address"}
        />

        <Input
            placeholder="Senha"
            leftIcon={{type: 'font-awesome', name: 'lock'}}
            onChangeText={value => setSenha(value)}
            secureTextEntry={true}
        />

        <Button
            icon={
              <Icon
                  name="check"
                  size={15}
                  color="white"
              />
            }
            title=" Entrar"
            buttonStyle={{backgroundColor: "green"}}
            onPress={() => entrar()}
        />
      </PaiView>
  );
}