import React from 'react';
import {createBottomTabNavigator} from "@react-navigation/bottom-tabs";
import ComponenteFuncao from "./ComponenteFuncao";
import Ionicons from "react-native-vector-icons/FontAwesome";
import CadUsuario from "./CadUsuario";
import ListaComponente from "./ListaComponente";
import CompartilhaComponente from "./CompartilhaComponente";
import Sessao from "../Sessao";

const Tab = createBottomTabNavigator();

export default function Home() {
  return (
      <Tab.Navigator
          initialRouteName={"Componente"}
          tabBarOptions={{
            activeTintColor: "blue"
          }}>

        {Sessao.codigoUsuario === 1 &&
        <Tab.Screen
            name="Usuario"
            component={CadUsuario}
            options={{
              tabBarLabel: "Usuário",
              tabBarIcon: ({color, size}) => {
                return <Ionicons name={"user"} color={color} size={size}/>
              }
            }}/>
        }
        <Tab.Screen
            name="Componente"
            component={ListaComponente}
            options={{
              tabBarLabel: "Componentes",
              tabBarIcon: ({color, size}) => {
                return <Ionicons name={"magic"} color={color} size={size}/>
              }
            }}/>
        {Sessao.codigoUsuario === 1 &&
        <Tab.Screen
            name="Compartilha"
            component={CompartilhaComponente}
            options={{
              tabBarLabel: "Compartilhar",
              tabBarIcon: ({color, size}) => {
                return <Ionicons name={"share"} color={color} size={size}/>
              }
            }}/>
        }
        <Tab.Screen
            name="Acoes"
            component={ComponenteFuncao}
            options={{
              tabBarLabel: "Funções",
              tabBarIcon: ({color, size}) => {
                return <Ionicons name={"plug"} color={color} size={size}/>
              }
            }}/>
      </Tab.Navigator>
  );
}
