import React, { Component } from 'react';
import {
  StyleSheet,
  View,
  Text,
  NativeModules,
  TextInput,
  ActivityIndicator,
  Button
} from 'react-native';

const { LoginModule } = NativeModules;
class Login extends React.Component {
  static navigationOptions = {
    title: 'Login',
  };

  state = {
    email: '',
    password: '',
    url: 'oauth-cpaas.att.com',
    clientId: '',
    sourceNumber: '',
    destinationNumber: '',
    showLoader: false
  }

  showLoader = () => { this.setState({ showLoader: true }); };
  hideLoader = () => { this.setState({ showLoader: false }); };

  handleEmail = (text) => {
    this.state.email = text
  }

  handlePassword = (text) => {
    this.state.password = text
  }

  handleClientId = (text) => {
    this.state.clientId = text
  }

  handleUrl = (text) => {
    this.state.url = text;
  }

  handleLogin = () => {
    if ( this.state.url && this.state.clientId && this.state.password && this.state.email != "") {
      this.showLoader();
      //   var loginManager = NativeModules.login;

      LoginModule.initLogin(this.state.clientId, this.state.password, this.state.email, this.state.url
        , (status, message) => {
          if (status == 'Success') {
            console.log(message);
            this.hideLoader();
            this.props.navigation.navigate('DashBoard')

        } else {
            console.log(message);
            this.hideLoader();
            alert(message);
        }
        
        });
    } else {
      alert('Please fill all the details.');
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.Welcome_text}>Welcome</Text>

        <Text style={styles.Login_text}>
          Login to continue
                  </Text>
        <Text style={styles.Normal_text}>
          Host URL
                  </Text>
        <TextInput style={styles.input}
          placeholder="Example. oauth-cpaas.att.com"
          placeholderTextColor="black"
          autoCapitalize="none"
          value={this.state.input}
          onChangeText={this.handleUrl}
        />
         <Text style={styles.Normal_text}>
          Client ID
                  </Text>
        <TextInput style={styles.input}
          placeholder="Example. PUB-nesonukuv.34mv"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleClientId}
        />
 <Text style={styles.Normal_text}>
          Email
                  </Text>

        <TextInput style={styles.input}
          placeholder="Example. nesonukuv@planet-travel.club"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleEmail}
        />
         <Text style={styles.Normal_text}>
          Password
                  </Text>
        <TextInput style={styles.input}
          placeholder="Example. Test@123"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handlePassword}
        />

        <View style={{ position: 'absolute', top: "50%", right: 0, left: 0 }}>
          <ActivityIndicator animating={this.state.showLoader} size="large" color="grey" />
        </View>

        <Button
          small
          title="Login"
          onPress={this.handleLogin}
        />

      </View>
    );
  }
}

const styles = StyleSheet.create({
  Welcome_text: {
    marginTop: 10,
    marginBottom: 0,
    marginLeft: 20,
    fontSize: 25,
    fontWeight: 'bold'
  },
  Login_text: {
    marginTop: 0,
    padding: 20,
    fontSize: 17,
    fontWeight: 'normal'
  }, Normal_text: {
    marginTop: 0,
    paddingTop: 0,
    paddingBottom: 0,
    paddingLeft: 20,
    paddingRight: 0,
    fontSize: 14,
    fontWeight: 'normal'
  },
  input: {
    margin: 15,
    height: 40,
    borderColor: 'black',
    borderWidth: 1,
    borderRadius: 5,
    borderColor: 'gray'
  },
  buttonStyle: {
    marginTop: 20,
    padding: 20,
    borderRadius: 10,
    borderColor: 'black',
    flex: 60
  }
});

export default Login; 