
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { Component } from 'react';
import 'react-native-gesture-handler';

import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import Login from './Screens/Login';
import { NativeModules } from 'react-native';
import DashBoard from './Screens/DashBoard';
import Chat from './Screens/Chat';
import SMS from './Screens/SMS';
import AddressBook from './Screens/AddressBook';
import UpdateContact from './Screens/UpdateContact';
import Directory from './Screens/Directory';
import Persence from './Screens/Persence';
const Stack = createStackNavigator();

export default class App extends Component{ 
  render() 
  {
  return (   
    <NavigationContainer>
    <Stack.Navigator>
      <Stack.Screen
        name="Login"
        options={{
          title: 'Login',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={Login}
      />
       <Stack.Screen
        name="DashBoard"
        options={{
          title: 'DashBoard',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={DashBoard}
      />
      <Stack.Screen
        name="SMS"
        options={{
          title: 'SMS',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={SMS}
      />
        <Stack.Screen
        name="Chat"
        options={{
          title: 'Chat',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={Chat}
      />
        <Stack.Screen
        name="AddressBook"
        options={{
          title: 'AddressBook',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={AddressBook}
      />
         <Stack.Screen
        name="UpdateContact"
        options={{
          title: 'UpdateContact',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={UpdateContact}
      />
  <Stack.Screen
        name="Directory"
        options={{
          title: 'Directory',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={Directory}
      />
       <Stack.Screen
        name="Persence"
        options={{
          title: 'Persence',
          headerStyle: {
            backgroundColor: '#0391C2',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
        component={Persence}
      />
    </Stack.Navigator>
    </NavigationContainer>      
  );
  }
  
}
  