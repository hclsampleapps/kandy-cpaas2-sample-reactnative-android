import React, { Component } from 'react';
import {
  StyleSheet,
  View,
  Text,
  NativeModules,
  TextInput,
  ActivityIndicator,
  PermissionsAndroid,
  Button
} from 'react-native';
import SMS from './SMS'
import DashBoard from './DashBoard'
import UpdateContact from './UpdateContact'
const requestCameraPermission = async () => {
  try {
    const granted = await PermissionsAndroid.requestMultiple([
      // PermissionsAndroid.PERMISSIONS.MODIFY_AUDIO_SETTINGS,
      PermissionsAndroid.PERMISSIONS.RECORD_AUDIO,
      PermissionsAndroid.PERMISSIONS.CAMERA,
      PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
      PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE
    ]);
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      // console.log("You can use the camera");
    } else {
      // console.log("Camera permission denied");
    }
  } catch (err) {
    console.warn(err);
  }
};
const { LoginModule } = NativeModules;
class Login extends React.Component {
  // constructor(props) {
  //   super(props);
  //   DeviceEventEmitter.addListener('CallReceived', (event) => this.onCallReceive(event));

  // }
  // onCallReceive = (event) => {
  //   console.log("Call Received event ", event);
  // };
  static navigationOptions = {
    title: 'Login',
  };

  state = {
    email: 'nesonukuv@planet-travel.club',
    password: 'Test@123',
    url: 'oauth-cpaas.att.com',
    clientId: 'PUB-nesonukuv.34mv',
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
    if (this.state.clientId && this.state.password && this.state.email != "") {
      this.showLoader();
      //   var loginManager = NativeModules.login;

      LoginModule.initLogin(this.state.clientId, this.state.password, this.state.email, this.state.url
        , (status,message) => {
          console.log(message);
          this.hideLoader();
          this.props.navigation.navigate('DashBoard')
        });

    //     .then((response) => {
    //     // this.setState({ isLoading: false });
    //     console.log(response);
    //     this.hideLoader();// this.props.navigation.navigate('DashBoard')
    //     // this.setState({ initilized: true });
    //     // console.log("initilization response", response);
    //     // ToastAndroid.show('Successful initilization', ToastAndroid.SHORT);
    // })
    // .catch((err) l̥=> {

    //   this.hideLoader();
    //     alert('Login Failed. Please check the credentials.');
    //       // this.setState({ isLoading: false });
    //     // this.setState({ initilized: false });
    //     // console.log(9999, err.message)
    //     // ToastAndroid.show('Try Again', ToastAndroid.SHORT);
    // })
    
  
      //  DeviceEventEmitter.addListener('CallReceived', (event) => this.onCallReceive(event));
      //  KandyModule.initKandyService("oauth-cpaas.att.com", value.access_token, value.id_token)

      //  loginManager.loginInApp(this.state.clientId,this.state.password,
      //    this.state.email,this.state.url,(error, token)=>{
      //      if(token != null) {
      //        console.log(token);
      //      //  this.props.navigation.navigate('DashBoard')
      //        this.hideLoader();
      //      } else {  
      //        this.hideLoader();
      //        alert('Login Failed. Please check the credentials.');
      //      }      
      //  });
    } else {
      alert('Please fill all the details.');
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.Welcome_text}>
          Welcome
                  </Text>

        <Text style={styles.Login_text}>
          Login to continue
                  </Text>
                  <TextInput style={styles.input}
          placeholder="Host URL"
          placeholderTextColor="black"
          autoCapitalize="none"
          value={this.state.input}
          onChangeText={this.handleUrl}
        />
        <TextInput style={styles.input}
          placeholder="Client Id"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleClientId}
        />

     
        <TextInput style={styles.input}
          placeholder="Email"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleEmail}
        />
   <TextInput style={styles.input}
          placeholder="Password"
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