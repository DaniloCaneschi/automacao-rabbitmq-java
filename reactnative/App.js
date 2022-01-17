import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import Login from "./views/Login";
import {NavigationContainer} from "@react-navigation/native";
import Home from "./views/Home";

const Stack = createStackNavigator();

function Rotas() {
  return (
      <Stack.Navigator headerMode={'none'}>
        <Stack.Screen name="Login" component={Login}/>
        <Stack.Screen name="Home" component={Home}/>
      </Stack.Navigator>
  );
}

export default function App() {

  return (
      <NavigationContainer>
        <Rotas/>
      </NavigationContainer>
  );
}
