import React, { Component } from 'react';

import {
    StyleSheet,
    View, Text,
    NativeModules,
    Button,
	 ActivityIndicator,
    TextInput,
    DeviceEventEmitter
} from 'react-native';

const { SMSModule } = NativeModules;

class SMS extends React.Component {
	showLoader = () => { this.setState({ showLoader: true }); };
    hideLoader = () => { this.setState({ showLoader: false }); };
    static navigationOptions = {
        title: 'SMS',
    };

    state = {
        sourceNumber: '',
        destinationNumber: '',
        messageText: '',
		showLoader: false
    }

    constructor(props) {
        super(props);
        DeviceEventEmitter.addListener('SMSmessageReceived', (event) => this.onSMSReceive(event));
    }

    onSMSReceive = (event) => {
        alert('Message Received Successfully!');
    };

    componentDidMount() {
        SMSModule.initSMSModule((error, message) => {

            if (error == 'Success') {
                console.log(message);

            } else {
                console.log(message);

            }
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


        if (this.state.destinationNumber && this.state.sourceNumber && this.state.messageText != "") {
            this.showLoader();
         
            SMSModule.initSendMessage(this.state.destinationNumber, this.state.sourceNumber, this.state.messageText, (error, message) => {
                if (error == 'Success') {
                    console.log(message);
					this.hideLoader();
                    alert(message);
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
            <View>
                <Text style={styles.Normal_text}>
                    Source Number
                  </Text>
                <TextInput style={styles.input}
                    placeholder="Example. +16543219873"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleSourceNumber}
                />
                <Text style={styles.Normal_text}>
                    Destination Number
                  </Text>
                <TextInput style={styles.input}
                    placeholder="Example. +16543219874"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleDestinationNumber}
                />
                <Text style={styles.Normal_text}>
                    Enter Message
                  </Text>
                <TextInput style={styles.input}
                    placeholder="Example. Hi Tony"
                    placeholderTextColor="black"
                    autoCapitalize="none"
                    onChangeText={this.handleMessageText}
                />
				 <View style={{ position: 'absolute', top: "50%", right: 0, left: 0 }}>
					<ActivityIndicator animating={this.state.showLoader} size="large" color="grey" />
				</View>


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

export default SMS; 