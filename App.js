/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { Platform, Alert, StyleSheet, Text, View, ActivityIndicator } from 'react-native';
import { ToastAndroid } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import Login from './src/components/Login';
import localStorage from './src/utility/LocalStorage';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component {

  static navigationOptions = {
    header: null
  }

  constructor(props) {
    super(props);
    this.state = { isLoading: false };
  }

  onLoginForm = (loginComponentData) => {
    //Alert.alert(JSON.stringify(loginComponentData));
    console.log('loginComponentData' + JSON.stringify(loginComponentData));
    this.setState({ isLoading: true });
    this.callLoginApi(loginComponentData).then((result) => {
      console.log("Inside", result);
      this.setState({ isLoading: false });

      if (result.status == 200) {
        let response = result.json().then(value => {
          localStorage.setTokenData(JSON.stringify(value));        
        });
        
        ToastAndroid.show('Login Successfully', ToastAndroid.SHORT);
        this.props.navigation.navigate('Home');

      } else {
        result.json().then(value => {
          //console.log("error value", value);
          ToastAndroid.show(value.error_description, ToastAndroid.SHORT);
        })
      }


    });
  }

  callLoginApi = (loginComponentData) => {

    console.log('username' + loginComponentData.username);
    let baseUrl = "https://" + loginComponentData.baseurl + "/cpaas/auth/v1/token"
    console.log('baseUrl ' + baseUrl);
    localStorage.setBaseUrl(loginComponentData.baseurl);

    let details = {
      username: loginComponentData.username,
      password: loginComponentData.password,
      client_id: loginComponentData.clientId,
      grant_type: "password",
      scope: "openid",
    };

    let formBody = [];
    for (let property in details) {
      let encodedKey = encodeURIComponent(property);
      let encodedValue = encodeURIComponent(details[property]);
      formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&")

    let data = {
      method: 'POST',
      body: formBody,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }

    return fetch(baseUrl, data).then(function (response) {
      //console.log("response ", response);
      //Alert.alert("response " + JSON.stringify(response));
      return response;

    }).then(function (result) {
      // console.log(result);
      //console.log("result", result);
      // Alert.alert("result " + JSON.stringify(result));
      return result;

    }).catch(function (error) {
      console.log("-------- error ------- " + error);
      alert("result:" + error)
    });
  }

  renderProgressbar() {

    return (
      <Spinner
        visible={this.state.isLoading}
        textContent={'Loading...'}
        textStyle={styles.spinnerTextStyle}
      />
    );
  }

  render() {
    const { navigate } = this.props.navigation;
    return (
      <View style={styles.container}>
        {this.renderProgressbar()}
        <Login onLoginForm={this.onLoginForm} />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  spinnerTextStyle: {
    color: '#0000ff'
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  spinnerContainer: {
    flex: 1,
    justifyContent: 'center'
  },
  horizontal: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    padding: 10
  }
});
