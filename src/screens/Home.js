import React, { Component } from 'react';
import { Platform, Alert, StyleSheet, Text, View, TextInput, Button, ScrollView } from 'react-native';
import { ToastAndroid } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import localStorage from '../utility/LocalStorage';
import KandyCpassLib from '../../KandyCpassLib';


export default class Home extends Component {

    static navigationOptions = {
        title: 'Home'
    }

    constructor(props) {
        super(props);
        this.state = { isLoading: false, initilized: false };
        this.initKandy = this.initKandy.bind(this);
    }

    componentWillMount() {

        this.onInitilised();

    }

    onInitilised = () => {

        localStorage.getTokenData().then((data) => {
            let response = JSON.parse(data);
            console.log("home local response", response);
            let accessToken = response.access_token;
            let idToken = response.id_token;

            localStorage.getBaseUrl().then((data) => {
                let baseUrl = data;
                this.initKandy(baseUrl, accessToken, idToken);
            });

        });

    }


    initKandy = (baseUrl, accessToken, idToken) => {
        console.log("baseUrl ", baseUrl);
        console.log("accessToken " + accessToken);
        console.log("idToken " + idToken);
        this.setState({ isLoading: true });
        KandyCpassLib.initKandyService(String(baseUrl), String(accessToken), String(idToken))
            .then((response) => {
                this.setState({ isLoading: false });
                this.setState({ initilized: true });
                console.log("initilization response", response);
                ToastAndroid.show('Successful initilization', ToastAndroid.SHORT);
            })
            .catch((err) => {
                this.setState({ isLoading: false });
                this.setState({ initilized: false });
                console.log(9999, err.message)
                ToastAndroid.show('Try Again', ToastAndroid.SHORT);
            })

    }

    onButtonPress = (value) => {
        console.log("Button value ", value);
        
        if (this.state.initilized) {

            if (value === 'sms') {
                this.props.navigation.navigate('Sms');
            } else if (value === 'chat') {
                this.props.navigation.navigate('Chat');
            } else if (value === 'addressBook') {
                this.props.navigation.navigate('AddressBook');
            } else if (value === 'presence') {
                this.props.navigation.navigate('Presence');
            }
            else {
                ToastAndroid.show('Coming Soon', ToastAndroid.SHORT);
            }
        }else{

            Alert.alert(
                'Alert',
                'Please subscribe',
                [
                  {
                    text: 'Cancel',
                    onPress: () => console.log('Cancel Pressed'),
                    style: 'cancel',
                  },
                  {text: 'OK', onPress: () => this.onInitilised()},
                ],
                {cancelable: false},
              );
        }
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
        return (
            <View>
                {this.renderProgressbar()}
                <View style={{ padding: 10 }}>
                    <Button
                        onPress={() => this.onButtonPress('sms')}
                        title="SMS"
                        color="#841584"
                        accessibilityLabel="Learn more about this purple button"
                    />
                </View>
                <View style={{ padding: 10 }}>
                    <Button
                        onPress={() => this.onButtonPress('chat')}
                        title="Chat"
                        color="#841584"
                        accessibilityLabel="Learn more about this purple button"
                    />
                </View>
                <View style={{ padding: 10 }}>
                    <Button
                        onPress={() => this.onButtonPress('addressBook')}
                        title="Addressbook"
                        color="#841584"
                        accessibilityLabel="Learn more about this purple button"
                    />
                </View>
                <View style={{ padding: 10 }}>
                    <Button
                        onPress={() => this.onButtonPress('presence')}
                        title="Presence"
                        color="#841584"
                        accessibilityLabel="Learn more about this purple button"
                    />
                </View>
                
            </View>
        );
    }

}

const styles = StyleSheet.create({
    spinnerTextStyle: {
        color: '#0000ff'
    }
});
