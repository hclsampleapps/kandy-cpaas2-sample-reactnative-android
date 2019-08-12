import React, { Component } from 'react';
import { Platform, Alert, StyleSheet, Text, View, TextInput, Button, ScrollView } from 'react-native';
import localStorage from '../utility/LocalStorage';
import KandyCpassLib from '../../KandyCpassLib';
import { ToastAndroid } from 'react-native';
import { DeviceEventEmitter } from 'react-native';
import { GiftedChat } from 'react-native-gifted-chat'



export default class SMS extends Component {

    static navigationOptions = {
        title: 'SMS',
    }

    constructor(props) {
        super(props);
        this.state = { destinationAddress: '+12028369036', onTextMessage: 'Hello', messages: [] };
        this.initSmsService = this.initSmsService.bind();
        this.onMessageSent = this.onMessageSent.bind();
        this.onMessageReceived = this.onMessageReceived.bind();
        DeviceEventEmitter.addListener('MessageReceived', (event) => this.onMessageReceived(event));
        DeviceEventEmitter.addListener('MessageSent', (event) => this.onMessageSent(event));

    }

    componentWillMount() {

        this.initSmsService();

    }

    initSmsService() {

        KandyCpassLib.initSMSService()
            .then((response) => {
                console.log("SMS response", response);
            })
            .catch((err) => {
                console.log("error", err)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })

    }

    sendMessage = (messages = []) => {
        console.log("messages ", JSON.stringify(messages));
        let participant = this.state.destinationAddress;
        let textMessage = messages[0].text;

        if (participant.trim() == '') {
            ToastAndroid.show('Please Enter destinationAddress', ToastAndroid.SHORT);
            return;
        }

        KandyCpassLib.sendSmSMessage(String(participant), String(textMessage))
            .then((response) => {
                console.log("Send Message Response " + response);
                ToastAndroid.show(response, ToastAndroid.SHORT);
                this.setState(prevState => ({
                    messages: GiftedChat.prepend(prevState.messages, messages)
                }));
            })
            .catch((err) => {
                console.log(9999, err)
                ToastAndroid.show(err, ToastAndroid.SHORT);
            })
    }


    onMessageSent = (event) => {
        console.log("Message Sent event ", event)
    };

    onMessageReceived = (event) => {
        console.log("Message Received event ", event);
        var receivedResponse = JSON.parse(event);
        console.log("Message Received ", receivedResponse.receivedMessage);
        
        //if(receivedResponse.senderAddress == this.state.destinationAddress){
            var messages = [
                {
                    _id: receivedResponse.receivedMessageId,
                    text: receivedResponse.receivedMessage,
                    createdAt: new Date(),
                    user: {
                        _id: 2,
                    },
                }
            ]
       // }
        

        this.setState(prevState => ({
            messages: GiftedChat.prepend(prevState.messages, messages)
        }));

    };

    onSend = (messages = []) => {
        console.log("messages ", messages);
    }

    render() {

        return (
            <View style={{ flex: 1 }}>
                <View style={{ flex: .2 }}>
                <TextInput placeholder='Enter Destination Address' defaultValue={this.state.destinationAddress} onChangeText={(value) => this.setState({ destinationAddress: value })} />
                </View>
                <View style={{ flex: .8 }}>
                <GiftedChat
                    inverted={false}
                    messages={this.state.messages}
                    onSend={messages => this.sendMessage(messages)}
                    user={{
                        _id: 1,
                    }}
                />
                </View>
                
            </View>
        );
    }

}