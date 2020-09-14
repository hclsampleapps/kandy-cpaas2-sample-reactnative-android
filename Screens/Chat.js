import React, { Component } from 'react';

import {
  StyleSheet,
  View,
  Text,
  StatusBar,
  NativeModules,
  Button,
  TextInput,
   ActivityIndicator,
  DeviceEventEmitter

} from 'react-native';

const { ChatModule } = NativeModules;

class Chat extends React.Component {
  static navigationOptions = {
    title: 'Chat',
  };

  state = {
    destinationId: '',
    messageText: '',
	showLoader: false
  }

  showLoader = () => { this.setState({ showLoader: true }); };
  hideLoader = () => { this.setState({ showLoader: false }); };

  constructor(props) {
    super(props);
    DeviceEventEmitter.addListener('ChatmessageReceived', (event) => this.onChatReceive(event));

  }
  onChatReceive = (event) => {
    alert('Chat Received Successfully!');
  };

  componentDidMount() {
    ChatModule.initChatModule((error, message) => {
      if (error == 'Success') {
        console.log(message);

      } else {
        console.log(message);

      }
    });
  }

  handleDestinationId = (text) => {
    this.state.destinationId = text;
  }

  handleMessageText = (text) => {
    this.state.messageText = text;
  }

  handleChat = () => {
    if (this.state.destinationId && this.state.messageText != "") {
      this.showLoader();
      //   var loginManager = NativeModules.login;

      ChatModule.sendChat(this.state.destinationId, this.state.messageText, (error, message) => {
        if (error == 'Success') {
          console.log(message);
		  this.hideLoader();
          alert(message);
        } else {
			this.hideLoader();
          console.log(message);
          alert(message);
        }
      });
    } else {
      alert('Please fill all the details.');
    }
    
  }

  render() {
    return (
      <View>
        <Text style={styles.Normal_text}>
          Destination Id
                  </Text>
        <TextInput style={styles.input}
          placeholder="Example abcdefg@domain.12ab.att.com"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleDestinationId}
        />

        <Text style={styles.Normal_text}>
          Enter Message
                  </Text>
        <TextInput style={styles.input}
          placeholder="Example Hi John"
          placeholderTextColor="black"
          autoCapitalize="none"
          onChangeText={this.handleMessageText}
        />
		 <View style={{ position: 'absolute', top: "50%", right: 0, left: 0 }}>
          <ActivityIndicator animating={this.state.showLoader} size="large" color="grey" />
        </View>


        <Button style={styles.buttonStyle}
          title="Send Chat"
          onPress={this.handleChat}
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

export default Chat; 