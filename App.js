
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
  



// export default class App extends Component {
//   constructor(props) {
//     super(props);
//      DeviceEventEmitter.addListener('CallReceived', (event) => this.onCallReceive(event));

//   }
// onCallReceive = (event) => {
//   console.log("Call Received event ", event);
// };
//   state = {
//     email: '',
//     password: '',
//     clientId: '',
//   }
//   handleEmail = (text) => {
//     this.state.email = text
//   }

//   handlePassword = (text) => {
//     this.state.password = text
//   }

//   handleClientId = (text) => {
//     this.state.clientId = text
//   }
//   render() {
//     return (
//       <View style={styles.container}>
//           <View 
//       style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
//       <VideoView style={{ flex: 1, width: '100%', height: '100%' }}       
//       url="https://www.radiantmediaplayer.com/media/bbb-360p.mp4" /> 
//       </View>


//         <TextInput style={styles.input}
//           placeholder="Client Id"
//           placeholderTextColor="black"
//           autoCapitalize="none"
//           // value = "PUB-nesonukuv.34mv"
//           onChangeText={this.handleClientId}
//         />
//         <TextInput style={styles.input}
//           placeholder="Email"
//           // value = "nesonukuv@planet-travel.club"
//           placeholderTextColor="black"
//           autoCapitalize="none"
//           onChangeText={this.handleEmail}
//         />
//         <TextInput style={styles.input}
//           placeholder="Password"
//           // value = "Test@123"
//           placeholderTextColor="black"
//           autoCapitalize="none"
//           onChangeText={this.handlePassword}
//         />
//         <View style={{ margin: 10 }}>
//           <Button onPress={this.onLogin} title="Login Btn" />
//         </View>
//         <View style={{ margin: 10 }}>
//           <Button style={styles.buttonStyle} onPress={requestCameraPermission} title="Accept Permission" />
//         </View>
        
//       </View>
//     );
//   }

//   onLogin = () => {
//     console.log("Login API");
//     this.callLoginApi().then((result) => {
//       console.log("Inside", result);
//       if (result.status == 200) {
//         let response = result.json().then(value => {
//           KandyModule.initKandyService("oauth-cpaas.att.com", value.access_token, value.id_token)

//         });
//       } else {
//         result.json().then(value => {
//         })
//       }


//     });
//   }


//   callLoginApi = () => {
//     let baseUrl = "https://oauth-cpaas.att.com/cpaas/auth/v1/token"

//     let details = {
//       username: this.state.email,// "nesonukuv1@planet-travel.club",
//       password: this.state.password,// "Test@123",
//       client_id: this.state.clientId,//"PUB-nesonukuv.34mv",
//       grant_type: "password",
//       scope: "openid",
//     };

//     let formBody = [];
//     for (let property in details) {
//       let encodedKey = encodeURIComponent(property);
//       let encodedValue = encodeURIComponent(details[property]);
//       formBody.push(encodedKey + "=" + encodedValue);
//     }
//     formBody = formBody.join("&")

//     let data = {
//       method: 'POST',
//       body: formBody,
//       headers: {
//         'Content-Type': 'application/x-www-form-urlencoded'
//       }
//     }

//     return fetch(baseUrl, data).then(function (response) {
//       return response;

//     }).then(function (result) {
//       return result;

//     }).catch(function (error) {
//       console.log("-------- error ------- " + error);
//       alert("result:" + error)
//     });
//   }

// }
// const styles = StyleSheet.create({
//   Welcome_text: {
//     marginTop: 10,
//     marginBottom: 0,
//     marginLeft: 20,
//     fontSize: 25,
//     fontWeight: 'bold'
//   },
//   Login_text: {
//     marginTop: 0,
//     padding: 20,
//     fontSize: 17,
//     fontWeight: 'normal'
//   },
//   input: {
//     margin: 15,
//     height: 40,
//     borderColor: 'black',
//     borderWidth: 1,
//     borderRadius: 5,
//     borderColor: 'gray'
//   },
//   buttonStyle: {
//     marginTop: 20,
//     marginBottom: 20,
//     padding: 20,
//     borderRadius: 10,
//     borderColor: 'black',
//     flex: 60
//   }
// });