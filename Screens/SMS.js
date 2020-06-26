import React, { Component } from 'react';

import {
    StyleSheet,
    View,
    NativeModules,
    Button,
    TextInput,
    DeviceEventEmitter
} from 'react-native';

const { SMSModule } = NativeModules;
// var smsManager = NativeModules.SMS;
// var smsEvents = new NativeEventEmitter(NativeModules.SMS)

class SMS extends React.Component {
    static navigationOptions = {
        title: 'SMS',
    };

    state = {
        sourceNumber: '+15205829010',
        destinationNumber: '+12015644538',
        messageText: 'Hi'
    }

      constructor(props) {
    super(props);
     DeviceEventEmitter.addListener('SMSmessageReceived', (event) => this.onSMSReceive(event));

  }
  onSMSReceive = (event) => {
    alert('Message Received Successfully!');
};

    // smsEvents = smsEvents.addListener(
    //     "messageReceived",
    //     res => {
    //         if (res != null) {
    //             alert('Message Received Successfully!');
    //         }
    //     }
    // )

    componentDidMount() {
        SMSModule.initSMSModule((error, message) => {
           
            if (error =='Success') {
                console.log(message);
               
            } else {
                console.log(message);
             
            }
             //if (error == null) {
         //       console.log("SMS module initialize");
            // }
        });
    }

    handleSourceNumber = (text) => {
        this.state.sourceNumber = text;
    }

    handleDestinationNumber = (text) => {
        this.state.destinationNumber = text;
    }

    handleMessageText = (text) => {
        this.state.messageText = text;
    }

    handleSMS = () => {
        SMSModule.initSendMessage(this.state.destinationNumber, this.state.sourceNumber, this.state.messageText, (error, message) => {
            if (error =='Success') {
                console.log(message);
                alert(message);
            } else {
                console.log(message);
                alert(message);
            }
        });
    }

    render() {
        return (
            <View>

                <TextInput style={styles.input}
                    placeholder="Source number"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleSourceNumber}
                />

                <TextInput style={styles.input}
                    placeholder="Destination number"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleDestinationNumber}
                />

                <TextInput style={styles.input}
                    placeholder="Enter the text"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleMessageText}
                />

                <Button style={styles.buttonStyle}
                    title="Send SMS"
                    onPress={this.handleSMS}
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

export default SMS; 